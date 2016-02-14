package laser.gui.client.machine;

import cofh.lib.gui.element.ElementBase;
import cofh.lib.gui.element.ElementDualScaled;
import cofh.lib.gui.element.ElementEnergyStored;
import cofh.lib.gui.element.ElementFluid;
import cofh.lib.gui.element.ElementFluidTank;
import cofh.thermalexpansion.gui.element.ElementSlotOverlay;
import laser.block.machine.TilePump;
import laser.gui.container.machine.ContainerPump;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class GuiPump extends GuiMachineBase {

	public static final ResourceLocation TEXTURE = new ResourceLocation("laser:textures/gui/machine/Pump.png");
	ElementBase slotOutput;
	ElementFluid progressFluid;
	ElementDualScaled progressOverlay;
	ElementDualScaled progress;

	public GuiPump(InventoryPlayer paramInventoryPlayer, TileEntity paramTileEntity) {
		super(new ContainerPump(paramInventoryPlayer, paramTileEntity), paramTileEntity, paramInventoryPlayer.player,
				TEXTURE);
	}

	public void func_73866_w_() {
		super.func_73866_w_();
		this.slotOutput = this.addElement((new ElementSlotOverlay(this, 152, 9)).setSlotInfo(3, 3, 2));
		this.addElement(new ElementEnergyStored(this, 8, 8, this.myTile.getEnergyStorage()));
		this.addElement((new ElementFluidTank(this, 152, 9, ((TilePump) this.myTile).getTank())).setGauge(1));
		this.progressFluid = (ElementFluid) this.addElement(
				(new ElementFluid(this, 76, 34)).setFluid(((TilePump) this.myTile).getTankFluid()).setSize(24, 16));
		this.progressOverlay = (ElementDualScaled) this
				.addElement((new ElementDualScaled(this, 76, 34)).setMode(1).setBackground(false).setSize(24, 16)
						.setTexture("cofh:textures/gui/elements/Progress_Fluid_Right.png", 48, 16));
		this.progress = (ElementDualScaled) this.addElement((new ElementDualScaled(this, 76, 34)).setMode(1)
				.setSize(24, 16).setTexture("cofh:textures/gui/elements/Progress_Arrow_Right.png", 48, 16));
	}

	protected void updateElementInformation() {
		super.updateElementInformation();
		this.slotOutput.setVisible(this.myTile.hasSide(1));
		this.progress.setVisible(!((TilePump) this.myTile).pump);
		this.progressFluid.setFluid(((TilePump) this.myTile).getTankFluid());
		this.progressFluid.setSize(this.myTile.getScaledProgress(24), 16);
		this.progressOverlay.setQuantity(this.myTile.getScaledProgress(24));
		this.progress.setQuantity(this.myTile.getScaledProgress(24));
	}

}
