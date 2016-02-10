package laser.block.generator;

import cofh.core.network.PacketCoFHBase;
import cofh.lib.util.helpers.ItemHelper;
import laser.gui.client.generator.GuiGeneratorCombustion;
import laser.gui.container.generator.ContainerGeneratorCombustion;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;
import thermalexpansion.block.TileTEBase;

public class TileGeneratorCombustion extends TileGeneratorBase {

   static final int TYPE = 0;
   static final int STEAM_MIN = 2000;
   int currentFuelRF;


   public static void initialize() {
      defaultEnergyConfig[0] = new TileTEBase.EnergyConfig();
      defaultEnergyConfig[0].setParams(10, 10, '\u9c40');
   }

   public static int getEnergyValue(ItemStack paramItemStack) {
      return paramItemStack == null?0:TileEntityFurnace.getItemBurnTime(paramItemStack) * 5;
   }

   public TileGeneratorCombustion() {
      this.inventory = new ItemStack[1];
   }

   public int getType() {
      return 0;
   }

   public int getLightValue() {
      return this.isActive?7:0;
   }

   protected boolean canGenerate() {
      return this.fuelRF > 0?true:getEnergyValue(this.inventory[0]) > 0;
   }

   public void generate() {
      int i;
      if(this.fuelRF <= 0) {
         i = getEnergyValue(this.inventory[0]);
         this.fuelRF += i;
         this.currentFuelRF = i;
         this.inventory[0] = ItemHelper.consumeItem(this.inventory[0]);
      }

      i = Math.min(this.fuelRF, this.calcEnergy());
      this.energyStorage.modifyEnergyStored(i);
      this.fuelRF -= i;
   }

   public Object getGuiClient(InventoryPlayer paramInventoryPlayer) {
      return new GuiGeneratorCombustion(paramInventoryPlayer, this);
   }

   public Object getGuiServer(InventoryPlayer paramInventoryPlayer) {
      return new ContainerGeneratorCombustion(paramInventoryPlayer, this);
   }

   public int getScaledDuration(int paramInt) {
      if(this.currentFuelRF <= 0) {
         this.currentFuelRF = 0;
         return 0;
      } else {
         return this.fuelRF * paramInt / this.currentFuelRF;
      }
   }

   public void readFromNBT(NBTTagCompound paramNBTTagCompound) {
      super.readFromNBT(paramNBTTagCompound);
      this.currentFuelRF = paramNBTTagCompound.getInteger("FuelMax");
      if(this.currentFuelRF <= 0) {
         this.currentFuelRF = 0;
      }

   }

   public void writeToNBT(NBTTagCompound paramNBTTagCompound) {
      super.writeToNBT(paramNBTTagCompound);
      paramNBTTagCompound.setInteger("FuelMax", this.currentFuelRF);
   }

   public PacketCoFHBase getGuiPacket() {
      PacketCoFHBase localPacketCoFHBase = super.getGuiPacket();
      localPacketCoFHBase.addInt(this.currentFuelRF);
      return localPacketCoFHBase;
   }

   protected void handleGuiPacket(PacketCoFHBase paramPacketCoFHBase) {
      super.handleGuiPacket(paramPacketCoFHBase);
      this.currentFuelRF = paramPacketCoFHBase.getInt();
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
public ItemStack decrStackSize(int p_70298_1_, int p_70298_2_) {
	// TODO 自動生成されたメソッド・スタブ
	return null;
}

@Override
public ItemStack getStackInSlotOnClosing(int p_70304_1_) {
	// TODO 自動生成されたメソッド・スタブ
	return null;
}

@Override
public void setInventorySlotContents(int p_70299_1_, ItemStack p_70299_2_) {
	// TODO 自動生成されたメソッド・スタブ

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
