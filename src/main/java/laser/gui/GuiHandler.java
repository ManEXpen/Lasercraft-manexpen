package laser.gui;

import cofh.core.block.TileCoFHBase;
import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class GuiHandler implements IGuiHandler {

	public Object getClientGuiElement(int paramInt1, EntityPlayer paramEntityPlayer, World paramWorld, int paramInt2,
			int paramInt3, int paramInt4) {
		switch (paramInt1) {
		case 0:
			TileEntity localTileEntity = paramWorld.getTileEntity(paramInt2, paramInt3, paramInt4);
			if (localTileEntity instanceof TileCoFHBase) {
				return ((TileCoFHBase) localTileEntity).getGuiClient(paramEntityPlayer.inventory);
			}

			return null;
		default:
			return null;
		}
	}

	public Object getServerGuiElement(int paramInt1, EntityPlayer paramEntityPlayer, World paramWorld, int paramInt2,
			int paramInt3, int paramInt4) {
		switch (paramInt1) {
		case 0:
			TileEntity localTileEntity = paramWorld.getTileEntity(paramInt2, paramInt3, paramInt4);
			if (localTileEntity instanceof TileCoFHBase) {
				return ((TileCoFHBase) localTileEntity).getGuiServer(paramEntityPlayer.inventory);
			}

			return null;
		default:
			return null;
		}
	}
}
