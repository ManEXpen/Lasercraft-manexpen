/*    */ package laser.gui.container.machine;
/*    */
/*    */ import cofh.lib.gui.slot.SlotEnergy;
/*    */ import laser.block.machine.TileChunkQuarry;
/*    */ import net.minecraft.entity.player.InventoryPlayer;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import thermalexpansion.gui.container.ContainerTEBase;
/*    */
/*    */ public class ContainerChunkQuarry extends ContainerTEBase
/*    */ {
/*    */   TileChunkQuarry myTile;
/*    */
/*    */   public ContainerChunkQuarry(InventoryPlayer paramInventoryPlayer, TileEntity paramTileEntity)
/*    */   {
/* 15 */     super(paramInventoryPlayer, paramTileEntity);
/*    */
/* 17 */     this.myTile = ((TileChunkQuarry)paramTileEntity);
/* 18 */     addSlotToContainer(new SlotEnergy(this.myTile, this.myTile.getChargeSlot(), 8, 53));
/*    */   }
/*    */ }


/* Location:              C:\Users\ManEXpen\Downloads\LaserCraft-1.7.10-0.2B4.jar!\laser\gui\container\machine\ContainerChunkQuarry.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */