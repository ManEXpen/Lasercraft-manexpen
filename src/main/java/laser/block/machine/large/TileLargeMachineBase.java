package laser.block.machine.large;

import java.util.ArrayList;
import java.util.Iterator;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyContainerItem;
import cofh.api.energy.IEnergyStorage;
import cofh.api.tileentity.IEnergyInfo;
import cofh.api.tileentity.IReconfigurableFacing;
import cofh.api.tileentity.ISidedTexture;
import cofh.core.network.PacketCoFHBase;
import cofh.core.render.IconRegistry;
import cofh.lib.util.TimeTracker;
import cofh.lib.util.helpers.BlockHelper;
import cofh.lib.util.helpers.EnergyHelper;
import cofh.lib.util.helpers.MathHelper;
import cofh.lib.util.helpers.ServerHelper;
import cofh.thermalexpansion.block.TileRSControl;
import cofh.thermalexpansion.block.TileTEBase;
import cofh.thermalexpansion.util.ReconfigurableHelper;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class TileLargeMachineBase extends TileRSControl
		implements IReconfigurableFacing, ISidedTexture, IEnergyInfo {

	protected static final TileTEBase.EnergyConfig[] defaultEnergyConfig = new TileTEBase.EnergyConfig[BlockLargeMachine.TYPES.length];
	protected static final String[] sounds = new String[BlockLargeMachine.TYPES.length];
	protected static final boolean[] enableSound = new boolean[] { true };
	protected static final int[] lightValue = new int[] { 0 };
	int processMax;
	int processRem;
	boolean wasActive;
	protected byte facing = 3;
	protected TileTEBase.EnergyConfig energyConfig;
	protected EnergyStorage energyStorage = new EnergyStorage(0);
	protected TimeTracker tracker = new TimeTracker();

	public TileLargeMachineBase() {
		this.energyConfig = defaultEnergyConfig[this.getType()].copy();
		this.energyStorage = new EnergyStorage(this.energyConfig.maxEnergy, this.energyConfig.maxPower);
	}

	public String getName() {
		return "tile.laser.machine.large." + BlockLargeMachine.TYPES[this.getType()] + ".name";
	}

	public int getLightValue() {
		return this.isActive ? lightValue[this.getType()] : 0;
	}

	public void updateEntity() {
		if (!ServerHelper.isClientWorld(this.worldObj)) {
			boolean bool = this.isActive;
			int i;
			if (this.isActive) {
				if (!this.isComplete()) {
					this.isActive = false;
					this.wasActive = true;
					this.tracker.markTime(this.worldObj);
				}

				if (this.processRem > 0) {
					i = this.calcEnergy();
					this.energyStorage.modifyEnergyStored(-i);
					this.processRem -= i;
				}

				if (this.canFinish()) {
					this.processFinish();
					this.transferProducts();
					this.energyStorage.modifyEnergyStored(-this.processRem);
					if (this.redstoneControlOrDisable() && this.canStart()) {
						this.processStart();
					} else {
						this.isActive = false;
						this.wasActive = true;
						this.tracker.markTime(this.worldObj);
					}
				}
			} else if (this.redstoneControlOrDisable()) {
				if (this.timeCheck()) {
					this.transferProducts();
				}

				if (this.timeCheckEighth() && this.canStart() && this.isComplete()) {
					this.processStart();
					i = this.calcEnergy();
					this.energyStorage.modifyEnergyStored(-i);
					this.processRem -= i;
					this.isActive = true;
				}
			}

			this.updateIfChanged(bool);
			this.chargeEnergy();
			if (this.timeCheckEighth() && this.isComplete()) {
				Iterator i$ = this.getCasingCoords().iterator();

				while (i$.hasNext()) {
					Integer[] coord = (Integer[]) i$.next();
					TileCasing localTileCasing = (TileCasing) this.worldObj.getTileEntity(coord[0].intValue(),
							coord[1].intValue(), coord[2].intValue());
					localTileCasing.setTile(this);
				}
			}

		}
	}

	protected int calcEnergy() {
		return !this.isActive ? 0
				: (this.energyStorage.getEnergyStored() > this.energyConfig.maxPowerLevel ? this.energyConfig.maxPower
						: (this.energyStorage.getEnergyStored() < this.energyConfig.minPowerLevel
								? this.energyConfig.minPower
								: this.energyStorage.getEnergyStored() / this.energyConfig.energyRamp));
	}

	protected int getMaxInputSlot() {
		return 0;
	}

	protected boolean canStart() {
		return false;
	}

	protected boolean canFinish() {
		return this.processRem > 0 ? false : this.hasValidInput();
	}

	protected boolean hasValidInput() {
		return true;
	}

	protected void processStart() {
	}

	protected void processFinish() {
	}

	protected void transferProducts() {
	}

	protected void updateIfChanged(boolean paramBoolean) {
		if (paramBoolean != this.isActive && this.isActive) {
			this.sendUpdatePacket(Side.CLIENT);
		} else if (this.tracker.hasDelayPassed(this.worldObj, 200) && this.wasActive) {
			this.wasActive = false;
			this.sendUpdatePacket(Side.CLIENT);
		}

	}

	public int getScaledProgress(int paramInt) {
		return this.isActive && this.processMax > 0 && this.processRem > 0
				? paramInt * (this.processMax - this.processRem) / this.processMax : 0;
	}

	public int getScaledSpeed(int paramInt) {
		if (!this.isActive) {
			return 0;
		} else {
			double d = (double) (this.energyStorage.getEnergyStored() / this.energyConfig.energyRamp);
			d = MathHelper.clip(d, (double) this.energyConfig.minPower, (double) this.energyConfig.maxPower);
			return MathHelper.round((double) paramInt * d / (double) this.energyConfig.maxPower);
		}
	}

	public void readFromNBT(NBTTagCompound paramNBTTagCompound) {
		super.readFromNBT(paramNBTTagCompound);
		this.facing = ReconfigurableHelper.getFacingFromNBT(paramNBTTagCompound);
		this.processMax = paramNBTTagCompound.getInteger("ProcMax");
		this.processRem = paramNBTTagCompound.getInteger("ProcRem");
	}

	public void writeToNBT(NBTTagCompound paramNBTTagCompound) {
		super.writeToNBT(paramNBTTagCompound);
		paramNBTTagCompound.setByte("Facing", this.facing);
		paramNBTTagCompound.setInteger("ProcMax", this.processMax);
		paramNBTTagCompound.setInteger("ProcRem", this.processRem);
	}

	public PacketCoFHBase getPacket() {
		PacketCoFHBase localPacketCoFHBase = super.getPacket();
		localPacketCoFHBase.addByte(this.facing);
		return localPacketCoFHBase;
	}

	public PacketCoFHBase getGuiPacket() {
		PacketCoFHBase localPacketCoFHBase = super.getGuiPacket();
		localPacketCoFHBase.addBool(this.isActive);
		localPacketCoFHBase.addInt(this.energyStorage.getMaxEnergyStored());
		localPacketCoFHBase.addInt(this.energyStorage.getEnergyStored());
		localPacketCoFHBase.addInt(this.processMax);
		localPacketCoFHBase.addInt(this.processRem);
		return localPacketCoFHBase;
	}

	protected void handleGuiPacket(PacketCoFHBase paramPacketCoFHBase) {
		this.isActive = paramPacketCoFHBase.getBool();
		this.energyStorage.setCapacity(paramPacketCoFHBase.getInt());
		this.energyStorage.setEnergyStored(paramPacketCoFHBase.getInt());
		this.processMax = paramPacketCoFHBase.getInt();
		this.processRem = paramPacketCoFHBase.getInt();
	}

	public void handleTilePacket(PacketCoFHBase paramPacketCoFHBase, boolean paramBoolean) {
		super.handleTilePacket(paramPacketCoFHBase, paramBoolean);
		if (!paramBoolean) {
			this.facing = paramPacketCoFHBase.getByte();
		} else {
			paramPacketCoFHBase.getByte();
		}

	}

	public ItemStack decrStackSize(int paramInt1, int paramInt2) {
		ItemStack localItemStack = super.func_70298_a(paramInt1, paramInt2);
		if (ServerHelper.isServerWorld(this.worldObj) && paramInt1 <= this.getMaxInputSlot() && this.isActive
				&& (this.inventory[paramInt1] == null || !this.hasValidInput())) {
			this.isActive = false;
			this.wasActive = true;
			this.tracker.markTime(this.worldObj);
			this.processRem = 0;
		}

		return localItemStack;
	}

	public void setInventorySlotContents(int paramInt, ItemStack paramItemStack) {
		if (ServerHelper.isServerWorld(this.worldObj) && paramInt <= this.getMaxInputSlot() && this.isActive
				&& this.inventory[paramInt] != null && (paramItemStack == null
						|| !paramItemStack.isItemEqual(this.inventory[paramInt]) || !this.hasValidInput())) {
			this.isActive = false;
			this.wasActive = true;
			this.tracker.markTime(this.worldObj);
			this.processRem = 0;
		}

		super.func_70299_a(paramInt, paramItemStack);
	}

	public void onInventoryChanged() {
		if (this.isActive && !this.hasValidInput()) {
			this.isActive = false;
			this.wasActive = true;
			this.tracker.markTime(this.worldObj);
			this.processRem = 0;
		}

		super.markDirty();// とりあえず
	}

	public int getInfoEnergyPerTick() {
		return this.calcEnergy();
	}

	public int getInfoMaxEnergyPerTick() {
		return this.energyConfig.maxPower;
	}

	public int getInfoEnergyStored() {
		return this.energyStorage.getEnergyStored();
	}

	public int getInfoMaxEnergyStored() {
		return this.energyStorage.getMaxEnergyStored();
	}

	public IIcon getTexture(int paramInt1, int paramInt2) {
		return paramInt1 != this.facing ? IconRegistry.getIcon("CasingSteel_15")
				: (this.isActive ? IconRegistry.getIcon("LCLargeMachineActive", this.getType())
						: IconRegistry.getIcon("LCLargeMachineFace", this.getType()));
	}

	public String getSoundName() {
		return enableSound[this.getType()] ? sounds[this.getType()] : null;
	}

	public boolean onWrench(EntityPlayer paramEntityPlayer, int paramInt) {
		return this.rotateBlock();
	}

	public final int getFacing() {
		return this.facing;
	}

	public boolean allowYAxisFacing() {
		return false;
	}

	public boolean rotateBlock() {
		if (this.allowYAxisFacing()) {
			++this.facing;
			this.facing = (byte) (this.facing % 6);
			this.onInventoryChanged();
			this.sendUpdatePacket(Side.CLIENT);
			return true;
		} else if (this.isActive) {
			return false;
		} else {
			this.facing = BlockHelper.SIDE_LEFT[this.facing];
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
				this.facing = (byte) paramInt;
				this.onInventoryChanged();
				this.sendUpdatePacket(Side.CLIENT);
				return true;
			}
		} else {
			return false;
		}
	}

	protected void chargeEnergy() {
		int i = getChargeSlot();
		if ((hasChargeSlot()) && (EnergyHelper.isEnergyContainerItem(this.inventory[i]))) {
			int j = Math.min(this.energyStorage.getMaxReceive(),
					this.energyStorage.getMaxEnergyStored() - this.energyStorage.getEnergyStored());
			this.energyStorage.receiveEnergy(
					((IEnergyContainerItem) this.inventory[i].getItem()).extractEnergy(this.inventory[i], j, false),
					false);
			if (this.inventory[i].stackSize <= 0) {
				this.inventory[i] = null;
			}
		}
	}

	public IEnergyStorage getEnergyStorage() {
		return this.energyStorage;
	}

	public int getMaxEnergyStored(ForgeDirection paramForgeDirection) {
		return this.energyStorage.getMaxEnergyStored();
	}

	public boolean hasChargeSlot() {
		return true;
	}

	public int getChargeSlot() {
		return this.inventory.length - 1;
	}

	public abstract ArrayList getCasingCoords();

	public abstract boolean isComplete();

}
