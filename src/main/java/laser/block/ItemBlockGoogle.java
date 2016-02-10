package laser.block;

import cofh.lib.util.helpers.StringHelper;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockGoogle extends ItemBlock {

   public ItemBlockGoogle(Block paramBlock) {
      super(paramBlock);
   }

   public String func_77653_i(ItemStack paramItemStack) {
      return StringHelper.localize(this.func_77667_c(paramItemStack));
   }

   public String func_77667_c(ItemStack paramItemStack) {
      return "tile.laser.google.name";
   }

   public EnumRarity func_77613_e(ItemStack paramItemStack) {
      return EnumRarity.uncommon;
   }

   public void func_77624_a(ItemStack paramItemStack, EntityPlayer paramEntityPlayer, List paramList, boolean paramBoolean) {
      paramList.add(StringHelper.localize("info.laser.google"));
   }
}
