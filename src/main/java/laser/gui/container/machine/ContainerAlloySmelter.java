/*    */ package laser.gui.container.machine;
/*    */
/*    */ import cofh.lib.gui.slot.ISlotValidator;
/*    */ import cofh.lib.gui.slot.SlotEnergy;
/*    */ import cofh.lib.gui.slot.SlotRemoveOnly;
/*    */ import cofh.lib.gui.slot.SlotValidated;
/*    */ import laser.block.machine.TileAlloySmelter;
/*    */ import laser.util.crafting.AlloySmelterManager;
/*    */ import net.minecraft.entity.player.InventoryPlayer;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import thermalexpansion.gui.container.ContainerTEBase;
/*    */
/*    */ public class ContainerAlloySmelter extends ContainerTEBase implements ISlotValidator
/*    */ {
/*    */   TileAlloySmelter myTile;
/*    */
/*    */   public ContainerAlloySmelter(InventoryPlayer paramInventoryPlayer, TileEntity paramTileEntity)
/*    */   {
/* 20 */     super(paramInventoryPlayer, paramTileEntity);
/*    */
/* 22 */     this.myTile = ((TileAlloySmelter)paramTileEntity);
/* 23 */     addSlotToContainer(new SlotValidated(this, this.myTile, 0, 31, 16));
/* 24 */     addSlotToContainer(new SlotValidated(this, this.myTile, 1, 49, 16));
/* 25 */     addSlotToContainer(new SlotValidated(this, this.myTile, 2, 67, 16));
/* 26 */     addSlotToContainer(new SlotValidated(this, this.myTile, 3, 31, 34));
/* 27 */     addSlotToContainer(new SlotValidated(this, this.myTile, 4, 49, 34));
/* 28 */     addSlotToContainer(new SlotValidated(this, this.myTile, 5, 67, 34));
/* 29 */     addSlotToContainer(new SlotValidated(this, this.myTile, 6, 31, 52));
/* 30 */     addSlotToContainer(new SlotValidated(this, this.myTile, 7, 49, 52));
/* 31 */     addSlotToContainer(new SlotValidated(this, this.myTile, 8, 67, 52));
/* 32 */     addSlotToContainer(new SlotRemoveOnly(this.myTile, 9, 129, 34));
/* 33 */     addSlotToContainer(new SlotRemoveOnly(this.myTile, 10, 147, 34));
/* 34 */     addSlotToContainer(new SlotEnergy(this.myTile, this.myTile.getChargeSlot(), 8, 53));
/*    */   }
/*    */
/*    */   public boolean isItemValid(ItemStack paramItemStack)
/*    */   {
/* 39 */     return AlloySmelterManager.isItemValid(paramItemStack);
/*    */   }
/*    */ }


/* Location:              C:\Users\ManEXpen\Downloads\LaserCraft-1.7.10-0.2B4.jar!\laser\gui\container\machine\ContainerAlloySmelter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */