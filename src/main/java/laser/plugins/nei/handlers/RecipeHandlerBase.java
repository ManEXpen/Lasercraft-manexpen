package laser.plugins.nei.handlers;

import java.awt.Point;
import java.awt.Rectangle;
import java.lang.reflect.Field;
import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.NEIClientConfig;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.GuiCraftingRecipe;
import codechicken.nei.recipe.GuiRecipe;
import codechicken.nei.recipe.GuiUsageRecipe;
import codechicken.nei.recipe.TemplateRecipeHandler;
import cofh.lib.render.RenderHelper;
import cofh.lib.util.helpers.ItemHelper;
import cofh.lib.util.helpers.StringHelper;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

public abstract class RecipeHandlerBase extends TemplateRecipeHandler {

   Class containerClass;
   String recipeName;
   static final String TEXTURE = "thermalexpansion:textures/gui/NEIHandler.png";
   int[] trCoords = new int[4];
   int maxEnergy = 24000;
   int scaleEnergy = 42;
   int maxFluid = 10000;
   int scaleFluid = 60;
   int[] energyAmount = new int[2];
   int[] fluidAmount = new int[2];
   int[] lastCycle = new int[2];
   int[] arecipe = new int[]{-1, -1};


   public void loadTransferRects() {
      this.initialize();
      this.transferRects.add(new RecipeTransferRect(new Rectangle(this.trCoords[0], this.trCoords[1], this.trCoords[2], this.trCoords[3]), this.getOverlayIdentifier(), new Object[0]));
   }

   public Class getGuiClass() {
      return this.containerClass;
   }

   public String getRecipeName() {
      return StringHelper.localize("tile.laser.machine." + this.recipeName + ".name");
   }

   public String getGuiTexture() {
      return "thermalexpansion:textures/gui/NEIHandler.png";
   }

   public String getOverlayIdentifier() {
      return "laser." + this.recipeName;
   }

   public abstract void initialize();

   public void drawBackground(int paramInt) {
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      GuiDraw.changeTexture(this.getGuiTexture());
      GuiDraw.drawTexturedModalRect(0, 0, 5, 11, 166, 65);
      this.drawBackgroundExtras(paramInt);
   }

   public void drawBackgroundExtras(int paramInt) {}

   public boolean keyTyped(GuiRecipe paramGuiRecipe, char paramChar, int paramInt1, int paramInt2) {
      if(paramInt1 == NEIClientConfig.getKeyBinding("gui.recipe")) {
         if(this.transferFluidTank(paramGuiRecipe, paramInt2, false)) {
            return true;
         }
      } else if(paramInt1 == NEIClientConfig.getKeyBinding("gui.usage") && this.transferFluidTank(paramGuiRecipe, paramInt2, true)) {
         return true;
      }

      return super.keyTyped(paramGuiRecipe, paramChar, paramInt1, paramInt2);
   }

   public boolean mouseClicked(GuiRecipe paramGuiRecipe, int paramInt1, int paramInt2) {
      if(paramInt1 == 0) {
         if(this.transferFluidTank(paramGuiRecipe, paramInt2, false)) {
            return true;
         }
      } else if(paramInt1 == 1 && this.transferFluidTank(paramGuiRecipe, paramInt2, true)) {
         return true;
      }

      return super.mouseClicked(paramGuiRecipe, paramInt1, paramInt2);
   }

   protected boolean transferFluidTank(GuiRecipe paramGuiRecipe, int paramInt, boolean paramBoolean) {
      short i = 153;
      short j = 169;
      byte k = 19;
      byte m = 79;
      byte n = 65;
      Point localPoint = GuiDraw.getMousePosition();
      FluidStack localFluidStack = null;
      int guiLeft = 0;
      int guiTop = 0;

      try {
         Field e = GuiContainer.class.getDeclaredField("guiLeft");
         Field f2 = GuiContainer.class.getDeclaredField("guiTop");
         if(e != null) {
            e.setAccessible(true);
            guiLeft = e.getInt(paramGuiRecipe);
         }

         if(f2 != null) {
            f2.setAccessible(true);
            guiTop = f2.getInt(paramGuiRecipe);
         }
      } catch (Exception var15) {
         ;
      }

      if(localPoint.x >= i + guiLeft && localPoint.x < j + guiLeft && localPoint.y >= k + guiTop && localPoint.y < m + guiTop && this.arecipe[0] == paramInt) {
         localFluidStack = ((RecipeHandlerBase.NEIRecipeBase)this.arecipes.get(paramInt)).fluid;
      } else if(localPoint.x >= i + guiLeft && localPoint.x < j + guiLeft && localPoint.y >= k + guiTop + n && localPoint.y < m + guiTop + n && this.arecipe[1] == paramInt) {
         localFluidStack = ((RecipeHandlerBase.NEIRecipeBase)this.arecipes.get(paramInt)).fluid;
      }

      if(localFluidStack != null && localFluidStack.amount > 0) {
         if(paramBoolean) {
            if(!GuiUsageRecipe.openRecipeGui("liquid", new Object[]{localFluidStack})) {
               return false;
            }

            if(!GuiCraftingRecipe.openRecipeGui("liquid", new Object[]{localFluidStack})) {
               return false;
            }
         }

         return true;
      } else {
         return false;
      }
   }

   private void resetCounters() {
      this.arecipe[0] = -1;
      this.arecipe[1] = -1;
      this.energyAmount[0] = 0;
      this.energyAmount[1] = 0;
      this.fluidAmount[0] = 0;
      this.fluidAmount[1] = 0;
      this.lastCycle[0] = 0;
      this.lastCycle[1] = 0;
   }

   public void drawEnergy(int paramInt) {
      byte i = 0;
      if(this.arecipe[0] == -1) {
         this.arecipe[0] = paramInt;
      } else if(this.arecipe[1] == -1 && this.arecipe[0] != paramInt) {
         this.arecipe[1] = paramInt;
      }

      if(this.arecipe[0] != paramInt && this.arecipe[1] != paramInt) {
         this.resetCounters();
         this.drawEnergy(paramInt);
      } else {
         if(this.arecipe[1] == paramInt) {
            i = 1;
         }

         GuiDraw.drawTexturedModalRect(4, 2, 0, 96, 16, this.scaleEnergy);
         int j = this.getScaledEnergy(this.energyAmount[i]);
         GuiDraw.drawTexturedModalRect(4, 2 + j, 16, 96 + j, 16, this.scaleEnergy - j);
         if(this.cycleticks % 20 == 0 && this.cycleticks != this.lastCycle[i]) {
            if(this.energyAmount[i] == this.maxEnergy) {
               this.energyAmount[i] = 0;
            }

            this.energyAmount[i] += ((RecipeHandlerBase.NEIRecipeBase)this.arecipes.get(paramInt)).energy;
            if(this.energyAmount[i] > this.maxEnergy) {
               this.energyAmount[i] = this.maxEnergy;
            }

            this.lastCycle[i] = this.cycleticks;
         }

      }
   }

   public void drawFluid(int paramInt, boolean paramBoolean) {
      byte i = 0;
      if(this.arecipe[0] == -1) {
         this.arecipe[0] = paramInt;
      } else if(this.arecipe[1] == -1 && this.arecipe[0] != paramInt) {
         this.arecipe[1] = paramInt;
      }

      if(this.arecipe[0] != paramInt && this.arecipe[1] != paramInt) {
         this.resetCounters();
         this.drawFluid(paramInt, paramBoolean);
      } else {
         if(this.arecipe[1] == paramInt) {
            i = 1;
         }

         GuiDraw.drawTexturedModalRect(147, 2, 32, 96, 18, this.scaleFluid + 2);
         int j = this.getScaledFluid(this.fluidAmount[i]);
         if(paramBoolean) {
            this.drawFluidRect(148, 3 + this.scaleFluid - j, ((RecipeHandlerBase.NEIRecipeBase)this.arecipes.get(paramInt)).fluid, 16, j);
         } else {
            this.drawFluidRect(148, 3 + j, ((RecipeHandlerBase.NEIRecipeBase)this.arecipes.get(paramInt)).fluid, 16, this.scaleFluid - j);
         }

         if(this.cycleticks % 20 == 0 && this.cycleticks != this.lastCycle[i]) {
            if(this.fluidAmount[i] == this.maxFluid) {
               this.fluidAmount[i] = 0;
            }

            this.fluidAmount[i] += ((RecipeHandlerBase.NEIRecipeBase)this.arecipes.get(paramInt)).fluid.amount;
            if(this.fluidAmount[i] > this.maxFluid) {
               this.fluidAmount[i] = this.maxFluid;
            }
         }

         GuiDraw.drawTexturedModalRect(148, 2, 80, 96, 18, this.scaleFluid + 2);
      }
   }

   public int getScaledEnergy(int paramInt) {
      return paramInt * this.scaleEnergy / this.maxEnergy;
   }

   public int getScaledFluid(int paramInt) {
      return paramInt * this.scaleFluid / this.maxFluid;
   }

   protected void drawFluidRect(int paramInt1, int paramInt2, FluidStack paramFluidStack, int paramInt3, int paramInt4) {
      if(paramInt4 > this.scaleFluid) {
         paramInt4 = this.scaleFluid;
      }

      boolean i = false;
      boolean j = false;
      RenderHelper.setBlockTextureSheet();
      RenderHelper.setColor3ub(paramFluidStack.getFluid().getColor(paramFluidStack));

      for(int k = 0; k < paramInt3; k += 16) {
         for(int m = 0; m < paramInt4; m += 16) {
            int j1 = Math.min(paramInt3 - k, 16);
            int i1 = Math.min(paramInt4 - m, 16);
            drawScaledTexturedModelRectFromIcon(paramInt1 + k, paramInt2 + m, paramFluidStack.getFluid().getIcon(), j1, i1);
         }
      }

      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      GuiDraw.changeTexture(this.getGuiTexture());
   }

   public static void drawScaledTexturedModelRectFromIcon(int paramInt1, int paramInt2, IIcon paramIIcon, int paramInt3, int paramInt4) {
      if(paramIIcon != null) {
         double d1 = (double)paramIIcon.getMinU();
         double d2 = (double)paramIIcon.getMaxU();
         double d3 = (double)paramIIcon.getMinV();
         double d4 = (double)paramIIcon.getMaxV();
         Tessellator localTessellator = Tessellator.instance;
         localTessellator.startDrawingQuads();
         localTessellator.addVertexWithUV((double)(paramInt1 + 0), (double)(paramInt2 + paramInt4), (double)GuiDraw.gui.getZLevel(), d1, d3 + (d4 - d3) * (double)paramInt4 / 16.0D);
         localTessellator.addVertexWithUV((double)(paramInt1 + paramInt3), (double)(paramInt2 + paramInt4), (double)GuiDraw.gui.getZLevel(), d1 + (d2 - d1) * (double)paramInt3 / 16.0D, d3 + (d4 - d3) * (double)paramInt4 / 16.0D);
         localTessellator.addVertexWithUV((double)(paramInt1 + paramInt3), (double)(paramInt2 + 0), (double)GuiDraw.gui.getZLevel(), d1 + (d2 - d1) * (double)paramInt3 / 16.0D, d3);
         localTessellator.addVertexWithUV((double)(paramInt1 + 0), (double)(paramInt2 + 0), (double)GuiDraw.gui.getZLevel(), d1, d3);
         localTessellator.draw();
      }
   }

   abstract class NEIRecipeBase extends CachedRecipe {

      ArrayList inputList = new ArrayList();
      ArrayList outputList = new ArrayList();
      ArrayList inputOreNameList = new ArrayList();
      ArrayList inputOreList = new ArrayList();
      int[] inputOrePosition;
      FluidStack fluid = null;
      int energy = 0;


      NEIRecipeBase() {
         super();
      }

      protected void setOres() {
         for(int i = 0; i < this.inputList.size(); ++i) {
            PositionedStack localPositionedStack = (PositionedStack)this.inputList.get(i);
            if(localPositionedStack != null) {
               this.inputOreNameList.add(ItemHelper.getOreName(localPositionedStack.item));
               if(!((String)this.inputOreNameList.get(i)).equals("Unknown")) {
                  this.inputOreList.add(OreDictionary.getOres((String)this.inputOreNameList.get(i)));
               } else {
                  this.inputOreList.add((Object)null);
               }
            }
         }

      }

      protected void increment() {
         for(int i = 0; i < this.inputOreNameList.size(); ++i) {
            if(!((String)this.inputOreNameList.get(i)).equals("Unknown")) {
               if(this.inputOrePosition == null) {
                  this.inputOrePosition = new int[this.inputOreNameList.size()];
               }

               ++this.inputOrePosition[i];
               this.inputOrePosition[i] %= ((ArrayList)this.inputOreList.get(i)).size();
               int j = ((PositionedStack)this.inputList.get(i)).item.stackSize;
               ((PositionedStack)this.inputList.get(i)).item = (ItemStack)((ArrayList)this.inputOreList.get(i)).get(this.inputOrePosition[i]);
               ((PositionedStack)this.inputList.get(i)).item.stackSize = j;
               if(((ItemStack)((ArrayList)this.inputOreList.get(i)).get(this.inputOrePosition[i])).getItemDamage() != 32767) {
                  ((PositionedStack)this.inputList.get(i)).item.setItemDamage(((ItemStack)((ArrayList)this.inputOreList.get(i)).get(this.inputOrePosition[i])).getItemDamage());
               }
            }
         }

      }

      public PositionedStack getIngredient() {
         if(RecipeHandlerBase.this.cycleticks % 20 == 0) {
            this.increment();
         }

         return null;
      }

      public PositionedStack getResult() {
         return null;
      }

      public ArrayList getOtherStacks() {
         ArrayList localArrayList = new ArrayList();
         localArrayList.addAll(this.inputList);
         localArrayList.addAll(this.outputList);
         return localArrayList;
      }
   }
}
