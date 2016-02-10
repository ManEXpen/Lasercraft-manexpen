package laser.block;

import cofh.core.item.ItemBlockBase;
import cofh.lib.util.helpers.ItemHelper;
import laser.block.BlockFrame;
import net.minecraft.block.Block;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;

public class ItemBlockFrame extends ItemBlockBase {

   public ItemBlockFrame(Block paramBlock) {
      super(paramBlock);
   }

   public String func_77667_c(ItemStack paramItemStack) {
      return "tile.laser.frame." + BlockFrame.NAMES[ItemHelper.getItemDamage(paramItemStack)] + ".name";
   }

   public EnumRarity func_77613_e(ItemStack paramItemStack) {
      return EnumRarity.values()[BlockFrame.RARITY[ItemHelper.getItemDamage(paramItemStack)]];
   }
}
