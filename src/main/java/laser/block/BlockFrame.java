package laser.block;

import java.util.ArrayList;
import java.util.List;

import cofh.api.block.IDismantleable;
import cofh.core.render.IconRegistry;
import cofh.core.util.CoreUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import laser.Laser;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockFrame extends Block implements IDismantleable {

   public static final String[] NAMES = new String[]{"steel"};
   public static final int[] RARITY = new int[]{1};


   public BlockFrame() {
      super(Material.iron);
      this.setHardness(15.0F);
      this.setResistance(25.0F);
      this.setStepSound(soundTypeMetal);
      this.setCreativeTab(Laser.tabBlocks);
      this.setBlockName("laser.frame");
   }

   @Override
   public void getSubBlocks(Item paramItem, CreativeTabs paramCreativeTabs, List paramList) {
      for(int i = 0; i < NAMES.length; ++i) {
         paramList.add(new ItemStack(paramItem, 1, i));
      }

   }

   @Override
   public int damageDropped(int paramInt) {
      return paramInt;
   }

   public boolean canCreatureSpawn(EnumCreatureType paramEnumCreatureType, IBlockAccess paramIBlockAccess, int paramInt1, int paramInt2, int paramInt3) {
      return false;
   }

   @Override
   public IIcon getIcon(int paramInt1, int paramInt2) {
      switch(paramInt2) {
      case 0:
         if(paramInt1 == 0) {
            return IconRegistry.getIcon("FrameSteelBottom");
         } else if(paramInt1 == 1) {
            return IconRegistry.getIcon("FrameSteelTop");
         }
      default:
         return IconRegistry.getIcon("FrameSteelSide");
      }
   }

   @SideOnly(Side.CLIENT)
   public void registerBlockIcons(IIconRegister paramIIconRegister) {
      IconRegistry.addIcon("FrameSteelBottom", "laser:machine/FrameSteelBottom", paramIIconRegister);
      IconRegistry.addIcon("FrameSteelTop", "laser:machine/FrameSteelTop", paramIIconRegister);
      IconRegistry.addIcon("FrameSteelSide", "laser:machine/FrameSteelSide", paramIIconRegister);
   }

   public ArrayList dismantleBlock(EntityPlayer paramEntityPlayer, World paramWorld, int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean) {
      int i = paramWorld.getBlockMetadata(paramInt1, paramInt2, paramInt3);
      ItemStack localItemStack = new ItemStack(this, 1, i);
      paramWorld.setBlockToAir(paramInt1, paramInt2, paramInt3);
      if(!paramBoolean) {
         float localArrayList = 0.3F;
         double d1 = (double)(paramWorld.rand.nextFloat() * localArrayList) + (double)(1.0F - localArrayList) * 0.5D;
         double d2 = (double)(paramWorld.rand.nextFloat() * localArrayList) + (double)(1.0F - localArrayList) * 0.5D;
         double d3 = (double)(paramWorld.rand.nextFloat() * localArrayList) + (double)(1.0F - localArrayList) * 0.5D;
         EntityItem localEntityItem = new EntityItem(paramWorld, (double)paramInt1 + d1, (double)paramInt2 + d2, (double)paramInt3 + d3, localItemStack);
         localEntityItem.delayBeforeCanPickup = 10;
         paramWorld.spawnEntityInWorld(localEntityItem);
         CoreUtils.dismantleLog(paramEntityPlayer.getCommandSenderName(), this, i, (double)paramInt1, (double)paramInt2, (double)paramInt3);
      }

      ArrayList localArrayList1 = new ArrayList();
      localArrayList1.add(localItemStack);
      return localArrayList1;
   }

   public boolean canDismantle(EntityPlayer paramEntityPlayer, World paramWorld, int paramInt1, int paramInt2, int paramInt3) {
      return true;
   }

}
