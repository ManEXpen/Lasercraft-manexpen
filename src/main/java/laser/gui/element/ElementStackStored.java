package laser.gui.element;

import cofh.lib.gui.GuiBase;
import cofh.lib.gui.element.ElementBase;
import cofh.lib.render.RenderHelper;
import cofh.lib.util.helpers.MathHelper;
import java.util.List;
import laser.gui.client.IStackGui;
import net.minecraft.util.ResourceLocation;

public class ElementStackStored extends ElementBase {

   private static ResourceLocation TEXTURE = new ResourceLocation("laser:textures/gui/elements/Stack.png");
   public int max;


   public ElementStackStored(GuiBase paramGuiBase, int paramInt1, int paramInt2, int paramInt3) {
      super(paramGuiBase, paramInt1, paramInt2);
      this.max = paramInt3;
      this.texture = TEXTURE;
      this.sizeX = 16;
      this.sizeY = 42;
      this.texW = 32;
      this.texH = 64;
   }

   public void drawBackground(int paramInt1, int paramInt2, float paramFloat) {
      RenderHelper.bindTexture(this.texture);
      this.drawTexturedModalRect(this.posX, this.posY, 0, 0, this.sizeX, this.sizeY);
      int i = this.getScaled();
      this.drawTexturedModalRect(this.posX, this.posY + 42 - i, 16, 42 - i, this.sizeX, i);
   }

   public void drawForeground(int paramInt1, int paramInt2) {}

   public void addTooltip(List paramList) {
      if(this.gui instanceof IStackGui) {
         paramList.add(((IStackGui)this.gui).getStackCount() + " / " + this.max + " stacks");
      }

   }

   int getScaled() {
      return this.gui instanceof IStackGui?MathHelper.round((double)(((IStackGui)this.gui).getStackCount() * this.sizeY / this.max)):0;
   }

}
