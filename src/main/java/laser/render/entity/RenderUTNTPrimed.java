package laser.render.entity;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import laser.Laser;
import laser.entity.EntityUTNTPrimed;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class RenderUTNTPrimed extends Render {

   private RenderBlocks blockRenderer = new RenderBlocks();


   public RenderUTNTPrimed() {
      this.shadowSize = 0.5F;
   }

   public void doRender(EntityUTNTPrimed paramEntityUTNTPrimed, double paramDouble1, double paramDouble2, double paramDouble3, float paramFloat1, float paramFloat2) {
      GL11.glPushMatrix();
      GL11.glTranslatef((float)paramDouble1, (float)paramDouble2, (float)paramDouble3);
      float f2;
      if((float)paramEntityUTNTPrimed.fuse - paramFloat2 + 1.0F < 10.0F) {
         f2 = 1.0F - ((float)paramEntityUTNTPrimed.fuse - paramFloat2 + 1.0F) / 10.0F;
         if(f2 < 0.0F) {
            f2 = 0.0F;
         }

         if(f2 > 1.0F) {
            f2 = 1.0F;
         }

         f2 *= f2;
         f2 *= f2;
         float f3 = 1.0F + f2 * 0.3F;
         GL11.glScalef(f3, f3, f3);
      }

      f2 = (1.0F - ((float)paramEntityUTNTPrimed.fuse - paramFloat2 + 1.0F) / 100.0F) * 0.8F;
      this.bindEntityTexture(paramEntityUTNTPrimed);
      this.blockRenderer.renderBlockAsItem(Laser.utnt, 0, paramEntityUTNTPrimed.getBrightness(paramFloat2));
      if(paramEntityUTNTPrimed.fuse / 5 % 2 == 0) {
         GL11.glDisable(3553);
         GL11.glDisable(2896);
         GL11.glEnable(3042);
         GL11.glBlendFunc(770, 772);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, f2);
         this.blockRenderer.renderBlockAsItem(Laser.utnt, 0, 1.0F);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         GL11.glDisable(3042);
         GL11.glEnable(2896);
         GL11.glEnable(3553);
      }

      GL11.glPopMatrix();
   }

   protected ResourceLocation getEntityTexture(EntityUTNTPrimed paramEntityUTNTPrimed) {
      return TextureMap.locationBlocksTexture;
   }

   protected ResourceLocation getEntityTexture(Entity paramEntity) {
      return this.getEntityTexture((EntityUTNTPrimed)paramEntity);
   }

   public void doRender(Entity paramEntity, double paramDouble1, double paramDouble2, double paramDouble3, float paramFloat1, float paramFloat2) {
      this.doRender((EntityUTNTPrimed)paramEntity, paramDouble1, paramDouble2, paramDouble3, paramFloat1, paramFloat2);
   }
}
