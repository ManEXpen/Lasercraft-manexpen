package laser.block.machine;

import java.util.ArrayList;
import java.util.Iterator;

import cofh.core.network.PacketCoFHBase;
import cofh.lib.util.helpers.BlockHelper;
import cofh.lib.util.helpers.ServerHelper;
import cofh.thermalexpansion.block.TileTEBase;
import cofh.thermalexpansion.util.Utils;
import cpw.mods.fml.relauncher.Side;
import laser.entity.EntityQuarryLaser;
import laser.gui.client.machine.GuiChunkQuarry;
import laser.gui.container.machine.ContainerChunkQuarry;
import laser.network.PacketLCBase;
import laser.util.helper.RenderHelper;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.EmptyChunk;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.IFluidBlock;

public class TileChunkQuarry extends TileMachineBase implements ISidedInventory {

	static final int TYPE = 1;
	int outputTracker;
	public int chunkCoordX;
	public int chunkCoordZ;
	public int aimY = 255;
	public ArrayList stacks = new ArrayList();
	public static final int maxStacks = 40000;
	public int stackCount;
	public boolean showBorder;
	public boolean buildBarrier;
	public EntityQuarryLaser[] entityQuarryLaser = new EntityQuarryLaser[15];
	public EntityQuarryLaser[] entityQuarryLaser1 = new EntityQuarryLaser[4];

	public static void initialize() {
		defaultSideConfig[1] = new TileTEBase.SideConfig();
		defaultSideConfig[1].numConfig = 2;
		defaultSideConfig[1].slotGroups = new int[][] { new int[0], new int[0] };
		defaultSideConfig[1].allowInsertionSlot = new boolean[] { false, false };
		defaultSideConfig[1].allowExtractionSlot = new boolean[] { false, true };
		defaultSideConfig[1].sideTex = new int[] { 0, 4 };
		defaultSideConfig[1].defaultSides = new byte[] { (byte) 1, (byte) 1, (byte) 1, (byte) 1, (byte) 1, (byte) 1 };
		defaultEnergyConfig[1] = new TileTEBase.EnergyConfig();
		defaultEnergyConfig[1].setParams(64, 1024, 10000000);
	}

	public TileChunkQuarry() {
		this.inventory = new ItemStack[1];
	}

	public String registerChunk(int x, int z) {
		Chunk chunk = this.worldObj.getChunkFromChunkCoords(x, z);
		if (this.isActive) {
			return "chat.laser.chunkQuarry.active";
		} else if (chunk instanceof EmptyChunk) {
			return "chat.laser.chunkQuarry.notExist";
		} else if (chunk.chunkTileEntityMap.containsValue(this)) {
			return "chat.laser.chunkQuarry.contains";
		} else {
			this.chunkCoordX = x;
			this.chunkCoordZ = z;
			this.aimY = 255;
			if (ServerHelper.isServerWorld(this.worldObj)) {
				this.onInventoryChanged();
				this.sendUpdatePacket(Side.CLIENT);
			}

			return "chat.laser.chunkQuarry.success";
		}
	}

	public int getType() {
		return 1;
	}

