package laser.gui.client.machine.large;

import cofh.lib.gui.element.ElementDualScaled;
import cofh.lib.gui.element.ElementEnergyStored;
import laser.gui.container.machine.large.ContainerGrinder;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class GuiGrinder extends GuiLargeMachineBase {

	public static final ResourceLocation TEXTURE = new ResourceLocation("laser:textures/gui/machine/Grinder.png");
	ElementDualScaled progress;
	ElementDualScaled speed;

	public GuiGrinder(InventoryPlayer paramInventoryPlayer, TileEntity paramTileEntity) {
		super(new ContainerGrinder(paramInventoryPlayer, paramTileEntity), paramTileEntity, paramInventoryPlayer.player,
				TEXTURE);
	}

	public void func_73866_w_() {
		super.func_73866_w_();
		this.addElement(new ElementEnergyStored(this, 8, 8, this.myTile.getEnergyStorage()));
		this.progress = (ElementDualScaled) this.addElement((new ElementDualScaled(this, 87, 34)).setMode(1)
				.setSize(24, 16).setTexture("cofh:textures/gui/elements/Progress_Arrow_Right.png", 48, 16));
		this.speed = (ElementDualScaled) this.addElement((new ElementDualScaled(this, 91, 52)).setSize(16, 16)
				.setTexture("cofh:textures/gui/elements/Scale_Saw.png", 32, 16));
	}

	protected void updateElementInformation() {
		super.updateElementInformation();
		this.progress.setQuantity(this.myTile.getScaledProgress(24));
		this.speed.setQuantity(this.myTile.getScaledSpeed(16));
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
		super.func_146976_a(par1, par2, par3);
	}

}
