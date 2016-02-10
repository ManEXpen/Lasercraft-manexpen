package laser.block.machine;

import java.util.List;

import cofh.api.tileentity.IRedstoneControl;
import cofh.api.tileentity.ISidedTexture;
import cofh.core.render.IconRegistry;
import cofh.lib.util.helpers.BlockHelper;
import cofh.lib.util.helpers.EnergyHelper;
import cofh.lib.util.helpers.RedstoneControlHelper;
import cofh.lib.util.helpers.StringHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import laser.Laser;
import laser.item.tool.ItemChunkUtil;
import laser.util.helper.ChunkQuarryHelper;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.chunk.EmptyChunk;
import net.minecraftforge.common.util.ForgeDirection;
import thermalexpansion.block.BlockTEBase;
import thermalexpansion.util.ReconfigurableHelper;

public class BlockMachine extends BlockTEBase {

   public static final String[] TYPES = new String[]{"alloySmelter", "chunkQuarry", "pump"};
   public static final int[] RARITY = new int[]{1, 2, 1};


   public BlockMachine() {
      super(Material.iron);
      this.setHardness(15.0F);
      this.setResistance(25.0F);
      this.setStepSound(soundTypeMetal);
      this.setCreativeTab(Laser.tabBlocks);
      this.setBlockName("laser.machine");
   }

   public TileEntity createNewTileEntity(World paramWorld, int paramInt) {
      if(paramInt >= TYPES.length) {
         return null;
      } else {
         switch(paramInt) {
         case 0:
            return new TileAlloySmelter();
         case 1:
            return new TileChunkQuarry();
         case 2:
            return new TilePump();
         default:
            return null;
         }
      }
   }

   public void breakBlock(World paramWorld, int paramInt1, int paramInt2, int paramInt3, Block paramBlock, int paramInt4) {
      if(paramInt4 == 1) {
         TileChunkQuarry tilechunkquarry = (TileChunkQuarry)paramWorld.getTileEntity(paramInt1, paramInt2, paramInt3);

         int i;
         for(i = 0; i < tilechunkquarry.entityQuarryLaser.length; ++i) {
            if(tilechunkquarry.entityQuarryLaser[i] != null) {
               tilechunkquarry.entityQuarryLaser[i].setDead();
               paramWorld.removeEntity(tilechunkquarry.entityQuarryLaser[i]);
               tilechunkquarry.entityQuarryLaser[i] = null;
            }
         }

         for(i = 0; i < tilechunkquarry.entityQuarryLaser1.length; ++i) {
            if(tilechunkquarry.entityQuarryLaser1[i] != null) {
               tilechunkquarry.entityQuarryLaser1[i].setDead();
               paramWorld.removeEntity(tilechunkquarry.entityQuarryLaser1[i]);
               tilechunkquarry.entityQuarryLaser1[i] = null;
            }
         }
      }

      super.breakBlock(paramWorld, paramInt1, paramInt2, paramInt3, paramBlock, paramInt4);
   }

   public static ItemStack setStackTag(ItemStack paramItemStack) {
      if(paramItemStack.getItem() instanceof ItemBlockMachine) {
         if(paramItemStack.getItemDamage() == 1) {
            ReconfigurableHelper.setFacing(paramItemStack, 3);
            ReconfigurableHelper.setSideCache(paramItemStack, TileMachineBase.defaultSideConfig[paramItemStack.getItemDamage()].defaultSides);
            RedstoneControlHelper.setControl(paramItemStack, IRedstoneControl.ControlMode.HIGH);
            EnergyHelper.setDefaultEnergyTag(paramItemStack, 0);
            ChunkQuarryHelper.setIsShowBorder(paramItemStack, true);
            ChunkQuarryHelper.setIsBuildBarrier(paramItemStack, true);
         } else {
            ItemBlockMachine.setDefaultTag(paramItemStack);
         }
      }

      return paramItemStack;
   }

   public void getSubBlocks(Item paramItem, CreativeTabs paramCreativeTabs, List paramList) {
      for(int i = 0; i < TYPES.length - 1; ++i) {
         paramList.add(setStackTag(new ItemStack(paramItem, 1, i)));
      }

   }

