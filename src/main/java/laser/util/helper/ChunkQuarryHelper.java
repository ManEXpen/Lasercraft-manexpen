package laser.util.helper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cofh.lib.util.helpers.StringHelper;
import laser.block.machine.TileChunkQuarry;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class ChunkQuarryHelper {

   public static NBTTagCompound setItemStackTagConfig(NBTTagCompound paramNBTTagCompound, TileChunkQuarry paramTileChunkQuarry) {
      if(paramTileChunkQuarry == null) {
         return null;
      } else {
         if(paramNBTTagCompound == null) {
            paramNBTTagCompound = new NBTTagCompound();
         }

         NBTTagList nbttaglist = new NBTTagList();
         Iterator i$ = paramTileChunkQuarry.stacks.iterator();

         while(i$.hasNext()) {
            ItemStack itemstack = (ItemStack)i$.next();
            nbttaglist.appendTag(itemstack.writeToNBT(new NBTTagCompound()));
         }

         paramNBTTagCompound.setTag("Stacks", nbttaglist);
         paramNBTTagCompound.setBoolean("ShowBorder", paramTileChunkQuarry.showBorder);
         paramNBTTagCompound.setBoolean("BuildBarrier", paramTileChunkQuarry.buildBarrier);
         return paramNBTTagCompound;
      }
   }

   public static void addConfigInformation(ItemStack paramItemStack, List paramList) {
      if(isConfigured(paramItemStack)) {
         int i = (NBTTagList)paramItemStack.stackTagCompound.getTag("Stacks") == null?0:((NBTTagList)paramItemStack.stackTagCompound.getTag("Stacks")).tagCount();
         paramList.add(StringHelper.localize("info.laser.chunkQuarry.stacks") + " : " + i + " / " + '\u9c40' + " stacks");
         paramList.add(StringHelper.localize("info.laser.chunkQuarry.showBorder") + " : " + paramItemStack.stackTagCompound.getBoolean("ShowBorder"));
         paramList.add(StringHelper.localize("info.laser.chunkQuarry.buildBarrier") + " : " + paramItemStack.stackTagCompound.getBoolean("BuildBarrier"));
      }

   }

   public static boolean isConfigured(ItemStack paramItemStack) {
      return paramItemStack.stackTagCompound == null?false:paramItemStack.stackTagCompound.hasKey("ShowBorder");
   }

   public static boolean setStacks(ItemStack paramItemStack, ArrayList paramArrayList) {
      if(paramItemStack.stackTagCompound == null) {
         paramItemStack.stackTagCompound = new NBTTagCompound();
      }

      NBTTagList nbttaglist = new NBTTagList();
      Iterator i$ = paramArrayList.iterator();

      while(i$.hasNext()) {
         ItemStack itemstack = (ItemStack)i$.next();
         nbttaglist.appendTag(itemstack.writeToNBT(new NBTTagCompound()));
      }

      paramItemStack.stackTagCompound.setTag("Stacks", nbttaglist);
      return true;
   }

   public static ArrayList getStacks(ItemStack paramItemStack) {
      if(paramItemStack.stackTagCompound == null) {
         return new ArrayList();
      } else {
         ArrayList arraylist = new ArrayList();
         NBTTagList nbttaglist = (NBTTagList)paramItemStack.stackTagCompound.getTag("Stacks");
         if(nbttaglist == null) {
            return arraylist;
         } else {
            for(int i = 0; i < nbttaglist.tagCount(); ++i) {
               arraylist.add(ItemStack.loadItemStackFromNBT(nbttaglist.getCompoundTagAt(i)));
            }

            return arraylist;
         }
      }
   }

   public static boolean setIsShowBorder(ItemStack paramItemStack, boolean paramBoolean) {
      if(paramItemStack.stackTagCompound == null) {
         paramItemStack.stackTagCompound = new NBTTagCompound();
      }

      paramItemStack.stackTagCompound.setBoolean("ShowBorder", paramBoolean);
      return true;
   }

   public static boolean isShowBorder(ItemStack paramItemStack) {
      return paramItemStack.stackTagCompound == null?false:paramItemStack.stackTagCompound.getBoolean("ShowBorder");
   }

   public static boolean setIsBuildBarrier(ItemStack paramItemStack, boolean paramBoolean) {
      if(paramItemStack.stackTagCompound == null) {
         paramItemStack.stackTagCompound = new NBTTagCompound();
      }

      paramItemStack.stackTagCompound.setBoolean("BuildBarrier", paramBoolean);
      return true;
   }

   public static boolean isBuildBarrier(ItemStack paramItemStack) {
      return paramItemStack.stackTagCompound == null?false:paramItemStack.stackTagCompound.getBoolean("BuildBarrier");
   }
}
