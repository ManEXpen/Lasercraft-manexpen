package laser.item.tool;

import java.lang.reflect.Method;
import java.util.List;

import cofh.api.energy.IEnergyContainerItem;
import cofh.core.item.ItemBase;
import cofh.lib.util.helpers.EnergyHelper;
import cofh.lib.util.helpers.StringHelper;
import laser.Laser;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;

public class ItemRockcutter extends ItemBase implements IEnergyContainerItem {

   public final int[] maxEnergy = new int[]{400000, 400000};
   public final int[] maxTransfer = new int[]{800, 800};
   public final int[] energyPerUse = new int[]{800, 1600};


   public ItemRockcutter() {
      super("laser");
      this.setMaxDamage(1);
      this.setMaxStackSize(1);
      this.setCreativeTab(Laser.tabItems);
      this.setUnlocalizedName("rockcutter");
   }

   public void getSubItems(Item paramItem, CreativeTabs paramCreativeTabs, List paramList) {
      for(int i = 0; i < this.maxEnergy.length; ++i) {
         paramList.add(EnergyHelper.setDefaultEnergyTag(new ItemStack(paramItem, 1, i), 0));
         paramList.add(EnergyHelper.setDefaultEnergyTag(new ItemStack(paramItem, 1, i), this.maxEnergy[i]));
      }

   }

   public void addInformation(ItemStack paramItemStack, EntityPlayer paramEntityPlayer, List paramList, boolean paramBoolean) {
      if(StringHelper.displayShiftForDetail && !StringHelper.isShiftKeyDown()) {
         paramList.add(StringHelper.shiftForDetails());
      }

      if(StringHelper.isShiftKeyDown()) {
         if(paramItemStack.stackTagCompound == null) {
            EnergyHelper.setDefaultEnergyTag(paramItemStack, 0);
         }

         paramList.add(StringHelper.localize("info.cofh.charge") + ": " + paramItemStack.stackTagCompound.getInteger("Energy") + " / " + this.maxEnergy[paramItemStack.getItemDamage()] + " RF");
         paramList.add("§6" + this.energyPerUse[paramItemStack.getItemDamage()] + " RF " + StringHelper.localize("info.cofh.perUse") + "§r");
      }
   }

   public void onUpdate(ItemStack paramItemStack, World paramWorld, Entity paramEntity, int paramInt, boolean paramBoolean) {
      int energy = this.energyPerUse[paramItemStack.getItemDamage()];
      if(paramBoolean && paramEntity instanceof EntityPlayer && ((EntityPlayer)paramEntity).swingProgressInt == -1 && !((EntityPlayer)paramEntity).capabilities.isCreativeMode && this.extractEnergy(paramItemStack, energy, true) == energy) {
         MovingObjectPosition movingobjectposition = this.getMovingObjectPositionFromPlayer(paramWorld, (EntityPlayer)paramEntity, false);
         if(movingobjectposition != null && movingobjectposition.typeOfHit == MovingObjectType.BLOCK) {
            int i = movingobjectposition.blockX;
            int j = movingobjectposition.blockY;
            int k = movingobjectposition.blockZ;
            Block block = paramWorld.getBlock(i, j, k);
            if(j > 0 && (block.getMaterial() == Material.rock || block.getMaterial() == Material.glass) && (block != Blocks.bedrock || paramItemStack.getItemDamage() == 1)) {
               ItemStack localItemStack = null;

               for(Class clazz = block.getClass(); clazz != null; clazz = clazz.getSuperclass()) {
                  try {
                     Method f = clazz.getDeclaredMethod("func_149644_j", new Class[]{Integer.TYPE});
                     if(f != null) {
                        f.setAccessible(true);
                        localItemStack = (ItemStack)f.invoke(block, new Object[]{Integer.valueOf(paramWorld.getBlockMetadata(i, j, k))});
                     }
                  } catch (Exception var22) {
                     ;
                  }
               }

               if(localItemStack != null) {
                  if(!paramWorld.isRemote) {
                     paramWorld.playAuxSFXAtEntity((EntityPlayer)paramEntity, 2001, i, j, k, Block.getIdFromBlock(block) + (paramWorld.getBlockMetadata(i, j, k) << 12));
                  } else {
                     this.extractEnergy(paramItemStack, energy, false);
                     paramWorld.setBlockToAir(i, j, k);
                     float f1 = 0.3F;
                     double d1 = (double)(paramWorld.rand.nextFloat() * f1) + (double)(1.0F - f1) * 0.5D;
                     double d2 = (double)(paramWorld.rand.nextFloat() * f1) + (double)(1.0F - f1) * 0.5D;
                     double d3 = (double)(paramWorld.rand.nextFloat() * f1) + (double)(1.0F - f1) * 0.5D;
                     EntityItem localEntityItem = new EntityItem(paramWorld, (double)i + d1, (double)j + d2, (double)k + d3, localItemStack);
                     localEntityItem.delayBeforeCanPickup = 10;
                     paramWorld.spawnEntityInWorld(localEntityItem);
                  }
               }
            }
         }
      }

   }

   public boolean isFull3D() {
      return true;
   }

   public boolean isDamaged(ItemStack paramItemStack) {
      return paramItemStack.getItemDamage() != 32767;
   }

   public int getDisplayDamage(ItemStack paramItemStack) {
      if(paramItemStack.stackTagCompound == null) {
         EnergyHelper.setDefaultEnergyTag(paramItemStack, 0);
      }

      return 1 + this.maxEnergy[paramItemStack.getItemDamage()] - paramItemStack.stackTagCompound.getInteger("Energy");
   }

   public int getMaxDamage(ItemStack paramItemStack) {
      return 1 + this.maxEnergy[paramItemStack.getItemDamage()];
   }

   public int receiveEnergy(ItemStack paramItemStack, int paramInt, boolean paramBoolean) {
      if(paramItemStack.stackTagCompound == null) {
         EnergyHelper.setDefaultEnergyTag(paramItemStack, 0);
      }

      int i = paramItemStack.stackTagCompound.getInteger("Energy");
      int j = Math.min(paramInt, Math.min(this.maxEnergy[paramItemStack.getItemDamage()] - i, this.maxTransfer[paramItemStack.getItemDamage()]));
      if(!paramBoolean) {
         i += j;
         paramItemStack.stackTagCompound.setInteger("Energy", i);
      }

      return j;
   }

   public int extractEnergy(ItemStack paramItemStack, int paramInt, boolean paramBoolean) {
      if(paramItemStack.stackTagCompound == null) {
         EnergyHelper.setDefaultEnergyTag(paramItemStack, 0);
      }

      int i = paramItemStack.stackTagCompound.getInteger("Energy");
      int j = Math.min(paramInt, i);
      if(!paramBoolean) {
         i -= j;
         paramItemStack.stackTagCompound.setInteger("Energy", i);
      }

      return j;
   }

   public int getEnergyStored(ItemStack paramItemStack) {
      if(paramItemStack.stackTagCompound == null) {
         EnergyHelper.setDefaultEnergyTag(paramItemStack, 0);
      }

      return paramItemStack.stackTagCompound.getInteger("Energy");
   }

   public int getMaxEnergyStored(ItemStack paramItemStack) {
      return this.maxEnergy[paramItemStack.getItemDamage()];
   }
}
