/*    */ package laser.gui.element;
/*    */
/*    */ import cofh.lib.gui.GuiBase;
/*    */ import cofh.lib.gui.element.ElementBase;
/*    */ import cofh.lib.render.RenderHelper;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import thermalexpansion.core.TEProps;
/*    */
/*    */ public class ElementSlotOverlay9 extends ElementBase
/*    */ {
/* 11 */   private static ResourceLocation TEXTURE = new ResourceLocation("laser:textures/gui/elements/Slot9.png");
/*    */   public int slotColor;
/*    */
/*    */   public ElementSlotOverlay9(GuiBase paramGuiBase, int paramInt1, int paramInt2)
/*    */   {
/* 16 */     super(paramGuiBase, paramInt1, paramInt2);
/* 17 */     this.texture = TEXTURE;
/*    */   }
/*    */
/*    */   public ElementSlotOverlay9 setSlotColor(int paramInt1)
/*    */   {
/* 22 */     this.slotColor = paramInt1;
/* 23 */     return this;
/*    */   }
/*    */
/*    */   public void drawBackground(int paramInt1, int paramInt2, float paramFloat)
/*    */   {
/* 28 */     if (!isVisible()) {
/* 29 */       return;
/*    */     }
/* 31 */     RenderHelper.bindTexture(this.texture);
/* 32 */     if (TEProps.enableGuiBorders) {
/* 33 */       drawSlotWithBorder(this.posX, this.posY);
/*    */     } else {
/* 35 */       drawSlotNoBorder(this.posX, this.posY);
/*    */     }
/*    */   }
/*    */
/*    */
/*    */   public void drawForeground(int paramInt1, int paramInt2) {}
/*    */
/*    */   public boolean intersectsWith(int paramInt1, int paramInt2)
/*    */   {
/* 44 */     return false;
/*    */   }
/*    */
/*    */   protected void drawSlotNoBorder(int paramInt1, int paramInt2)
/*    */   {
/* 49 */     int i = this.slotColor * 68 + 8;
/* 50 */     int j = 8;
/* 51 */     this.gui.drawTexturedModalRect(paramInt1, paramInt2, i, j, 68, 68);
/*    */   }
/*    */
/*    */   protected void drawSlotWithBorder(int paramInt1, int paramInt2)
/*    */   {
/* 56 */     int i = this.slotColor * 68;
/* 57 */     int j = 0;
/* 58 */     paramInt1 -= 8;
/* 59 */     paramInt2 -= 8;
/* 60 */     this.gui.drawTexturedModalRect(paramInt1, paramInt2, i, j, 68, 68);
/*    */   }
/*    */ }


/* Location:              C:\Users\ManEXpen\Downloads\LaserCraft-1.7.10-0.2B4.jar!\laser\gui\element\ElementSlotOverlay9.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */