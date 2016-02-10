package laser.proxy;

import cpw.mods.fml.client.registry.RenderingRegistry;
import laser.proxy.CommonProxy;
import laser.render.block.RenderGenerator;

public class ClientProxy extends CommonProxy {

   public int getNewRenderType() {
      return RenderingRegistry.getNextAvailableRenderId();
   }

   public void registerRenderers() {
      RenderingRegistry.registerBlockHandler(new RenderGenerator());
   }
}
