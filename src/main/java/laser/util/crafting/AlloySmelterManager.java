package laser.util.crafting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import cofh.lib.inventory.ComparableItemStack;
import cofh.lib.inventory.ComparableItemStackSafe;
import cofh.lib.util.helpers.ItemHelper;
import gnu.trove.map.hash.THashMap;
import gnu.trove.set.hash.THashSet;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import thermalexpansion.util.crafting.SmelterManager;

public class AlloySmelterManager {

	private static Map recipeMap = new THashMap();
	private static ComparableItemStack query = new ComparableItemStack(new ItemStack(Blocks.stone));
	private static Set validationSet = new THashSet();

	public static AlloySmelterManager.RecipeAlloySmelter getRecipeFromInventory(ItemStack[] paramItemStacks) {
		if (paramItemStacks.length < 9) {
			return null;
		} else {
			ArrayList localArrayList = new ArrayList();

			for (int i = 0; i < 9; ++i) {
				if (paramItemStacks[i] != null) {
					localArrayList.add(paramItemStacks[i].copy());
				}
			}

			return getRecipe((ItemStack[]) localArrayList.toArray(new ItemStack[0]));
		}
	}

	public static AlloySmelterManager.RecipeAlloySmelter getRecipe(ItemStack[] paramItemStacks) {
		if (paramItemStacks.length == 0) {
			return null;
		} else {
			ArrayList localArrayList = new ArrayList();
			localArrayList.addAll(Arrays.asList(paramItemStacks));
			Iterator i$ = recipeMap.keySet().iterator();

			Set localSet1;
			THashSet localSet2;
			do {
				if (!i$.hasNext()) {
					return null;
				}

				localSet1 = (Set) i$.next();
				localSet2 = new THashSet();
				localSet2.addAll(localSet1);
				Iterator i$1 = localArrayList.iterator();

				while (i$1.hasNext()) {
					ItemStack localItemStack = (ItemStack) i$1.next();
					localSet2.remove(query.set(localItemStack));
				}
			} while (!localSet2.isEmpty());

			return (AlloySmelterManager.RecipeAlloySmelter) recipeMap.get(localSet1);
		}
	}

	public static boolean recipeExists(ItemStack[] paramItemStacks) {
		return getRecipe(paramItemStacks) != null;
	}

	public static AlloySmelterManager.RecipeAlloySmelter[] getRecipeList() {
		return (AlloySmelterManager.RecipeAlloySmelter[]) recipeMap.values()
				.toArray(new AlloySmelterManager.RecipeAlloySmelter[0]);
	}

	public static boolean isItemValid(ItemStack paramItemStack) {
		return paramItemStack == null ? false : validationSet.contains(query.set(paramItemStack));
	}

	public static void loadRecipes() {
		SmelterManager.RecipeSmelter[] arr$ = SmelterManager.getRecipeList();
		int len$ = arr$.length;

		for (int i$ = 0; i$ < len$; ++i$) {
			SmelterManager.RecipeSmelter localRecipeSmelter = arr$[i$];
			ItemStack[] localItemStacks = new ItemStack[] { localRecipeSmelter.getPrimaryInput(),
					localRecipeSmelter.getSecondaryInput() };
			addRecipe(localRecipeSmelter.getEnergy() / 2, localRecipeSmelter.getPrimaryOutput(), localItemStacks);
		}

	}

	public static void refreshRecipes() {
		THashMap localTHashMap = new THashMap(recipeMap.size());
		THashSet localTHashSet1 = new THashSet();
		Iterator i$ = recipeMap.entrySet().iterator();

		while (i$.hasNext()) {
			Entry localEntry = (Entry) i$.next();
			AlloySmelterManager.RecipeAlloySmelter localRecipeAlloySmelter = (AlloySmelterManager.RecipeAlloySmelter) localEntry
					.getValue();
			THashSet localTHashSet2 = new THashSet();
			Iterator i$1 = localRecipeAlloySmelter.getInputs().iterator();

			while (i$1.hasNext()) {
				ItemStack localItemStack = (ItemStack) i$1.next();
				ComparableItemStackSafe localComparableItemStack = new ComparableItemStackSafe(localItemStack);
				localTHashSet1.add(localComparableItemStack);
				localTHashSet2.add(localComparableItemStack);
			}

			localTHashMap.put(localTHashSet2, localRecipeAlloySmelter);
		}

		recipeMap.clear();
		recipeMap = localTHashMap;
		validationSet.clear();
		validationSet = localTHashSet1;
	}

	public static boolean addRecipe(int paramInt1, ItemStack paramItemStack, ItemStack... paramItemStacks) {
		if (paramItemStack != null && paramItemStacks.length != 0 && paramInt1 > 0) {
			AlloySmelterManager.RecipeAlloySmelter var10000 = new AlloySmelterManager.RecipeAlloySmelter(paramInt1,
					paramItemStack, paramItemStacks);
			AlloySmelterManager var10002 = new AlloySmelterManager();
			var10002.getClass();
			// つんだつんだｗｗｗ（classにstaticつけて、どうぞ）
			AlloySmelterManager.RecipeAlloySmelter localRecipeAlloySmelter = var10000;
			ComparableItemStack[] localComparableItemStacks = new ComparableItemStack[paramItemStacks.length];

			for (int localTHashSet = 0; localTHashSet < localComparableItemStacks.length; ++localTHashSet) {
				localComparableItemStacks[localTHashSet] = new ComparableItemStack(paramItemStacks[localTHashSet]);
			}

			THashSet var6 = new THashSet();
			var6.addAll(Arrays.asList(localComparableItemStacks));
			recipeMap.put(var6, localRecipeAlloySmelter);
			validationSet.addAll(var6);
			return true;
		} else {
			return false;
		}
	}

	public static boolean addOreDictionaryRecipe(int paramInt1, ItemStack paramItemStack, Object... paramObjects) {
		if (paramItemStack != null && paramObjects.length != 0 && paramInt1 > 0) {
			ArrayList localArrayList = new ArrayList();

			for (int i = 0; i < paramObjects.length; ++i) {
				if (paramObjects[i] instanceof ItemStack) {
					localArrayList.add(ItemHelper.cloneStack((ItemStack) paramObjects[i]));
				}

				if (paramObjects[i] instanceof String) {
					if (OreDictionary.getOres((String) paramObjects[i]).isEmpty()) {
						return false;
					}

					ItemStack localItemStack = ItemHelper
							.cloneStack((ItemStack) OreDictionary.getOres((String) paramObjects[i]).get(0), 1);
					localArrayList.add(localItemStack);
					if (i + 1 < paramObjects.length && paramObjects[i + 1] instanceof Integer) {
						localArrayList.set(localArrayList.indexOf(localItemStack),
								ItemHelper.cloneStack(localItemStack, ((Integer) paramObjects[i + 1]).intValue()));
						++i;
					}
				}
			}

			if (localArrayList.size() <= 0) {
				return false;
			} else {
				return addRecipe(paramInt1, paramItemStack, (ItemStack[]) localArrayList.toArray(new ItemStack[0]));
			}
		} else {
			return false;
		}
	}

	public static boolean removeRecipe(ItemStack[] paramItemStacks) {
		THashSet localSet = new THashSet();

		for (int i = 0; i < paramItemStacks.length; ++i) {
			localSet.add(new ComparableItemStack(paramItemStacks[i]));
		}

		return recipeMap.remove(localSet) != null;
	}

	public static class RecipeAlloySmelter {

		final int energy;
		final ItemStack output;
		final ArrayList inputs = new ArrayList();

		public RecipeAlloySmelter(int paramInt, ItemStack paramItemStack, ItemStack... paramItemStacks) {
			this.energy = paramInt;
			this.output = paramItemStack;
			this.inputs.addAll(Arrays.asList(paramItemStacks));
		}

		public ArrayList getInputs() {
			ArrayList localArrayList = new ArrayList();
			localArrayList.addAll(this.inputs);
			return localArrayList;
		}

		public ItemStack getOutput() {
			return this.output.copy();
		}

		public int getEnergy() {
			return this.energy;
		}
	}
}
