package laser.block;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cofh.api.block.IDismantleable;
import cofh.core.util.CoreUtils;
import cofh.lib.util.helpers.ServerHelper;
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
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import thermalexpansion.util.Utils;

public class BlockGlass extends Block implements IDismantleable {

   public static final String[] NAMES = new String[]{"reinforced", "resonant", "mirror", "halfMirror", "lightShielding"};
   public static final IIcon[] TEXTURES = new IIcon[NAMES.length];
   public static final int[] LIGHTOPACITY = new int[]{0, 0, 0, 0, 255};
   public static final float[] HARDNESS = new float[]{10.0F, -1.0F, 0.5F, 0.5F, -1.0F};
   public static final float[] RESISTANCE = new float[]{1000.0F, 6000000.0F, 0.5F, 0.5F, 6000000.0F};
   public static final int[] RARITY = new int[]{1, 2, 0, 0, 2};


   public BlockGlass() {
      super(Material.glass);
      this.setStepSound(soundTypeGlass);
      this.setCreativeTab(Laser.tabBlocks);
      this.setBlockName("laser.glass");
   }

   @Override
   public void getSubBlocks(Item paramItem, CreativeTabs paramCreativeTabs, List paramList) {
      for(int i = 0; i < NAMES.length; ++i) {
         paramList.add(new ItemStack(paramItem, 1, i));
      }

   }

   @Override
   public boolean onBlockActivated(World paramWorld, int paramInt1, int paramInt2, int paramInt3, EntityPlayer paramEntityPlayer, int paramInt4, float paramFloat1, float paramFloat2, float paramFloat3) {
      if(paramEntityPlayer.isSneaking() && Utils.isHoldingUsableWrench(paramEntityPlayer, paramInt1, paramInt2, paramInt3)) {
         if(ServerHelper.isServerWorld(paramWorld)) {
            this.dismantleBlock(paramEntityPlayer, paramWorld, paramInt1, paramInt2, paramInt3, false);
            Utils.usedWrench(paramEntityPlayer, paramInt1, paramInt2, paramInt3);
         }

         return true;
      } else {
         return false;
      }
   }

   public int getLightOpacity(IBlockAccess paramIBlockAccess, int paramInt1, int paramInt2, int paramInt3) {
      return LIGHTOPACITY[paramIBlockAccess.getBlockMetadata(paramInt1, paramInt2, paramInt3)];
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

   @Override
   public int getRenderBlockPass() {
      return 1;
   }

   @Override
   public int quantityDropped(Random paramRandom) {
      return 0;
   }

   public boolean canCreatureSpawn(EnumCreatureType paramEnumCreatureType, IBlockAccess paramIBlockAccess, int paramInt1, int paramInt2, int paramInt3) {
      return false;
   }

   @Override
   protected boolean canSilkHarvest() {
      return true;
   }

   @Override
   public boolean isOpaqueCube() {
      return false;
   }

   @Override
   public boolean renderAsNormalBlock() {
      return false;
   }

   @Override
   public boolean shouldSideBeRendered(IBlockAccess paramIBlockAccess, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
      Block localBlock = paramIBlockAccess.getBlock(paramInt1, paramInt2, paramInt3);
      int i = paramIBlockAccess.getBlockMetadata(paramInt1, paramInt2, paramInt3);
      ForgeDirection direction = ForgeDirection.VALID_DIRECTIONS[paramInt4 ^ 1];
      int j = paramIBlockAccess.getBlockMetadata(paramInt1 + direction.offsetX, paramInt2 + direction.offsetY, paramInt3 + direction.offsetZ);
      return localBlock == this && i == j?false:super.shouldSideBeRendered(paramIBlockAccess, paramInt1, paramInt2, paramInt3, paramInt4);
   }

   @Override
   public IIcon getIcon(int paramInt1, int paramInt2) {
      return TEXTURES[paramInt2];
   }

   @SideOnly(Side.CLIENT)
   public void registerBlockIcons(IIconRegister paramIIconRegister) {
      for(int i = 0; i < NAMES.length; ++i) {
         TEXTURES[i] = paramIIconRegister.registerIcon("laser:glass/Glass" + StringHelper.titleCase(NAMES[i]));
      }

   }

   public void onBlockExploded(World paramWorld, int paramInt1, int paramInt2, int paramInt3, Explosion paramExplosion) {
      if(paramWorld.getBlockMetadata(paramInt1, paramInt2, paramInt3) != 1) {
         super.onBlockExploded(paramWorld, paramInt1, paramInt2, paramInt3, paramExplosion);
      }
   }

   public boolean canEntityDestroy(IBlockAccess paramIBlockAccess, int paramInt1, int paramInt2, int paramInt3, Entity paramEntity) {
      return paramIBlockAccess.getBlockMetadata(paramInt1, paramInt2, paramInt3) != 1;
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
