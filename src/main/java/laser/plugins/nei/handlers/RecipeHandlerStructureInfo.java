package laser.plugins.nei.handlers;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.PositionedStack;
import codechicken.nei.api.IOverlayHandler;
import codechicken.nei.api.IRecipeOverlayRenderer;
import codechicken.nei.recipe.GuiRecipe;
import codechicken.nei.recipe.ICraftingHandler;
import codechicken.nei.recipe.IUsageHandler;
import cofh.lib.util.helpers.StringHelper;
import laser.block.machine.large.BlockLargeMachine;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;

public class RecipeHandlerStructureInfo implements ICraftingHandler, IUsageHandler {

   static final String TEXTURE = "laser:textures/gui/NEIHandler.png";
   public static final int displayTime = 15;
   public static final int[] depth = new int[]{3};
   public int cycleticks = Math.abs((int)System.currentTimeMillis());
   public ItemStack displayItem;


   public RecipeHandlerStructureInfo() {}

   public RecipeHandlerStructureInfo(ItemStack item) {
      this.displayItem = item.copy();
      this.displayItem.stackSize = 1;
   }

   public String getRecipeName() {
      return StringHelper.localize("nei.laser.construct.title");
   }

   public int numRecipes() {
      return this.displayItem != null && this.displayItem.getItemDamage() < depth.length?1:0;
   }

   public void drawLevel(int meta) {
      switch(meta) {
      case 0:
         switch(this.cycleticks / 15 % depth[meta]) {
         case 0:
            GuiDraw.drawTexturedModalRect(59, 32, 192, 0, 16, 16);
            GuiDraw.drawTexturedModalRect(75, 32, 160, 0, 16, 16);
            GuiDraw.drawTexturedModalRect(91, 32, 144, 0, 16, 16);
            GuiDraw.drawTexturedModalRect(59, 48, 80, 0, 16, 16);
            GuiDraw.drawTexturedModalRect(75, 48, 0, 128, 16, 16);
            GuiDraw.drawTexturedModalRect(91, 48, 80, 0, 16, 16);
            GuiDraw.drawTexturedModalRect(59, 64, 96, 0, 16, 16);
            GuiDraw.drawTexturedModalRect(75, 64, 160, 0, 16, 16);
            GuiDraw.drawTexturedModalRect(91, 64, 48, 0, 16, 16);
            break;
         case 1:
            GuiDraw.drawTexturedModalRect(59, 32, 192, 0, 16, 16);
            GuiDraw.drawTexturedModalRect(75, 32, 160, 0, 16, 16);
            GuiDraw.drawTexturedModalRect(91, 32, 144, 0, 16, 16);
            GuiDraw.drawTexturedModalRect(59, 48, 80, 0, 16, 16);
            GuiDraw.drawTexturedModalRect(91, 48, 80, 0, 16, 16);
            GuiDraw.drawTexturedModalRect(59, 64, 96, 0, 16, 16);
            GuiDraw.drawTexturedModalRect(75, 64, 160, 0, 16, 16);
            GuiDraw.drawTexturedModalRect(91, 64, 48, 0, 16, 16);
            break;
         case 2:
            GuiDraw.drawTexturedModalRect(59, 32, 192, 0, 16, 16);
            GuiDraw.drawTexturedModalRect(75, 32, 128, 0, 16, 16);
            GuiDraw.drawTexturedModalRect(91, 32, 144, 0, 16, 16);
            GuiDraw.drawTexturedModalRect(59, 48, 64, 0, 16, 16);
            GuiDraw.drawTexturedModalRect(75, 48, 0, 0, 16, 16);
            GuiDraw.drawTexturedModalRect(91, 48, 16, 0, 16, 16);
            GuiDraw.drawTexturedModalRect(59, 64, 96, 0, 16, 16);
            GuiDraw.drawTexturedModalRect(75, 64, 32, 0, 16, 16);
            GuiDraw.drawTexturedModalRect(91, 64, 48, 0, 16, 16);
         }
      default:
      }
   }

   public void drawBackground(int arg0) {
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      GuiDraw.changeTexture("laser:textures/gui/NEIHandler.png");
      this.drawLevel(this.displayItem.getItemDamage());
   }

   public void drawForeground(int arg0) {
      String s = StringHelper.localize("nei.laser.construct.depth") + " : " + (this.cycleticks / 15 % depth[this.displayItem.getItemDamage()] + 1);
      GuiDraw.drawString(s, 83 - Minecraft.getMinecraft().fontRenderer.getStringWidth(s) / 2, 84, 4210752, false);
   }

   public List getIngredientStacks(int arg0) {
      return new ArrayList();
   }

   public PositionedStack getResultStack(int arg0) {
      return null;
   }

   public List getOtherStacks(int arg0) {
      return new ArrayList();
   }

   public IOverlayHandler getOverlayHandler(GuiContainer arg0, int arg1) {
      return null;
   }

   public IRecipeOverlayRenderer getOverlayRenderer(GuiContainer arg0, int arg1) {
      return null;
   }

   public List handleItemTooltip(GuiRecipe arg0, ItemStack arg1, List arg2, int arg3) {
      return arg2;
   }

   public List handleTooltip(GuiRecipe arg0, List arg1, int arg2) {
      return arg1;
   }

   public boolean hasOverlay(GuiContainer arg0, Container arg1, int arg2) {
      return false;
   }

   public boolean keyTyped(GuiRecipe arg0, char arg1, int arg2, int arg3) {
      return false;
   }

   public boolean mouseClicked(GuiRecipe arg0, int arg1, int arg2) {
      return false;
   }

   public void onUpdate() {
      if(!StringHelper.isShiftKeyDown()) {
         ++this.cycleticks;
      }

   }

   public int recipiesPerPage() {
      return 1;
   }

   public boolean isValidItem(ItemStack item) {
      return Block.getBlockFromItem(item.getItem()) instanceof BlockLargeMachine;
   }

   public ICraftingHandler getRecipeHandler(String paramString, Object ... paramArrayOfObject) {
      if(!paramString.equals("item")) {
         return this;
      } else {
         Object[] arr$ = paramArrayOfObject;
         int len$ = paramArrayOfObject.length;

         for(int i$ = 0; i$ < len$; ++i$) {
            Object localObject = arr$[i$];
            if(localObject instanceof ItemStack && this.isValidItem((ItemStack)localObject)) {
               return new RecipeHandlerStructureInfo((ItemStack)localObject);
            }
         }

         return this;
      }
   }

   public IUsageHandler getUsageHandler(String paramString, Object ... paramArrayOfObject) {
      if(!paramString.equals("item")) {
         return this;
      } else {
         Object[] arr$ = paramArrayOfObject;
         int len$ = paramArrayOfObject.length;

         for(int i$ = 0; i$ < len$; ++i$) {
            Object localObject = arr$[i$];
            if(localObject instanceof ItemStack && this.isValidItem((ItemStack)localObject)) {
               return new RecipeHandlerStructureInfo((ItemStack)localObject);
            }
         }

         return this;
      }
   }

}
