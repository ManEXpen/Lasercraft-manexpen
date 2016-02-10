/*    */ package laser.gui.client.machine;

import org.lwjgl.opengl.GL11;

/*    */
/*    */ import cofh.lib.gui.element.ElementBase;
/*    */ import cofh.lib.gui.element.ElementDualScaled;
/*    */ import cofh.lib.gui.element.ElementEnergyStored;
/*    */ import laser.block.machine.TileChunkQuarry;
/*    */ import laser.gui.client.IStackGui;
/*    */ import laser.gui.element.ElementSlotOverlayStack;
/*    */ import laser.gui.element.ElementStackStored;
import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.entity.player.InventoryPlayer;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.util.ResourceLocation;

/*    */
/*    */ public class GuiChunkQuarry extends GuiMachineBase implements IStackGui
/*    */ {
	/* 18 */ public static final ResourceLocation TEXTURE = new ResourceLocation(
			"laser:textures/gui/machine/ChunkQuarry.png");
	/*    */ ElementBase slotStack;
	/*    */ ElementDualScaled progress;

	/*    */
	/*    */ public GuiChunkQuarry(InventoryPlayer paramInventoryPlayer, TileEntity paramTileEntity)
	/*    */ {
		/* 24 */ super(new laser.gui.container.machine.ContainerChunkQuarry(paramInventoryPlayer, paramTileEntity),
				paramTileEntity, paramInventoryPlayer.player, TEXTURE);
		/*    */ }

	/*    */
	/*    */ public void initGui()
	/*    */ {
		/* 29 */ super.initGui();
		/*    */
		/* 31 */ addTab(new laser.gui.element.TabChunkQuarry(this, (TileChunkQuarry) this.myTile));
		/*    */
		/* 33 */ this.slotStack = addElement(new ElementSlotOverlayStack(this, 154, 9).setSlotColor(1));
		/*    */
		/* 35 */ addElement(new ElementEnergyStored(this, 8, 8, this.myTile.getEnergyStorage()));
		/* 36 */ addElement(new ElementStackStored(this, 152, 8, 40000));
		/* 37 */ this.progress = ((ElementDualScaled) addElement(new ElementDualScaled(this, 72, 26).setSize(32, 32)
				.setTexture("laser:textures/gui/elements/Progress_Quarry.png", 64, 32)));
		/*    */ }

	/*    */
	/*    */ protected void func_146979_b(int paramInt1, int paramInt2)
	/*    */ {
		/* 42 */ super.func_146979_b(paramInt1, paramInt2);
		/*    */
		/* 44 */ if ((this.myTile.isActive) && (((TileChunkQuarry) this.myTile).aimY > -1)) {
			/* 45 */ int i = ((TileChunkQuarry) this.myTile).aimY;
			/* 46 */ this.fontRendererObj.drawString(String.valueOf(i + 1), getCenteredOffset(String.valueOf(i + 1)),
					18, 4210752);
			/* 47 */ this.fontRendererObj.drawString(String.valueOf(i), getCenteredOffset(String.valueOf(i)), 59,
					4210752);
			/*    */ }
		/*    */ }

	/*    */
	/*    */ protected void updateElementInformation()
	/*    */ {
		/* 53 */ super.updateElementInformation();
		/*    */
		/* 55 */ this.slotStack.setVisible(this.myTile.hasSide(1));
		/*    */
		/* 57 */ this.progress.setQuantity(33 - this.myTile.getScaledProgress(33));
		/*    */ }

	/*    */
	/*    */ public int getStackCount()
	/*    */ {
		/* 62 */ return ((TileChunkQuarry) this.myTile).stackCount;
		/*    */ }

	/*    */
	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		// ここに画像後で実装

		GL11.glColor4f(1F, 1F, 1F, 1F);
		Minecraft.getMinecraft().renderEngine.bindTexture(TEXTURE);

		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;

		this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);

	}
}

/*
 * Location:
 * C:\Users\ManEXpen\Downloads\LaserCraft-1.7.10-0.2B4.jar!\laser\gui\client\
 * machine\GuiChunkQuarry.class Java compiler version: 6 (50.0) JD-Core Version:
 * 0.7.1
 */