   public void onBlockPlacedBy(World paramWorld, int paramInt1, int paramInt2, int paramInt3, EntityLivingBase paramEntityLivingBase, ItemStack paramItemStack) {
      if(paramItemStack.stackTagCompound != null) {
         TileMachineBase localTileMachineBase = (TileMachineBase)paramWorld.getTileEntity(paramInt1, paramInt2, paramInt3);
         localTileMachineBase.setEnergyStored(paramItemStack.stackTagCompound.getInteger("Energy"));
         int i = BlockHelper.determineXZPlaceFacing(paramEntityLivingBase);
         byte j = ReconfigurableHelper.getFacing(paramItemStack);
         byte[] arrayOfByte = ReconfigurableHelper.getSideCache(paramItemStack, localTileMachineBase.getDefaultSides());
         localTileMachineBase.sideCache[0] = arrayOfByte[0];
         localTileMachineBase.sideCache[1] = arrayOfByte[1];
         localTileMachineBase.sideCache[i] = 0;
         localTileMachineBase.sideCache[BlockHelper.getLeftSide(i)] = arrayOfByte[BlockHelper.getLeftSide(j)];
         localTileMachineBase.sideCache[BlockHelper.getRightSide(i)] = arrayOfByte[BlockHelper.getRightSide(j)];
         localTileMachineBase.sideCache[BlockHelper.getOppositeSide(i)] = arrayOfByte[BlockHelper.getOppositeSide(j)];
         if(localTileMachineBase instanceof TileChunkQuarry) {
            ((TileChunkQuarry)localTileMachineBase).stacks = ChunkQuarryHelper.getStacks(paramItemStack);
            ((TileChunkQuarry)localTileMachineBase).showBorder = ChunkQuarryHelper.isShowBorder(paramItemStack);
            ((TileChunkQuarry)localTileMachineBase).buildBarrier = ChunkQuarryHelper.isBuildBarrier(paramItemStack);
         }

         if(localTileMachineBase instanceof TilePump) {
            ((TilePump)localTileMachineBase).aimY = paramInt2 - 1;
         }

         localTileMachineBase.onNeighborBlockChange();
      }

      super.onBlockPlacedBy(paramWorld, paramInt1, paramInt2, paramInt3, paramEntityLivingBase, paramItemStack);
   }

   public boolean onBlockActivated(World paramWorld, int paramInt1, int paramInt2, int paramInt3, EntityPlayer paramEntityPlayer, int paramInt4, float paramFloat1, float paramFloat2, float paramFloat3) {
      TileEntity localTileEntity = paramWorld.getTileEntity(paramInt1, paramInt2, paramInt3);
      if(localTileEntity instanceof TileChunkQuarry) {
         ItemStack localItemStack = paramEntityPlayer.getCurrentEquippedItem();
         if(localItemStack != null && localItemStack.getItem() instanceof ItemChunkUtil && localItemStack.getItemDamage() == 1 && localItemStack.stackTagCompound != null && localItemStack.stackTagCompound.hasKey("Chunk")) {
            NBTTagCompound nbttagcompound = (NBTTagCompound)localItemStack.stackTagCompound.getTag("Chunk");
            int xCoord = nbttagcompound.getInteger("X");
            int zCoord = nbttagcompound.getInteger("Z");
            if(!(paramWorld.getChunkFromChunkCoords(xCoord, zCoord) instanceof EmptyChunk)) {
               String s = ((TileChunkQuarry)localTileEntity).registerChunk(xCoord, zCoord);
               if(!paramWorld.isRemote) {
                  paramEntityPlayer.addChatMessage(new ChatComponentText(StringHelper.localize(s)));
               }

               return true;
            }
         }
      }

      return super.onBlockActivated(paramWorld, paramInt1, paramInt2, paramInt3, paramEntityPlayer, paramInt4, paramFloat1, paramFloat2, paramFloat3);
   }

   public int getRenderBlockPass() {
      return 1;
   }

   public boolean canRenderInPass(int paramInt) {
      renderPass = paramInt;
      return paramInt < 2;
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
      return localISidedTexture == null?null:localISidedTexture.getTexture(paramInt4, renderPass);
   }

   public IIcon getIcon(int paramInt1, int paramInt2) {
      return paramInt1 == 0?IconRegistry.getIcon("LCMachineBottom"):(paramInt1 == 1?IconRegistry.getIcon("LCMachineTop"):(paramInt1 != 3?IconRegistry.getIcon("LCMachineSide"):IconRegistry.getIcon("LCMachineFace" + paramInt2)));
   }

   @SideOnly(Side.CLIENT)
   public void registerBlockIcons(IIconRegister paramIIconRegister) {
      for(int i = 0; i < TYPES.length; ++i) {
         IconRegistry.addIcon("LCMachineBottom", "laser:machine/MachineBottom", paramIIconRegister);
         IconRegistry.addIcon("LCMachineTop", "laser:machine/MachineTop", paramIIconRegister);
         IconRegistry.addIcon("LCMachineSide", "laser:machine/MachineSide", paramIIconRegister);
         IconRegistry.addIcon("LCMachineFace" + i, "laser:machine/Machine" + StringHelper.titleCase(TYPES[i]), paramIIconRegister);
         IconRegistry.addIcon("LCMachineActive" + i, "laser:machine/Machine" + StringHelper.titleCase(TYPES[i]) + "_Active", paramIIconRegister);
      }

   }

   public NBTTagCompound getItemStackTag(World paramWorld, int paramInt1, int paramInt2, int paramInt3) {
      NBTTagCompound localNBTTagCompound = super.getItemStackTag(paramWorld, paramInt1, paramInt2, paramInt3);
      TileMachineBase localTileMachineBase = (TileMachineBase)paramWorld.getTileEntity(paramInt1, paramInt2, paramInt3);
      if(localTileMachineBase != null) {
         if(localNBTTagCompound == null) {
            localNBTTagCompound = new NBTTagCompound();
         }

         ReconfigurableHelper.setItemStackTagReconfig(localNBTTagCompound, localTileMachineBase);
         localNBTTagCompound.setInteger("Energy", localTileMachineBase.getEnergyStored(ForgeDirection.UNKNOWN));
         if(localTileMachineBase instanceof TileChunkQuarry) {
            ChunkQuarryHelper.setItemStackTagConfig(localNBTTagCompound, (TileChunkQuarry)localTileMachineBase);
         }
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
