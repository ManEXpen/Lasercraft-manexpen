package laser.block.machine.large;

import java.util.List;

import codechicken.nei.recipe.GuiUsageRecipe;
import cofh.api.tileentity.ISidedTexture;
import cofh.core.render.IconRegistry;
import cofh.lib.util.helpers.StringHelper;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import laser.Laser;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import thermalexpansion.block.BlockTEBase;

public class BlockLargeMachine extends BlockTEBase {

   public static final String[] TYPES = new String[]{"grinder"};
   public static final int[] RARITY = new int[]{1};


   public BlockLargeMachine() {
      super(Material.iron);
      this.setHardness(15.0F);
      this.setResistance(25.0F);
      this.setStepSound(soundTypeMetal);
      this.setCreativeTab(Laser.tabBlocks);
      this.setBlockName("laser.machine.large");
   }

   public TileEntity createNewTileEntity(World paramWorld, int paramInt) {
      if(paramInt >= TYPES.length) {
         return null;
      } else {
         switch(paramInt) {
         case 0:
            return new TileGrinder();
         default:
            return null;
         }
      }
   }

   public void getSubBlocks(Item paramItem, CreativeTabs paramCreativeTabs, List paramList) {
      for(int i = 0; i < TYPES.length; ++i) {
         paramList.add(ItemBlockLargeMachine.setDefaultTag(new ItemStack(paramItem, 1, i)));
      }

   }

   public void onBlockPlacedBy(World paramWorld, int paramInt1, int paramInt2, int paramInt3, EntityLivingBase paramEntityLivingBase, ItemStack paramItemStack) {
      if(paramItemStack.stackTagCompound != null) {
         TileLargeMachineBase localTileLargeMachineBase = (TileLargeMachineBase)paramWorld.getTileEntity(paramInt1, paramInt2, paramInt3);
         localTileLargeMachineBase.setEnergyStored(paramItemStack.stackTagCompound.getInteger("Energy"));
         localTileLargeMachineBase.onNeighborBlockChange();
      }

      super.func_149689_a(paramWorld, paramInt1, paramInt2, paramInt3, paramEntityLivingBase, paramItemStack);
   }

   public boolean onBlockActivated(World paramWorld, int paramInt1, int paramInt2, int paramInt3, EntityPlayer paramEntityPlayer, int paramInt4, float paramFloat1, float paramFloat2, float paramFloat3) {
      TileLargeMachineBase localTileLargeMachineBase = (TileLargeMachineBase)paramWorld.getTileEntity(paramInt1, paramInt2, paramInt3);
      if(!localTileLargeMachineBase.isComplete()) {
         if(Loader.isModLoaded("NotEnoughItems")) {
            if(!paramWorld.isRemote) {
               GuiUsageRecipe.openRecipeGui("item", new Object[]{new ItemStack(this, 1, paramWorld.getBlockMetadata(paramInt1, paramInt2, paramInt3))});
            }

            return true;
         } else {
            return false;
         }
      } else {
         return super.func_149727_a(paramWorld, paramInt1, paramInt2, paramInt3, paramEntityPlayer, paramInt4, paramFloat1, paramFloat2, paramFloat3);
      }
   }

   public boolean isNormalCube(IBlockAccess paramIBlockAccess, int paramInt1, int paramInt2, int paramInt3) {
      return false;
   }

   public boolean isSideSolid(IBlockAccess paramIBlockAccess, int paramInt1, int paramInt2, int paramInt3, ForgeDirection paramForgeDirection) {
      return true;
   }

   public boolean renderAsNormalBlock() {
      return true;
   }

   public IIcon getIcon(IBlockAccess paramIBlockAccess, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
      ISidedTexture localISidedTexture = (ISidedTexture)paramIBlockAccess.getTileEntity(paramInt1, paramInt2, paramInt3);
      return localISidedTexture == null?null:localISidedTexture.getTexture(paramInt4, 0);
   }

   public IIcon getIcon(int paramInt1, int paramInt2) {
      return paramInt1 != 3?IconRegistry.getIcon("CasingSteel_15"):IconRegistry.getIcon("LCLargeMachineFace", paramInt2);
   }

   @SideOnly(Side.CLIENT)
   public void registerBlockIcons(IIconRegister paramIIconRegister) {
      for(int i = 0; i < TYPES.length; ++i) {
         IconRegistry.addIcon("LCLargeMachineFace" + i, "laser:machine/MachineLarge" + StringHelper.titleCase(TYPES[i]), paramIIconRegister);
         IconRegistry.addIcon("LCLargeMachineActive" + i, "laser:machine/MachineLarge" + StringHelper.titleCase(TYPES[i]) + "_Active", paramIIconRegister);
      }

   }

   public NBTTagCompound getItemStackTag(World paramWorld, int paramInt1, int paramInt2, int paramInt3) {
      NBTTagCompound localNBTTagCompound = super.getItemStackTag(paramWorld, paramInt1, paramInt2, paramInt3);
      TileLargeMachineBase localTileLargeMachineBase = (TileLargeMachineBase)paramWorld.getTileEntity(paramInt1, paramInt2, paramInt3);
      if(localTileLargeMachineBase != null) {
         if(localNBTTagCompound == null) {
            localNBTTagCompound = new NBTTagCompound();
         }

         localNBTTagCompound.setByte("Facing", (byte)localTileLargeMachineBase.getFacing());
         localNBTTagCompound.setInteger("Energy", localTileLargeMachineBase.getEnergyStored(ForgeDirection.UNKNOWN));
      }

      return localNBTTagCompound;
   }

   public boolean initialize() {
      return true;
   }

   public boolean postInit() {
      return true;
   }

}
