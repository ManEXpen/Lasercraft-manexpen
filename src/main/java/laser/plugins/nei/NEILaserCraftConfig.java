package laser.plugins.nei;

import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;
import laser.Laser;
import laser.plugins.nei.handlers.RecipeHandlerAlloySmelter;
import laser.plugins.nei.handlers.RecipeHandlerGrinder;
import laser.plugins.nei.handlers.RecipeHandlerStructureInfo;

public class NEILaserCraftConfig implements IConfigureNEI {

   public void loadConfig() {
      API.registerRecipeHandler(new RecipeHandlerAlloySmelter());
      API.registerUsageHandler(new RecipeHandlerAlloySmelter());
      API.registerRecipeHandler(new RecipeHandlerGrinder());
      API.registerUsageHandler(new RecipeHandlerGrinder());
      API.registerRecipeHandler(new RecipeHandlerStructureInfo());
      API.registerUsageHandler(new RecipeHandlerStructureInfo());
      API.hideItem(Laser.machinePump);
   }

   public String getName() {
      return "LaserCraft";
   }

   public String getVersion() {
      return "1.7.10-0.2B4";
   }
}
