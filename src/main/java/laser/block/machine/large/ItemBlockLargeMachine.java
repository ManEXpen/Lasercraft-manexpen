package laser.block.machine.large;

import java.util.List;

import cofh.api.tileentity.IRedstoneControl;
import cofh.core.item.ItemBlockBase;
import cofh.lib.util.helpers.EnergyHelper;
import cofh.lib.util.helpers.ItemHelper;
import cofh.lib.util.helpers.RedstoneControlHelper;
import cofh.lib.util.helpers.StringHelper;
import cofh.thermalexpansion.util.ReconfigurableHelper;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;

public class ItemBlockLargeMachine extends ItemBlockBase {

   public static ItemStack setDefaultTag(ItemStack paramItemStack) {
      return setDefaultTag(paramItemStack, (byte)0);
   }

   public static ItemStack setDefaultTag(ItemStack paramItemStack, byte paramByte) {
      ReconfigurableHelper.setFacing(paramItemStack, 3);
      RedstoneControlHelper.setControl(paramItemStack, IRedstoneControl.ControlMode.DISABLED);
      EnergyHelper.setDefaultEnergyTag(paramItemStack, 0);
      return paramItemStack;
   }

   public ItemBlockLargeMachine(Block paramBlock) {
      super(paramBlock);
      this.setHasSubtypes(true);
      this.setMaxDamage(0);
      this.setNoRepair();
   }

   public String getItemStackDisplayName(ItemStack paramItemStack) {
      return StringHelper.localize(this.getUnlocalizedName(paramItemStack));
   }

   public String getUnlocalizedName(ItemStack paramItemStack) {
      return "tile.laser.machine.large." + BlockLargeMachine.TYPES[ItemHelper.getItemDamage(paramItemStack)] + ".name";
   }

   public EnumRarity getRarity(ItemStack paramItemStack) {
      return EnumRarity.values()[BlockLargeMachine.RARITY[ItemHelper.getItemDamage(paramItemStack)]];
   }

   public void addInformation(ItemStack paramItemStack, EntityPlayer paramEntityPlayer, List paramList, boolean paramBoolean) {
      if(StringHelper.displayShiftForDetail && !StringHelper.isShiftKeyDown()) {
         paramList.add(StringHelper.shiftForDetails());
      }

      if(StringHelper.isShiftKeyDown()) {
         paramList.add(StringHelper.getInfoText("info.laser.machine.large." + BlockLargeMachine.TYPES[ItemHelper.getItemDamage(paramItemStack)]));
      }
   }
}
