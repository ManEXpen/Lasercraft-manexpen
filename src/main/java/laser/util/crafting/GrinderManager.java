package laser.util.crafting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import cofh.lib.inventory.ComparableItemStack;
import cofh.thermalexpansion.util.crafting.PulverizerManager;
import gnu.trove.map.hash.THashMap;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class GrinderManager {

   private static Map recipeMap = new THashMap();
   private static ComparableItemStack query = new ComparableItemStack(new ItemStack(Blocks.stone));


   public static GrinderManager.RecipeGrinder getRecipe(ItemStack paramItemStack) {
      return paramItemStack == null?null:(GrinderManager.RecipeGrinder)recipeMap.get(query.set(paramItemStack));
   }

   public static boolean recipeExists(ItemStack paramItemStack) {
      return getRecipe(paramItemStack) != null;
   }

   public static GrinderManager.RecipeGrinder[] getRecipeList() {
      return (GrinderManager.RecipeGrinder[])((GrinderManager.RecipeGrinder[])recipeMap.values().toArray(new GrinderManager.RecipeGrinder[0]));
   }

   public static void loadRecipes() {
      PulverizerManager.RecipePulverizer[] arr$ = PulverizerManager.getRecipeList();
      int len$ = arr$.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         PulverizerManager.RecipePulverizer localRecipePulverizer = arr$[i$];
         if(localRecipePulverizer.getSecondaryOutput() == null) {
            addRecipe(localRecipePulverizer.getEnergy(), localRecipePulverizer.getInput(), new ItemStack[]{localRecipePulverizer.getPrimaryOutput()});
         } else {
            addRecipe(localRecipePulverizer.getEnergy() * (3 - localRecipePulverizer.getSecondaryOutputChance() / 50), localRecipePulverizer.getInput(), new ItemStack[]{localRecipePulverizer.getPrimaryOutput(), localRecipePulverizer.getSecondaryOutput()});
         }
      }

   }

   public static void refreshRecipes() {
      THashMap localTHashMap = new THashMap(recipeMap.size());
      Iterator i$ = recipeMap.entrySet().iterator();

      while(i$.hasNext()) {
         Entry localEntry = (Entry)i$.next();
         GrinderManager.RecipeGrinder localRecipeGrinder = (GrinderManager.RecipeGrinder)localEntry.getValue();
         localTHashMap.put(new ComparableItemStack(localRecipeGrinder.input), localRecipeGrinder);
      }

      recipeMap.clear();
      recipeMap = localTHashMap;
   }

   public static boolean addRecipe(int paramInt1, ItemStack paramItemStack, ItemStack ... paramItemStacks) {
      if(paramItemStack != null && paramItemStacks != null && paramInt1 > 0) {
         GrinderManager.RecipeGrinder var10000 = new GrinderManager.RecipeGrinder(paramInt1, paramItemStack, paramItemStacks);
         GrinderManager var10002 = new GrinderManager();
         var10002.getClass();
         GrinderManager.RecipeGrinder localRecipeGrinder = var10000;
         recipeMap.put(new ComparableItemStack(paramItemStack), localRecipeGrinder);
         return true;
      } else {
         return false;
      }
   }

   public static boolean removeRecipe(ItemStack paramItemStack) {
      return recipeMap.remove(new ComparableItemStack(paramItemStack)) != null;
   }


   public static class RecipeGrinder {

      final int energy;
      final ItemStack input;
      final ArrayList outputs = new ArrayList();


      public RecipeGrinder(int paramInt, ItemStack paramItemStack, ItemStack ... paramItemStacks) {
         this.energy = paramInt;
         this.input = paramItemStack;
         this.outputs.addAll(Arrays.asList(paramItemStacks));
      }

      public ItemStack getInput() {
         return this.input.copy();
      }

      public ArrayList getOutputs() {
         ArrayList localArrayList = new ArrayList();
         localArrayList.addAll(this.outputs);
         return localArrayList;
      }

      public int getEnergy() {
         return this.energy;
      }
   }
}
