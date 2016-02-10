package laser.util.helper;

import laser.entity.particle.EntityBorderFX;
import net.minecraft.client.Minecraft;
import net.minecraft.world.World;

public class RenderHelper {

   public static void renderBorder(World world, int chunkX, int chunkZ) {
      renderBorder(world, chunkX, chunkZ, 0.0F, 0.0F, 0.0F);
   }

   public static void renderBorder(World world, int chunkX, int chunkZ, float r, float g, float b) {
      Minecraft mc = Minecraft.getMinecraft();
      int beginX = chunkX * 16;
      int beginZ = chunkZ * 16;
      int endX = beginX + 16;
      int endZ = beginZ + 16;

      for(int i = 0; i < 256; ++i) {
         EntityBorderFX entityfx = new EntityBorderFX(world, (double)beginX, (double)i, (double)beginZ, r, g, b);
         mc.effectRenderer.addEffect(entityfx);
         entityfx = new EntityBorderFX(world, (double)beginX, (double)i, (double)endZ, r, g, b);
         mc.effectRenderer.addEffect(entityfx);
         entityfx = new EntityBorderFX(world, (double)endX, (double)i, (double)beginZ, r, g, b);
         mc.effectRenderer.addEffect(entityfx);
         entityfx = new EntityBorderFX(world, (double)endX, (double)i, (double)endZ, r, g, b);
         mc.effectRenderer.addEffect(entityfx);
      }

   }
}
