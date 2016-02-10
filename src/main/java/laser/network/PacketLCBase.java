package laser.network;

import cofh.core.network.PacketCoFHBase;
import cofh.core.network.PacketHandler;
import laser.block.machine.TileChunkQuarry;
import net.minecraft.entity.player.EntityPlayer;

public class PacketLCBase extends PacketCoFHBase {

   public void handlePacket(EntityPlayer paramEntityPlayer, boolean paramBoolean) {
      try {
         byte localException = this.getByte();
         int[] arrayOfInt;
         TileChunkQuarry localTileChunkQuarry;
         switch(localException) {
         case 0:
            arrayOfInt = this.getCoords();
            localTileChunkQuarry = (TileChunkQuarry)paramEntityPlayer.worldObj.getTileEntity(arrayOfInt[0], arrayOfInt[1], arrayOfInt[2]);
            localTileChunkQuarry.setShowBorder(this.getBool());
            return;
         case 1:
            arrayOfInt = this.getCoords();
            localTileChunkQuarry = (TileChunkQuarry)paramEntityPlayer.worldObj.getTileEntity(arrayOfInt[0], arrayOfInt[1], arrayOfInt[2]);
            localTileChunkQuarry.setBuildBarrier(this.getBool());
            return;
         default:
            System.out.println("Unknown Packet! ID: " + localException);
         }
      } catch (Exception var6) {
         var6.printStackTrace();
      }

   }

   public static void sendShowBorderPacketToServer(TileChunkQuarry paramTileChunkQuarry, int paramInt1, int paramInt2, int paramInt3) {
      PacketHandler.sendToServer(getPacket(PacketLCBase.PacketTypes.CQ_SHOW_BORDER).addCoords(paramInt1, paramInt2, paramInt3).addBool(paramTileChunkQuarry.showBorder));
   }

   public static void sendBuildBarrierPacketToServer(TileChunkQuarry paramTileChunkQuarry, int paramInt1, int paramInt2, int paramInt3) {
      PacketHandler.sendToServer(getPacket(PacketLCBase.PacketTypes.CQ_BUILD_BARRIER).addCoords(paramInt1, paramInt2, paramInt3).addBool(paramTileChunkQuarry.buildBarrier));
   }

   public static PacketCoFHBase getPacket(PacketLCBase.PacketTypes paramPacketTypes) {
      return (new PacketLCBase()).addByte(paramPacketTypes.ordinal());
   }

   public static enum PacketTypes {

      CQ_SHOW_BORDER("CQ_SHOW_BORDER", 0),
      CQ_BUILD_BARRIER("CQ_BUILD_BARRIER", 1);
      // $FF: synthetic field
      private static final PacketLCBase.PacketTypes[] $VALUES = new PacketLCBase.PacketTypes[]{CQ_SHOW_BORDER, CQ_BUILD_BARRIER};


      private PacketTypes(String var1, int var2) {}

   }
}
