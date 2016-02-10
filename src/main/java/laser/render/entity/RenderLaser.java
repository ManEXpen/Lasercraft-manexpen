package laser.render.entity;

import org.lwjgl.opengl.GL11;

import laser.entity.EntityLaser;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderLaser extends Render {

   protected static ModelBase model = new ModelBase() {
   };
   private static ModelRenderer[] box;


   private static ModelRenderer getBox(int index) {
      if(box == null) {
         box = new ModelRenderer[40];

         for(int j = 0; j < box.length; ++j) {
            box[j] = new ModelRenderer(model, box.length - j, 0);
            box[j].addBox(0.0F, -0.5F, -0.5F, 16, 1, 1);
            box[j].rotationPointX = 0.0F;
            box[j].rotationPointY = 0.0F;
            box[j].rotationPointZ = 0.0F;
         }
      }

      return box[index];
   }

   public void doRender(Entity entity, double x, double y, double z, float f, float f1) {
      this.doRender((EntityLaser)entity, x, y, z, f, f1);
   }

   private void doRender(EntityLaser laser, double x, double y, double z, float f, float f1) {
      GL11.glPushMatrix();
      GL11.glPushAttrib(8192);
      GL11.glDisable(2896);
      GL11.glTranslated(x + laser.renderOffsetX(), y + laser.renderOffsetY(), z + laser.renderOffsetZ());
      doRenderLaser(this.renderManager.renderEngine, laser);
      GL11.glPopAttrib();
      GL11.glPopMatrix();
   }

   public static void doRenderLaser(TextureManager textureManager, EntityLaser laser) {
      GL11.glPushMatrix();
      laser.updateData();
      GL11.glRotatef((float)laser.angleZ, 0.0F, 1.0F, 0.0F);
      GL11.glRotatef((float)laser.angleY, 0.0F, 0.0F, 1.0F);
      textureManager.bindTexture(laser.getTexture());
      doRenderLaserLine(laser.renderSize, laser.laserTexAnimation);
      GL11.glPopMatrix();
   }

   private static void doRenderLaserLine(double len, int texId) {
      float lasti = 0.0F;
      if(len - 1.0D > 0.0D) {
         for(float i = 0.0F; (double)i <= len - 1.0D; lasti = i++) {
            getBox(texId).render(0.0625F);
            GL11.glTranslated(1.0D, 0.0D, 0.0D);
         }

         ++lasti;
      }

      GL11.glPushMatrix();
      GL11.glScalef((float)len - lasti, 1.0F, 1.0F);
      getBox(texId).render(0.0625F);
      GL11.glPopMatrix();
      GL11.glTranslated((double)((float)(len - (double)lasti)), 0.0D, 0.0D);
   }

   protected ResourceLocation getEntityTexture(Entity entity) {
      return ((EntityLaser)entity).getTexture();
   }

}
