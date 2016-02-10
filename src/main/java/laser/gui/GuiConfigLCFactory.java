/*    */ package laser.gui;
/*    */ 
/*    */ import cpw.mods.fml.client.IModGuiFactory;
/*    */ import cpw.mods.fml.client.IModGuiFactory.RuntimeOptionCategoryElement;
/*    */ import cpw.mods.fml.client.IModGuiFactory.RuntimeOptionGuiHandler;
/*    */ import java.util.Set;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.gui.GuiScreen;
/*    */ 
/*    */ public class GuiConfigLCFactory
/*    */   implements IModGuiFactory
/*    */ {
/*    */   public void initialize(Minecraft paramMinecraft) {}
/*    */   
/*    */   public Class<? extends GuiScreen> mainConfigGuiClass()
/*    */   {
/* 17 */     return GuiConfigLC.class;
/*    */   }
/*    */   
/*    */   public Set<IModGuiFactory.RuntimeOptionCategoryElement> runtimeGuiCategories()
/*    */   {
/* 22 */     return null;
/*    */   }
/*    */   
/*    */   public IModGuiFactory.RuntimeOptionGuiHandler getHandlerFor(IModGuiFactory.RuntimeOptionCategoryElement paramRuntimeOptionCategoryElement)
/*    */   {
/* 27 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\ManEXpen\Downloads\LaserCraft-1.7.10-0.2B4.jar!\laser\gui\GuiConfigLCFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */