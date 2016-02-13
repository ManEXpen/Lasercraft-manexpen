package laser.gui;

import cpw.mods.fml.client.IModGuiFactory;
import java.util.Set;
import laser.gui.GuiConfigLC;
import net.minecraft.client.Minecraft;

public class GuiConfigLCFactory implements IModGuiFactory {

   public void initialize(Minecraft paramMinecraft) {}

   public Class mainConfigGuiClass() {
      return GuiConfigLC.class;
   }

   public Set runtimeGuiCategories() {
      return null;
   }

   public IModGuiFactory.RuntimeOptionGuiHandler getHandlerFor(IModGuiFactory.RuntimeOptionCategoryElement paramRuntimeOptionCategoryElement) {
      return null;
   }
}
