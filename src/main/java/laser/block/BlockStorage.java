package laser.block;

import java.util.List;

import cofh.lib.util.helpers.StringHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import laser.Laser;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockStorage extends Block {

   public static final String[] NAMES = new String[]{"zinc", "brass", "aluminium", "titanium", "chromium", "steel", "stainlessSteel", "ghastly", "tungsten", "unobtainium", "ruby", "sapphire", "bediron"};
   public static final IIcon[] TEXTURES = new IIcon[NAMES.length];
   public static final int[] LIGHT = new int[]{0, 0, 0, 4, 4, 2, 2, 7, 4, 7, 7, 7, 0};
   public static final float[] HARDNESS = new float[]{5.0F, 5.0F, 2.0F, 20.0F, 10.0F, 15.0F, 20.0F, 15.0F, 15.0F, 30.0F, 5.0F, 5.0F, 50.0F};
   public static final float[] RESISTANCE = new float[]{6.0F, 6.0F, 1.0F, 100.0F, 30.0F, 30.0F, 50.0F, 0.0F, 40.0F, 10000.0F, 6.0F, 6.0F, 6000000.0F};
   public static final int[] RARITY = new int[]{0, 0, 0, 1, 1, 0, 1, 3, 1, 2, 0, 0, 3};


   public BlockStorage() {
      super(Material.iron);
      this.setStepSound(soundTypeMetal);
      this.setCreativeTab(Laser.tabResources);
      this.setBlockName("laser.storage");
      this.setHarvestLevel("pickaxe", 1, 0);
      this.setHarvestLevel("pickaxe", 1, 1);
      this.setHarvestLevel("pickaxe", 1, 2);
      this.setHarvestLevel("pickaxe", 3, 3);
      this.setHarvestLevel("pickaxe", 2, 4);
      this.setHarvestLevel("pickaxe", 2, 5);
      this.setHarvestLevel("pickaxe", 3, 6);
      this.setHarvestLevel("pickaxe", 2, 7);
      this.setHarvestLevel("pickaxe", 2, 8);
      this.setHarvestLevel("pickaxe", 3, 9);
      this.setHarvestLevel("pickaxe", 2, 10);
      this.setHarvestLevel("pickaxe", 2, 11);
      this.setHarvestLevel("pickaxe", 3, 12);
   }

   @Override
   public void getSubBlocks(Item paramItem, CreativeTabs paramCreativeTabs, List paramList) {
      for(int i = 0; i < NAMES.length; ++i) {
         paramList.add(new ItemStack(paramItem, 1, i));
      }

   }

   public int getLightValue(IBlockAccess paramIBlockAccess, int paramInt1, int paramInt2, int paramInt3) {
      return LIGHT[paramIBlockAccess.getBlockMetadata(paramInt1, paramInt2, paramInt3)];
   }

   @Override
   public float getBlockHardness(World paramWorld, int paramInt1, int paramInt2, int paramInt3) {
      return HARDNESS[paramWorld.getBlockMetadata(paramInt1, paramInt2, paramInt3)];
   }

   public float getExplosionResistance(Entity paramEntity, World paramWorld, int paramInt1, int paramInt2, int paramInt3, double paramDouble1, double paramDouble2, double paramDouble3) {
      return RESISTANCE[paramWorld.getBlockMetadata(paramInt1, paramInt2, paramInt3)];
   }

   @Override
   public int damageDropped(int paramInt) {
      return paramInt;
   }

   public boolean canCreatureSpawn(EnumCreatureType paramEnumCreatureType, IBlockAccess paramIBlockAccess, int paramInt1, int paramInt2, int paramInt3) {
      return false;
   }

   public boolean isBeaconBase(IBlockAccess paramIBlockAccess, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) {
      return true;
   }

   @Override
   public IIcon getIcon(int paramInt1, int paramInt2) {
      return TEXTURES[paramInt2];
   }

   @SideOnly(Side.CLIENT)
   public void registerBlockIcons(IIconRegister paramIIconRegister) {
      for(int i = 0; i < NAMES.length; ++i) {
         TEXTURES[i] = paramIIconRegister.registerIcon("laser:storage/Block" + StringHelper.titleCase(NAMES[i]));
      }

   }

}
