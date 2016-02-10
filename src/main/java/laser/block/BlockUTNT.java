package laser.block;

import cofh.core.render.IconRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import laser.Laser;
import laser.entity.EntityUTNTPrimed;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.util.IIcon;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class BlockUTNT extends Block {

   public BlockUTNT() {
      super(Material.tnt);
      this.setHardness(0.0F);
      this.setStepSound(soundTypeGrass);
      this.setCreativeTab(Laser.tabBlocks);
      this.setBlockName("laser.utnt");
   }

   @Override
   public IIcon getIcon(int paramInt1, int paramInt2) {
      switch(paramInt2) {
      case 0:
         if(paramInt1 == 0) {
            return IconRegistry.getIcon("UTNTBottom");
         } else if(paramInt1 == 1) {
            return IconRegistry.getIcon("UTNTTop");
         }
      default:
         return IconRegistry.getIcon("UTNTSide");
      }
   }

   @SideOnly(Side.CLIENT)
   public void registerBlockIcons(IIconRegister paramIIconRegister) {
      IconRegistry.addIcon("UTNTBottom", "laser:utnt/UTNTBottom", paramIIconRegister);
      IconRegistry.addIcon("UTNTTop", "laser:utnt/UTNTTop", paramIIconRegister);
      IconRegistry.addIcon("UTNTSide", "laser:utnt/UTNTSide", paramIIconRegister);
   }

   public void onBlockAdded(World paramWorld, int paramInt1, int paramInt2, int paramInt3) {
      super.onBlockAdded(paramWorld, paramInt1, paramInt2, paramInt3);
      if(paramWorld.isBlockIndirectlyGettingPowered(paramInt1, paramInt2, paramInt3)) {
         this.onBlockDestroyedByPlayer(paramWorld, paramInt1, paramInt2, paramInt3, 1);
         paramWorld.setBlockToAir(paramInt1, paramInt2, paramInt3);
      }

   }

   public void onNeighborBlockChange(World paramWorld, int paramInt1, int paramInt2, int paramInt3, Block paramBlock) {
      if(paramWorld.isBlockIndirectlyGettingPowered(paramInt1, paramInt2, paramInt3)) {
         this.onBlockDestroyedByPlayer(paramWorld, paramInt1, paramInt2, paramInt3, 1);
         paramWorld.setBlockToAir(paramInt1, paramInt2, paramInt3);
      }

   }

   public void onBlockDestroyedByExplosion(World paramWorld, int paramInt1, int paramInt2, int paramInt3, Explosion paramExplosion) {
      if(paramWorld.isRemote) {
         EntityUTNTPrimed entityutntprimed = new EntityUTNTPrimed(paramWorld, (double)((float)paramInt1 + 0.5F), (double)((float)paramInt2 + 0.5F), (double)((float)paramInt3 + 0.5F), paramExplosion.getExplosivePlacedBy());
         entityutntprimed.fuse = paramWorld.rand.nextInt(entityutntprimed.fuse / 4) + entityutntprimed.fuse / 8;
         paramWorld.spawnEntityInWorld(entityutntprimed);
      }

   }

   public void onBlockDestroyedByPlayer(World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
      this.primeTNT(paramWorld, paramInt1, paramInt2, paramInt3, paramInt4, (EntityLivingBase)null);
   }

   public void primeTNT(World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4, EntityLivingBase paramEntityLivingBase) {
      if(paramWorld.isRemote && (paramInt4 & 1) == 1) {
         EntityUTNTPrimed entityutntprimed = new EntityUTNTPrimed(paramWorld, (double)((float)paramInt1 + 0.5F), (double)((float)paramInt2 + 0.5F), (double)((float)paramInt3 + 0.5F), paramEntityLivingBase);
         paramWorld.spawnEntityInWorld(entityutntprimed);
         paramWorld.playSoundAtEntity(entityutntprimed, "game.tnt.primed", 1.0F, 1.0F);
      }

   }

   public boolean onBlockActivated(World paramWorld, int paramInt1, int paramInt2, int paramInt3, EntityPlayer paramEntityPlayer, int paramInt4, float paramFloat1, float paramFloat2, float paramFloat3) {
      if(paramEntityPlayer.getCurrentEquippedItem() != null && paramEntityPlayer.getCurrentEquippedItem().getItem() == Items.flint_and_steel) {
         this.primeTNT(paramWorld, paramInt1, paramInt2, paramInt3, 1, paramEntityPlayer);
         paramWorld.setBlockToAir(paramInt1, paramInt2, paramInt3);
         paramEntityPlayer.getCurrentEquippedItem().damageItem(1, paramEntityPlayer);
         return true;
      } else {
         return super.onBlockActivated(paramWorld, paramInt1, paramInt2, paramInt3, paramEntityPlayer, paramInt4, paramFloat1, paramFloat2, paramFloat3);
      }
   }

   public boolean canDropFromExplosion(Explosion paramExplosion) {
      return false;
   }
}
