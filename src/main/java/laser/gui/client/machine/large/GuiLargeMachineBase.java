package laser.gui.client.machine.large;

import cofh.core.gui.GuiBaseAdv;
import cofh.core.gui.element.TabEnergy;
import cofh.core.gui.element.TabRedstone;
import laser.block.machine.large.TileLargeMachineBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class GuiLargeMachineBase extends GuiBaseAdv {

	protected TileLargeMachineBase myTile;

	public GuiLargeMachineBase(Container paramContainer, TileEntity paramTileEntity, EntityPlayer paramEntityPlayer,
			ResourceLocation paramResourceLocation) {
		super(paramContainer, paramResourceLocation);
		this.myTile = (TileLargeMachineBase) paramTileEntity;
		this.name = this.myTile.func_145825_b();
	}

	public void func_73866_w_() {
		super.func_73866_w_();
		this.addTab(new TabRedstone(this, this.myTile));
		if (this.myTile.getMaxEnergyStored(ForgeDirection.UNKNOWN) > 0) {
			this.addTab(new TabEnergy(this, this.myTile, false));
		}

	}

	public void updateScreen() {
		super.updateScreen();
		if (!this.myTile.canAccess()) {
			this.mc.thePlayer.closeScreen();
		}

	}

	protected void updateElementInformation() {
		super.updateElementInformation();
	}
}
