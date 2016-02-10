package laser.block.machine.large;

import java.util.ArrayList;
import java.util.List;

import cofh.core.block.BlockCoFHBase;
import cofh.core.render.IconRegistry;
import cofh.core.util.CoreUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import laser.Laser;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockCasing extends BlockCoFHBase {

   public static final String[] NAMES = new String[]{"steel"};
   public static final int[] RARITY = new int[]{0};


   public BlockCasing() {
      super(Material.iron);
      this.setHardness(15.0F);
      this.setResistance(25.0F);
      this.setStepSound(soundTypeMetal);
      this.setCreativeTab(Laser.tabBlocks);
      this.setBlockName("laser.casing");
   }

   public TileEntity createNewTileEntity(World paramWorld, int paramInt) {
      return new TileCasing();
   }

   public void getSubBlocks(Item paramItem, CreativeTabs paramCreativeTabs, List paramList) {
      for(int i = 0; i < NAMES.length; ++i) {
         paramList.add(new ItemStack(paramItem, 1, i));
      }

   }

   public boolean isOpaqueCube() {
      return true;
   }

   public boolean renderAsNormalBlock() {
      return true;
   }

   public IIcon getIcon(int paramInt1, int paramInt2) {
      return IconRegistry.getIcon("CasingSteel_15");
   }

   @SideOnly(Side.CLIENT)
   public IIcon getIcon(IBlockAccess paramIBlockAccess, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
      TileCasing localTileCasing = (TileCasing)paramIBlockAccess.getTileEntity(paramInt1, paramInt2, paramInt3);
      int meta = paramIBlockAccess.getBlockMetadata(paramInt1, paramInt2, paramInt3);
      if(localTileCasing.hasTile() && localTileCasing.getTile().isComplete()) {
         int m = 0;
         if(paramInt4 == 0 || paramInt4 == 1) {
            m |= this.isConnect(paramIBlockAccess.getBlock(paramInt1 + 1, paramInt2, paramInt3), paramIBlockAccess.getBlockMetadata(paramInt1 + 1, paramInt2, paramInt3), meta)?1:0;
            m |= this.isConnect(paramIBlockAccess.getBlock(paramInt1, paramInt2, paramInt3 + 1), paramIBlockAccess.getBlockMetadata(paramInt1, paramInt2, paramInt3 + 1), meta)?2:0;
            m |= this.isConnect(paramIBlockAccess.getBlock(paramInt1 - 1, paramInt2, paramInt3), paramIBlockAccess.getBlockMetadata(paramInt1 - 1, paramInt2, paramInt3), meta)?4:0;
            m |= this.isConnect(paramIBlockAccess.getBlock(paramInt1, paramInt2, paramInt3 - 1), paramIBlockAccess.getBlockMetadata(paramInt1, paramInt2, paramInt3 - 1), meta)?8:0;
         }

         if(paramInt4 == 2) {
            m |= this.isConnect(paramIBlockAccess.getBlock(paramInt1, paramInt2 + 1, paramInt3), paramIBlockAccess.getBlockMetadata(paramInt1, paramInt2 + 1, paramInt3), meta)?8:0;
            m |= this.isConnect(paramIBlockAccess.getBlock(paramInt1 + 1, paramInt2, paramInt3), paramIBlockAccess.getBlockMetadata(paramInt1 + 1, paramInt2, paramInt3), meta)?4:0;
            m |= this.isConnect(paramIBlockAccess.getBlock(paramInt1, paramInt2 - 1, paramInt3), paramIBlockAccess.getBlockMetadata(paramInt1, paramInt2 - 1, paramInt3), meta)?2:0;
            m |= this.isConnect(paramIBlockAccess.getBlock(paramInt1 - 1, paramInt2, paramInt3), paramIBlockAccess.getBlockMetadata(paramInt1 - 1, paramInt2, paramInt3), meta)?1:0;
         }

         if(paramInt4 == 3) {
            m |= this.isConnect(paramIBlockAccess.getBlock(paramInt1, paramInt2 + 1, paramInt3), paramIBlockAccess.getBlockMetadata(paramInt1, paramInt2 + 1, paramInt3), meta)?8:0;
            m |= this.isConnect(paramIBlockAccess.getBlock(paramInt1 - 1, paramInt2, paramInt3), paramIBlockAccess.getBlockMetadata(paramInt1 - 1, paramInt2, paramInt3), meta)?4:0;
            m |= this.isConnect(paramIBlockAccess.getBlock(paramInt1, paramInt2 - 1, paramInt3), paramIBlockAccess.getBlockMetadata(paramInt1, paramInt2 - 1, paramInt3), meta)?2:0;
            m |= this.isConnect(paramIBlockAccess.getBlock(paramInt1 + 1, paramInt2, paramInt3), paramIBlockAccess.getBlockMetadata(paramInt1 + 1, paramInt2, paramInt3), meta)?1:0;
         }

         if(paramInt4 == 4) {
            m |= this.isConnect(paramIBlockAccess.getBlock(paramInt1, paramInt2 + 1, paramInt3), paramIBlockAccess.getBlockMetadata(paramInt1, paramInt2 + 1, paramInt3), meta)?8:0;
            m |= this.isConnect(paramIBlockAccess.getBlock(paramInt1, paramInt2, paramInt3 - 1), paramIBlockAccess.getBlockMetadata(paramInt1, paramInt2, paramInt3 - 1), meta)?4:0;
            m |= this.isConnect(paramIBlockAccess.getBlock(paramInt1, paramInt2 - 1, paramInt3), paramIBlockAccess.getBlockMetadata(paramInt1, paramInt2 - 1, paramInt3), meta)?2:0;
            m |= this.isConnect(paramIBlockAccess.getBlock(paramInt1, paramInt2, paramInt3 + 1), paramIBlockAccess.getBlockMetadata(paramInt1, paramInt2, paramInt3 + 1), meta)?1:0;
         }

         if(paramInt4 == 5) {
            m |= this.isConnect(paramIBlockAccess.getBlock(paramInt1, paramInt2 + 1, paramInt3), paramIBlockAccess.getBlockMetadata(paramInt1, paramInt2 + 1, paramInt3), meta)?8:0;
            m |= this.isConnect(paramIBlockAccess.getBlock(paramInt1, paramInt2, paramInt3 + 1), paramIBlockAccess.getBlockMetadata(paramInt1, paramInt2, paramInt3 + 1), meta)?4:0;
            m |= this.isConnect(paramIBlockAccess.getBlock(paramInt1, paramInt2 - 1, paramInt3), paramIBlockAccess.getBlockMetadata(paramInt1, paramInt2 - 1, paramInt3), meta)?2:0;
            m |= this.isConnect(paramIBlockAccess.getBlock(paramInt1, paramInt2, paramInt3 - 1), paramIBlockAccess.getBlockMetadata(paramInt1, paramInt2, paramInt3 - 1), meta)?1:0;
         }

         return IconRegistry.getIcon("CasingSteel_" + (15 - m));
      } else {
         return this.getIcon(paramInt4, meta);
      }
   }

   private boolean isConnect(Block paramBlock, int paramInt1, int paramInt2) {
      return paramBlock == this && paramInt1 == paramInt2 || paramBlock instanceof BlockLargeMachine;
   }

   @SideOnly(Side.CLIENT)
   public void registerBlockIcons(IIconRegister paramIIconRegister) {
      for(int i = 0; i < 16; ++i) {
         IconRegistry.addIcon("CasingSteel_" + i, "laser:machine/CasingSteel_" + i, paramIIconRegister);
      }

   }

   public ArrayList dismantleBlock(EntityPlayer paramEntityPlayer, NBTTagCompound paramNBTTagCompound, World paramWorld, int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean1, boolean paramBoolean2) {
      int i = paramWorld.getBlockMetadata(paramInt1, paramInt2, paramInt3);
      ItemStack localItemStack = new ItemStack(this, 1, i);
      paramWorld.setBlockToAir(paramInt1, paramInt2, paramInt3);
      if(!paramBoolean1) {
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

   public boolean initialize() {
      return true;
   }

   public boolean postInit() {
      return true;
   }

}
