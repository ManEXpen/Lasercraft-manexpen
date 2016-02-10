/*    */ package laser.gui.element;
/*    */ 
/*    */ import cofh.lib.gui.GuiBase;
/*    */ import cofh.lib.gui.element.ElementBase;
/*    */ import cofh.lib.render.RenderHelper;
/*    */ import cofh.lib.util.helpers.MathHelper;
/*    */ import java.util.List;
/*    */ import laser.gui.client.IStackGui;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class ElementStackStored
/*    */   extends ElementBase
/*    */ {
/* 14 */   private static ResourceLocation TEXTURE = new ResourceLocation("laser:textures/gui/elements/Stack.png");
/*    */   public int max;
/*    */   
/*    */   public ElementStackStored(GuiBase paramGuiBase, int paramInt1, int paramInt2, int paramInt3)
/*    */   {
/* 19 */     super(paramGuiBase, paramInt1, paramInt2);
/* 20 */     this.max = paramInt3;
/* 21 */     this.texture = TEXTURE;
/* 22 */     this.sizeX = 16;
/* 23 */     this.sizeY = 42;
/* 24 */     this.texW = 32;
/* 25 */     this.texH = 64;
/*    */   }
/*    */   
/*    */   public void drawBackground(int paramInt1, int paramInt2, float paramFloat)
/*    */   {
/* 30 */     RenderHelper.bindTexture(this.texture);
/* 31 */     drawTexturedModalRect(this.posX, this.posY, 0, 0, this.sizeX, this.sizeY);
/* 32 */     int i = getScaled();
/* 33 */     drawTexturedModalRect(this.posX, this.posY + 42 - i, 16, 42 - i, this.sizeX, i);
/*    */   }
/*    */   
/*    */ 
/*    */   public void drawForeground(int paramInt1, int paramInt2) {}
/*    */   
/*    */ 
/*    */   public void addTooltip(List<String> paramList)
/*    */   {
/* 42 */     if ((this.gui instanceof IStackGui)) {
/* 43 */       paramList.add(((IStackGui)this.gui).getStackCount() + " / " + this.max + " stacks");
/*    */     }
/*    */   }
/*    */   
/*    */   int getScaled() {
/* 48 */     if ((this.gui instanceof IStackGui)) {
/* 49 */       return MathHelper.round(((IStackGui)this.gui).getStackCount() * this.sizeY / this.max);
/*    */     }
/* 51 */     return 0;
/*    */   }
/*    */ }


/* Location:              C:\Users\ManEXpen\Downloads\LaserCraft-1.7.10-0.2B4.jar!\laser\gui\element\ElementStackStored.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */