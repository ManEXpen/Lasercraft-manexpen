/*    */ package laser.gui.element;
/*    */
/*    */ import cofh.lib.gui.GuiBase;
/*    */ import cofh.lib.gui.element.ElementBase;
/*    */ import cofh.lib.render.RenderHelper;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */
/*    */ public class ElementSlotOverlayStack extends ElementBase
/*    */ {
/* 10 */   private static ResourceLocation TEXTURE = new ResourceLocation("laser:textures/gui/elements/SlotStack.png");
/*    */   public int slotColor;
/*    */
/*    */   public ElementSlotOverlayStack(GuiBase paramGuiBase, int paramInt1, int paramInt2)
/*    */   {
/* 15 */     super(paramGuiBase, paramInt1, paramInt2);
/* 16 */     this.texture = TEXTURE;
/*    */   }
/*    */
/*    */   public ElementSlotOverlayStack setSlotColor(int paramInt1)
/*    */   {
/* 21 */     this.slotColor = paramInt1;
/* 22 */     return this;
/*    */   }
/*    */
/*    */   public void drawBackground(int paramInt1, int paramInt2, float paramFloat)
/*    */   {
/* 27 */     if (!isVisible()) {
/* 28 */       return;
/*    */     }
/* 30 */     RenderHelper.bindTexture(this.texture);
/* 31 */     drawSlotWithBorder(this.posX, this.posY);
/*    */   }
/*    */
/*    */
/*    */   public void drawForeground(int paramInt1, int paramInt2) {}
/*    */
/*    */
/*    */   public boolean intersectsWith(int paramInt1, int paramInt2)
/*    */   {
/* 40 */     return false;
/*    */   }
/*    */
/*    */   protected void drawSlotWithBorder(int paramInt1, int paramInt2)
/*    */   {
/* 45 */     int i = this.slotColor * 28;
/* 46 */     int j = 0;
/* 47 */     paramInt1 -= 8;
/* 48 */     paramInt2 -= 8;
/* 49 */     this.gui.drawTexturedModalRect(paramInt1, paramInt2, i, j, 28, 56);
/*    */   }
/*    */ }


/* Location:              C:\Users\ManEXpen\Downloads\LaserCraft-1.7.10-0.2B4.jar!\laser\gui\element\ElementSlotOverlayStack.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */