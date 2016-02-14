package laser.block.machine.large;

import cofh.core.network.PacketCoFHBase;
import cofh.thermalexpansion.block.TileTEBase;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileCasing extends TileTEBase {

   public int tileX;
   public int tileY;
   public int tileZ;


   public String getName() {
      return "";
   }

   public int getType() {
      return 0;
   }

   public void updateEntity() {
      if(this.timeCheckEighth()) {
         this.sendUpdatePacket(Side.CLIENT);
      }

   }

   public void setTile(TileLargeMachineBase paramTileLargeMachineBase) {
      this.tileX = paramTileLargeMachineBase.xCoord;
      this.tileY = paramTileLargeMachineBase.yCoord;
      this.tileZ = paramTileLargeMachineBase.zCoord;
   }

   public TileLargeMachineBase getTile() {
      return this.hasTile()?(TileLargeMachineBase)this.worldObj.getTileEntity(this.tileX, this.tileY, this.tileZ):null;
   }

   public boolean hasTile() {
      TileEntity localTileEntity = this.worldObj.getTileEntity(this.tileX, this.tileY, this.tileZ);
      return localTileEntity != null && localTileEntity instanceof TileLargeMachineBase;
   }

   public void readFromNBT(NBTTagCompound paramNBTTagCompound) {
      super.func_145839_a(paramNBTTagCompound);
      this.tileX = paramNBTTagCompound.getInteger("MachineX");
      this.tileY = paramNBTTagCompound.getInteger("MachineY");
      this.tileZ = paramNBTTagCompound.getInteger("MachineZ");
   }

   public void writeToNBT(NBTTagCompound paramNBTTagCompound) {
      super.func_145841_b(paramNBTTagCompound);
      paramNBTTagCompound.setInteger("MachineX", this.tileX);
      paramNBTTagCompound.setInteger("MachineY", this.tileY);
      paramNBTTagCompound.setInteger("MachineZ", this.tileZ);
   }

   public PacketCoFHBase getPacket() {
      PacketCoFHBase localPacketCoFHBase = super.getPacket();
      localPacketCoFHBase.addInt(this.tileX);
      localPacketCoFHBase.addInt(this.tileY);
      localPacketCoFHBase.addInt(this.tileZ);
      return localPacketCoFHBase;
   }

   public void handleTilePacket(PacketCoFHBase paramPacketCoFHBase, boolean paramBoolean) {
      super.handleTilePacket(paramPacketCoFHBase, paramBoolean);
      if(!paramBoolean) {
         this.tileX = paramPacketCoFHBase.getInt();
         this.tileY = paramPacketCoFHBase.getInt();
         this.tileZ = paramPacketCoFHBase.getInt();
      } else {
         paramPacketCoFHBase.getInt();
         paramPacketCoFHBase.getInt();
         paramPacketCoFHBase.getInt();
      }

   }
}
