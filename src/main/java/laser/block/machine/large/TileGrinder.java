package laser.block.machine.large;

import java.util.ArrayList;

import cofh.core.util.CoreUtils;
import cofh.thermalexpansion.block.TileTEBase;
import laser.Laser;
import laser.gui.client.machine.large.GuiGrinder;
import laser.gui.container.machine.large.ContainerGrinder;
import laser.util.crafting.GrinderManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;

public class TileGrinder extends TileLargeMachineBase {

	static final int TYPE = 0;

	public static void initialize() {
		defaultEnergyConfig[0] = new TileTEBase.EnergyConfig();
		defaultEnergyConfig[0].setParams(16, 256, 200000);
		sounds[0] = CoreUtils.getSoundName("ThermalExpansion", "blockMachinePulverizer");
	}

	public TileGrinder() {
		this.inventory = new ItemStack[19];
	}

	public int getType() {
		return 0;
	}

	public int getMaxInputSlot() {
		return 8;
	}

	protected boolean canStart() {
		if (!this.isComplete()) {
			return false;
		} else {
			boolean flag = false;
			GrinderManager.RecipeGrinder[] localRecipesGrinder = new GrinderManager.RecipeGrinder[9];
			ArrayList localArrayList = new ArrayList();
			int energy = 0;

			for (int i = 0; i < 9; ++i) {
				flag |= GrinderManager.recipeExists(this.inventory[i]);
				localRecipesGrinder[i] = GrinderManager.getRecipe(this.inventory[i]);
				if (localRecipesGrinder[i] != null) {
					localArrayList.addAll(localRecipesGrinder[i].getOutputs());
					if (localRecipesGrinder[i].getEnergy() > energy) {
						energy = localRecipesGrinder[i].getEnergy();
					}
				}
			}

			if (flag && this.energyStorage.getEnergyStored() >= energy) {
				return this.canInsertProducts(localArrayList);
			} else {
				return false;
			}
		}
	}

	private boolean canInsertProducts(ArrayList paramArrayList) {
		if (paramArrayList.isEmpty()) {
			return true;
		} else {
			ItemStack localItemStack1 = ((ItemStack) paramArrayList.get(0)).copy();

			for (int i = 9; !paramArrayList.isEmpty() && i < 18; ++i) {
				while (!paramArrayList.isEmpty()) {
					if (this.inventory[i] == null) {
						paramArrayList.remove(0);
					} else {
						if (!this.inventory[i].isItemEqual(localItemStack1)) {
							break;
						}

						if (this.inventory[i].stackSize + localItemStack1.stackSize > localItemStack1
								.getMaxStackSize()) {
							int j = localItemStack1.getMaxStackSize() - this.inventory[i].stackSize;
							ItemStack localItemStack2 = localItemStack1.copy();
							localItemStack2.stackSize -= j;
							paramArrayList.set(0, localItemStack2.copy());
							break;
						}

						paramArrayList.remove(0);
					}
				}
			}

			return paramArrayList.isEmpty();
		}
	}

	protected void processStart() {
		GrinderManager.RecipeGrinder[] localRecipesGrinder = new GrinderManager.RecipeGrinder[9];
		int energy = 0;

		for (int i = 0; i < 9; ++i) {
			localRecipesGrinder[i] = GrinderManager.getRecipe(this.inventory[i]);
			if (localRecipesGrinder[i] != null && localRecipesGrinder[i].getEnergy() > energy) {
				energy = localRecipesGrinder[i].getEnergy();
			}
		}

		this.processMax = energy;
		this.processRem = this.processMax;
	}

	protected void processFinish() {
		this.worldObj.playSoundEffect((double) this.xCoord + 0.5D, (double) this.yCoord + 0.5D,
				(double) this.zCoord + 0.5D, "mob.irongolem.death", 0.5F,
				this.worldObj.rand.nextFloat() / 10.0F + 0.7F);
		GrinderManager.RecipeGrinder[] localRecipesGrinder = new GrinderManager.RecipeGrinder[9];
		ArrayList localArrayList = new ArrayList();

		int i;
		for (i = 0; i < 9; ++i) {
			localRecipesGrinder[i] = GrinderManager.getRecipe(this.inventory[i]);
			if (localRecipesGrinder[i] != null) {
				localArrayList.addAll(localRecipesGrinder[i].getOutputs());
			}
		}

		if (!localArrayList.isEmpty()) {
			ItemStack localItemStack1 = ((ItemStack) localArrayList.get(0)).copy();

			for (i = 9; !localArrayList.isEmpty() && i < 18; ++i) {
				while (!localArrayList.isEmpty()) {
					if (this.inventory[i] == null) {
						this.inventory[i] = localItemStack1.copy();
						localArrayList.remove(0);
					} else {
						if (!this.inventory[i].isItemEqual(localItemStack1)) {
							break;
						}

						if (this.inventory[i].stackSize + localItemStack1.stackSize > localItemStack1
								.getMaxStackSize()) {
							int j = localItemStack1.getMaxStackSize() - this.inventory[i].stackSize;
							this.inventory[i].stackSize += j;
							ItemStack localItemStack2 = localItemStack1.copy();
							localItemStack2.stackSize -= j;
							localArrayList.set(0, localItemStack2.copy());
							break;
						}

						this.inventory[i].stackSize += localItemStack1.stackSize;
						localArrayList.remove(0);
					}
				}
			}

			for (i = 0; i < 9; ++i) {
				if (this.inventory[i] != null) {
					--this.inventory[i].stackSize;
					if (this.inventory[i].stackSize <= 0) {
						this.inventory[i] = null;
					}
				}
			}
		}

	}

	public Object getGuiClient(InventoryPlayer paramInventoryPlayer) {
		return new GuiGrinder(paramInventoryPlayer, this);
	}

	public Object getGuiServer(InventoryPlayer paramInventoryPlayer) {
		return new ContainerGrinder(paramInventoryPlayer, this);
	}

	public ArrayList getCasingCoords() {
		ArrayList coords = new ArrayList();
		ForgeDirection face = ForgeDirection.getOrientation(this.getFacing());
		int centerX = this.xCoord - face.offsetX;
		int centerY = this.yCoord - face.offsetY;
		int centerZ = this.zCoord - face.offsetZ;

		for (int i = -1; i <= 1; ++i) {
			for (int j = -1; j <= 1; ++j) {
				for (int k = -1; k <= 1; ++k) {
					if (this.worldObj.getBlock(centerX + i, centerY + j, centerZ + k) == Laser.casing
							&& this.worldObj.getBlockMetadata(centerX + i, centerY + j,
									centerZ + k) == Laser.casingSteel.getItemDamage()) {
						coords.add(new Integer[] { Integer.valueOf(centerX + i), Integer.valueOf(centerY + j),
								Integer.valueOf(centerZ + k) });
					}
				}
			}
		}

		return coords;
	}

	public boolean isComplete() {
		ForgeDirection face = ForgeDirection.getOrientation(this.getFacing());
		return !this.worldObj.isAirBlock(this.xCoord - face.offsetX, this.yCoord, this.zCoord - face.offsetZ) ? false
				: this.getCasingCoords().size() == 25;
	}

	@Override
	public ItemStack getStackInSlot(int p_70301_1_) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int p_70304_1_) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public String getInventoryName() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public boolean hasCustomInventoryName() {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		// TODO 自動生成されたメソッド・スタブ
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer p_70300_1_) {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}

	@Override
	public void openInventory() {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void closeInventory() {
		// TODO 自動生成されたメソッド・スタブ
		super.markChunkDirty();

	}

	@Override
	public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_) {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}

	@Override
	public int getSizeInventory() {
		// TODO 自動生成されたメソッド・スタブ
		return 19;
	}
}
