package laser.entity;

import laser.block.machine.TileChunkQuarry;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class EntityQuarryLaser extends EntityLaser {

   TileChunkQuarry myTile;


   public EntityQuarryLaser(World world, TileChunkQuarry tileChunkQuarry, ForgeDirection direction) {
      super(world);
      this.myTile = tileChunkQuarry;
      this.damage = 6.0F;
      int beginX = tileChunkQuarry.chunkCoordX * 16;
      int beginZ = tileChunkQuarry.chunkCoordZ * 16;
      int endX = beginX + 16;
      int endZ = beginZ + 16;
      double y = (double)tileChunkQuarry.aimY + 1.0D - (double)tileChunkQuarry.getScaledProgress(32) / 32.0D;
      switch(direction.ordinal()) {
      case 2:
         this.setPositions((double)beginX, y, (double)beginZ, (double)endX, y, (double)beginZ);
         break;
      case 3:
         this.setPositions((double)beginX, y, (double)endZ, (double)endX, y, (double)endZ);
         break;
      case 4:
         this.setPositions((double)beginX, y, (double)beginZ, (double)beginX, y, (double)endZ);
         break;
      case 5:
         this.setPositions((double)endX, y, (double)beginZ, (double)endX, y, (double)endZ);
      }

   }

   public EntityQuarryLaser(World world, TileChunkQuarry tileChunkQuarry, int i) {
      super(world);
      this.myTile = tileChunkQuarry;
      this.damage = 6.0F;
      int beginX = tileChunkQuarry.chunkCoordX * 16;
      int beginZ = tileChunkQuarry.chunkCoordZ * 16;
      int endX = beginX + 16;
      double y = (double)tileChunkQuarry.aimY + 1.0D - (double)tileChunkQuarry.getScaledProgress(32) / 32.0D;
      this.setPositions((double)beginX, y, (double)beginZ + (double)i + 1.0D, (double)endX, y, (double)beginZ + (double)i + 1.0D);
   }

   public void onUpdate() {
      if(this.myTile != null) {
         this.setHeight((double)this.myTile.aimY + 1.0D - (double)this.myTile.getScaledProgress(32) / 32.0D);
      }

      super.onUpdate();
   }

   public void setHeight(double y) {
      this.headY = y;
      this.tailY = y;
      this.setPositionAndRotation(this.headX, this.headY, this.headZ, 0.0F, 0.0F);
      this.needsUpdate = true;
   }

   protected void readEntityFromNBT(NBTTagCompound nbt) {
      super.readEntityFromNBT(nbt);
      TileEntity tileentity = this.worldObj.getTileEntity(nbt.getInteger("TileX"), nbt.getInteger("TileY"), nbt.getInteger("TileZ"));
      if(tileentity != null && tileentity instanceof TileChunkQuarry) {
         this.myTile = (TileChunkQuarry)tileentity;
      } else {
         this.myTile = null;
      }

   }

   protected void writeEntityToNBT(NBTTagCompound nbt) {
      super.writeEntityToNBT(nbt);
      if(this.myTile != null) {
         nbt.setInteger("TileX", this.myTile.xCoord);
         nbt.setInteger("TileY", this.myTile.yCoord);
         nbt.setInteger("TileZ", this.myTile.zCoord);
      }

   }
}
