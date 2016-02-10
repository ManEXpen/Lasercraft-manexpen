/*    */ package laser.gui.client.generator;
/*    */
/*    */ import cofh.core.gui.GuiBaseAdv;
/*    */ import cofh.core.gui.element.TabEnergy;
/*    */ import cofh.core.gui.element.TabRedstone;
/*    */ import cofh.lib.gui.element.ElementEnergyStored;
/*    */ import laser.block.generator.TileGeneratorBase;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.inventory.Container;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */
/*    */ public abstract class GuiGeneratorBase extends GuiBaseAdv
/*    */ {
/*    */   protected TileGeneratorBase myTile;
/*    */
/*    */   public GuiGeneratorBase(Container paramContainer, TileEntity paramTileEntity, EntityPlayer paramEntityPlayer, ResourceLocation paramResourceLocation)
/*    */   {
/* 20 */     super(paramContainer, paramResourceLocation);
/*    */
/* 22 */     this.myTile = ((TileGeneratorBase)paramTileEntity);
/* 23 */     this.name = this.myTile.func_145825_b();
/*    */   }
/*    */
/*    */   public void initGui()
/*    */   {
/* 28 */     super.initGui();
/*    */
/* 30 */     addElement(new ElementEnergyStored(this, 80, 18, this.myTile.getEnergyStorage()));
/* 31 */     addTab(new TabRedstone(this, this.myTile));
/*    */
/* 33 */     if (this.myTile.getMaxEnergyStored(net.minecraftforge.common.util.ForgeDirection.UNKNOWN) > 0) {
/* 34 */       addTab(new TabEnergy(this, this.myTile, true));
/*    */     }
/*    */   }
/*    */
/*    */   public void updateScreen()
/*    */   {
/* 40 */     super.updateScreen();
/*    */
/* 42 */     if (!this.myTile.canAccess()) {
/* 43 */       this.mc.thePlayer.closeScreen();
/*    */     }
/*    */   }
/*    */
/*    */   protected void updateElementInformation() {
/* 48 */     super.updateElementInformation();
/*    */   }
/*    */
}


/* Location:              C:\Users\ManEXpen\Downloads\LaserCraft-1.7.10-0.2B4.jar!\laser\gui\client\generator\GuiGeneratorBase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */