/*    */ package laser.gui;
/*    */
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ import cpw.mods.fml.relauncher.SideOnly;
/*    */ import laser.Laser;
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemStack;
/*    */
/*    */ public class CreativeTabItems extends CreativeTabs
/*    */ {
/*    */   public CreativeTabItems()
/*    */   {
/* 14 */     super("Laser");
/*    */   }
/*    */
/*    */   public Item getTabIconItem()
/*    */   {
/* 19 */     return getIconItemStack().getItem();
/*    */   }
/*    */
/*    */   public ItemStack getIconItemStack()
/*    */   {
/* 24 */     return Laser.swordBediron;
/*    */   }
/*    */
/*    */   @SideOnly(Side.CLIENT)
/*    */   public String getTabLabel()
/*    */   {
/* 30 */     return "laser.creativeTabItems";
/*    */   }
/*    */ }


/* Location:              C:\Users\ManEXpen\Downloads\LaserCraft-1.7.10-0.2B4.jar!\laser\gui\CreativeTabItems.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */