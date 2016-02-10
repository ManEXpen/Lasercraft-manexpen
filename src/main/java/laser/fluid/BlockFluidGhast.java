package laser.fluid;

import java.util.Random;

import cofh.core.fluid.BlockFluidCoFHBase;
import laser.Laser;
import net.minecraft.block.material.Material;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockFluidGhast extends BlockFluidCoFHBase {

   public static final int LEVELS = 5;


   public BlockFluidGhast() {
      super("laser", Laser.fluidGhast, Material.lava, "ghast");
      this.setQuantaPerBlock(5);
      this.setTickRate(20);
      this.setHardness(100.0F);
      this.setLightOpacity(15);
      this.setParticleColor(1.0F, 1.0F, 1.0F);
   }

   public int getLightValue(IBlockAccess paramIBlockAccess, int paramInt1, int paramInt2, int paramInt3) {
      return Laser.fluidGhast.getLuminosity();
   }

   public void updateTick(World paramWorld, int paramInt1, int paramInt2, int paramInt3, Random paramRandom) {
      paramWorld.playSoundEffect((double)paramInt1 + 0.5D, (double)paramInt2 + 0.5D, (double)paramInt3 + 0.5D, "mob.ghast.scream", 0.2F, (paramRandom.nextFloat() - paramRandom.nextFloat()) * 0.2F + 0.8F);
      super.updateTick(paramWorld, paramInt1, paramInt2, paramInt3, paramRandom);
   }
}
