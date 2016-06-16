
package laser.network;

import cofh.core.network.PacketCoFHBase;
import cofh.core.network.PacketHandler;
import laser.block.machine.TileChunkQuarry;
import net.minecraft.entity.player.EntityPlayer;

public class PacketLCBase extends PacketCoFHBase {

	public void handlePacket(EntityPlayer player, boolean paramBoolean) {
		try {
			byte localException = this.getByte();
			int[] arrayOfInt;
			TileChunkQuarry tile;
			switch (localException) {
				case 0 :
					arrayOfInt = this.getCoords();
					tile = (TileChunkQuarry) player.worldObj.getTileEntity(
							arrayOfInt[0], arrayOfInt[1], arrayOfInt[2]);
					tile.setShowBorder(this.getBool());
					return;
				case 1 :
					arrayOfInt = this.getCoords();
					tile = (TileChunkQuarry) player.worldObj.getTileEntity(
							arrayOfInt[0], arrayOfInt[1], arrayOfInt[2]);
					tile.setBuildBarrier(this.getBool());
					return;
				case 2 :
					arrayOfInt = this.getCoords();
					tile = (TileChunkQuarry) player.worldObj.getTileEntity(
							arrayOfInt[0], arrayOfInt[1], arrayOfInt[2]);
					tile.setGuiChunkSize(this.getInt(), this.getInt());
					return;
				default :
					System.out.println("Unknown Packet! ID: " + localException);
			}
		} catch (Exception var6) {
			var6.printStackTrace();
		}

	}

	public static void sendShowBorderPacketToServer(
			TileChunkQuarry paramTileChunkQuarry, int paramInt1, int paramInt2,
			int paramInt3) {
		PacketHandler
				.sendToServer(getPacket(PacketLCBase.PacketTypes.CQ_SHOW_BORDER)
						.addCoords(paramInt1, paramInt2, paramInt3).addBool(
								paramTileChunkQuarry.showBorder));
	}

	public static void sendBuildBarrierPacketToServer(
			TileChunkQuarry paramTileChunkQuarry, int paramInt1, int paramInt2,
			int paramInt3) {
		PacketHandler.sendToServer(
				getPacket(PacketLCBase.PacketTypes.CQ_BUILD_BARRIER)
						.addCoords(paramInt1, paramInt2, paramInt3)
						.addBool(paramTileChunkQuarry.buildBarrier));
	}

	public static void sendChunkSizePacketToServer(TileChunkQuarry tile,
			int var0, int var1, int var2) {
		PacketHandler.sendToServer(
				getPacket(PacketTypes.CQ_CHUNKSIZE).addCoords(var0, var1, var2)
						.addInt(tile.GuiChunkSizeX).addInt(tile.GuiChunkSizeY));
	}

	public static PacketCoFHBase getPacket(
			PacketLCBase.PacketTypes paramPacketTypes) {
		return (new PacketLCBase()).addByte(paramPacketTypes.ordinal());
	}

	public static enum PacketTypes {

		CQ_SHOW_BORDER("CQ_SHOW_BORDER", 0), CQ_BUILD_BARRIER(
				"CQ_BUILD_BARRIER", 1), CQ_CHUNKSIZE("CQ_SETCHUNK", 2);

		private static final PacketLCBase.PacketTypes[] $VALUES = new PacketLCBase.PacketTypes[]{
				CQ_BUILD_BARRIER, CQ_SHOW_BORDER, CQ_CHUNKSIZE};

		private PacketTypes(String var1, int var2) {
		}

	}
}
