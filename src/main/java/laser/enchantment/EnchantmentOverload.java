package laser.enchantment;

import laser.Laser;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;

public class EnchantmentOverload extends Enchantment {

   public EnchantmentOverload(int paramInt1, int paramInt2) {
      super(paramInt1, paramInt2, Laser.enchantmentTypeLC);
      this.setName("laser.overload");
   }

   public int getMinEnchantability(int p_77321_1_) {
      return 1 + (p_77321_1_ - 1) * 11;
   }

   public int getMaxEnchantability(int p_77317_1_) {
      return super.getMinEnchantability(p_77317_1_) + 20;
   }

   public int getMaxLevel() {
      return 5;
   }

   public boolean canApply(ItemStack p_92089_1_) {
      return p_92089_1_.getItem() == Laser.sword;
   }
}
