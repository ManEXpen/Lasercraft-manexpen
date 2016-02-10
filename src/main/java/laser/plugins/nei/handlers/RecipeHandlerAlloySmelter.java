package laser.plugins.nei.handlers;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import java.util.Iterator;
import laser.gui.client.machine.GuiAlloySmelter;
import laser.plugins.nei.handlers.RecipeHandlerBase;
import laser.util.crafting.AlloySmelterManager;
import net.minecraft.item.ItemStack;

public class RecipeHandlerAlloySmelter extends RecipeHandlerBase {

   public RecipeHandlerAlloySmelter() {
      this.maxEnergy = 200000;
   }

   public void initialize() {
      this.trCoords = new int[]{87, 23, 24, 18};
      this.recipeName = "alloySmelter";
      this.containerClass = GuiAlloySmelter.class;
   }

   public void drawBackgroundExtras(int paramInt) {
      GuiDraw.drawTexturedModalRect(25, 5, 176, 96, 18, 18);
      GuiDraw.drawTexturedModalRect(43, 5, 176, 96, 18, 18);
      GuiDraw.drawTexturedModalRect(61, 5, 176, 96, 18, 18);
      GuiDraw.drawTexturedModalRect(25, 23, 176, 96, 18, 18);
      GuiDraw.drawTexturedModalRect(43, 23, 176, 96, 18, 18);
      GuiDraw.drawTexturedModalRect(61, 23, 176, 96, 18, 18);
      GuiDraw.drawTexturedModalRect(25, 41, 176, 96, 18, 18);
      GuiDraw.drawTexturedModalRect(43, 41, 176, 96, 18, 18);
      GuiDraw.drawTexturedModalRect(61, 41, 176, 96, 18, 18);
      GuiDraw.drawTexturedModalRect(119, 19, 224, 96, 26, 26);
      GuiDraw.drawTexturedModalRect(91, 42, 224, 32, 16, 16);
      this.drawProgressBar(91, 42, 240, 32, 16, 16, 100, 7);
      GuiDraw.drawTexturedModalRect(87, 24, 176, 16, 24, 16);
      this.drawProgressBar(87, 24, 200, 16, 24, 16, 20, 0);
   }

   public void drawExtras(int paramInt) {
      this.drawEnergy(paramInt);
      int i = ((RecipeHandlerBase.NEIRecipeBase)this.arecipes.get(paramInt)).energy;
      if(i < 1000) {
         GuiDraw.drawString(i + "RF", 120, 50, 9671571, false);
      } else {
         GuiDraw.drawString(i + "RF", 116, 50, 9671571, false);
      }

   }

   public void loadCraftingRecipes(String paramString, Object ... paramArrayOfObject) {
      if(paramString.equals(this.getOverlayIdentifier())) {
         AlloySmelterManager.RecipeAlloySmelter[] arrayOfRecipeAlloySmelter1 = AlloySmelterManager.getRecipeList();
         AlloySmelterManager.RecipeAlloySmelter[] arr$ = arrayOfRecipeAlloySmelter1;
         int len$ = arrayOfRecipeAlloySmelter1.length;

         for(int i$ = 0; i$ < len$; ++i$) {
            AlloySmelterManager.RecipeAlloySmelter localRecipeAlloySmelter = arr$[i$];
            this.arecipes.add(new RecipeHandlerAlloySmelter.NEIRecipeAlloySmelter(localRecipeAlloySmelter));
         }
      } else {
         super.loadCraftingRecipes(paramString, paramArrayOfObject);
      }

   }

   public void loadCraftingRecipes(ItemStack paramItemStack) {
      AlloySmelterManager.RecipeAlloySmelter[] arrayOfRecipeAlloySmelter1 = AlloySmelterManager.getRecipeList();
      AlloySmelterManager.RecipeAlloySmelter[] arr$ = arrayOfRecipeAlloySmelter1;
      int len$ = arrayOfRecipeAlloySmelter1.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         AlloySmelterManager.RecipeAlloySmelter localRecipeAlloySmelter = arr$[i$];
         if(NEIServerUtils.areStacksSameType(localRecipeAlloySmelter.getOutput(), paramItemStack)) {
            this.arecipes.add(new RecipeHandlerAlloySmelter.NEIRecipeAlloySmelter(localRecipeAlloySmelter));
         }
      }

   }

   public void loadUsageRecipes(String paramString, Object ... paramArrayOfObject) {
      if(paramString.equals("fuel") && this.getClass() == RecipeHandlerAlloySmelter.class) {
         this.loadCraftingRecipes(this.getOverlayIdentifier(), new Object[0]);
      } else {
         super.loadUsageRecipes(paramString, paramArrayOfObject);
      }

   }

   public void loadUsageRecipes(ItemStack paramItemStack) {
      AlloySmelterManager.RecipeAlloySmelter[] arrayOfRecipeAlloySmelter1 = AlloySmelterManager.getRecipeList();
      AlloySmelterManager.RecipeAlloySmelter[] arr$ = arrayOfRecipeAlloySmelter1;
      int len$ = arrayOfRecipeAlloySmelter1.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         AlloySmelterManager.RecipeAlloySmelter localRecipeAlloySmelter = arr$[i$];
         Iterator i$1 = localRecipeAlloySmelter.getInputs().iterator();

         while(i$1.hasNext()) {
            ItemStack localItemStack = (ItemStack)i$1.next();
            if(NEIServerUtils.areStacksSameType(localItemStack, paramItemStack)) {
               this.arecipes.add(new RecipeHandlerAlloySmelter.NEIRecipeAlloySmelter(localRecipeAlloySmelter));
            }
         }
      }

   }

   class NEIRecipeAlloySmelter extends RecipeHandlerBase.NEIRecipeBase {

      public NEIRecipeAlloySmelter(AlloySmelterManager.RecipeAlloySmelter arg2) {
         super();

         for(int i = 0; i < 9 && i < arg2.getInputs().size(); ++i) {
            int j = i % 3 * 18;
            int k = i / 3 * 18;
            this.inputList.add(new PositionedStack(arg2.getInputs().get(i), 26 + j, 6 + k));
         }

         this.outputList.add(new PositionedStack(arg2.getOutput(), 124, 24));
         this.energy = arg2.getEnergy();
         this.setOres();
      }
   }
}
