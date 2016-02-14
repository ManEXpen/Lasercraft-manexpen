package laser.gui.container.machine;

import cofh.lib.gui.slot.ISlotValidator;
import cofh.lib.gui.slot.SlotEnergy;
import cofh.lib.gui.slot.SlotRemoveOnly;
import cofh.lib.gui.slot.SlotValidated;
import cofh.thermalexpansion.gui.container.ContainerTEBase;
import laser.block.machine.TileAlloySmelter;
import laser.util.crafting.AlloySmelterManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class ContainerAlloySmelter extends ContainerTEBase implements ISlotValidator {

	TileAlloySmelter myTile;

	public ContainerAlloySmelter(InventoryPlayer paramInventoryPlayer, TileEntity paramTileEntity) {
		super(paramInventoryPlayer, paramTileEntity);
		this.myTile = (TileAlloySmelter) paramTileEntity;
		this.addSlotToContainer(new SlotValidated(this, this.myTile, 0, 31, 16));
		this.addSlotToContainer(new SlotValidated(this, this.myTile, 1, 49, 16));
		this.addSlotToContainer(new SlotValidated(this, this.myTile, 2, 67, 16));
		this.addSlotToContainer(new SlotValidated(this, this.myTile, 3, 31, 34));
		this.addSlotToContainer(new SlotValidated(this, this.myTile, 4, 49, 34));
		this.addSlotToContainer(new SlotValidated(this, this.myTile, 5, 67, 34));
		this.addSlotToContainer(new SlotValidated(this, this.myTile, 6, 31, 52));
		this.addSlotToContainer(new SlotValidated(this, this.myTile, 7, 49, 52));
		this.addSlotToContainer(new SlotValidated(this, this.myTile, 8, 67, 52));
		this.addSlotToContainer(new SlotRemoveOnly(this.myTile, 9, 129, 34));
		this.addSlotToContainer(new SlotRemoveOnly(this.myTile, 10, 147, 34));
		this.addSlotToContainer(new SlotEnergy(this.myTile, this.myTile.getChargeSlot(), 8, 53));
	}

	public boolean isItemValid(ItemStack paramItemStack) {
		return AlloySmelterManager.isItemValid(paramItemStack);
	}
}
