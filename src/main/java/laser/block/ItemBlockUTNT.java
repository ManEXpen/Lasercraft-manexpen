package laser.block;

import cofh.lib.util.helpers.StringHelper;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockUTNT extends ItemBlock {

   public ItemBlockUTNT(Block paramBlock) {
      super(paramBlock);
   }

   public String func_77653_i(ItemStack paramItemStack) {
      return StringHelper.localize(this.func_77667_c(paramItemStack));
   }

   public String func_77667_c(ItemStack paramItemStack) {
      return "tile.laser.utnt.name";
   }
}
