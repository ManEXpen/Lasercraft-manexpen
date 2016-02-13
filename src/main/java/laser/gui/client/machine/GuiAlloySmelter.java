package laser.gui.client.machine;

import cofh.lib.gui.element.ElementBase;
import cofh.lib.gui.element.ElementDualScaled;
import cofh.lib.gui.element.ElementEnergyStored;
import laser.gui.container.machine.ContainerAlloySmelter;
import laser.gui.element.ElementSlotOverlay9;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import thermalexpansion.gui.element.ElementSlotOverlay;

public class GuiAlloySmelter extends GuiMachineBase {

	public static final ResourceLocation TEXTURE = new ResourceLocation("laser:textures/gui/machine/AlloySmelter.png");
	ElementBase slotInput;
	ElementBase slotOutput;
	ElementDualScaled progress;
	ElementDualScaled speed;

	public GuiAlloySmelter(InventoryPlayer paramInventoryPlayer, TileEntity paramTileEntity) {
		super(new ContainerAlloySmelter(paramInventoryPlayer, paramTileEntity), paramTileEntity,
				paramInventoryPlayer.player, TEXTURE);
	}

	public void func_73866_w_() {
		super.func_73866_w_();
		this.slotInput = this.addElement((new ElementSlotOverlay9(this, 31, 16)).setSlotColor(0));
		this.slotOutput = this.addElement((new ElementSlotOverlay(this, 125, 30)).setSlotInfo(3, 2, 2));
		this.addElement(new ElementEnergyStored(this, 8, 8, this.myTile.getEnergyStorage()));
		this.progress = (ElementDualScaled) this.addElement((new ElementDualScaled(this, 92, 34)).setMode(1)
				.setSize(24, 16).setTexture("cofh:textures/gui/elements/Progress_Arrow_Right.png", 48, 16));
		this.speed = (ElementDualScaled) this.addElement((new ElementDualScaled(this, 96, 52)).setSize(16, 16)
				.setTexture("cofh:textures/gui/elements/Scale_Flame.png", 32, 16));
	}

	protected void updateElementInformation() {
		super.updateElementInformation();
		this.slotInput.setVisible(this.myTile.hasSide(1));
		this.slotOutput.setVisible(this.myTile.hasSide(2));
		this.progress.setQuantity(this.myTile.getScaledProgress(24));
		this.speed.setQuantity(this.myTile.getScaledSpeed(16));
	}

}
