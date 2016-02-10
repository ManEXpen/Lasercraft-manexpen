package laser.block.generator;

import java.util.List;

import cofh.core.item.ItemBlockBase;
import cofh.lib.util.helpers.EnergyHelper;
import cofh.lib.util.helpers.ItemHelper;
import cofh.lib.util.helpers.StringHelper;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class ItemBlockGenerator extends ItemBlockBase {

   public static ItemStack setDefaultTag(ItemStack paramItemStack) {
      EnergyHelper.setDefaultEnergyTag(paramItemStack, 0);
      return paramItemStack;
   }

   public ItemBlockGenerator(Block paramBlock) {
      super(paramBlock);
      this.setHasSubtypes(true);
      this.setMaxDamage(0);
      this.setNoRepair();
   }

   public String getUnlocalizedName(ItemStack paramItemStack) {
      return "tile.laser.generator." + BlockGenerator.TYPES[ItemHelper.getItemDamage(paramItemStack)] + ".name";
   }

   public void addInformation(ItemStack paramItemStack, EntityPlayer paramEntityPlayer, List paramList, boolean paramBoolean) {
      if(StringHelper.displayShiftForDetail && !StringHelper.isShiftKeyDown()) {
         paramList.add(StringHelper.shiftForDetails());
      }

      if(StringHelper.isShiftKeyDown()) {
         paramList.add(StringHelper.localize("info.thermalexpansion.dynamo.generate"));
         paramList.add(StringHelper.localize("info.laser.generator." + BlockGenerator.TYPES[ItemHelper.getItemDamage(paramItemStack)]));
      }
   }
}
