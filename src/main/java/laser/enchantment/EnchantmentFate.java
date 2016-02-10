package laser.enchantment;

import laser.Laser;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;

public class EnchantmentFate extends Enchantment {

   public EnchantmentFate(int paramInt1, int paramInt2) {
      super(paramInt1, paramInt2, Laser.enchantmentTypeLC);
      this.setName("laser.fate");
   }

   public int getMinEnchantability(int p_77321_1_) {
      return 15 + (p_77321_1_ - 1) * 9;
   }

   public int getMaxEnchantability(int p_77317_1_) {
      return super.getMinEnchantability(p_77317_1_) + 50;
   }

   public int getMaxLevel() {
      return 3;
   }

   public boolean canApply(ItemStack p_92089_1_) {
      return p_92089_1_.getItem() == Laser.sword;
   }
}
