/*    */ package laser.gui.client.generator;
/*    */
/*    */ import cofh.lib.gui.element.ElementDualScaled;
/*    */ import laser.gui.container.generator.ContainerGeneratorCombustion;
/*    */ import net.minecraft.entity.player.InventoryPlayer;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */
/*    */ public class GuiGeneratorCombustion extends GuiGeneratorBase
/*    */ {
/* 11 */   static final ResourceLocation TEXTURE = new ResourceLocation("thermalexpansion:textures/gui/dynamo/DynamoEnervation.png");
/*    */   ElementDualScaled duration;
/*    */
/*    */   public GuiGeneratorCombustion(InventoryPlayer paramInventoryPlayer, TileEntity paramTileEntity)
/*    */   {
/* 16 */     super(new ContainerGeneratorCombustion(paramInventoryPlayer, paramTileEntity), paramTileEntity, paramInventoryPlayer.player, TEXTURE);
/*    */   }
/*    */
/*    */   public void initGui()
/*    */   {
/* 21 */     super.initGui();
/*    */
/* 23 */     this.duration = ((ElementDualScaled)addElement(new ElementDualScaled(this, 115, 35).setSize(16, 16).setTexture("cofh:textures/gui/elements/Scale_Flame.png", 32, 16)));
/*    */   }
/*    */
/*    */   protected void updateElementInformation()
/*    */   {
/* 28 */     super.updateElementInformation();
/*    */
/* 30 */     this.duration.setQuantity(this.myTile.getScaledDuration(16));
/*    */   }
/*    */
@Override
protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
	//ここに画像後で実装

} }


/* Location:              C:\Users\ManEXpen\Downloads\LaserCraft-1.7.10-0.2B4.jar!\laser\gui\client\generator\GuiGeneratorCombustion.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */