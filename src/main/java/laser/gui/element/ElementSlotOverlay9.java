package laser.gui.element;

import cofh.lib.gui.GuiBase;
import cofh.lib.gui.element.ElementBase;
import cofh.lib.render.RenderHelper;
import net.minecraft.util.ResourceLocation;
import thermalexpansion.core.TEProps;

public class ElementSlotOverlay9 extends ElementBase {

	private static ResourceLocation TEXTURE = new ResourceLocation("laser:textures/gui/elements/Slot9.png");
	public int slotColor;

	public ElementSlotOverlay9(GuiBase paramGuiBase, int paramInt1, int paramInt2) {
		super(paramGuiBase, paramInt1, paramInt2);
		this.texture = TEXTURE;
	}

	public ElementSlotOverlay9 setSlotColor(int paramInt1) {
		this.slotColor = paramInt1;
		return this;
	}

	public void drawBackground(int paramInt1, int paramInt2, float paramFloat) {
		if (this.isVisible()) {
			RenderHelper.bindTexture(this.texture);
			if (TEProps.enableGuiBorders) {
				this.drawSlotWithBorder(this.posX, this.posY);
			} else {
				this.drawSlotNoBorder(this.posX, this.posY);
			}

		}
	}

	public void drawForeground(int paramInt1, int paramInt2) {
	}

	public boolean intersectsWith(int paramInt1, int paramInt2) {
		return false;
	}

	protected void drawSlotNoBorder(int paramInt1, int paramInt2) {
		int i = this.slotColor * 68 + 8;
		byte j = 8;
		this.gui.drawTexturedModalRect(paramInt1, paramInt2, i, j, 68, 68);
	}

	protected void drawSlotWithBorder(int paramInt1, int paramInt2) {
		int i = this.slotColor * 68;
		byte j = 0;
		paramInt1 -= 8;
		paramInt2 -= 8;
		this.gui.drawTexturedModalRect(paramInt1, paramInt2, i, j, 68, 68);
	}

}
