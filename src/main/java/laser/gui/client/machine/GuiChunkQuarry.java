package laser.gui.client.machine;

import cofh.lib.gui.element.ElementBase;
import cofh.lib.gui.element.ElementDualScaled;
import cofh.lib.gui.element.ElementEnergyStored;
import laser.block.machine.TileChunkQuarry;
import laser.gui.client.IStackGui;
import laser.gui.container.machine.ContainerChunkQuarry;
import laser.gui.element.ElementSlotOverlayStack;
import laser.gui.element.ElementStackStored;
import laser.gui.element.TabChunkQuarry;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class GuiChunkQuarry extends GuiMachineBase implements IStackGui {

	public static final ResourceLocation TEXTURE = new ResourceLocation("laser:textures/gui/machine/ChunkQuarry.png");
	ElementBase slotStack;
	ElementDualScaled progress;

	public GuiChunkQuarry(InventoryPlayer paramInventoryPlayer, TileEntity paramTileEntity) {
		super(new ContainerChunkQuarry(paramInventoryPlayer, paramTileEntity), paramTileEntity, null, TEXTURE);
	}

	public void func_73866_w_() {
		super.func_73866_w_();

		this.addTab(new TabChunkQuarry(this, (TileChunkQuarry) this.myTile));
		this.slotStack = this.addElement((new ElementSlotOverlayStack(this, 154, 9)).setSlotColor(1));
		this.addElement(new ElementEnergyStored(this, 8, 8, this.myTile.getEnergyStorage()));
		this.addElement(new ElementStackStored(this, 152, 8, '\u9c40'));
		this.progress = (ElementDualScaled) this.addElement((new ElementDualScaled(this, 72, 26)).setSize(32, 32)
				.setTexture("laser:textures/gui/elements/Progress_Quarry.png", 64, 32));
	}

	protected void func_146979_b(int paramInt1, int paramInt2) {
		super.func_146979_b(paramInt1, paramInt2);
		if (this.myTile.isActive && ((TileChunkQuarry) this.myTile).aimY > -1) {
			int i = ((TileChunkQuarry) this.myTile).aimY;
			this.fontRendererObj.drawString(String.valueOf(i + 1), this.getCenteredOffset(String.valueOf(i + 1)), 18,
					4210752);
			this.fontRendererObj.drawString(String.valueOf(i), this.getCenteredOffset(String.valueOf(i)), 59, 4210752);
		}

	}

	protected void updateElementInformation() {
		super.updateElementInformation();
		this.slotStack.setVisible(this.myTile.hasSide(1));
		this.progress.setQuantity(33 - this.myTile.getScaledProgress(33));
	}

	public int getStackCount() {
		return ((TileChunkQuarry) this.myTile).stackCount;
	}

}
