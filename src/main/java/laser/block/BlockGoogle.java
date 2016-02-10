package laser.block;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import laser.Laser;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockGoogle extends Block {

	public static double RANGE = 6.0D;
	public static int SPEED = 10;

	public BlockGoogle() {
		super(Material.iron);
		this.setHardness(4.0F);
		this.setStepSound(soundTypeMetal);
		this.setCreativeTab(Laser.tabBlocks);
		this.setBlockName("laser.google");
	}

	public int getLightValue(IBlockAccess paramIBlockAccess, int paramInt1, int paramInt2, int paramInt3) {
		return 15;
	}

	@Override
	public void onBlockAdded(World paramWorld, int paramInt1, int paramInt2, int paramInt3) {
		paramWorld.scheduleBlockUpdate(paramInt1, paramInt2, paramInt3, this, 0);
	}

	@Override
	public void updateTick(World paramWorld, int paramInt1, int paramInt2, int paramInt3, Random paramRandom) {
		AxisAlignedBB aabb = AxisAlignedBB.getBoundingBox((double) paramInt1 - RANGE, (double) paramInt2 - RANGE,
				(double) paramInt3 - RANGE, (double) paramInt1 + 1.0D + RANGE, (double) paramInt2 + 1.0D + RANGE,
				(double) paramInt3 + 1.0D + RANGE);
		List list = paramWorld.getEntitiesWithinAABB(Entity.class, aabb);
		Iterator x = list.iterator();

		int z;
		while (x.hasNext()) {
			Entity y = (Entity) x.next();
			if (y != null && !(y instanceof EntityPlayer) && !(y instanceof EntityItem)
					&& !(y instanceof EntityXPOrb)) {
				for (z = 0; z < SPEED - 1; ++z) {
					y.onUpdate();
				}
			}
		}

		for (int var14 = paramInt1 - ((int) RANGE - 1); var14 < paramInt1 + (int) RANGE; ++var14) {
			for (int var15 = paramInt2 - ((int) RANGE - 1); var15 < paramInt2 + (int) RANGE; ++var15) {
				for (z = paramInt3 - ((int) RANGE - 1); z < paramInt3 + (int) RANGE; ++z) {
					Block block = paramWorld.getBlock(var14, var15, z);
					TileEntity tile = paramWorld.getTileEntity(var14, var15, z);

					for (int i = 0; i < SPEED - 1; ++i) {
						if (block != this) {
							block.updateTick(paramWorld, var14, var15, z, paramRandom);
						}

						if (tile != null) {
							tile.updateEntity();
						}
					}
				}
			}
		}

		paramWorld.scheduleBlockUpdate(paramInt1, paramInt2, paramInt3, this, 0);
	}

}