	public void updateEntity() {
		int i;
		if (ServerHelper.isServerWorld(this.worldObj)) {
			boolean bool = this.isActive;
			if (this.isActive) {
				if (this.processRem > 0) {
					i = this.calcEnergy();
					this.energyStorage.modifyEnergyStored(-i);
					this.processRem -= i;
				}

				if (this.canFinish()) {
					this.processFinish();
					this.energyStorage.modifyEnergyStored(-this.processRem);
					if (this.redstoneControlOrDisable() && this.canStart()) {
						this.processStart();
					} else {
						this.isActive = false;
						this.wasActive = true;
						this.tracker.markTime(this.worldObj);
					}
				}

				this.transferProducts();
			} else if (this.redstoneControlOrDisable()) {
				if (this.timeCheckEighth() && this.canStart()) {
					this.processStart();
					i = this.calcEnergy();
					this.energyStorage.modifyEnergyStored(-i);
					this.processRem -= i;
					this.isActive = true;
				}

				this.transferProducts();
			}

			this.updateIfChanged(bool);
			this.chargeEnergy();
		}

		if (ServerHelper.isClientWorld(this.worldObj) && this.showBorder && this.timeCheckEighth()
				&& !this.isContainItself()) {
			RenderHelper.renderBorder(this.worldObj, this.chunkCoordX, this.chunkCoordZ);
		}

		if (this.isActive) {
			for (i = 0; i < this.entityQuarryLaser.length; ++i) {
				if (this.entityQuarryLaser[i] == null) {
					this.entityQuarryLaser[i] = new EntityQuarryLaser(this.worldObj, this, i);
					this.worldObj.spawnEntityInWorld(this.entityQuarryLaser[i]);
				}
			}

			for (i = 0; i < this.entityQuarryLaser1.length; ++i) {
				if (this.entityQuarryLaser1[i] == null) {
					this.entityQuarryLaser1[i] = new EntityQuarryLaser(this.worldObj, this,
							ForgeDirection.getOrientation(i + 2));
					this.worldObj.spawnEntityInWorld(this.entityQuarryLaser1[i]);
				}
			}

			this.sendUpdatePacket(Side.CLIENT);
		} else {
			for (i = 0; i < this.entityQuarryLaser.length; ++i) {
				if (this.entityQuarryLaser[i] != null) {
					this.entityQuarryLaser[i].setDead();
					this.worldObj.removeEntity(this.entityQuarryLaser[i]);
					this.entityQuarryLaser[i] = null;
				}
			}

			for (i = 0; i < this.entityQuarryLaser1.length; ++i) {
				if (this.entityQuarryLaser1[i] != null) {
					this.entityQuarryLaser1[i].setDead();
					this.worldObj.removeEntity(this.entityQuarryLaser1[i]);
					this.entityQuarryLaser1[i] = null;
				}
			}
		}

	}

	private boolean isContainItself() {
		Chunk chunk = this.worldObj.getChunkFromChunkCoords(this.chunkCoordX, this.chunkCoordZ);
		return chunk.chunkTileEntityMap.containsValue(this);
	}

	protected boolean canStart() {
		Chunk chunk = this.worldObj.getChunkFromChunkCoords(this.chunkCoordX, this.chunkCoordZ);
		return this.aimY >= 0 && this.aimY < 256 && !(chunk instanceof EmptyChunk) && !this.isContainItself()
				&& this.energyStorage.getEnergyStored() >= this.getRequireEnergy();
	}

	protected boolean canFinish() {
		Chunk chunk = this.worldObj.getChunkFromChunkCoords(this.chunkCoordX, this.chunkCoordZ);
		return this.processRem <= 0 && !(chunk instanceof EmptyChunk) && !this.isContainItself();
	}

	private int getRequireEnergy() {
		if (this.isContainItself()) {
			return 0;
		} else if (this.aimY >= 0 && this.aimY < 256
				&& !(this.worldObj.getChunkFromChunkCoords(this.chunkCoordX, this.chunkCoordZ) instanceof EmptyChunk)) {
			int beginX = this.chunkCoordX * 16;
			int beginZ = this.chunkCoordZ * 16;
			int endX = beginX + 16;
			int endZ = beginZ + 16;
			int energy = 1024;

			for (int i = beginX; i < endX; ++i) {
				for (int j = beginZ; j < endZ; ++j) {
					energy += this.getEnergyForBlock(this.worldObj.getBlock(i, this.aimY, j), i, this.aimY, j);
				}
			}

			return energy;
		} else {
			return 0;
		}
	}

	private int getEnergyForBlock(Block block, int x, int y, int z) {
		return !block.isAir(this.worldObj, x, y, z) && block.getBlockHardness(this.worldObj, x, y, z) >= 0.0F
				? (FluidRegistry.lookupFluidForBlock(block) != null
						? Math.abs(FluidRegistry.lookupFluidForBlock(block).getDensity())
						: (int) Math.ceil((2.0D + (double) block.getBlockHardness(this.worldObj, x, y, z)) * 200.0D))
				: 0;
	}

	protected void processStart() {
		int e = 0;
		int beginX = this.chunkCoordX * 16 - 1;
		int beginZ = this.chunkCoordZ * 16 - 1;
		int endX = beginX + 18;
		int endZ = beginZ + 18;
		if (this.buildBarrier) {
			for (int i = beginX; i < endX; ++i) {
				for (int j = beginZ; j < endZ; ++j) {
					if (i == beginX || i == endX - 1 || j == beginZ || j == endZ - 1) {
						Block block = this.worldObj.getBlock(i, this.aimY, j);
						if (block instanceof IFluidBlock || block == Blocks.water || block == Blocks.flowing_water
								|| block == Blocks.lava || block == Blocks.flowing_lava) {
							this.worldObj.setBlock(i, this.aimY, j, Blocks.glass);
							e += 256;
						}
					}
				}
			}
		}

		this.processMax = this.getRequireEnergy() + e;
		this.processRem = this.processMax;
	}

