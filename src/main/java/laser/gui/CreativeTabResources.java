package laser.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import laser.Laser;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CreativeTabResources extends CreativeTabs {

   public CreativeTabResources() {
      super("Laser");
   }

   public Item getTabIconItem() {
      return this.getIconItemStack().getItem();
   }

   public ItemStack getIconItemStack() {
      return Laser.ingotBediron;
   }

   @SideOnly(Side.CLIENT)
   public String getTabLabel() {
      return "laser.creativeTabResources";
   }
}
