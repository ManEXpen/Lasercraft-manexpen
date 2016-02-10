/*    */ package laser.gui.client.machine.large;
/*    */
/*    */ import cofh.core.gui.GuiBaseAdv;
/*    */ import cofh.core.gui.element.TabEnergy;
/*    */ import laser.block.machine.large.TileLargeMachineBase;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.inventory.Container;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */
/*    */ public abstract class GuiLargeMachineBase extends GuiBaseAdv
/*    */ {
/*    */   protected TileLargeMachineBase myTile;
/*    */
/*    */   public GuiLargeMachineBase(Container paramContainer, TileEntity paramTileEntity, EntityPlayer paramEntityPlayer, ResourceLocation paramResourceLocation)
/*    */   {
/* 19 */     super(paramContainer, paramResourceLocation);
/*    */
/* 21 */     this.myTile = ((TileLargeMachineBase)paramTileEntity);
/* 22 */     this.name = this.myTile.func_145825_b();
/*    */   }
/*    */
/*    */   public void initGui()
/*    */   {
/* 27 */     super.initGui();
/*    */
/* 29 */     addTab(new cofh.core.gui.element.TabRedstone(this, this.myTile));
/*    */
/* 31 */     if (this.myTile.getMaxEnergyStored(net.minecraftforge.common.util.ForgeDirection.UNKNOWN) > 0) {
/* 32 */       addTab(new TabEnergy(this, this.myTile, false));
/*    */     }
/*    */   }
/*    */
/*    */   public void updateScreen()
/*    */   {
/* 38 */     super.updateScreen();
/*    */
/* 40 */     if (!this.myTile.canAccess()) {
/* 41 */       this.mc.thePlayer.closeScreen();
/*    */     }
/*    */   }
/*    */
/*    */   protected void updateElementInformation() {
/* 46 */     super.updateElementInformation();
/*    */   }
/*    */ }


/* Location:              C:\Users\ManEXpen\Downloads\LaserCraft-1.7.10-0.2B4.jar!\laser\gui\client\machine\large\GuiLargeMachineBase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */