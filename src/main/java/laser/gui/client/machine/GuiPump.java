/*    */ package laser.gui.client.machine;
/*    */
/*    */ import cofh.lib.gui.element.ElementBase;
/*    */ import cofh.lib.gui.element.ElementDualScaled;
/*    */ import cofh.lib.gui.element.ElementEnergyStored;
/*    */ import cofh.lib.gui.element.ElementFluid;
/*    */ import cofh.lib.gui.element.ElementFluidTank;
/*    */ import laser.block.machine.TilePump;
/*    */ import net.minecraft.entity.player.InventoryPlayer;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import thermalexpansion.gui.element.ElementSlotOverlay;
/*    */
/*    */ public class GuiPump extends GuiMachineBase
/*    */ {
/* 17 */   public static final ResourceLocation TEXTURE = new ResourceLocation("laser:textures/gui/machine/Pump.png");
/*    */   ElementBase slotOutput;
/*    */   ElementFluid progressFluid;
/*    */   ElementDualScaled progressOverlay;
/*    */   ElementDualScaled progress;
/*    */
/*    */   public GuiPump(InventoryPlayer paramInventoryPlayer, TileEntity paramTileEntity)
/*    */   {
/* 25 */     super(new laser.gui.container.machine.ContainerPump(paramInventoryPlayer, paramTileEntity), paramTileEntity, paramInventoryPlayer.player, TEXTURE);
/*    */   }
/*    */
/*    */   public void initGui()
/*    */   {
/* 30 */     super.initGui();
/*    */
/* 32 */     this.slotOutput = addElement(new ElementSlotOverlay(this, 152, 9).setSlotInfo(3, 3, 2));
/*    */
/* 34 */     addElement(new ElementEnergyStored(this, 8, 8, this.myTile.getEnergyStorage()));
/* 35 */     addElement(new ElementFluidTank(this, 152, 9, ((TilePump)this.myTile).getTank()).setGauge(1));
/* 36 */     this.progressFluid = ((ElementFluid)addElement(new ElementFluid(this, 76, 34).setFluid(((TilePump)this.myTile).getTankFluid()).setSize(24, 16)));
/* 37 */     this.progressOverlay = ((ElementDualScaled)addElement(new ElementDualScaled(this, 76, 34).setMode(1).setBackground(false).setSize(24, 16).setTexture("cofh:textures/gui/elements/Progress_Fluid_Right.png", 48, 16)));
/* 38 */     this.progress = ((ElementDualScaled)addElement(new ElementDualScaled(this, 76, 34).setMode(1).setSize(24, 16).setTexture("cofh:textures/gui/elements/Progress_Arrow_Right.png", 48, 16)));
/*    */   }
/*    */
/*    */   protected void updateElementInformation()
/*    */   {
/* 43 */     super.updateElementInformation();
/*    */
/* 45 */     this.slotOutput.setVisible(this.myTile.hasSide(1));
/* 46 */     this.progress.setVisible(!((TilePump)this.myTile).pump);
/*    */
/* 48 */     this.progressFluid.setFluid(((TilePump)this.myTile).getTankFluid());
/* 49 */     this.progressFluid.setSize(this.myTile.getScaledProgress(24), 16);
/* 50 */     this.progressOverlay.setQuantity(this.myTile.getScaledProgress(24));
/* 51 */     this.progress.setQuantity(this.myTile.getScaledProgress(24));
/*    */   }
/*    */
@Override
protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
	//ここに画像後で実装

} }


/* Location:              C:\Users\ManEXpen\Downloads\LaserCraft-1.7.10-0.2B4.jar!\laser\gui\client\machine\GuiPump.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */