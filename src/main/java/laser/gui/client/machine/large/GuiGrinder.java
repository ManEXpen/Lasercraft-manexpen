/*    */ package laser.gui.client.machine.large;
/*    */ import cofh.lib.gui.element.ElementDualScaled;
/*    */ import net.minecraft.entity.player.InventoryPlayer;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */
/*    */ public class GuiGrinder extends GuiLargeMachineBase
/*    */ {
/* 12 */   public static final ResourceLocation TEXTURE = new ResourceLocation("laser:textures/gui/machine/Grinder.png");
/*    */   ElementDualScaled progress;
/*    */   ElementDualScaled speed;
/*    */
/*    */   public GuiGrinder(InventoryPlayer paramInventoryPlayer, TileEntity paramTileEntity)
/*    */   {
/* 18 */     super(new laser.gui.container.machine.large.ContainerGrinder(paramInventoryPlayer, paramTileEntity), paramTileEntity, paramInventoryPlayer.player, TEXTURE);
/*    */   }
/*    */
/*    */   public void initGui()
/*    */   {
/* 23 */     super.initGui();
/*    */
/* 25 */     addElement(new cofh.lib.gui.element.ElementEnergyStored(this, 8, 8, this.myTile.getEnergyStorage()));
/* 26 */     this.progress = ((ElementDualScaled)addElement(new ElementDualScaled(this, 87, 34).setMode(1).setSize(24, 16).setTexture("cofh:textures/gui/elements/Progress_Arrow_Right.png", 48, 16)));
/* 27 */     this.speed = ((ElementDualScaled)addElement(new ElementDualScaled(this, 91, 52).setSize(16, 16).setTexture("cofh:textures/gui/elements/Scale_Saw.png", 32, 16)));
/*    */   }
/*    */
/*    */   protected void updateElementInformation()
/*    */   {
/* 32 */     super.updateElementInformation();
/*    */
/* 34 */     this.progress.setQuantity(this.myTile.getScaledProgress(24));
/* 35 */     this.speed.setQuantity(this.myTile.getScaledSpeed(16));
/*    */   }
/*    */
@Override
protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
	//ここに画像後で実装

} }


/* Location:              C:\Users\ManEXpen\Downloads\LaserCraft-1.7.10-0.2B4.jar!\laser\gui\client\machine\large\GuiGrinder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */