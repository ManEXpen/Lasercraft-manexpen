/*    */ package laser.gui.client.machine;
/*    */
/*    */ import cofh.lib.gui.element.ElementBase;
/*    */ import cofh.lib.gui.element.ElementDualScaled;
/*    */ import cofh.lib.gui.element.ElementEnergyStored;
/*    */ import laser.gui.element.ElementSlotOverlay9;
/*    */ import net.minecraft.entity.player.InventoryPlayer;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import thermalexpansion.gui.element.ElementSlotOverlay;
/*    */
/*    */ public class GuiAlloySmelter extends GuiMachineBase
/*    */ {
/* 15 */   public static final ResourceLocation TEXTURE = new ResourceLocation("laser:textures/gui/machine/AlloySmelter.png");
/*    */   ElementBase slotInput;
/*    */   ElementBase slotOutput;
/*    */   ElementDualScaled progress;
/*    */   ElementDualScaled speed;
/*    */
/*    */   public GuiAlloySmelter(InventoryPlayer paramInventoryPlayer, TileEntity paramTileEntity)
/*    */   {
/* 23 */     super(new laser.gui.container.machine.ContainerAlloySmelter(paramInventoryPlayer, paramTileEntity), paramTileEntity, paramInventoryPlayer.player, TEXTURE);
/*    */   }
/*    */
/*    */   public void initGui()
/*    */   {
/* 28 */     super.initGui();
/*    */
/* 30 */     this.slotInput = addElement(new ElementSlotOverlay9(this, 31, 16).setSlotColor(0));
/* 31 */     this.slotOutput = addElement(new ElementSlotOverlay(this, 125, 30).setSlotInfo(3, 2, 2));
/*    */
/* 33 */     addElement(new ElementEnergyStored(this, 8, 8, this.myTile.getEnergyStorage()));
/* 34 */     this.progress = ((ElementDualScaled)addElement(new ElementDualScaled(this, 92, 34).setMode(1).setSize(24, 16).setTexture("cofh:textures/gui/elements/Progress_Arrow_Right.png", 48, 16)));
/* 35 */     this.speed = ((ElementDualScaled)addElement(new ElementDualScaled(this, 96, 52).setSize(16, 16).setTexture("cofh:textures/gui/elements/Scale_Flame.png", 32, 16)));
/*    */   }
/*    */
/*    */   protected void updateElementInformation()
/*    */   {
/* 40 */     super.updateElementInformation();
/*    */
/* 42 */     this.slotInput.setVisible(this.myTile.hasSide(1));
/* 43 */     this.slotOutput.setVisible(this.myTile.hasSide(2));
/*    */
/* 45 */     this.progress.setQuantity(this.myTile.getScaledProgress(24));
/* 46 */     this.speed.setQuantity(this.myTile.getScaledSpeed(16));
/*    */   }
/*    */
@Override
protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
	//ここに画像後で実装

} }


/* Location:              C:\Users\ManEXpen\Downloads\LaserCraft-1.7.10-0.2B4.jar!\laser\gui\client\machine\GuiAlloySmelter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */