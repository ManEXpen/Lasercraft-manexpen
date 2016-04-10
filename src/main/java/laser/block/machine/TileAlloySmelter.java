package laser.block.machine;

import java.util.ArrayList;
import java.util.Iterator;

import cofh.core.util.CoreUtils;
import cofh.lib.inventory.ComparableItemStack;
import cofh.thermalexpansion.block.TileTEBase;
import laser.gui.client.machine.GuiAlloySmelter;
import laser.gui.container.machine.ContainerAlloySmelter;
import laser.util.crafting.AlloySmelterManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TileAlloySmelter extends TileMachineBase {

	static final int TYPE = 0;
	int outputTracker;

	public static void initialize() {
		defaultSideConfig[0] = new TileTEBase.SideConfig();
		defaultSideConfig[0].numConfig = 3;
		defaultSideConfig[0].slotGroups = new int[][] { new int[0], { 0, 1, 2, 3, 4, 5, 6, 7, 8 }, { 9, 10 } };
		defaultSideConfig[0].allowInsertionSlot = new boolean[] { false, true, false };
		defaultSideConfig[0].allowExtractionSlot = new boolean[] { false, true, true };
		defaultSideConfig[0].sideTex = new int[] { 0, 1, 4 };
		defaultSideConfig[0].defaultSides = new byte[] { (byte) 1, (byte) 1, (byte) 2, (byte) 2, (byte) 2, (byte) 2 };
		defaultEnergyConfig[0] = new TileTEBase.EnergyConfig();
		defaultEnergyConfig[0].setParams(16, 256, 200000);
		sounds[0] = CoreUtils.getSoundName("ThermalExpansion", "blockMachineFurnace");
	}

	public TileAlloySmelter() {
		this.inventory = new ItemStack[12];
	}

	public int getType() {
		return 0;
	}

	public int getMaxInputSlot() {
		return 8;
	}

	protected boolean canStart() {
		AlloySmelterManager.RecipeAlloySmelter localRecipeAlloySmelter = AlloySmelterManager
				.getRecipeFromInventory(this.inventory);
		if (localRecipeAlloySmelter != null
				&& this.energyStorage.getEnergyStored() >= localRecipeAlloySmelter.getEnergy()) {
			ItemStack localItemStack = localRecipeAlloySmelter.getOutput();
			return this.inventory[9] != null && this.inventory[10] != null
					? (!this.inventory[9].isItemEqual(localItemStack) && !this.inventory[10].isItemEqual(localItemStack)
							? false
							: (!this.inventory[9].isItemEqual(localItemStack)
									? this.inventory[10].stackSize + localItemStack.stackSize <= localItemStack
											.getMaxStackSize()
									: (!this.inventory[10].isItemEqual(localItemStack)
											? this.inventory[9].stackSize + localItemStack.stackSize <= localItemStack
													.getMaxStackSize()
											: this.inventory[9].stackSize + this.inventory[10].stackSize
													+ localItemStack.stackSize <= localItemStack.getMaxStackSize()
															* 2)))
					: true;
		} else {
			return false;
		}
	}

	protected void processStart() {
		AlloySmelterManager.RecipeAlloySmelter localRecipeAlloySmelter = AlloySmelterManager
				.getRecipeFromInventory(this.inventory);
		this.processMax = localRecipeAlloySmelter.getEnergy();
		this.processRem = this.processMax;
	}

	protected void processFinish() {
		AlloySmelterManager.RecipeAlloySmelter localRecipeAlloySmelter = AlloySmelterManager
				.getRecipeFromInventory(this.inventory);
		ItemStack localItemStack1 = localRecipeAlloySmelter.getOutput();
		int i;
		if (this.inventory[9] == null) {
			this.inventory[9] = localItemStack1;
		} else if (this.inventory[9].isItemEqual(localItemStack1)) {
			if (this.inventory[9].stackSize + localItemStack1.stackSize <= localItemStack1.getMaxStackSize()) {
				this.inventory[9].stackSize += localItemStack1.stackSize;
			} else {
				i = localItemStack1.getMaxStackSize() - this.inventory[9].stackSize;
				this.inventory[9].stackSize += i;
				if (this.inventory[10] == null) {
					this.inventory[10] = localItemStack1;
					this.inventory[10].stackSize = localItemStack1.stackSize - i;
				} else {
					this.inventory[10].stackSize += localItemStack1.stackSize - i;
				}
			}
		} else if (this.inventory[10] == null) {
			this.inventory[10] = localItemStack1;
		} else {
			this.inventory[10].stackSize += localItemStack1.stackSize;
		}

		ArrayList localArrayList = new ArrayList();

		for (i = 0; i < localRecipeAlloySmelter.getInputs().size(); ++i) {
			localArrayList.add(new ComparableItemStack((ItemStack) localRecipeAlloySmelter.getInputs().get(i)));
		}

		Iterator i$ = localArrayList.iterator();

		while (i$.hasNext()) {
			ComparableItemStack localComparableItemStack = (ComparableItemStack) i$.next();

			for (i = 0; i < 9; ++i) {
				if (localComparableItemStack.isItemEqual(new ComparableItemStack(this.inventory[i]))) {
					boolean flag = true;
					if (localComparableItemStack.stackSize > this.inventory[i].stackSize) {
						localComparableItemStack.stackSize -= this.inventory[i].stackSize;
						flag = false;
					}

					this.inventory[i].stackSize -= localComparableItemStack.stackSize;
					if (this.inventory[i].stackSize <= 0) {
						this.inventory[i] = null;
					}

					if (flag) {
						break;
					}
				}
			}
		}

	}

	protected void transferProducts() {
		if (this.inventory[9] != null || this.inventory[10] != null) {
			for (int j = this.outputTracker + 1; j <= this.outputTracker + 6; ++j) {
				int i = j % 6;
				if (this.sideCache[i] == 2) {
					if (this.transferItem(9, 4, i)) {
						if (!this.transferItem(10, 4, i)) {
							this.transferItem(9, 4, i);
						}

						this.outputTracker = i;
						break;
					}

					if (this.transferItem(10, 8, i)) {
						this.outputTracker = i;
						break;
					}
				}
			}
		}

	}

	public boolean isItemValid(ItemStack paramItemStack, int paramInt1, int paramInt2) {
		return AlloySmelterManager.isItemValid(paramItemStack);
	}

	public Object getGuiClient(InventoryPlayer paramInventoryPlayer) {
		return new GuiAlloySmelter(paramInventoryPlayer, this);
	}

	public Object getGuiServer(InventoryPlayer paramInventoryPlayer) {
		return new ContainerAlloySmelter(paramInventoryPlayer, this);
	}

	public void readFromNBT(NBTTagCompound paramNBTTagCompound) {
		super.readFromNBT(paramNBTTagCompound);
		this.outputTracker = paramNBTTagCompound.getInteger("Tracker");
	}

	public void writeToNBT(NBTTagCompound paramNBTTagCompound) {
		super.writeToNBT(paramNBTTagCompound);
		paramNBTTagCompound.setInteger("Tracker", this.outputTracker);
	}

	@Override
	public int getSizeInventory() {
		// TODO 自動生成されたメソッド・スタブ
		return 12;
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
		super.markDirty();

	}

	@Override
	public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_) {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}

}
