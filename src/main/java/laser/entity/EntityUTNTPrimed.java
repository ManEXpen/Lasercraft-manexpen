package laser.entity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidRegistry;
import thermalexpansion.block.TEBlocks;

public class EntityUTNTPrimed extends Entity {

   public int fuse;
   private EntityLivingBase tntPlacedBy;


   public EntityUTNTPrimed(World paramWorld) {
      super(paramWorld);
      this.preventEntitySpawning = true;
      this.setSize(0.98F, 0.98F);
      this.yOffset = this.height / 2.0F;
   }

   public EntityUTNTPrimed(World paramWorld, double paramDouble1, double paramDouble2, double paramDouble3, EntityLivingBase paramEntityLivingBase) {
      this(paramWorld);
      this.setPosition(paramDouble1, paramDouble2, paramDouble3);
      float f = (float)(Math.random() * 3.141592653589793D * 2.0D);
      this.motionX = (double)(-((float)Math.sin((double)f)) * 0.02F);
      this.motionY = 0.20000000298023224D;
      this.motionZ = (double)(-((float)Math.cos((double)f)) * 0.02F);
      this.fuse = 80;
      this.prevPosX = paramDouble1;
      this.prevPosY = paramDouble2;
      this.prevPosZ = paramDouble3;
      this.tntPlacedBy = paramEntityLivingBase;
   }

   protected void entityInit() {}

   protected boolean canTriggerWalking() {
      return false;
   }

   public boolean canBeCollidedWith() {
      return !this.isDead;
   }

   public void onUpdate() {
      this.prevPosX = this.posX;
      this.prevPosY = this.posY;
      this.prevPosZ = this.posZ;
      this.motionY -= 0.03999999910593033D;
      this.moveEntity(this.motionX, this.motionY, this.motionZ);
      this.motionX *= 0.9800000190734863D;
      this.motionY *= 0.9800000190734863D;
      this.motionZ *= 0.9800000190734863D;
      if(this.onGround) {
         this.motionX *= 0.699999988079071D;
         this.motionZ *= 0.699999988079071D;
         this.motionY *= -0.5D;
      }

      if(this.fuse-- <= 0) {
         this.setDead();
         if(this.worldObj.isRemote) {
            this.explode();
         }
      } else {
         this.worldObj.spawnParticle("smoke", this.posX, this.posY + 0.5D, this.posZ, 0.0D, 0.0D, 0.0D);
      }

   }

   private void explode() {
      Block block = this.worldObj.getBlock(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ));
      boolean flag = FluidRegistry.lookupFluidForBlock(block) != null;
      if(flag) {
         this.worldObj.setBlock(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ), TEBlocks.blockAirBarrier);
      }

      this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, flag?8.0F:1.5F, true);
   }

   protected void writeEntityToNBT(NBTTagCompound paramNBTTagCompound) {
      paramNBTTagCompound.setByte("Fuse", (byte)this.fuse);
   }

   protected void readEntityFromNBT(NBTTagCompound paramNBTTagCompound) {
      this.fuse = paramNBTTagCompound.getByte("Fuse");
   }

   @SideOnly(Side.CLIENT)
   public float getShadowSize() {
      return 0.0F;
   }

   public EntityLivingBase getTntPlacedBy() {
      return this.tntPlacedBy;
   }
}
