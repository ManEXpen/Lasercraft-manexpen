/*    */ package laser.gui.container.machine.large;
/*    */
/*    */ import cofh.lib.gui.slot.ISlotValidator;
/*    */ import cofh.lib.gui.slot.SlotEnergy;
/*    */ import cofh.lib.gui.slot.SlotRemoveOnly;
/*    */ import cofh.lib.gui.slot.SlotValidated;
/*    */ import laser.block.machine.large.TileGrinder;
/*    */ import laser.util.crafting.GrinderManager;
/*    */ import net.minecraft.entity.player.InventoryPlayer;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import thermalexpansion.gui.container.ContainerTEBase;
/*    */
/*    */ public class ContainerGrinder extends ContainerTEBase implements ISlotValidator
/*    */ {
/*    */   TileGrinder myTile;
/*    */
/*    */   public ContainerGrinder(InventoryPlayer paramInventoryPlayer, TileEntity paramTileEntity)
/*    */   {
/* 20 */     super(paramInventoryPlayer, paramTileEntity);
/*    */
/* 22 */     this.myTile = ((TileGrinder)paramTileEntity);
/* 23 */     addSlotToContainer(new SlotValidated(this, this.myTile, 0, 30, 17));
/* 24 */     addSlotToContainer(new SlotValidated(this, this.myTile, 1, 48, 17));
/* 25 */     addSlotToContainer(new SlotValidated(this, this.myTile, 2, 66, 17));
/* 26 */     addSlotToContainer(new SlotValidated(this, this.myTile, 3, 30, 35));
/* 27 */     addSlotToContainer(new SlotValidated(this, this.myTile, 4, 48, 35));
/* 28 */     addSlotToContainer(new SlotValidated(this, this.myTile, 5, 66, 35));
/* 29 */     addSlotToContainer(new SlotValidated(this, this.myTile, 6, 30, 53));
/* 30 */     addSlotToContainer(new SlotValidated(this, this.myTile, 7, 48, 53));
/* 31 */     addSlotToContainer(new SlotValidated(this, this.myTile, 8, 66, 53));
/* 32 */     addSlotToContainer(new SlotRemoveOnly(this.myTile, 9, 116, 17));
/* 33 */     addSlotToContainer(new SlotRemoveOnly(this.myTile, 10, 134, 17));
/* 34 */     addSlotToContainer(new SlotRemoveOnly(this.myTile, 11, 152, 17));
/* 35 */     addSlotToContainer(new SlotRemoveOnly(this.myTile, 12, 116, 35));
/* 36 */     addSlotToContainer(new SlotRemoveOnly(this.myTile, 13, 134, 35));
/* 37 */     addSlotToContainer(new SlotRemoveOnly(this.myTile, 14, 152, 35));
/* 38 */     addSlotToContainer(new SlotRemoveOnly(this.myTile, 15, 116, 53));
/* 39 */     addSlotToContainer(new SlotRemoveOnly(this.myTile, 16, 134, 53));
/* 40 */     addSlotToContainer(new SlotRemoveOnly(this.myTile, 17, 152, 53));
/* 41 */     addSlotToContainer(new SlotEnergy(this.myTile, this.myTile.getChargeSlot(), 8, 53));
/*    */   }
/*    */
/*    */   public boolean isItemValid(ItemStack paramItemStack)
/*    */   {
/* 46 */     return GrinderManager.recipeExists(paramItemStack);
/*    */   }
/*    */ }


/* Location:              C:\Users\ManEXpen\Downloads\LaserCraft-1.7.10-0.2B4.jar!\laser\gui\container\machine\large\ContainerGrinder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */