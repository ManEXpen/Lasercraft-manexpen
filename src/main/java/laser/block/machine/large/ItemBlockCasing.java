package laser.block.machine.large;

import cofh.core.item.ItemBlockBase;
import cofh.lib.util.helpers.ItemHelper;
import laser.block.machine.large.BlockCasing;
import net.minecraft.block.Block;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;

public class ItemBlockCasing extends ItemBlockBase {

   public ItemBlockCasing(Block paramBlock) {
      super(paramBlock);
   }

   public String func_77667_c(ItemStack paramItemStack) {
      return "tile.laser.casing." + BlockCasing.NAMES[ItemHelper.getItemDamage(paramItemStack)] + ".name";
   }

   public EnumRarity func_77613_e(ItemStack paramItemStack) {
      return EnumRarity.values()[BlockCasing.RARITY[ItemHelper.getItemDamage(paramItemStack)]];
   }
}
