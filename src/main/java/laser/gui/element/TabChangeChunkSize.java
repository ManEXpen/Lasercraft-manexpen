package laser.gui.element;

import cofh.lib.gui.GuiBase;
import cofh.lib.gui.element.TabBase;
import laser.block.machine.TileChunkQuarry;

public class TabChangeChunkSize extends TabBase {

	// 1で右、0で左
	private static int defaultSide = 1;
	public static int defaultHeaderColor = 14797103;
	public static int defaultSubHeaderColor = 11186104;
	public static int defaultTextColor = 0;
	public static int defaultBackgroundColor = 2263142;
	TileChunkQuarry myTile;
	public static boolean enable;

	public TabChangeChunkSize(GuiBase paramGuiBase, TileChunkQuarry paramTileChunkQuarry) {
		this(paramGuiBase, defaultSide, paramTileChunkQuarry);
	}

	public TabChangeChunkSize(GuiBase paramGuiBase, int defaultSide2, TileChunkQuarry paramTileChunkQuarry) {
		super(paramGuiBase, defaultSide2);
		this.headerColor = defaultHeaderColor;
		this.subheaderColor = defaultSubHeaderColor;
		this.textColor = defaultTextColor;
		this.backgroundColor = defaultBackgroundColor;
		this.maxHeight = 92;
		this.maxWidth = 120;
		this.myTile = paramTileChunkQuarry;
	}

}
