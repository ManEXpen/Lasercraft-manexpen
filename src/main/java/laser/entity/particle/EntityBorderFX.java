package laser.entity.particle;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.World;

@SideOnly(Side.CLIENT)
public class EntityBorderFX extends EntityFX {

   float borderParticleScale;


   public EntityBorderFX(World p_i1223_1_, double p_i1223_2_, double p_i1223_4_, double p_i1223_6_, float p_i1223_8_, float p_i1223_9_, float p_i1223_10_) {
      this(p_i1223_1_, p_i1223_2_, p_i1223_4_, p_i1223_6_, 1.0F, p_i1223_8_, p_i1223_9_, p_i1223_10_);
   }

   public EntityBorderFX(World p_i1224_1_, double p_i1224_2_, double p_i1224_4_, double p_i1224_6_, float p_i1224_8_, float p_i1224_9_, float p_i1224_10_, float p_i1224_11_) {
      super(p_i1224_1_, p_i1224_2_, p_i1224_4_, p_i1224_6_, 0.0D, 0.0D, 0.0D);
      this.motionX *= 0.10000000149011612D;
      this.motionY *= 0.10000000149011612D;
      this.motionZ *= 0.10000000149011612D;
      if(p_i1224_9_ == 0.0F) {
         p_i1224_9_ = 1.0F;
      }

      float f4 = (float)Math.random() * 0.4F + 0.6F;
      this.particleRed = ((float)(Math.random() * 0.20000000298023224D) + 0.8F) * p_i1224_9_ * f4;
      this.particleGreen = ((float)(Math.random() * 0.20000000298023224D) + 0.8F) * p_i1224_10_ * f4;
      this.particleBlue = ((float)(Math.random() * 0.20000000298023224D) + 0.8F) * p_i1224_11_ * f4;
      this.particleScale *= 0.75F;
      this.particleScale *= p_i1224_8_;
      this.borderParticleScale = this.particleScale;
      this.particleMaxAge = (int)(8.0D / (Math.random() * 0.8D + 0.2D));
      this.particleMaxAge = (int)((float)this.particleMaxAge * p_i1224_8_);
      this.noClip = false;
   }

   public void renderParticle(Tessellator p_70539_1_, float p_70539_2_, float p_70539_3_, float p_70539_4_, float p_70539_5_, float p_70539_6_, float p_70539_7_) {
      float f5 = ((float)this.particleAge + p_70539_2_) / (float)this.particleMaxAge * 32.0F;
      if(f5 < 0.0F) {
         f5 = 0.0F;
      }

      if(f5 > 1.0F) {
         f5 = 1.0F;
      }

      this.particleScale = f5;
      float f6 = (float)this.particleTextureIndexX / 16.0F;
      float f7 = f6 + 0.0624375F;
      float f8 = (float)this.particleTextureIndexY / 16.0F;
      float f9 = f8 + 0.0624375F;
      float f10 = 0.1F * this.particleScale;
      float f11 = (float)(this.prevPosX + (this.posX - this.prevPosX) * (double)p_70539_2_ - interpPosX);
      float f12 = (float)(this.prevPosY + (this.posY - this.prevPosY) * (double)p_70539_2_ - interpPosY);
      float f13 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * (double)p_70539_2_ - interpPosZ);
      p_70539_1_.setColorRGBA_F(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha);
      p_70539_1_.addVertexWithUV((double)(f11 - p_70539_3_ * f10 - p_70539_6_ * f10), (double)(f12 - p_70539_4_ * f10), (double)(f13 - p_70539_5_ * f10 - p_70539_7_ * f10), (double)f7, (double)f9);
      p_70539_1_.addVertexWithUV((double)(f11 - p_70539_3_ * f10 + p_70539_6_ * f10), (double)(f12 + p_70539_4_ * f10), (double)(f13 - p_70539_5_ * f10 + p_70539_7_ * f10), (double)f7, (double)f8);
      p_70539_1_.addVertexWithUV((double)(f11 + p_70539_3_ * f10 + p_70539_6_ * f10), (double)(f12 + p_70539_4_ * f10), (double)(f13 + p_70539_5_ * f10 + p_70539_7_ * f10), (double)f6, (double)f8);
      p_70539_1_.addVertexWithUV((double)(f11 + p_70539_3_ * f10 - p_70539_6_ * f10), (double)(f12 - p_70539_4_ * f10), (double)(f13 + p_70539_5_ * f10 - p_70539_7_ * f10), (double)f6, (double)f9);
   }

   public void onUpdate() {
      this.prevPosX = this.posX;
      this.prevPosY = this.posY;
      this.prevPosZ = this.posZ;
      if(this.particleAge++ >= this.particleMaxAge) {
         this.setDead();
      }

      this.setParticleTextureIndex(7 - this.particleAge * 8 / this.particleMaxAge);
      this.moveEntity(this.motionX, this.motionY, this.motionZ);
      if(this.posY == this.prevPosY) {
         this.motionX *= 1.1D;
         this.motionZ *= 1.1D;
      }

      this.motionX *= 0.9599999785423279D;
      this.motionY *= 0.9599999785423279D;
      this.motionZ *= 0.9599999785423279D;
      if(this.onGround) {
         this.motionX *= 0.699999988079071D;
         this.motionZ *= 0.699999988079071D;
      }

   }
}
