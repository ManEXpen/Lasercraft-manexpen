package laser.gui.element;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import cofh.lib.gui.GuiBase;
import cofh.lib.gui.element.TabBase;
import cofh.lib.util.helpers.StringHelper;
import laser.block.machine.TileChunkQuarry;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.init.Items;

public class TabChunkQuarry extends TabBase {

	public static boolean enable;
	public static int defaultSide = 1;
	public static int defaultHeaderColor = 14797103;
	public static int defaultSubHeaderColor = 11186104;
	public static int defaultTextColor = 0;
	public static int defaultBackgroundColor = 2263142;
	private List<GuiTextField> textField;
	private TileChunkQuarry myTile;

	public TabChunkQuarry(GuiBase paramGuiBase,
			TileChunkQuarry paramTileChunkQuarry) {
		this(paramGuiBase, defaultSide, paramTileChunkQuarry);
	}

	public TabChunkQuarry(GuiBase paramGuiBase, int paramInt,
			TileChunkQuarry paramTileChunkQuarry) {
		super(paramGuiBase, paramInt);
		this.headerColor = defaultHeaderColor;
		this.subheaderColor = defaultSubHeaderColor;
		this.textColor = defaultTextColor;
		this.backgroundColor = defaultBackgroundColor;
		this.maxHeight = 92;
		this.maxWidth = 120;
		this.myTile = paramTileChunkQuarry;
	}

	public void draw() {
		this.textField = new ArrayList<>();
		this.drawBackground();
		this.drawTabIcon("IconConfig");
		if (this.isFullyOpened()) {
			this.getFontRenderer().drawStringWithShadow(
					StringHelper.localize("info.laser.chunkQuarry.config"),
					this.posXOffset() + 18, this.posY + 6, this.headerColor);
			if (this.myTile.showBorder) {
				this.gui.drawButton(Items.redstone.getIconFromDamage(0),
						this.posX() + 16, this.posY + 20, 1, 1);
			} else {
				this.gui.drawButton(Items.gunpowder.getIconFromDamage(0),
						this.posX() + 16, this.posY + 20, 1, 0);
			}

			this.textField.add(new GuiTextField(getFontRenderer(),
					this.posX() + 16, this.posY + 38, 20, 10));
			this.textField.add(new GuiTextField(getFontRenderer(),
					this.posX() + 16, this.posY + 50, 20, 10));
			this.textField.stream().forEach(x -> {
				x.setFocused(false);
				x.setText("0");
				x.setMaxStringLength(100);
				x.drawTextBox();
			});

			this.gui.drawString(getFontRenderer(),
					StringHelper
							.localize("info.laser.chunkQuarry.xchunkchange"),
					super.posX() + 40, super.posY + 38, 0xffffff);

			this.gui.drawString(getFontRenderer(),
					StringHelper
							.localize("info.laser.chunkQuarry.ychunkchange"),
					super.posX() + 40, super.posY + 50, 0xffffff);

			this.gui.drawButton(Items.arrow.getIconFromDamage(0),
					this.posX() + 16, this.posY + 70, 1, 0);

			if (this.myTile.buildBarrier) {
				this.gui.drawButton(Items.sugar.getIconFromDamage(0),
						this.posX() + 36, this.posY + 20, 1, 1);
			} else {
				this.gui.drawButton(Items.gunpowder.getIconFromDamage(0),
						this.posX() + 36, this.posY + 20, 1, 0);
			}

			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		}
	}

	public void addTooltip(List paramList) {
		if (!this.isFullyOpened()) {
			paramList.add(
					StringHelper.localize("info.laser.chunkQuarry.config"));
		} else {
			int i = this.gui.getMouseX() - this.currentShiftX;
			int j = this.gui.getMouseY() - this.currentShiftY;
			if (16 <= i && i < 32 && 20 <= j && j < 36) {
				paramList.add(StringHelper
						.localize("info.laser.chunkQuarry.showBorder"));
			} else if (36 <= i && i < 52 && 20 <= j && j < 36) {
				paramList.add(StringHelper
						.localize("info.laser.chunkQuarry.buildBarrier"));
			} else if (16 <= i && i < 32 && 70 <= j && j < 36) {
				paramList.add(
						StringHelper.localize("info.laser.chunkQuarry.save"));
			}

		}
	}

	public boolean onMousePressed(int paramInt1, int paramInt2, int paramInt3) {
		if (!this.isFullyOpened()) {
			return false;
		} else {
			if (this.side == LEFT) {
				paramInt1 += this.currentWidth;
			}

			paramInt1 -= this.currentShiftX;
			paramInt2 -= this.currentShiftY;
			if (paramInt1 >= 12 && paramInt1 < 96 && paramInt2 >= 16
					&& paramInt2 < 40) {
				if (16 <= paramInt1 && paramInt1 < 32 && 20 <= paramInt2
						&& paramInt2 < 36) {
					if (!this.myTile.showBorder) {
						this.myTile.setShowBorder(true);
						GuiBase.playSound("random.click", 1.0F, 0.8F);
					} else {
						this.myTile.setShowBorder(false);
						GuiBase.playSound("random.click", 1.0F, 0.4F);
					}
				} else if (36 <= paramInt1 && paramInt1 < 52 && 20 <= paramInt2
						&& paramInt2 < 36) {
					if (!this.myTile.buildBarrier) {
						this.myTile.setBuildBarrier(true);
						GuiBase.playSound("random.click", 1.0F, 0.8F);
					} else {
						this.myTile.setBuildBarrier(false);
						GuiBase.playSound("random.click", 1.0F, 0.4F);
					}
				}

				return true;
			} else {
				return false;
			}
		}
	}
}