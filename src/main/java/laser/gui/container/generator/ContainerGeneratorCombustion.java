/*    */ package laser.gui.container.generator;
/*    */
/*    */ import cofh.lib.gui.slot.ISlotValidator;
/*    */ import cofh.lib.gui.slot.SlotValidated;
/*    */ import laser.block.generator.TileGeneratorCombustion;
/*    */ import net.minecraft.entity.player.InventoryPlayer;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import thermalexpansion.gui.container.ContainerTEBase;
/*    */
/*    */ public class ContainerGeneratorCombustion extends ContainerTEBase implements ISlotValidator
/*    */ {
/*    */   TileGeneratorCombustion myTile;
/*    */
/*    */   public ContainerGeneratorCombustion(InventoryPlayer paramInventoryPlayer, TileEntity paramTileEntity)
/*    */   {
/* 17 */     super(paramInventoryPlayer, paramTileEntity);
/*    */
/* 19 */     this.myTile = ((TileGeneratorCombustion)paramTileEntity);
/* 20 */     addSlotToContainer(new SlotValidated(this, this.myTile, 0, 44, 35));
/*    */   }
/*    */
/*    */
/*    */   public boolean isItemValid(ItemStack paramItemStack)
/*    */   {
/* 26 */     return TileGeneratorCombustion.getEnergyValue(paramItemStack) > 0;
/*    */   }
/*    */ }


/* Location:              C:\Users\ManEXpen\Downloads\LaserCraft-1.7.10-0.2B4.jar!\laser\gui\container\generator\ContainerGeneratorCombustion.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */