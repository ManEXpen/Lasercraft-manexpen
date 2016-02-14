package laser.block.machine;

import cofh.api.energy.EnergyStorage;
import cofh.api.tileentity.IEnergyInfo;
import cofh.core.network.PacketCoFHBase;
import cofh.core.render.IconRegistry;
import cofh.lib.util.TimeTracker;
import cofh.lib.util.helpers.MathHelper;
import cofh.lib.util.helpers.ServerHelper;
import cofh.thermalexpansion.block.TileReconfigurable;
import cofh.thermalexpansion.block.TileTEBase;
import cofh.thermalexpansion.core.TEProps;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;

public abstract class TileMachineBase extends TileReconfigurable implements IEnergyInfo, ISidedInventory {

   protected static final TileTEBase.SideConfig[] defaultSideConfig = new TileTEBase.SideConfig[BlockMachine.TYPES.length];
   protected static final TileTEBase.EnergyConfig[] defaultEnergyConfig = new TileTEBase.EnergyConfig[BlockMachine.TYPES.length];
   protected static final String[] sounds = new String[BlockMachine.TYPES.length];
   protected static final boolean[] enableSound = new boolean[]{true, false, false};
   protected static final int[] lightValue = new int[]{14, 0, 0};
   int processMax;
   int processRem;
   boolean wasActive;
   protected TileTEBase.SideConfig sideConfig;
   protected TileTEBase.EnergyConfig energyConfig;
   protected TimeTracker tracker = new TimeTracker();


   public TileMachineBase() {
      this.sideConfig = defaultSideConfig[this.getType()];
      this.energyConfig = defaultEnergyConfig[this.getType()].copy();
      this.energyStorage = new EnergyStorage(this.energyConfig.maxEnergy, this.energyConfig.maxPower);
      this.setDefaultSides();
   }

   public byte[] getDefaultSides() {
      return (byte[])((byte[])this.sideConfig.defaultSides.clone());
   }

   public String getName() {
      return "tile.laser.machine." + BlockMachine.TYPES[this.getType()] + ".name";
   }

   public int getLightValue() {
      return this.isActive?lightValue[this.getType()]:0;
   }

   public void updateEntity() {
      if(!ServerHelper.isClientWorld(this.worldObj)) {
         boolean bool = this.isActive;
         int i;
         if(this.isActive) {
            if(this.processRem > 0) {
               i = this.calcEnergy();
               this.energyStorage.modifyEnergyStored(-i);
               this.processRem -= i;
            }

            if(this.canFinish()) {
               this.processFinish();
               this.transferProducts();
               this.energyStorage.modifyEnergyStored(-this.processRem);
               if(this.redstoneControlOrDisable() && this.canStart()) {
                  this.processStart();
               } else {
                  this.isActive = false;
                  this.wasActive = true;
                  this.tracker.markTime(this.worldObj);
               }
            }
         } else if(this.redstoneControlOrDisable()) {
            if(this.timeCheck()) {
               this.transferProducts();
            }

            if(this.timeCheckEighth() && this.canStart()) {
               this.processStart();
               i = this.calcEnergy();
               this.energyStorage.modifyEnergyStored(-i);
               this.processRem -= i;
               this.isActive = true;
            }
         }

         this.updateIfChanged(bool);
         this.chargeEnergy();
      }
   }

   protected int calcEnergy() {
      return !this.isActive?0:(this.energyStorage.getEnergyStored() > this.energyConfig.maxPowerLevel?this.energyConfig.maxPower:(this.energyStorage.getEnergyStored() < this.energyConfig.minPowerLevel?this.energyConfig.minPower:this.energyStorage.getEnergyStored() / this.energyConfig.energyRamp));
   }

   protected int getMaxInputSlot() {
      return 0;
   }

   protected boolean canStart() {
      return false;
   }

   protected boolean canFinish() {
      return this.processRem > 0?false:this.hasValidInput();
   }

   protected boolean hasValidInput() {
      return true;
   }

   protected void processStart() {}

   protected void processFinish() {}

   protected void transferProducts() {}

   protected void updateIfChanged(boolean paramBoolean) {
      if(paramBoolean != this.isActive && this.isActive) {
         this.sendUpdatePacket(Side.CLIENT);
      } else if(this.tracker.hasDelayPassed(this.worldObj, 200) && this.wasActive) {
         this.wasActive = false;
         this.sendUpdatePacket(Side.CLIENT);
      }

   }

   public int getScaledProgress(int paramInt) {
      return this.isActive && this.processMax > 0 && this.processRem > 0?paramInt * (this.processMax - this.processRem) / this.processMax:0;
   }

   public int getScaledSpeed(int paramInt) {
      if(!this.isActive) {
         return 0;
      } else {
         double d = (double)(this.energyStorage.getEnergyStored() / this.energyConfig.energyRamp);
         d = MathHelper.clip(d, (double)this.energyConfig.minPower, (double)this.energyConfig.maxPower);
         return MathHelper.round((double)paramInt * d / (double)this.energyConfig.maxPower);
      }
   }

   public void readFromNBT(NBTTagCompound paramNBTTagCompound) {
      super.func_145839_a(paramNBTTagCompound);
      this.processMax = paramNBTTagCompound.getInteger("ProcMax");
      this.processRem = paramNBTTagCompound.getInteger("ProcRem");
   }

