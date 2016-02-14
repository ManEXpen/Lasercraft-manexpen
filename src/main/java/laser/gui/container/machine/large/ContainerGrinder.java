package laser.gui.container.machine.large;

import cofh.lib.gui.slot.ISlotValidator;
import cofh.lib.gui.slot.SlotEnergy;
import cofh.lib.gui.slot.SlotRemoveOnly;
import cofh.lib.gui.slot.SlotValidated;
import cofh.thermalexpansion.gui.container.ContainerTEBase;
import laser.block.machine.large.TileGrinder;
import laser.util.crafting.GrinderManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class ContainerGrinder extends ContainerTEBase implements ISlotValidator {

   TileGrinder myTile;


   public ContainerGrinder(InventoryPlayer paramInventoryPlayer, TileEntity paramTileEntity) {
      super(paramInventoryPlayer, paramTileEntity);
      this.myTile = (TileGrinder)paramTileEntity;
      this.addSlotToContainer(new SlotValidated(this, this.myTile, 0, 30, 17));
      this.addSlotToContainer(new SlotValidated(this, this.myTile, 1, 48, 17));
      this.addSlotToContainer(new SlotValidated(this, this.myTile, 2, 66, 17));
      this.addSlotToContainer(new SlotValidated(this, this.myTile, 3, 30, 35));
      this.addSlotToContainer(new SlotValidated(this, this.myTile, 4, 48, 35));
      this.addSlotToContainer(new SlotValidated(this, this.myTile, 5, 66, 35));
      this.addSlotToContainer(new SlotValidated(this, this.myTile, 6, 30, 53));
      this.addSlotToContainer(new SlotValidated(this, this.myTile, 7, 48, 53));
      this.addSlotToContainer(new SlotValidated(this, this.myTile, 8, 66, 53));
      this.addSlotToContainer(new SlotRemoveOnly(this.myTile, 9, 116, 17));
      this.addSlotToContainer(new SlotRemoveOnly(this.myTile, 10, 134, 17));
      this.addSlotToContainer(new SlotRemoveOnly(this.myTile, 11, 152, 17));
      this.addSlotToContainer(new SlotRemoveOnly(this.myTile, 12, 116, 35));
      this.addSlotToContainer(new SlotRemoveOnly(this.myTile, 13, 134, 35));
      this.addSlotToContainer(new SlotRemoveOnly(this.myTile, 14, 152, 35));
      this.addSlotToContainer(new SlotRemoveOnly(this.myTile, 15, 116, 53));
      this.addSlotToContainer(new SlotRemoveOnly(this.myTile, 16, 134, 53));
      this.addSlotToContainer(new SlotRemoveOnly(this.myTile, 17, 152, 53));
      this.addSlotToContainer(new SlotEnergy(this.myTile, this.myTile.getChargeSlot(), 8, 53));
   }

   public boolean isItemValid(ItemStack paramItemStack) {
      return GrinderManager.recipeExists(paramItemStack);
   }
}
