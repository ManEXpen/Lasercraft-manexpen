package laser.util;

import cofh.lib.util.helpers.ItemHelper;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import laser.Laser;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.TextureStitchEvent.Pre;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import net.minecraftforge.fluids.Fluid;

public class EventHandler {

   @SubscribeEvent
   public void onLivingDrop(LivingDropsEvent event) {
      if(event.entityLiving instanceof EntityGhast && event.recentlyHit) {
         EntityItem entityitem = new EntityItem(event.entityLiving.worldObj, event.entityLiving.posX, event.entityLiving.posY, event.entityLiving.posZ);
         entityitem.setEntityItemStack(ItemHelper.cloneStack(Laser.tentacleGhast, event.entityLiving.worldObj.rand.nextInt(3 + event.lootingLevel)));
         event.drops.add(entityitem);
      }

   }

   @SubscribeEvent
   public void PreTextureStitchEvent(Pre event) {
      if(event.map.getTextureType() == 0) {
         Fluid fluid = Laser.fluidGhast;
         if(fluid != null) {
            fluid.setIcons(fluid.getBlock().getBlockTextureFromSide(1));
         }
      }

   }

   @SubscribeEvent
   public void onInteractEntity(EntityInteractEvent event) {
      ItemStack itemstack = event.entityPlayer.getCurrentEquippedItem();
      if(event.target instanceof EntityVillager && itemstack != null && itemstack.getItem() == Items.bucket && !event.entityPlayer.capabilities.isCreativeMode) {
         if(itemstack.stackSize-- == 1) {
            event.entityPlayer.inventory.setInventorySlotContents(event.entityPlayer.inventory.currentItem, new ItemStack(Items.milk_bucket));
         } else if(!event.entityPlayer.inventory.addItemStackToInventory(new ItemStack(Items.milk_bucket))) {
            event.entityPlayer.dropPlayerItemWithRandomChoice(new ItemStack(Items.milk_bucket, 1, 0), false);
         }

         event.setCanceled(true);
      }

   }
}
