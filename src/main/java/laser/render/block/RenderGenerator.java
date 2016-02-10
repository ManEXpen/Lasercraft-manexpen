package laser.render.block;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import laser.Laser;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;

public class RenderGenerator implements ISimpleBlockRenderingHandler {

   public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {}

   public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
      return true;
   }

   public boolean shouldRender3DInInventory(int modelId) {
      return true;
   }

   public int getRenderId() {
      return Laser.renderIDs[0];
   }
}
