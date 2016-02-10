package laser.plugins.nei.handlers;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import java.util.Iterator;
import laser.gui.client.machine.large.GuiGrinder;
import laser.plugins.nei.handlers.RecipeHandlerBase;
import laser.util.crafting.GrinderManager;
import net.minecraft.item.ItemStack;

public class RecipeHandlerGrinder extends RecipeHandlerBase {

   public RecipeHandlerGrinder() {
      this.maxEnergy = 200000;
   }

   public void initialize() {
      this.trCoords = new int[]{82, 23, 24, 18};
      this.recipeName = "large.grinder";
      this.containerClass = GuiGrinder.class;
   }

   public void drawBackgroundExtras(int paramInt) {
      GuiDraw.drawTexturedModalRect(42, 23, 176, 96, 18, 18);
      GuiDraw.drawTexturedModalRect(110, 5, 176, 96, 18, 18);
      GuiDraw.drawTexturedModalRect(128, 5, 176, 96, 18, 18);
      GuiDraw.drawTexturedModalRect(146, 5, 176, 96, 18, 18);
      GuiDraw.drawTexturedModalRect(110, 23, 176, 96, 18, 18);
      GuiDraw.drawTexturedModalRect(128, 23, 176, 96, 18, 18);
      GuiDraw.drawTexturedModalRect(146, 23, 176, 96, 18, 18);
      GuiDraw.drawTexturedModalRect(110, 41, 176, 96, 18, 18);
      GuiDraw.drawTexturedModalRect(128, 41, 176, 96, 18, 18);
      GuiDraw.drawTexturedModalRect(146, 41, 176, 96, 18, 18);
      GuiDraw.drawTexturedModalRect(86, 41, 224, 48, 16, 16);
      this.drawProgressBar(86, 41, 240, 48, 16, 16, 100, 7);
      GuiDraw.drawTexturedModalRect(82, 24, 176, 16, 24, 16);
      this.drawProgressBar(82, 24, 200, 16, 24, 16, 20, 0);
   }

   public void drawExtras(int paramInt) {
      this.drawEnergy(paramInt);
      int i = ((RecipeHandlerBase.NEIRecipeBase)this.arecipes.get(paramInt)).energy;
      if(i < 1000) {
         GuiDraw.drawString(i + "RF", 40, 50, 9671571, false);
      } else {
         GuiDraw.drawString(i + "RF", 34, 50, 9671571, false);
      }

   }

   public void loadCraftingRecipes(String paramString, Object ... paramArrayOfObject) {
      if(paramString.equals(this.getOverlayIdentifier())) {
         GrinderManager.RecipeGrinder[] arrayOfRecipeGrinder1 = GrinderManager.getRecipeList();
         GrinderManager.RecipeGrinder[] arr$ = arrayOfRecipeGrinder1;
         int len$ = arrayOfRecipeGrinder1.length;

         for(int i$ = 0; i$ < len$; ++i$) {
            GrinderManager.RecipeGrinder localRecipeGrinder = arr$[i$];
            this.arecipes.add(new RecipeHandlerGrinder.NEIRecipeGrinder(localRecipeGrinder));
         }
      } else {
         super.loadCraftingRecipes(paramString, paramArrayOfObject);
      }

   }

   public void loadCraftingRecipes(ItemStack paramItemStack) {
      GrinderManager.RecipeGrinder[] arrayOfRecipeGrinder1 = GrinderManager.getRecipeList();
      GrinderManager.RecipeGrinder[] arr$ = arrayOfRecipeGrinder1;
      int len$ = arrayOfRecipeGrinder1.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         GrinderManager.RecipeGrinder localRecipeGrinder = arr$[i$];
         Iterator i$1 = localRecipeGrinder.getOutputs().iterator();

         while(i$1.hasNext()) {
            ItemStack localItemStack = (ItemStack)i$1.next();
            if(NEIServerUtils.areStacksSameType(localItemStack, paramItemStack)) {
               this.arecipes.add(new RecipeHandlerGrinder.NEIRecipeGrinder(localRecipeGrinder));
            }
         }
      }

   }

   public void loadUsageRecipes(String paramString, Object ... paramArrayOfObject) {
      if(paramString.equals("fuel") && this.getClass() == RecipeHandlerGrinder.class) {
         this.loadCraftingRecipes(this.getOverlayIdentifier(), new Object[0]);
      } else {
         super.loadUsageRecipes(paramString, paramArrayOfObject);
      }

   }

   public void loadUsageRecipes(ItemStack paramItemStack) {
      GrinderManager.RecipeGrinder[] arrayOfRecipeGrinder1 = GrinderManager.getRecipeList();
      GrinderManager.RecipeGrinder[] arr$ = arrayOfRecipeGrinder1;
      int len$ = arrayOfRecipeGrinder1.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         GrinderManager.RecipeGrinder localRecipeGrinder = arr$[i$];
         if(NEIServerUtils.areStacksSameType(localRecipeGrinder.getInput(), paramItemStack)) {
            this.arecipes.add(new RecipeHandlerGrinder.NEIRecipeGrinder(localRecipeGrinder));
         }
      }

   }

   class NEIRecipeGrinder extends RecipeHandlerBase.NEIRecipeBase {

      public NEIRecipeGrinder(GrinderManager.RecipeGrinder arg2) {
         super();
         this.inputList.add(new PositionedStack(arg2.getInput(), 43, 24));

         for(int i = 0; i < 9 && i < arg2.getOutputs().size(); ++i) {
            int j = i % 3 * 18;
            int k = i / 3 * 18;
            this.outputList.add(new PositionedStack(arg2.getOutputs().get(i), 111 + j, 6 + k));
         }

         this.energy = arg2.getEnergy();
         this.setOres();
      }
   }
}