	protected void processFinish() {
		int beginX = this.chunkCoordX * 16;
		int beginZ = this.chunkCoordZ * 16;
		int endX = beginX + 16;
		int endZ = beginZ + 16;

		for (int i = beginX; i < endX; ++i) {
			for (int j = beginZ; j < endZ; ++j) {
				Block block = this.worldObj.getBlock(i, this.aimY, j);
				if (!block.isAir(this.worldObj, i, this.aimY, j)
						&& block.getBlockHardness(this.worldObj, i, this.aimY, j) >= 0.0F) {
					this.stacks.addAll(block.getDrops(this.worldObj, i, this.aimY, j,
							this.worldObj.getBlockMetadata(i, this.aimY, j), 0));
					if (this.stacks.size() > '\u9c40') {
						int k = this.stacks.size() - '\u9c40';

						for (int l = 0; l < k; ++l) {
							this.stacks.remove(0);
						}
					}

					this.worldObj.setBlockToAir(i, this.aimY, j);
				}
			}
		}

		this.stackCount = this.stacks.size();
		--this.aimY;
	}

	protected void transferProducts() {
		if (!this.stacks.isEmpty()) {
			for (int j = this.outputTracker + 1; j <= this.outputTracker + 6; ++j) {
				int i = j % 6;
				if (this.sideCache[i] == 1) {
					ItemStack itemstack = ((ItemStack) this.stacks.get(0)).copy();
					TileEntity localTileEntity = BlockHelper.getAdjacentTileEntity(this, i);
					if (Utils.isAccessibleInventory(localTileEntity, i)) {
						itemstack.stackSize -= itemstack.stackSize
								- Utils.addToInventory(localTileEntity, i, itemstack);
						if (itemstack.stackSize <= 0) {
							this.stacks.remove(0);
						}

						this.outputTracker = i;
						break;
					}

					if (Utils.isPipeTile(localTileEntity)) {
						itemstack.stackSize -= Utils.addToPipeTile(localTileEntity, i, itemstack);
						if (itemstack.stackSize <= 0) {
							this.stacks.remove(0);
						}

						this.outputTracker = i;
						break;
					}
				}
			}
		}

		this.stackCount = this.stacks.size();
	}

	public boolean rotateBlock() {
		byte[] arrayOfByte = new byte[6];
		int i;
		ForgeDirection localForgeDirection;
		if (this.allowYAxisFacing()) {
			label63: switch (this.facing) {
			case 0:
				i = 0;

				while (true) {
					if (i >= 6) {
						break label63;
					}

					arrayOfByte[i] = this.sideCache[BlockHelper.INVERT_AROUND_X[i]];
					++i;
				}
			case 1:
				i = 0;

				while (true) {
					if (i >= 6) {
						break label63;
					}

					arrayOfByte[i] = this.sideCache[BlockHelper.ROTATE_CLOCK_X[i]];
					++i;
				}
			case 2:
				i = 0;

				while (true) {
					if (i >= 6) {
						break label63;
					}

					arrayOfByte[i] = this.sideCache[BlockHelper.INVERT_AROUND_Y[i]];
					++i;
				}
			case 3:
				i = 0;

				while (true) {
					if (i >= 6) {
						break label63;
					}

					arrayOfByte[i] = this.sideCache[BlockHelper.ROTATE_CLOCK_Y[i]];
					++i;
				}
			case 4:
				i = 0;

				while (true) {
					if (i >= 6) {
						break label63;
					}

					arrayOfByte[i] = this.sideCache[BlockHelper.INVERT_AROUND_Z[i]];
					++i;
				}
			case 5:
				for (i = 0; i < 6; ++i) {
					arrayOfByte[i] = this.sideCache[BlockHelper.ROTATE_CLOCK_Z[i]];
				}
			}

			this.sideCache = (byte[]) ((byte[]) arrayOfByte.clone());
			++this.facing;
			this.facing = (byte) (this.facing % 6);
			localForgeDirection = ForgeDirection.getOrientation(this.facing);
			this.chunkCoordX = (this.xCoord >> 4) - localForgeDirection.offsetX;
			this.chunkCoordZ = (this.zCoord >> 4) - localForgeDirection.offsetZ;
			this.aimY = 255;
			this.onInventoryChanged();
			this.sendUpdatePacket(Side.CLIENT);
			return true;
		} else if (this.isActive) {
			return false;
		} else {
			arrayOfByte = new byte[6];

			for (i = 0; i < 6; ++i) {
				arrayOfByte[i] = this.sideCache[BlockHelper.ROTATE_CLOCK_Y[i]];
			}

			this.sideCache = (byte[]) ((byte[]) arrayOfByte.clone());
			this.facing = BlockHelper.SIDE_LEFT[this.facing];
			localForgeDirection = ForgeDirection.getOrientation(this.facing);
			this.chunkCoordX = (this.xCoord >> 4) - localForgeDirection.offsetX;
			this.chunkCoordZ = (this.zCoord >> 4) - localForgeDirection.offsetZ;
			this.aimY = 255;
			this.onInventoryChanged();
			this.sendUpdatePacket(Side.CLIENT);
			return true;
		}
	}

	public boolean setFacing(int paramInt) {
		if (paramInt >= 0 && paramInt <= 5) {
			if (!this.allowYAxisFacing() && paramInt < 2) {
				return false;
			} else {
				ForgeDirection localForgeDirection = ForgeDirection.getOrientation(paramInt);
				this.chunkCoordX = (this.xCoord >> 4) - localForgeDirection.offsetX;
				this.chunkCoordZ = (this.zCoord >> 4) - localForgeDirection.offsetZ;
				this.aimY = 255;
				return super.setFacing(paramInt);
			}
		} else {
			return false;
		}
	}

	public boolean isItemValid(ItemStack paramItemStack, int paramInt1, int paramInt2) {
		return false;
	}

	public Object getGuiClient(InventoryPlayer paramInventoryPlayer) {
		return new GuiChunkQuarry(paramInventoryPlayer, this);
	}

	public Object getGuiServer(InventoryPlayer paramInventoryPlayer) {
		return new ContainerChunkQuarry(paramInventoryPlayer, this);
	}

	public void readFromNBT(NBTTagCompound paramNBTTagCompound) {
		super.func_145839_a(paramNBTTagCompound);
		this.outputTracker = paramNBTTagCompound.getInteger("Tracker");
		this.chunkCoordX = paramNBTTagCompound.getInteger("ChunkX");
		this.chunkCoordZ = paramNBTTagCompound.getInteger("ChunkZ");
		this.aimY = paramNBTTagCompound.getInteger("ProcessingY");
		this.showBorder = paramNBTTagCompound.getBoolean("ShowBorder");
		this.buildBarrier = paramNBTTagCompound.getBoolean("BuildBarrier");
		ArrayList arraylist = new ArrayList();
		NBTTagList nbttaglist = (NBTTagList) paramNBTTagCompound.getTag("Stacks");

		for (int i = 0; i < nbttaglist.tagCount(); ++i) {
			arraylist.add(ItemStack.loadItemStackFromNBT(nbttaglist.getCompoundTagAt(i)));
		}

		this.stacks = arraylist;
	}

	public void writeToNBT(NBTTagCompound paramNBTTagCompound) {
		super.func_145841_b(paramNBTTagCompound);
		paramNBTTagCompound.setInteger("Tracker", this.outputTracker);
		paramNBTTagCompound.setInteger("ChunkX", this.chunkCoordX);
		paramNBTTagCompound.setInteger("ChunkZ", this.chunkCoordZ);
		paramNBTTagCompound.setInteger("ProcessingY", this.aimY);
		paramNBTTagCompound.setBoolean("ShowBorder", this.showBorder);
		paramNBTTagCompound.setBoolean("BuildBarrier", this.buildBarrier);
		NBTTagList nbttaglist = new NBTTagList();
		Iterator i$ = this.stacks.iterator();

		while (i$.hasNext()) {
			ItemStack itemstack = (ItemStack) i$.next();
			nbttaglist.appendTag(itemstack.writeToNBT(new NBTTagCompound()));
		}

		paramNBTTagCompound.setTag("Stacks", nbttaglist);
	}

	public PacketCoFHBase getPacket() {
		PacketCoFHBase localPacketCoFHBase = super.getPacket();
		localPacketCoFHBase.addBool(this.isActive);
		localPacketCoFHBase.addInt(this.chunkCoordX);
		localPacketCoFHBase.addInt(this.chunkCoordZ);
		localPacketCoFHBase.addInt(this.processMax);
		localPacketCoFHBase.addInt(this.processRem);
		localPacketCoFHBase.addInt(this.aimY);
		localPacketCoFHBase.addBool(this.showBorder);
		localPacketCoFHBase.addBool(this.buildBarrier);
		return localPacketCoFHBase;
	}

