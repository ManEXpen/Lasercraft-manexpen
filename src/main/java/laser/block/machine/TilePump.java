package laser.block.machine;

import java.util.TreeMap;

import cofh.core.network.PacketCoFHBase;
import cofh.core.util.fluid.FluidTankAdv;
import cofh.lib.util.helpers.FluidHelper;
import cofh.lib.util.helpers.ServerHelper;
import cofh.thermalexpansion.block.TileTEBase;
import laser.gui.client.machine.GuiPump;
import laser.gui.container.machine.ContainerPump;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class TilePump extends TileMachineBase implements IFluidHandler {

   static final int TYPE = 2;
   int outputTrackerFluid;
   FluidTankAdv tank = new FluidTankAdv(10000);
   FluidStack outputBuffer;
   FluidStack renderFluid;
   public boolean pump;
   public int aimY;
   TreeMap pumpLayerQueues;
   int numFluidBlocksFound;


   public static void initialize() {
      defaultSideConfig[2] = new TileTEBase.SideConfig();
      defaultSideConfig[2].numConfig = 2;
      defaultSideConfig[2].slotGroups = new int[][]{new int[0], new int[0]};
      defaultSideConfig[2].allowInsertionSlot = new boolean[]{false, false};
      defaultSideConfig[2].allowExtractionSlot = new boolean[]{false, true};
      defaultSideConfig[2].sideTex = new int[]{0, 4};
      defaultSideConfig[2].defaultSides = new byte[]{(byte)1, (byte)1, (byte)1, (byte)1, (byte)1, (byte)1};
      defaultEnergyConfig[2] = new TileTEBase.EnergyConfig();
      defaultEnergyConfig[2].setParams(16, 256, 200000);
   }

   public TilePump() {
      this.renderFluid = new FluidStack(FluidRegistry.LAVA, 0);
      this.aimY = -1;
      this.pumpLayerQueues = new TreeMap();
      this.numFluidBlocksFound = 0;
      this.inventory = new ItemStack[1];
   }

   public int getType() {
      return 2;
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
               this.energyStorage.modifyEnergyStored(-this.processRem);
               if(this.redstoneControlOrDisable() && this.canStart()) {
                  this.processStart();
               } else {
                  this.isActive = false;
                  this.wasActive = true;
                  this.tracker.markTime(this.worldObj);
               }
            }
         } else if(this.redstoneControlOrDisable() && this.timeCheckEighth() && this.canStart()) {
            this.processStart();
            i = this.calcEnergy();
            this.energyStorage.modifyEnergyStored(-i);
            this.processRem -= i;
            this.isActive = true;
         }

         if(!this.isActive) {
            this.pumpLayerQueues.clear();
         }

         this.transferFluid();
         this.updateIfChanged(bool);
         this.chargeEnergy();
      }
   }

   protected boolean canStart() {
      Block block = this.worldObj.getBlock(this.xCoord, this.aimY, this.zCoord);
      int meta = this.worldObj.getBlockMetadata(this.xCoord, this.aimY, this.zCoord);
      FluidStack localFluidStack = this.getFluidStackFromBlock(block, meta, true);
      if(localFluidStack != null) {
         this.pump = true;
         return this.aimY >= 0 && this.aimY < this.yCoord && this.tank.fill(localFluidStack, false) == localFluidStack.amount && this.energyStorage.getEnergyStored() >= this.getRequireEnergy();
      } else {
         this.pump = false;
         return this.aimY >= 0 && this.aimY < this.yCoord && block.getBlockHardness(this.worldObj, this.xCoord, this.aimY, this.zCoord) >= 0.0F && this.energyStorage.getEnergyStored() >= this.getRequireEnergy();
      }
   }

   private int getRequireEnergy() {
      return this.getEnergyForBlock(this.worldObj.getBlock(this.xCoord, this.aimY, this.zCoord), this.xCoord, this.aimY, this.zCoord);
   }

   private int getEnergyForBlock(Block block, int x, int y, int z) {
      return !block.isAir(this.worldObj, x, y, z) && block.getBlockHardness(this.worldObj, x, y, z) >= 0.0F?(FluidRegistry.lookupFluidForBlock(block) != null?Math.abs(FluidRegistry.lookupFluidForBlock(block).getDensity()):(int)Math.ceil((2.0D + (double)block.getBlockHardness(this.worldObj, x, y, z)) * 200.0D)):0;
   }

   private FluidStack getFluidStackFromBlock(Block block, int meta, boolean flag) {
      int amount = flag?1000:0;
      return FluidRegistry.lookupFluidForBlock(block) != null && meta == 0?new FluidStack(FluidRegistry.lookupFluidForBlock(block), amount):null;
   }

   private void fillFluidFromBlock(Block block, int meta) {
      if(FluidRegistry.lookupFluidForBlock(block) != null && meta == 0) {
         this.tank.fill(this.getFluidStackFromBlock(block, meta, true), true);
      }

   }

   protected void processStart() {
      this.processMax = this.getRequireEnergy();
      this.processRem = this.processMax;
      Block block = this.worldObj.getBlock(this.xCoord, this.aimY, this.zCoord);
      int meta = this.worldObj.getBlockMetadata(this.xCoord, this.aimY, this.zCoord);
      if(this.getFluidStackFromBlock(block, meta, false) != null) {
         int i = this.renderFluid.getFluidID();
         this.renderFluid = this.getFluidStackFromBlock(block, meta, false);
         if(i != this.renderFluid.getFluidID()) {
            this.sendFluidPacket();
         }
      }

   }

   protected void processFinish() {
      Block block = this.worldObj.getBlock(this.xCoord, this.aimY, this.zCoord);
      int meta = this.worldObj.getBlockMetadata(this.xCoord, this.aimY, this.zCoord);
      FluidStack localFluidStack = this.getFluidStackFromBlock(block, meta, true);
      this.pump = localFluidStack != null;
      if(!this.pump) {
         if(block.getBlockHardness(this.worldObj, this.xCoord, this.aimY, this.zCoord) >= 0.0F) {
            this.worldObj.func_147480_a(this.xCoord, this.aimY, this.zCoord, false);
            --this.aimY;
         }
      } else {
         this.fillFluidFromBlock(block, meta);
      }

   }

   protected void transferFluid() {
      if(this.tank.getFluidAmount() > 0) {
         this.outputBuffer = new FluidStack(this.tank.getFluid(), Math.min(this.tank.getFluidAmount(), 500));

         for(int j = this.outputTrackerFluid + 1; j <= this.outputTrackerFluid + 6; ++j) {
            int i = j % 6;
            if(this.sideCache[i] == 1) {
               int k = FluidHelper.insertFluidIntoAdjacentFluidHandler(this, i, this.outputBuffer, true);
               if(k > 0) {
                  this.tank.drain(k, true);
                  this.outputTrackerFluid = i;
                  break;
               }
            }
         }

      }
   }

   public boolean isItemValid(ItemStack paramItemStack, int paramInt1, int paramInt2) {
      return false;
   }

   public Object getGuiClient(InventoryPlayer paramInventoryPlayer) {
      return new GuiPump(paramInventoryPlayer, this);
   }

   public Object getGuiServer(InventoryPlayer paramInventoryPlayer) {
      return new ContainerPump(paramInventoryPlayer, this);
   }

   public FluidTankAdv getTank() {
      return this.tank;
   }

   public FluidStack getTankFluid() {
      return this.tank.getFluid();
   }

   public void readFromNBT(NBTTagCompound paramNBTTagCompound) {
      super.func_145839_a(paramNBTTagCompound);
      this.outputTrackerFluid = paramNBTTagCompound.getInteger("Tracker");
      this.aimY = paramNBTTagCompound.getInteger("ProcessingY");
      this.pump = paramNBTTagCompound.getBoolean("Pump");
      this.tank.readFromNBT(paramNBTTagCompound);
      Block block = this.worldObj.getBlock(this.xCoord, this.aimY, this.zCoord);
      int meta = this.worldObj.getBlockMetadata(this.xCoord, this.aimY, this.zCoord);
      if(this.tank.getFluid() != null) {
         this.renderFluid = this.tank.getFluid();
      } else if(this.getFluidStackFromBlock(block, meta, false) != null) {
         this.renderFluid = this.getFluidStackFromBlock(block, meta, false);
      }

   }

   public void writeToNBT(NBTTagCompound paramNBTTagCompound) {
      super.func_145841_b(paramNBTTagCompound);
      paramNBTTagCompound.setInteger("Tracker", this.outputTrackerFluid);
      paramNBTTagCompound.setInteger("ProcessingY", this.aimY);
      paramNBTTagCompound.setBoolean("Pump", this.pump);
      this.tank.writeToNBT(paramNBTTagCompound);
   }

   public PacketCoFHBase getPacket() {
      PacketCoFHBase localPacketCoFHBase = super.getPacket();
      localPacketCoFHBase.addFluidStack(this.renderFluid);
      return localPacketCoFHBase;
   }

   public PacketCoFHBase getGuiPacket() {
      PacketCoFHBase localPacketCoFHBase = super.getGuiPacket();
      localPacketCoFHBase.addBool(this.pump);
      localPacketCoFHBase.addInt(this.aimY);
      if(this.tank.getFluid() == null) {
         localPacketCoFHBase.addFluidStack(this.renderFluid);
      } else {
         localPacketCoFHBase.addFluidStack(this.tank.getFluid());
      }

      return localPacketCoFHBase;
   }

   public PacketCoFHBase getFluidPacket() {
      PacketCoFHBase localPacketCoFHBase = super.getFluidPacket();
      localPacketCoFHBase.addFluidStack(this.renderFluid);
      return localPacketCoFHBase;
   }

   protected void handleGuiPacket(PacketCoFHBase paramPacketCoFHBase) {
      super.handleGuiPacket(paramPacketCoFHBase);
      this.pump = paramPacketCoFHBase.getBool();
      this.aimY = paramPacketCoFHBase.getInt();
      this.tank.setFluid(paramPacketCoFHBase.getFluidStack());
   }

   protected void handleFluidPacket(PacketCoFHBase paramPacketCoFHBase) {
      super.handleFluidPacket(paramPacketCoFHBase);
      this.renderFluid = paramPacketCoFHBase.getFluidStack();
      this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
   }

   public void handleTilePacket(PacketCoFHBase paramPacketCoFHBase, boolean paramBoolean) {
      super.handleTilePacket(paramPacketCoFHBase, paramBoolean);
      if(!paramBoolean) {
         this.renderFluid = paramPacketCoFHBase.getFluidStack();
      } else {
         paramPacketCoFHBase.getFluidStack();
      }

   }

   public int fill(ForgeDirection paramForgeDirection, FluidStack paramFluidStack, boolean paramBoolean) {
      return 0;
   }

   public FluidStack drain(ForgeDirection paramForgeDirection, FluidStack paramFluidStack, boolean paramBoolean) {
      return paramForgeDirection != ForgeDirection.UNKNOWN && this.sideCache[paramForgeDirection.ordinal()] == 2?(paramFluidStack != null && paramFluidStack.isFluidEqual(this.tank.getFluid())?this.tank.drain(paramFluidStack.amount, paramBoolean):null):null;
   }

   public FluidStack drain(ForgeDirection paramForgeDirection, int paramInt, boolean paramBoolean) {
      return paramForgeDirection != ForgeDirection.UNKNOWN && this.sideCache[paramForgeDirection.ordinal()] != 2?null:this.tank.drain(paramInt, paramBoolean);
   }

   public boolean canFill(ForgeDirection paramForgeDirection, Fluid paramFluid) {
      return false;
   }

   public boolean canDrain(ForgeDirection paramForgeDirection, Fluid paramFluid) {
      return true;
   }

   public FluidTankInfo[] getTankInfo(ForgeDirection paramForgeDirection) {
      return new FluidTankInfo[]{this.tank.getInfo()};
   }

@Override
public int getSizeInventory() {
	// TODO 自動生成されたメソッド・スタブ
	return 0;
}

@Override
public ItemStack getStackInSlot(int p_70301_1_) {
	// TODO 自動生成されたメソッド・スタブ
	return null;
}

@Override
public ItemStack getStackInSlotOnClosing(int p_70304_1_) {
	// TODO 自動生成されたメソッド・スタブ
	return null;
}

@Override
public String getInventoryName() {
	// TODO 自動生成されたメソッド・スタブ
	return null;
}

@Override
public boolean hasCustomInventoryName() {
	// TODO 自動生成されたメソッド・スタブ
	return false;
}

@Override
public int getInventoryStackLimit() {
	// TODO 自動生成されたメソッド・スタブ
	return 0;
}

@Override
public boolean isUseableByPlayer(EntityPlayer p_70300_1_) {
	// TODO 自動生成されたメソッド・スタブ
	return false;
}

@Override
public void openInventory() {
	// TODO 自動生成されたメソッド・スタブ

}

@Override
public void closeInventory() {
	// TODO 自動生成されたメソッド・スタブ

}

@Override
public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_) {
	// TODO 自動生成されたメソッド・スタブ
	return false;
}


}
