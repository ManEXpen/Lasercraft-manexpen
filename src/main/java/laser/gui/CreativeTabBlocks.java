package laser.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import laser.Laser;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CreativeTabBlocks extends CreativeTabs {

   public CreativeTabBlocks() {
      super("Laser");
   }

   public Item getTabIconItem() {
      return this.getIconItemStack().getItem();
   }

   public ItemStack getIconItemStack() {
      return Laser.machineChunkQuarry;
   }

   @SideOnly(Side.CLIENT)
   public String getTabLabel() {
      return "laser.creativeTabBlocks";
   }
}
