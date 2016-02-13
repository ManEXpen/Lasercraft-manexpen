package laser.gui;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.client.config.ConfigGuiType;
import cpw.mods.fml.client.config.DummyConfigElement;
import cpw.mods.fml.client.config.GuiConfig;
import net.minecraft.client.gui.GuiScreen;

public class GuiConfigLC extends GuiConfig {

	public static final String[] CATEGORIES = new String[0];

	public GuiConfigLC(GuiScreen paramGuiScreen) {
		super(paramGuiScreen, getConfigElements(), "LaserCraft", false, false, "LaserCraft");
	}

	private static List getConfigElements() {
		ArrayList<DummyConfigElement> localArrayList = new ArrayList();
		localArrayList.add(new DummyConfigElement("test", "This is dummy.", ConfigGuiType.STRING, "config.laser.test"));
		return localArrayList;
	}

}
