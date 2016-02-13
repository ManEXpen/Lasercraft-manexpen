package laser.gui.client.generator;

import cofh.lib.gui.element.ElementDualScaled;
import laser.gui.container.generator.ContainerGeneratorCombustion;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class GuiGeneratorCombustion extends GuiGeneratorBase {

	static final ResourceLocation TEXTURE = new ResourceLocation(
			"thermalexpansion:textures/gui/dynamo/DynamoEnervation.png");
	ElementDualScaled duration;

	public GuiGeneratorCombustion(InventoryPlayer paramInventoryPlayer, TileEntity paramTileEntity) {
		super(new ContainerGeneratorCombustion(paramInventoryPlayer, paramTileEntity), paramTileEntity,
				paramInventoryPlayer.player, TEXTURE);
	}

	public void func_73866_w_() {
		super.func_73866_w_();
		this.duration = (ElementDualScaled) this.addElement((new ElementDualScaled(this, 115, 35)).setSize(16, 16)
				.setTexture("cofh:textures/gui/elements/Scale_Flame.png", 32, 16));
	}

	protected void updateElementInformation() {
		super.updateElementInformation();
		this.duration.setQuantity(this.myTile.getScaledDuration(16));
	}

}
