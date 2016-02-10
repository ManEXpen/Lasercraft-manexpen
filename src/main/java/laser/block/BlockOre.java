package laser.block;

import java.util.ArrayList;
import java.util.List;

import cofh.lib.util.helpers.ItemHelper;
import cofh.lib.util.helpers.StringHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import laser.Laser;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.particle.EntityExplodeFX;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockOre extends Block {

   public static final String[] NAMES = new String[]{"zinc", "bauxite", "titanium", "chromium", "infused", "tungsten", "ruby", "sapphire"};
   public static final IIcon[] TEXTURES = new IIcon[NAMES.length];
   public static final int[] LIGHT = new int[]{0, 0, 4, 4, 7, 4, 7, 7};
   public static final float[] HARDNESS = new float[]{3.0F, 3.0F, 6.0F, 5.0F, 2.5F, 5.0F, 3.0F, 3.0F};
   public static final int[] RARITY = new int[]{0, 0, 1, 1, 1, 1, 0, 0};


   public BlockOre() {
      super(Material.rock);
      this.setResistance(5.0F);
      this.setStepSound(soundTypeStone);
      this.setCreativeTab(Laser.tabResources);
      this.setBlockName("laser.ore");
      this.setHarvestLevel("pickaxe", 1, 0);
      this.setHarvestLevel("pickaxe", 1, 1);
      this.setHarvestLevel("pickaxe", 2, 2);
      this.setHarvestLevel("pickaxe", 2, 3);
      this.setHarvestLevel("pickaxe", 0, 4);
      this.setHarvestLevel("pickaxe", 2, 5);
      this.setHarvestLevel("pickaxe", 2, 6);
      this.setHarvestLevel("pickaxe", 2, 7);
   }

   public ArrayList getDrops(World world, int x, int y, int z, int metadata, int fortune) {
      ArrayList ret = new ArrayList();
      int j;
      if(metadata == 4) {
         ret.add(new ItemStack(Blocks.cobblestone));

         for(j = 0; j < world.rand.nextInt(2 + fortune); ++j) {
            ret.add(ItemHelper.cloneStack(Laser.dustInfused));
         }

         return ret;
      } else {
         int i;
         if(metadata == 6) {
            j = world.rand.nextInt(2 + fortune);
            if(j < 1) {
               j = 1;
            }

            for(i = 0; i < j; ++i) {
               ret.add(ItemHelper.cloneStack(Laser.gemRuby));
            }

            return ret;
         } else if(metadata != 7) {
            return super.getDrops(world, x, y, z, metadata, fortune);
         } else {
            j = world.rand.nextInt(2 + fortune);
            if(j < 1) {
               j = 1;
            }

            for(i = 0; i < j; ++i) {
               ret.add(ItemHelper.cloneStack(Laser.gemSapphire));
            }

            return ret;
         }
      }
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

   @Override
   public int damageDropped(int paramInt) {
      return paramInt;
   }

   @Override
   public IIcon getIcon(int paramInt1, int paramInt2) {
      return TEXTURES[paramInt2];
   }

   @SideOnly(Side.CLIENT)
   public void registerBlockIcons(IIconRegister paramIIconRegister) {
      for(int i = 0; i < NAMES.length; ++i) {
         TEXTURES[i] = paramIIconRegister.registerIcon("laser:ore/Ore" + StringHelper.titleCase(NAMES[i]));
      }

   }

   @Override
   protected boolean canSilkHarvest() {
      return true;
   }

   @SideOnly(Side.CLIENT)
   public boolean addDestroyEffects(World world, int x, int y, int z, int meta, EffectRenderer effectRenderer) {
      if(meta == 4) {
         for(int i = 0; i < 10; ++i) {
            double d0 = world.rand.nextGaussian() * 0.02D;
            double d1 = world.rand.nextGaussian() * 0.02D;
            double d2 = world.rand.nextGaussian() * 0.02D;
            EntityExplodeFX entityfx = new EntityExplodeFX(world, (double)x + (double)world.rand.nextFloat(), (double)y + (double)world.rand.nextFloat(), (double)z + (double)world.rand.nextFloat(), d0, d1, d2);
            entityfx.setRBGColorF(0.25F, 0.0F, 0.0F);
            effectRenderer.addEffect(entityfx);
         }
      }

      return false;
   }

}
