package laser.item.tool;

import java.util.List;
import java.util.Random;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import cofh.core.item.ItemBase;
import laser.Laser;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentDurability;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatList;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class ItemSword extends ItemBase {

   public final int[] maxDamage = new int[]{4096};
   public final float[] attackDamage = new float[]{16.0F};
   public final int[] enchantability = new int[]{32};


   public ItemSword() {
      super("laser");
      this.setMaxDamage(1);
      this.setMaxStackSize(1);
      this.setNoRepair();
      this.setCreativeTab(Laser.tabItems);
      this.func_77655_b("sword");
   }

   public boolean attemptDamageItem(ItemStack paramItemStack, int paramInt, Random paramRandom) {
      if(paramInt > 0) {
         int j = EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, paramItemStack);
         int k = 0;

         for(int l = 0; j > 0 && l < paramInt; ++l) {
            if(EnchantmentDurability.negateDamage(paramItemStack, j, paramRandom)) {
               ++k;
            }
         }

         paramInt -= k;
         if(paramInt <= 0) {
            return false;
         }
      }

      paramItemStack.stackTagCompound.setInteger("Damage", paramItemStack.stackTagCompound.getInteger("Damage") + paramInt);
      return paramItemStack.stackTagCompound.getInteger("Damage") > this.maxDamage[paramItemStack.getItemDamage()];
   }

   public void damageItem(ItemStack paramItemStack, int paramInt, EntityLivingBase paramEntityLivingBase) {
      if(paramItemStack.stackTagCompound == null) {
         paramItemStack.setTagCompound(new NBTTagCompound());
         paramItemStack.stackTagCompound.setInteger("Damage", 0);
      }

      if((!(paramEntityLivingBase instanceof EntityPlayer) || !((EntityPlayer)paramEntityLivingBase).capabilities.isCreativeMode) && this.attemptDamageItem(paramItemStack, paramInt, paramEntityLivingBase.getRNG())) {
         paramEntityLivingBase.renderBrokenItemStack(paramItemStack);
         --paramItemStack.stackSize;
         if(paramEntityLivingBase instanceof EntityPlayer) {
            EntityPlayer entityplayer = (EntityPlayer)paramEntityLivingBase;
            entityplayer.addStat(StatList.objectBreakStats[Item.getIdFromItem(paramItemStack.getItem())], 1);
         }

         if(paramItemStack.stackSize < 0) {
            paramItemStack.stackSize = 0;
         }

         paramItemStack.stackTagCompound.setInteger("Damage", 0);
      }

   }

   public boolean hitEntity(ItemStack paramItemStack, EntityLivingBase paramEntityLivingBase1, EntityLivingBase paramEntityLivingBase2) {
      int i = EnchantmentHelper.getEnchantmentLevel(Laser.enchantmentOverload.effectId, paramItemStack);
      if(i > 0) {
         this.damageItem(paramItemStack, 2 * i, paramEntityLivingBase2);
         if(paramEntityLivingBase2.getRNG().nextInt(10) < i) {
            paramEntityLivingBase2.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer)paramEntityLivingBase2), (float)i);
         }
      } else {
         this.damageItem(paramItemStack, 1, paramEntityLivingBase2);
      }

      return true;
   }

   public boolean onBlockDestroyed(ItemStack paramItemStack, World paramWorld, Block paramBlock, int paramInt1, int paramInt2, int paramInt3, EntityLivingBase paramEntityLivingBase) {
      if((double)paramBlock.getBlockHardness(paramWorld, paramInt1, paramInt2, paramInt3) != 0.0D) {
         this.damageItem(paramItemStack, 2, paramEntityLivingBase);
      }

      return true;
   }

   public ItemStack onItemRightClick(ItemStack p_77659_1_, World p_77659_2_, EntityPlayer p_77659_3_) {
      p_77659_3_.setItemInUse(p_77659_1_, this.getMaxItemUseDuration(p_77659_1_));
      return p_77659_1_;
   }

   public void addInformation(ItemStack paramItemStack, EntityPlayer paramEntityPlayer, List paramList, boolean paramBoolean) {
      if(paramItemStack.stackTagCompound == null) {
         paramItemStack.setTagCompound(new NBTTagCompound());
         paramItemStack.stackTagCompound.setInteger("Damage", 0);
      }

      paramList.add("Durability: " + (this.maxDamage[paramItemStack.getItemDamage()] - paramItemStack.stackTagCompound.getInteger("Damage")) + " / " + this.maxDamage[paramItemStack.getItemDamage()]);
   }

   public boolean isFull3D() {
      return true;
   }

   public boolean isDamageable() {
      return true;
   }

   public EnumAction getItemUseAction(ItemStack p_77661_1_) {
      return EnumAction.block;
   }

   public int getMaxItemUseDuration(ItemStack p_77626_1_) {
      return 72000;
   }

   public boolean func_150897_b(Block p_150897_1_) {
      return p_150897_1_ == Blocks.web;
   }

   public int getItemEnchantability(ItemStack paramItemStack) {
      return this.enchantability[paramItemStack.getItemDamage()];
   }

   public boolean isDamaged(ItemStack paramItemStack) {
      return paramItemStack.stackTagCompound != null && paramItemStack.stackTagCompound.getInteger("Damage") > 0;
   }

   public int getDisplayDamage(ItemStack paramItemStack) {
      if(paramItemStack.stackTagCompound == null) {
         paramItemStack.setTagCompound(new NBTTagCompound());
         paramItemStack.stackTagCompound.setInteger("Damage", 0);
      }

      return paramItemStack.stackTagCompound.getInteger("Damage");
   }

   public int getMaxDamage(ItemStack paramItemStack) {
      return this.maxDamage[paramItemStack.getItemDamage()];
   }

   public Multimap getAttributeModifiers(ItemStack paramItemStack) {
      HashMultimap multimap = HashMultimap.create();
      int i = (int)this.attackDamage[paramItemStack.getItemDamage()];
      if(paramItemStack.stackTagCompound != null) {
         i += (int)((double)paramItemStack.stackTagCompound.getInteger("Damage") / (double)this.maxDamage[paramItemStack.getItemDamage()] * 8.0D * (double)EnchantmentHelper.getEnchantmentLevel(Laser.enchantmentFate.effectId, paramItemStack));
         i += 2 * EnchantmentHelper.getEnchantmentLevel(Laser.enchantmentOverload.effectId, paramItemStack);
      }

      multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Weapon modifier", (double)i, 0));
      return multimap;
   }
}
