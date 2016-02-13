package laser.gui.client.generator;

import cofh.core.gui.GuiBaseAdv;
import cofh.core.gui.element.TabEnergy;
import cofh.core.gui.element.TabRedstone;
import cofh.lib.gui.element.ElementEnergyStored;
import laser.block.generator.TileGeneratorBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class GuiGeneratorBase extends GuiBaseAdv {

	protected TileGeneratorBase myTile;

	public GuiGeneratorBase(Container paramContainer, TileEntity paramTileEntity, EntityPlayer paramEntityPlayer,
			ResourceLocation paramResourceLocation) {
		super(paramContainer, paramResourceLocation);
		this.myTile = (TileGeneratorBase) paramTileEntity;
		this.name = this.myTile.func_145825_b();
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
		super.func_146976_a(par1, par2, par3);
	}

	public void func_73866_w_() {
		super.func_73866_w_();
		this.addElement(new ElementEnergyStored(this, 80, 18, this.myTile.getEnergyStorage()));
		this.addTab(new TabRedstone(this, this.myTile));
		if (this.myTile.getMaxEnergyStored(ForgeDirection.UNKNOWN) > 0) {
			this.addTab(new TabEnergy(this, this.myTile, true));
		}

	}

	protected void updateElementInformation() {
		super.updateElementInformation();
	}

	public void updateScreen() {
		super.updateScreen();
		if (!this.myTile.canAccess()) {
			this.mc.thePlayer.closeScreen();
		}

	}

}
