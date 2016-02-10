package laser.item;

import cofh.core.item.ItemBase;
import laser.Laser;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemFood extends ItemBase {

   public static final int[] healAmounts = new int[]{5};
   public static final float[] saturationModifiers = new float[]{0.2F};


   public ItemFood() {
      super("laser");
      this.setCreativeTab(Laser.tabItems);
      this.setUnlocalizedName("food");
   }

   public ItemStack onEaten(ItemStack p_77654_1_, World p_77654_2_, EntityPlayer p_77654_3_) {
      --p_77654_1_.stackSize;
      p_77654_3_.getFoodStats().addStats(healAmounts[p_77654_1_.getItemDamage()], saturationModifiers[p_77654_1_.getItemDamage()]);
      p_77654_2_.playSoundAtEntity(p_77654_3_, "random.burp", 0.5F, p_77654_2_.rand.nextFloat() * 0.1F + 0.9F);
      this.onFoodEaten(p_77654_1_, p_77654_2_, p_77654_3_);
      return p_77654_1_;
   }

   public void onFoodEaten(ItemStack p_77849_1_, World p_77849_2_, EntityPlayer p_77849_3_) {
      if(p_77849_2_.isRemote && p_77849_1_.getItemDamage() == 0) {
         p_77849_3_.addPotionEffect(new PotionEffect(Potion.confusion.id, 200, 1));
         if(p_77849_2_.rand.nextFloat() < 0.75F) {
            p_77849_3_.addPotionEffect(new PotionEffect(Potion.poison.id, 200, 1));
         }
      }

   }

   public int getMaxItemUseDuration(ItemStack p_77626_1_) {
      return 32;
   }

   public EnumAction getItemUseAction(ItemStack p_77661_1_) {
      return EnumAction.eat;
   }

   public ItemStack onItemRightClick(ItemStack p_77659_1_, World p_77659_2_, EntityPlayer p_77659_3_) {
      if(p_77659_3_.canEat(false)) {
         p_77659_3_.setItemInUse(p_77659_1_, this.getMaxItemUseDuration(p_77659_1_));
      }

      return p_77659_1_;
   }

}
