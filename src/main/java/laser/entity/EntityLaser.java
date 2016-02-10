package laser.entity;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class EntityLaser extends Entity {

   public static final ResourceLocation[] TEXTURES = new ResourceLocation[]{new ResourceLocation("laser:textures/entities/laserRed.png"), new ResourceLocation("laser:textures/entities/laserBlue.png"), new ResourceLocation("laser:textures/entities/laserDarkRed.png")};
   public double headX;
   public double headY;
   public double headZ;
   public double tailX;
   public double tailY;
   public double tailZ;
   public double renderSize;
   public double angleY;
   public double angleZ;
   public float damage = 4.0F;
   public int laserTexAnimation = 0;
   protected byte textureIndex;
   protected boolean needsUpdate = true;


   public EntityLaser(World world) {
      super(world);
   }

   public EntityLaser(World world, double headX, double headY, double headZ, double tailX, double tailY, double tailZ) {
      super(world);
      this.headX = headX;
      this.headY = headY;
      this.headZ = headZ;
      this.tailX = tailX;
      this.tailY = tailY;
      this.tailZ = tailZ;
      this.setPositionAndRotation(headX, headY, headZ, 0.0F, 0.0F);
      this.setSize(10.0F, 10.0F);
   }

   protected void entityInit() {
      this.preventEntitySpawning = false;
      this.noClip = true;
      this.isImmuneToFire = true;
      this.dataWatcher.addObject(8, Integer.valueOf(0));
      this.dataWatcher.addObject(9, Integer.valueOf(0));
      this.dataWatcher.addObject(10, Integer.valueOf(0));
      this.dataWatcher.addObject(11, Integer.valueOf(0));
      this.dataWatcher.addObject(12, Integer.valueOf(0));
      this.dataWatcher.addObject(13, Integer.valueOf(0));
      this.dataWatcher.addObject(14, Byte.valueOf((byte)0));
   }

   public void iterateTexture() {
      this.laserTexAnimation = (this.laserTexAnimation + 1) % 40;
   }

   public void onUpdate() {
      if(this.worldObj.isRemote && this.needsUpdate) {
         this.updateDataServer();
         this.needsUpdate = false;
      }

      this.boundingBox.minX = Math.min(this.headX, this.tailX);
      this.boundingBox.minY = Math.min(this.headY, this.tailY);
      this.boundingBox.minZ = Math.min(this.headZ, this.tailZ);
      this.boundingBox.maxX = Math.max(this.headX, this.tailX);
      this.boundingBox.maxY = Math.max(this.headY, this.tailY);
      this.boundingBox.maxZ = Math.max(this.headZ, this.tailZ);
      --this.boundingBox.minX;
      --this.boundingBox.minY;
      --this.boundingBox.minZ;
      ++this.boundingBox.maxX;
      ++this.boundingBox.maxY;
      ++this.boundingBox.maxZ;
      this.updateData();
      this.iterateTexture();
   }

   public void updateData() {
      double dx = this.headX - this.tailX;
      double dy = this.headY - this.tailY;
      double dz = this.headZ - this.tailZ;
      this.renderSize = Math.sqrt(dx * dx + dy * dy + dz * dz);
      this.angleZ = 360.0D - (Math.atan2(dz, dx) * 180.0D / 3.141592653589793D + 180.0D);
      dx = Math.sqrt(this.renderSize * this.renderSize - dy * dy);
      this.angleY = -Math.atan2(dy, dx) * 180.0D / 3.141592653589793D;
   }

   protected void updateDataClient() {
      this.headX = this.decodeDouble(this.dataWatcher.getWatchableObjectInt(8));
      this.headY = this.decodeDouble(this.dataWatcher.getWatchableObjectInt(9));
      this.headZ = this.decodeDouble(this.dataWatcher.getWatchableObjectInt(10));
      this.tailX = this.decodeDouble(this.dataWatcher.getWatchableObjectInt(11));
      this.tailY = this.decodeDouble(this.dataWatcher.getWatchableObjectInt(12));
      this.tailZ = this.decodeDouble(this.dataWatcher.getWatchableObjectInt(13));
   }

   protected void updateDataServer() {
      this.dataWatcher.updateObject(8, Integer.valueOf(this.encodeDouble(this.headX)));
      this.dataWatcher.updateObject(9, Integer.valueOf(this.encodeDouble(this.headY)));
      this.dataWatcher.updateObject(10, Integer.valueOf(this.encodeDouble(this.headZ)));
      this.dataWatcher.updateObject(11, Integer.valueOf(this.encodeDouble(this.tailX)));
      this.dataWatcher.updateObject(12, Integer.valueOf(this.encodeDouble(this.tailY)));
      this.dataWatcher.updateObject(13, Integer.valueOf(this.encodeDouble(this.tailZ)));
   }

   public void applyEntityCollision(Entity entity) {
      if(!entity.isImmuneToFire()) {
         entity.attackEntityFrom(DamageSource.inFire, this.damage);
         entity.setFire(10);
      }

   }

   public boolean canBePushed() {
      return true;
   }

   public void setPositions(double headX, double headY, double headZ, double tailX, double tailY, double tailZ) {
      this.headX = headX;
      this.headY = headY;
      this.headZ = headZ;
      this.tailX = tailX;
      this.tailY = tailY;
      this.tailZ = tailZ;
      this.setPositionAndRotation(this.headX, this.headY, this.headZ, 0.0F, 0.0F);
      this.needsUpdate = true;
   }

   public void setTextureIndex(int i) {
      if(i < TEXTURES.length) {
         this.textureIndex = (byte)i;
      }

   }

   public int getTextureIndex() {
      return this.textureIndex;
   }

   public ResourceLocation getTexture() {
      return TEXTURES[this.textureIndex];
   }

   protected int encodeDouble(double d) {
      return (int)(d * 8192.0D);
   }

   protected double decodeDouble(int i) {
      return (double)i / 8192.0D;
   }

   protected void readEntityFromNBT(NBTTagCompound nbt) {
      this.headX = nbt.getDouble("headX");
      this.headY = nbt.getDouble("headY");
      this.headZ = nbt.getDouble("headZ");
      this.tailX = nbt.getDouble("tailX");
      this.tailY = nbt.getDouble("tailY");
      this.tailZ = nbt.getDouble("tailZ");
   }

   protected void writeEntityToNBT(NBTTagCompound nbt) {
      nbt.setDouble("headX", this.headX);
      nbt.setDouble("headY", this.headY);
      nbt.setDouble("headZ", this.headZ);
      nbt.setDouble("tailX", this.tailX);
      nbt.setDouble("tailY", this.tailY);
      nbt.setDouble("tailZ", this.tailZ);
   }

   public double renderOffsetX() {
      return this.headX - this.posX;
   }

   public double renderOffsetY() {
      return this.headY - this.posY;
   }

   public double renderOffsetZ() {
      return this.headZ - this.posZ;
   }

   public int getBrightnessForRender(float par1) {
      return 210;
   }

}
