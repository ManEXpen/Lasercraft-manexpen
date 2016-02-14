package laser.block.generator;

import java.util.List;

import cofh.api.energy.EnergyStorage;
import cofh.lib.util.helpers.StringHelper;
import cofh.thermalexpansion.block.BlockTEBase;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import laser.Laser;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockGenerator extends BlockTEBase {

	protected EnergyStorage energyStorage = new EnergyStorage(0);
   public static final String[] TYPES = new String[]{"combustion", "solar", "kinetic", "hydroelectric", "creaturePowered", "ghast"};
   public static final IIcon[][] TEXTURES = new IIcon[TYPES.length][3];


   public BlockGenerator() {
      super(Material.iron);
      this.setHardness(15.0F);
      this.setResistance(25.0F);
      this.setCreativeTab(Laser.tabBlocks);
      this.setBlockName("laser.generator");
   }

   public IIcon getIcon(int paramInt1, int paramInt2) {
      int i = paramInt1 == 1?2:(paramInt1 != 0?1:0);
      return TEXTURES[paramInt2][i];
   }

   public TileEntity createNewTileEntity(World paramWorld, int paramInt) {
      if(paramInt >= TYPES.length) {
         return null;
      } else {
         switch(paramInt) {
         case 0:
            return new TileGeneratorCombustion();
         default:
            return null;
         }
      }
   }

   public void getSubBlocks(Item paramItem, CreativeTabs paramCreativeTabs, List paramList) {}

   public void func_149689_a(World paramWorld, int paramInt1, int paramInt2, int paramInt3, EntityLivingBase paramEntityLivingBase, ItemStack paramItemStack) {
      TileGeneratorBase localTileGeneratorBase = (TileGeneratorBase)paramWorld.getTileEntity(paramInt1, paramInt2, paramInt3);
      if(paramItemStack.stackTagCompound != null) {
         setEnergyStored(paramItemStack.stackTagCompound.getInteger("Energy"));
      }

      super.func_149689_a(paramWorld, paramInt1, paramInt2, paramInt3, paramEntityLivingBase, paramItemStack);
   }

   public boolean func_149740_M() {
      return true;
   }

   @SideOnly(Side.CLIENT)
   public void func_149651_a(IIconRegister paramIIconRegister) {
      String[] s = new String[]{"Bottom", "Side", "Top"};

      for(int i = 0; i < TYPES.length; ++i) {
         for(int j = 0; j < 3; ++j) {
            TEXTURES[i][j] = paramIIconRegister.registerIcon("laser:generator/Generator" + StringHelper.titleCase(TYPES[i]) + "_" + s[j]);
         }
      }

   }

   public NBTTagCompound getItemStackTag(World paramWorld, int paramInt1, int paramInt2, int paramInt3) {
      NBTTagCompound localNBTTagCompound = super.getItemStackTag(paramWorld, paramInt1, paramInt2, paramInt3);
      TileGeneratorBase localTileGeneratorBase = (TileGeneratorBase)paramWorld.getTileEntity(paramInt1, paramInt2, paramInt3);
      if(localTileGeneratorBase != null) {
         if(localNBTTagCompound == null) {
            localNBTTagCompound = new NBTTagCompound();
         }

         localNBTTagCompound.setInteger("Energy", localTileGeneratorBase.getEnergyStored(ForgeDirection.UNKNOWN));
      }

      return localNBTTagCompound;
   }

   public boolean initialize() {
      return true;
   }

   public boolean postInit() {
      return true;
   }

   public final void setEnergyStored(int paramInt) {
		this.energyStorage.setEnergyStored(paramInt);
	}

}
