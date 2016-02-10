/*    */ package laser.gui;
/*    */ 
/*    */ import cpw.mods.fml.client.config.ConfigGuiType;
/*    */ import cpw.mods.fml.client.config.DummyConfigElement;
/*    */ import cpw.mods.fml.client.config.GuiConfig;
/*    */ import cpw.mods.fml.client.config.IConfigElement;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import net.minecraft.client.gui.GuiScreen;
/*    */ 
/*    */ 
/*    */ public class GuiConfigLC
/*    */   extends GuiConfig
/*    */ {
/* 15 */   public static final String[] CATEGORIES = new String[0];
/*    */   
/*    */   public GuiConfigLC(GuiScreen paramGuiScreen)
/*    */   {
/* 19 */     super(paramGuiScreen, getConfigElements(), "LaserCraft", false, false, "LaserCraft");
/*    */   }
/*    */   
/*    */   private static List<IConfigElement> getConfigElements()
/*    */   {
/* 24 */     ArrayList localArrayList = new ArrayList();
/*    */     
/* 26 */     localArrayList.add(new DummyConfigElement("test", "This is dummy.", ConfigGuiType.STRING, "config.laser.test"));
/*    */     
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/* 32 */     return localArrayList;
/*    */   }
/*    */ }


/* Location:              C:\Users\ManEXpen\Downloads\LaserCraft-1.7.10-0.2B4.jar!\laser\gui\GuiConfigLC.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */