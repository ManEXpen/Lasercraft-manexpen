package laser.gui.container.machine;

import cofh.lib.gui.slot.SlotEnergy;
import cofh.thermalexpansion.gui.container.ContainerTEBase;
import laser.block.machine.TileChunkQuarry;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;

public class ContainerChunkQuarry extends ContainerTEBase {

	TileChunkQuarry myTile;

	public ContainerChunkQuarry(InventoryPlayer paramInventoryPlayer, TileEntity paramTileEntity) {
		super(paramInventoryPlayer, paramTileEntity);
		this.myTile = (TileChunkQuarry) paramTileEntity;
		this.addSlotToContainer(new SlotEnergy(this.myTile, this.myTile.getChargeSlot(), 8, 53));
	}
}
