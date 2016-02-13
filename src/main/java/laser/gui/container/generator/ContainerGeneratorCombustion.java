package laser.gui.container.generator;

import cofh.lib.gui.slot.ISlotValidator;
import cofh.lib.gui.slot.SlotValidated;
import laser.block.generator.TileGeneratorCombustion;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import thermalexpansion.gui.container.ContainerTEBase;

public class ContainerGeneratorCombustion extends ContainerTEBase implements ISlotValidator {

	TileGeneratorCombustion myTile;

	public ContainerGeneratorCombustion(InventoryPlayer paramInventoryPlayer, TileEntity paramTileEntity) {
		super(paramInventoryPlayer, paramTileEntity);
		this.myTile = (TileGeneratorCombustion) paramTileEntity;
		this.addSlotToContainer(new SlotValidated(this, this.myTile, 0, 44, 35));
	}

	public boolean isItemValid(ItemStack paramItemStack) {
		return TileGeneratorCombustion.getEnergyValue(paramItemStack) > 0;
	}
}
