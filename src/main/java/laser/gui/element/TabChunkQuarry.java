/*     */ package laser.gui.element;
/*     */ import java.util.List;

/*     */ import org.lwjgl.opengl.GL11;

/*     */
/*     */ import cofh.lib.gui.GuiBase;
/*     */ import cofh.lib.gui.element.TabBase;
/*     */ import cofh.lib.util.helpers.StringHelper;
/*     */ import laser.block.machine.TileChunkQuarry;
/*     */ import net.minecraft.init.Items;
/*     */
/*     */ public class TabChunkQuarry
/*     */   extends TabBase
/*     */ {
/*     */   public static boolean enable;
/*  17 */   public static int defaultSide = 1;
/*  18 */   public static int defaultHeaderColor = 14797103;
/*  19 */   public static int defaultSubHeaderColor = 11186104;
/*  20 */   public static int defaultTextColor = 0;
/*  21 */   public static int defaultBackgroundColor = 2263142;
/*     */   TileChunkQuarry myTile;
/*     */
/*     */   public TabChunkQuarry(GuiBase paramGuiBase, TileChunkQuarry paramTileChunkQuarry)
/*     */   {
/*  26 */     this(paramGuiBase, defaultSide, paramTileChunkQuarry);
/*     */   }
/*     */
/*     */   public TabChunkQuarry(GuiBase paramGuiBase, int paramInt, TileChunkQuarry paramTileChunkQuarry)
/*     */   {
/*  31 */     super(paramGuiBase, paramInt);
/*     */
/*  33 */     this.headerColor = defaultHeaderColor;
/*  34 */     this.subheaderColor = defaultSubHeaderColor;
/*  35 */     this.textColor = defaultTextColor;
/*  36 */     this.backgroundColor = defaultBackgroundColor;
/*     */
/*  38 */     this.maxHeight = 92;
/*  39 */     this.maxWidth = 120;
/*  40 */     this.myTile = paramTileChunkQuarry;
/*     */   }
/*     */
/*     */   public void draw()
/*     */   {
/*  45 */     drawBackground();
/*  46 */     drawTabIcon("IconConfig");
/*  47 */     if (!isFullyOpened()) {
/*  48 */       return;
/*     */     }
/*  50 */     getFontRenderer().drawStringWithShadow(StringHelper.localize("info.laser.chunkQuarry.config"), posXOffset() + 18, this.posY + 6, this.headerColor);
/*     */
/*  52 */     if (this.myTile.showBorder) {
/*  53 */       this.gui.drawButton(Items.redstone.getIconFromDamage(0), posX() + 16, this.posY + 20, 1, 1);
/*     */     } else {
/*  55 */       this.gui.drawButton(Items.gunpowder.getIconFromDamage(0), posX() + 16, this.posY + 20, 1, 0);
/*     */     }
/*  57 */     if (this.myTile.buildBarrier) {
/*  58 */       this.gui.drawButton(Items.sugar.getIconFromDamage(0), posX() + 36, this.posY + 20, 1, 1);
/*     */     } else {
/*  60 */       this.gui.drawButton(Items.gunpowder.getIconFromDamage(0), posX() + 36, this.posY + 20, 1, 0);
/*     */     }
/*  62 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*     */   }
/*     */
/*     */   public void addTooltip(List<String> paramList)
/*     */   {
/*  67 */     if (!isFullyOpened()) {
/*  68 */       paramList.add(StringHelper.localize("info.laser.chunkQuarry.config"));
/*  69 */       return;
/*     */     }
/*  71 */     int i = this.gui.getMouseX() - this.currentShiftX;
/*  72 */     int j = this.gui.getMouseY() - this.currentShiftY;
/*  73 */     if ((16 <= i) && (i < 32) && (20 <= j) && (j < 36)) {
/*  74 */       paramList.add(StringHelper.localize("info.laser.chunkQuarry.showBorder"));
/*  75 */     } else if ((36 <= i) && (i < 52) && (20 <= j) && (j < 36)) {
/*  76 */       paramList.add(StringHelper.localize("info.laser.chunkQuarry.buildBarrier"));
/*     */     }
/*     */   }
/*     */
/*     */   public boolean onMousePressed(int paramInt1, int paramInt2, int paramInt3) {
/*  81 */     if (!isFullyOpened()) {
/*  82 */       return false;
/*     */     }
/*  84 */     if (this.side == LEFT) {
/*  85 */       paramInt1 += this.currentWidth;
/*     */     }
/*  87 */     paramInt1 -= this.currentShiftX;
/*  88 */     paramInt2 -= this.currentShiftY;
/*     */
/*  90 */     if ((paramInt1 < 12) || (paramInt1 >= 96) || (paramInt2 < 16) || (paramInt2 >= 40)) {
/*  91 */       return false;
/*     */     }
/*  93 */     if ((16 <= paramInt1) && (paramInt1 < 32) && (20 <= paramInt2) && (paramInt2 < 36)) {
/*  94 */       if (!this.myTile.showBorder) {
/*  95 */         this.myTile.setShowBorder(true);
/*  96 */         GuiBase.playSound("random.click", 1.0F, 0.8F);
/*     */       } else {
/*  98 */         this.myTile.setShowBorder(false);
/*  99 */         GuiBase.playSound("random.click", 1.0F, 0.4F);
/*     */       }
/* 101 */     } else if ((36 <= paramInt1) && (paramInt1 < 52) && (20 <= paramInt2) && (paramInt2 < 36)) {
/* 102 */       if (!this.myTile.buildBarrier) {
/* 103 */         this.myTile.setBuildBarrier(true);
/* 104 */         GuiBase.playSound("random.click", 1.0F, 0.8F);
/*     */       } else {
/* 106 */         this.myTile.setBuildBarrier(false);
/* 107 */         GuiBase.playSound("random.click", 1.0F, 0.4F);
/*     */       }
/*     */     }
/*     */
/*     */
/*     */
/*     */
/*     */
/* 115 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\ManEXpen\Downloads\LaserCraft-1.7.10-0.2B4.jar!\laser\gui\element\TabChunkQuarry.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */