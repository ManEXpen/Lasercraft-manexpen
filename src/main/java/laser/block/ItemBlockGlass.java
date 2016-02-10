package laser.block;

import cofh.lib.util.helpers.ItemHelper;
import cofh.lib.util.helpers.StringHelper;
import net.minecraft.block.Block;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockGlass extends ItemBlock {

   public ItemBlockGlass(Block paramBlock) {
      super(paramBlock);
      this.setHasSubtypes(true);
      this.setMaxDamage(0);
   }

   public String getItemStackDisplayName(ItemStack paramItemStack) {
      return StringHelper.localize(this.getUnlocalizedName(paramItemStack));
   }

   public String getUnlocalizedName(ItemStack paramItemStack) {
      return "tile.laser.glass." + BlockGlass.NAMES[ItemHelper.getItemDamage(paramItemStack)] + ".name";
   }

   public int getMetadata(int paramInt) {
      return paramInt;
   }

   public EnumRarity getRarity(ItemStack paramItemStack) {
      return EnumRarity.values()[BlockGlass.RARITY[ItemHelper.getItemDamage(paramItemStack)]];
   }
}
