package laser.block.generator;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyHandler;
import cofh.api.energy.IEnergyStorage;
import cofh.api.tileentity.IEnergyInfo;
import cofh.api.tileentity.ISecurable;
import cofh.core.CoFHProps;
import cofh.core.network.ITileInfoPacketHandler;
import cofh.core.network.PacketCoFHBase;
import cofh.core.util.CoreUtils;
import cofh.lib.util.TimeTracker;
import cofh.lib.util.helpers.BlockHelper;
import cofh.lib.util.helpers.EnergyHelper;
import cofh.lib.util.helpers.RedstoneControlHelper;
import cofh.lib.util.helpers.ServerHelper;
import cofh.thermalexpansion.block.TileRSControl;
import cofh.thermalexpansion.block.TileTEBase;
import cpw.mods.fml.relauncher.Side;
import laser.util.OldCofhCompatibility.OldCofhCompatibility;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class TileGeneratorBase extends TileRSControl implements ITileInfoPacketHandler, IEnergyInfo {

   protected static final TileTEBase.EnergyConfig[] defaultEnergyConfig = new TileTEBase.EnergyConfig[BlockGenerator.TYPES.length];
   protected static final int MAX_FLUID = 4000;
   protected static final int[] SLOTS = new int[]{0};
   public static final int FUEL_MOD = 1000;
   int compareTracker;
   byte outputTracker;
   int fuelRF;
   boolean wasActive;
   boolean cached = false;
   IEnergyHandler[] adjacentHandlers = new IEnergyHandler[6];
   protected TileTEBase.EnergyConfig config;
   protected TimeTracker tracker = new TimeTracker();
   protected EnergyStorage energyStorage = new EnergyStorage(0);


   public TileGeneratorBase() {
      this.config = defaultEnergyConfig[this.getType()];
		this.energyStorage = new EnergyStorage(this.config.maxEnergy, this.config.maxPower * 2);
   }

   public String getName() {
      return "tile.laser.generator." + BlockGenerator.TYPES[this.getType()] + ".name";
   }

   public int getComparatorInput(int paramInt) {
      return this.compareTracker;
   }

   public boolean enableSecurity() {
      return false;
   }

   public boolean onWrench(EntityPlayer paramEntityPlayer, int paramInt) {
      return false;
   }

   public void onNeighborBlockChange() {
      super.onNeighborBlockChange();
      this.updateAdjacentHandlers();
   }

   public void onNeighborTileChange(int paramInt1, int paramInt2, int paramInt3) {
      super.onNeighborTileChange(paramInt1, paramInt2, paramInt3);
      this.updateAdjacentHandler(paramInt1, paramInt2, paramInt3);
   }

   public void updateEntity() {
      if(!ServerHelper.isClientWorld(this.worldObj)) {
         if(!this.cached) {
            this.onNeighborBlockChange();
         }

         boolean bool = this.isActive;
         int i;
         if(this.isActive) {
            if(this.redstoneControlOrDisable() && this.canGenerate()) {
               this.generate();

               for(i = this.outputTracker; i < 6 && this.hasStoredEnergy(); ++i) {
                  this.transferEnergy(i);
               }

               for(i = 0; i < this.outputTracker && this.hasStoredEnergy(); ++i) {
                  this.transferEnergy(i);
               }

               ++this.outputTracker;
               this.outputTracker = (byte)(this.outputTracker % 6);
            } else {
               this.isActive = false;
               this.wasActive = true;
               this.tracker.markTime(this.worldObj);
            }
         } else if(this.redstoneControlOrDisable() && this.canGenerate()) {
            this.isActive = true;
            this.generate();

            for(i = this.outputTracker; i < 6 && this.hasStoredEnergy(); ++i) {
               this.transferEnergy(i);
            }

            for(i = 0; i < this.outputTracker && this.hasStoredEnergy(); ++i) {
               this.transferEnergy(i);
            }

            ++this.outputTracker;
            this.outputTracker = (byte)(this.outputTracker % 6);
         } else {
            this.attenuate();
         }

         if(this.timeCheck()) {
            i = this.getScaledEnergyStored(15);
            if(this.compareTracker != i) {
               this.compareTracker = i;
               this.callNeighborTileChange();
            }
         }

         this.updateIfChanged(bool);
      }
   }

   protected void updateIfChanged(boolean paramBoolean) {
      if(paramBoolean != this.isActive && this.isActive) {
         this.sendUpdatePacket(Side.CLIENT);
      } else if(this.tracker.hasDelayPassed(this.worldObj, 100) && this.wasActive) {
         this.wasActive = false;
         this.sendUpdatePacket(Side.CLIENT);
      }

   }

   public void invalidate() {
      this.cached = false;
      super.invalidate();
   }

   protected abstract boolean canGenerate();

   protected abstract void generate();

   protected int calcEnergy() {
      return !this.isActive?0:(this.energyStorage.getEnergyStored() < this.config.minPowerLevel?this.config.maxPower:(this.energyStorage.getEnergyStored() > this.config.maxPowerLevel?this.config.minPower:(this.energyStorage.getMaxEnergyStored() - this.energyStorage.getEnergyStored()) / this.config.energyRamp));
   }

   protected int calcEnergy2() {
      return this.isActive && this.energyStorage.getEnergyStored() != this.energyStorage.getMaxEnergyStored()?(this.energyStorage.getEnergyStored() < this.config.maxPowerLevel?this.config.maxPower:(this.energyStorage.getEnergyStored() > this.config.minPowerLevel?this.config.minPower:(this.energyStorage.getMaxEnergyStored() - this.energyStorage.getEnergyStored()) / this.config.energyRamp)):0;
   }

   protected boolean hasStoredEnergy() {
      return this.energyStorage.getEnergyStored() > 0;
   }

   public int getScaledEnergyStored(int paramInt) {
		return this.energyStorage.getEnergyStored() * paramInt / this.energyStorage.getMaxEnergyStored();
	}

   protected void attenuate() {
      if(this.timeCheck() && this.fuelRF > 0) {
         this.fuelRF -= 10;
         if(this.fuelRF < 0) {
            this.fuelRF = 0;
         }
      }

   }

   protected void transferEnergy(int paramInt) {
      if(this.adjacentHandlers[paramInt] != null) {
         this.energyStorage.modifyEnergyStored(-this.adjacentHandlers[paramInt].receiveEnergy(ForgeDirection.VALID_DIRECTIONS[paramInt ^ 1], Math.min(this.energyStorage.getMaxExtract(), this.energyStorage.getEnergyStored()), false));
      }
   }

   protected void updateAdjacentHandlers() {
      if(!ServerHelper.isClientWorld(this.worldObj)) {
         for(int i = 0; i < 6; ++i) {
            TileEntity localTileEntity = BlockHelper.getAdjacentTileEntity(this, i);
            if(EnergyHelper.isEnergyHandlerFromSide(localTileEntity, ForgeDirection.VALID_DIRECTIONS[i ^ 1])) {
               this.adjacentHandlers[i] = (IEnergyHandler)localTileEntity;
            } else {
               this.adjacentHandlers[i] = null;
            }
         }

         this.cached = true;
      }
   }

   protected void updateAdjacentHandler(int paramInt1, int paramInt2, int paramInt3) {
      if(!ServerHelper.isClientWorld(this.worldObj)) {
         int i = BlockHelper.determineAdjacentSide(this, paramInt1, paramInt2, paramInt3);
         TileEntity localTileEntity = this.worldObj.getTileEntity(paramInt1, paramInt2, paramInt3);
         if(EnergyHelper.isEnergyHandlerFromSide(localTileEntity, ForgeDirection.VALID_DIRECTIONS[i ^ 1])) {
            this.adjacentHandlers[i] = (IEnergyHandler)localTileEntity;
         } else {
            this.adjacentHandlers[i] = null;
         }

      }
   }

   public int getScaledDuration(int paramInt) {
      return 0;
   }

   public void readFromNBT(NBTTagCompound paramNBTTagCompound) {
      super.readFromNBT(paramNBTTagCompound);
      this.energyStorage.readFromNBT(paramNBTTagCompound);
      this.isActive = paramNBTTagCompound.getBoolean("Active");
      this.fuelRF = paramNBTTagCompound.getInteger("Fuel");
      this.outputTracker = paramNBTTagCompound.getByte("Tracker");
   }

   public void writeToNBT(NBTTagCompound paramNBTTagCompound) {
      super.writeToNBT(paramNBTTagCompound);
      paramNBTTagCompound.setBoolean("Active", this.isActive);
      paramNBTTagCompound.setInteger("Fuel", this.fuelRF);
      paramNBTTagCompound.setByte("Tracker", this.outputTracker);
   }

   public PacketCoFHBase getGuiPacket() {
      PacketCoFHBase localPacketCoFHBase = super.getGuiPacket();
      localPacketCoFHBase.addInt(this.energyStorage.getMaxEnergyStored());
      localPacketCoFHBase.addInt(this.energyStorage.getEnergyStored());
      localPacketCoFHBase.addInt(this.fuelRF);
      return localPacketCoFHBase;
   }

   protected void handleGuiPacket(PacketCoFHBase paramPacketCoFHBase) {
      super.handleGuiPacket(paramPacketCoFHBase);
      this.energyStorage.setCapacity(paramPacketCoFHBase.getInt());
      this.energyStorage.setEnergyStored(paramPacketCoFHBase.getInt());
      this.fuelRF = paramPacketCoFHBase.getInt();
   }

   public int receiveEnergy(ForgeDirection paramForgeDirection, int paramInt, boolean paramBoolean) {
      return 0;
   }

   public int extractEnergy(ForgeDirection paramForgeDirection, int paramInt, boolean paramBoolean) {
      return 0;
   }

   public int getEnergyStored(ForgeDirection paramForgeDirection) {
      return this.energyStorage.getEnergyStored();
   }

   public int getMaxEnergyStored(ForgeDirection paramForgeDirection) {
      return this.energyStorage.getMaxEnergyStored();
   }

   public boolean canConnectEnergy(ForgeDirection paramForgeDirection) {
      return paramForgeDirection != ForgeDirection.UNKNOWN;
   }

   public int getInfoEnergyPerTick() {
      return this.calcEnergy();
   }

   public int getInfoMaxEnergyPerTick() {
      return this.config.maxPower;
   }

   public int getInfoEnergyStored() {
      return this.energyStorage.getEnergyStored();
   }

   public int getInfoMaxEnergyStored() {
      return this.config.maxEnergy;
   }

   public String getDataType() {
      return "tile.ghastly.generator";
   }

   public void readPortableData(EntityPlayer paramEntityPlayer, NBTTagCompound paramNBTTagCompound) {
      if(this.canPlayerAccess(paramEntityPlayer.getCommandSenderName())) {
         RedstoneControlHelper.getControlFromNBT(paramNBTTagCompound);
      }
   }

   public void writePortableData(EntityPlayer paramEntityPlayer, NBTTagCompound paramNBTTagCompound) {
      if(this.canPlayerAccess(paramEntityPlayer.getCommandSenderName())) {
         RedstoneControlHelper.setItemStackTagRS(paramNBTTagCompound, this);
      }
   }

   public boolean canPlayerAccess(String paramString) {
		if (!(this instanceof ISecurable)) {
			return true;
		}
		ISecurable.AccessMode localAccessMode = ((ISecurable) this).getAccess();
		String str = ((ISecurable) this).getOwnerName();

		return (localAccessMode.isPublic()) || ((CoFHProps.enableOpSecureAccess) && (CoreUtils.isOp(paramString)))
				|| (str.equals("[None]")) || (str.equals(paramString))
				|| ((localAccessMode.isRestricted()) && (OldCofhCompatibility.playerHasAccess(paramString, str)));
	}

   public IEnergyStorage getEnergyStorage() {
		return this.energyStorage;
	}



}