   public void writeToNBT(NBTTagCompound paramNBTTagCompound) {
      super.func_145841_b(paramNBTTagCompound);
      paramNBTTagCompound.setInteger("ProcMax", this.processMax);
      paramNBTTagCompound.setInteger("ProcRem", this.processRem);
   }

   public PacketCoFHBase getPacket() {
      PacketCoFHBase localPacketCoFHBase = super.getPacket();
      return localPacketCoFHBase;
   }

   public PacketCoFHBase getGuiPacket() {
      PacketCoFHBase localPacketCoFHBase = super.getGuiPacket();
      localPacketCoFHBase.addBool(this.isActive);
      localPacketCoFHBase.addInt(this.energyStorage.getMaxEnergyStored());
      localPacketCoFHBase.addInt(this.energyStorage.getEnergyStored());
      localPacketCoFHBase.addInt(this.processMax);
      localPacketCoFHBase.addInt(this.processRem);
      return localPacketCoFHBase;
   }

   protected void handleGuiPacket(PacketCoFHBase paramPacketCoFHBase) {
      this.isActive = paramPacketCoFHBase.getBool();
      this.energyStorage.setCapacity(paramPacketCoFHBase.getInt());
      this.energyStorage.setEnergyStored(paramPacketCoFHBase.getInt());
      this.processMax = paramPacketCoFHBase.getInt();
      this.processRem = paramPacketCoFHBase.getInt();
   }

   public void handleTilePacket(PacketCoFHBase paramPacketCoFHBase, boolean paramBoolean) {
      super.handleTilePacket(paramPacketCoFHBase, paramBoolean);
   }

   public ItemStack decrStackSize(int paramInt1, int paramInt2) {
      ItemStack localItemStack = super.func_70298_a(paramInt1, paramInt2);
      if(ServerHelper.isServerWorld(this.worldObj) && paramInt1 <= this.getMaxInputSlot() && this.isActive && (this.inventory[paramInt1] == null || !this.hasValidInput())) {
         this.isActive = false;
         this.wasActive = true;
         this.tracker.markTime(this.worldObj);
         this.processRem = 0;
      }

      return localItemStack;
   }

   public void setInventorySlotContents(int paramInt, ItemStack paramItemStack) {
      if(ServerHelper.isServerWorld(this.worldObj) && paramInt <= this.getMaxInputSlot() && this.isActive && this.inventory[paramInt] != null && (paramItemStack == null || !paramItemStack.isItemEqual(this.inventory[paramInt]) || !this.hasValidInput())) {
         this.isActive = false;
         this.wasActive = true;
         this.tracker.markTime(this.worldObj);
         this.processRem = 0;
      }

      super.func_70299_a(paramInt, paramItemStack);
   }

   public void onInventoryChanged() {
      if(this.isActive && !this.hasValidInput()) {
         this.isActive = false;
         this.wasActive = true;
         this.tracker.markTime(this.worldObj);
         this.processRem = 0;
      }

      super.markDirty();//とりあえず・・・
   }

   public int getInfoEnergyPerTick() {
      return this.calcEnergy();
   }

   public int getInfoMaxEnergyPerTick() {
      return this.energyConfig.maxPower;
   }

   public int getInfoEnergyStored() {
      return this.energyStorage.getEnergyStored();
   }

   public int getInfoMaxEnergyStored() {
      return this.energyStorage.getMaxEnergyStored();
   }

   public boolean setFacing(int paramInt) {
      if(paramInt >= 0 && paramInt <= 5) {
         if(!this.allowYAxisFacing() && paramInt < 2) {
            return false;
         } else {
            this.sideCache[paramInt] = 0;
            this.facing = (byte)paramInt;
            this.onInventoryChanged();
            this.sendUpdatePacket(Side.CLIENT);
            return true;
         }
      } else {
         return false;
      }
   }

   public IIcon getTexture(int paramInt1, int paramInt2) {
      return paramInt2 == 0?(paramInt1 == 0?IconRegistry.getIcon("LCMachineBottom"):(paramInt1 == 1?IconRegistry.getIcon("LCMachineTop"):(paramInt1 != this.facing?IconRegistry.getIcon("LCMachineSide"):(this.isActive?IconRegistry.getIcon("LCMachineActive", this.getType()):IconRegistry.getIcon("LCMachineFace", this.getType()))))):(paramInt1 < 6?IconRegistry.getIcon(TEProps.textureSelection, this.sideConfig.sideTex[this.sideCache[paramInt1]]):IconRegistry.getIcon("LCMachineSide"));
   }

   public String getSoundName() {
      return enableSound[this.getType()]?sounds[this.getType()]:null;
   }

   public int getNumConfig(int paramInt) {
      return this.sideConfig.numConfig;
   }

   public boolean isItemValid(ItemStack paramItemStack, int paramInt1, int paramInt2) {
      return true;
   }

   public int[] getAccessibleSlotsFromSide(int paramInt) {
      return this.sideConfig.slotGroups[this.sideCache[paramInt]];
   }

   public boolean canInsertItem(int paramInt1, ItemStack paramItemStack, int paramInt2) {
      return this.sideConfig.allowInsertionSlot[this.sideCache[paramInt2]]?this.isItemValid(paramItemStack, paramInt1, paramInt2):false;
   }

   public boolean canExtractItem(int paramInt1, ItemStack paramItemStack, int paramInt2) {
      return this.sideConfig.allowInsertionSlot[this.sideCache[paramInt2]];
   }

}