	public PacketCoFHBase getGuiPacket() {
		PacketCoFHBase localPacketCoFHBase = super.getGuiPacket();
		localPacketCoFHBase.addInt(this.aimY);
		localPacketCoFHBase.addInt(this.stackCount);
		return localPacketCoFHBase;
	}

	protected void handleGuiPacket(PacketCoFHBase paramPacketCoFHBase) {
		super.handleGuiPacket(paramPacketCoFHBase);
		this.aimY = paramPacketCoFHBase.getInt();
		this.stackCount = paramPacketCoFHBase.getInt();
	}

	public void handleTilePacket(PacketCoFHBase paramPacketCoFHBase, boolean paramBoolean) {
		super.handleTilePacket(paramPacketCoFHBase, paramBoolean);
		if (!paramBoolean) {
			this.isActive = paramPacketCoFHBase.getBool();
			this.chunkCoordX = paramPacketCoFHBase.getInt();
			this.chunkCoordZ = paramPacketCoFHBase.getInt();
			this.processMax = paramPacketCoFHBase.getInt();
			this.processRem = paramPacketCoFHBase.getInt();
			this.aimY = paramPacketCoFHBase.getInt();
			this.showBorder = paramPacketCoFHBase.getBool();
			this.buildBarrier = paramPacketCoFHBase.getBool();
		} else {
			paramPacketCoFHBase.getBool();
			paramPacketCoFHBase.getInt();
			paramPacketCoFHBase.getInt();
			paramPacketCoFHBase.getInt();
			paramPacketCoFHBase.getInt();
			paramPacketCoFHBase.getInt();
			paramPacketCoFHBase.getBool();
			paramPacketCoFHBase.getBool();
		}

	}

	public void setShowBorder(boolean paramBoolean) {
		this.showBorder = paramBoolean;
		if (ServerHelper.isClientWorld(this.worldObj)) {
			PacketLCBase.sendShowBorderPacketToServer(this, this.xCoord, this.yCoord, this.zCoord);
		} else {
			this.sendUpdatePacket(Side.CLIENT);
		}

	}

	public void setBuildBarrier(boolean paramBoolean) {
		this.buildBarrier = paramBoolean;
		if (ServerHelper.isClientWorld(this.worldObj)) {
			PacketLCBase.sendBuildBarrierPacketToServer(this, this.xCoord, this.yCoord, this.zCoord);
		} else {
			this.sendUpdatePacket(Side.CLIENT);
		}

	}

	public String getSoundName() {
		return enableSound[this.getType()] ? sounds[this.getType()] : null;
	}

	public int getNumConfig(int paramInt) {
		return this.sideConfig.numConfig;
	}

	public int[] getSlotsForFace(int paramInt) {
		return this.sideConfig.slotGroups[this.sideCache[paramInt]];
	}

	public boolean canInsertItem(int paramInt1, ItemStack paramItemStack, int paramInt2) {
		return this.sideConfig.allowInsertionSide[this.sideCache[paramInt2]]
				? isItemValid(paramItemStack, paramInt1, paramInt2) : false;
	}

	public boolean canExtractItem(int paramInt1, ItemStack paramItemStack, int paramInt2) {
		return this.sideConfig.allowExtractionSide[this.sideCache[paramInt2]];
	}

	@Override
	public int getSizeInventory() {
		// TODO 自動生成されたメソッド・スタブ
		return 1;
	}

	@Override
	public ItemStack getStackInSlot(int p_70301_1_) {
		// TODO 自動生成されたメソッド・スタブ
		return this.inventory[p_70301_1_];
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int p_70304_1_) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public String getInventoryName() {
		// TODO 自動生成されたメソッド・スタブ
		return "Chunk Quarry";
	}

	@Override
	public boolean hasCustomInventoryName() {
		// TODO 自動生成されたメソッド・スタブ
		return true;
	}

	@Override
	public int getInventoryStackLimit() {
		// TODO 自動生成されたメソッド・スタブ
		return 1;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer p_70300_1_) {
		// TODO 自動生成されたメソッド・スタブ
		return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false
				: p_70300_1_.getDistanceSq((double) this.xCoord + 0.5D, (double) this.yCoord + 0.5D,
						(double) this.zCoord + 0.5D) <= 64.0D;
	}

	@Override
	public void openInventory() {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void closeInventory() {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_) {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}

}
