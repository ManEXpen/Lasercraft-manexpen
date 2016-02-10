/*    */ package laser.gui;
/*    */
/*    */ import cofh.core.block.TileCoFHBase;
/*    */ import cpw.mods.fml.common.network.IGuiHandler;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.world.World;
/*    */
/*    */ public class GuiHandler implements IGuiHandler
/*    */ {
/*    */   public Object getClientGuiElement(int paramInt1, EntityPlayer paramEntityPlayer, World paramWorld, int paramInt2, int paramInt3, int paramInt4)
/*    */   {
/* 13 */     switch (paramInt1) {
/*    */     case 0:
/* 15 */       TileEntity localTileEntity = paramWorld.getTileEntity(paramInt2, paramInt3, paramInt4);
/* 16 */       if ((localTileEntity instanceof TileCoFHBase)) {
/* 17 */         return ((TileCoFHBase)localTileEntity).getGuiClient(paramEntityPlayer.inventory);
/*    */       }
/* 19 */       return null;
/*    */     }
/* 21 */     return null;
/*    */   }
/*    */
/*    */   public Object getServerGuiElement(int paramInt1, EntityPlayer paramEntityPlayer, World paramWorld, int paramInt2, int paramInt3, int paramInt4)
/*    */   {
/* 26 */     switch (paramInt1) {
/*    */     case 0:
/* 28 */       TileEntity localTileEntity = paramWorld.getTileEntity(paramInt2, paramInt3, paramInt4);
/* 29 */       if ((localTileEntity instanceof TileCoFHBase)) {
/* 30 */         return ((TileCoFHBase)localTileEntity).getGuiServer(paramEntityPlayer.inventory);
/*    */       }
/* 32 */       return null;
/*    */     }
/* 34 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\ManEXpen\Downloads\LaserCraft-1.7.10-0.2B4.jar!\laser\gui\GuiHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */