package laser.item.tool;

import java.util.List;

import cofh.core.item.ItemBase;
import cofh.lib.util.helpers.StringHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import laser.Laser;
import laser.util.helper.RenderHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.EmptyChunk;

public class ItemChunkUtil extends ItemBase {

   public ItemChunkUtil() {
      super("laser");
      this.setMaxStackSize(1);
      this.setCreativeTab(Laser.tabItems);
      this.setUnlocalizedName("chunkUtil");
   }

   public boolean hasEffect(ItemStack paramItemStack) {
      return paramItemStack.stackTagCompound != null && paramItemStack.stackTagCompound.hasKey("Chunk");
   }

   public ItemStack onItemRightClick(ItemStack p_77659_1_, World p_77659_2_, EntityPlayer p_77659_3_) {
      Chunk chunk = p_77659_2_.getChunkFromBlockCoords(MathHelper.floor_double(p_77659_3_.posX), MathHelper.floor_double(p_77659_3_.posZ));
      int beginX = chunk.xPosition * 16;
      int beginZ = chunk.zPosition * 16;
      int endX = beginX + 16;
      int endZ = beginZ + 16;
      String s = chunk.getRandomWithSeed(987234911L).nextInt(10) == 0?"YES":"NO";
      int max = 0;

      for(int nbttagcompound = 0; nbttagcompound < 16; ++nbttagcompound) {
         for(int j = 0; j < 16; ++j) {
            if(max < chunk.getHeightValue(nbttagcompound, j)) {
               max = chunk.getHeightValue(nbttagcompound, j);
            }
         }
      }

      if(!p_77659_3_.isSneaking()) {
         if(!p_77659_2_.isRemote) {
            p_77659_3_.addChatMessage(new ChatComponentText("**************************************************"));
            p_77659_3_.addChatMessage(new ChatComponentText("Chunk coordinates"));
            p_77659_3_.addChatMessage(new ChatComponentText("**************************************************"));
            p_77659_3_.addChatMessage(new ChatComponentText("X start : " + beginX + ", X end : " + endX));
            p_77659_3_.addChatMessage(new ChatComponentText("Z start : " + beginZ + ", Z end : " + endZ));
            p_77659_3_.addChatMessage(new ChatComponentText("Y min : " + chunk.heightMapMinimum + ", Y max : " + max));
            p_77659_3_.addChatMessage(new ChatComponentText("**************************************************"));
            p_77659_3_.addChatMessage(new ChatComponentText("Slime"));
            p_77659_3_.addChatMessage(new ChatComponentText("**************************************************"));
            p_77659_3_.addChatMessage(new ChatComponentText("Slime chunk : " + s));
         }
      } else if(p_77659_1_.getItemDamage() == 1) {
         if(p_77659_1_.stackTagCompound == null) {
            p_77659_1_.stackTagCompound = new NBTTagCompound();
         }

         NBTTagCompound var13 = new NBTTagCompound();
         var13.setInteger("X", chunk.xPosition);
         var13.setInteger("Z", chunk.zPosition);
         p_77659_1_.stackTagCompound.setTag("Chunk", var13);
         if(!p_77659_2_.isRemote) {
            p_77659_3_.addChatMessage(new ChatComponentText(StringHelper.localize("chat.laser.chunkUtil.success")));
         }
      }

      return p_77659_1_;
   }

   public void onUpdate(ItemStack p_77663_1_, World p_77663_2_, Entity p_77663_3_, int p_77663_4_, boolean p_77663_5_) {
      Minecraft mc = Minecraft.getMinecraft();
      if(p_77663_5_ && !p_77663_2_.isRemote && p_77663_2_.getTotalWorldTime() % 4L == 0L) {
         if(p_77663_1_.getItemDamage() == 1 && p_77663_1_.stackTagCompound != null && p_77663_1_.stackTagCompound.hasKey("Chunk")) {
            NBTTagCompound nbttagcompound = (NBTTagCompound)p_77663_1_.stackTagCompound.getTag("Chunk");
            if(!(p_77663_2_.getChunkFromChunkCoords(nbttagcompound.getInteger("X"), nbttagcompound.getInteger("Z")) instanceof EmptyChunk)) {
               RenderHelper.renderBorder(p_77663_2_, nbttagcompound.getInteger("X"), nbttagcompound.getInteger("Z"), 0.75F, 0.0F, 0.75F);
            }
         }

         RenderHelper.renderBorder(p_77663_2_, p_77663_3_.chunkCoordX, p_77663_3_.chunkCoordZ, 1.0F, 1.0F, 0.0F);
      }

   }

   @SideOnly(Side.CLIENT)
   public void addInformation(ItemStack p_77624_1_, EntityPlayer p_77624_2_, List p_77624_3_, boolean p_77624_4_) {
      if(p_77624_1_.getItemDamage() == 1 && p_77624_1_.stackTagCompound != null && p_77624_1_.stackTagCompound.hasKey("Chunk")) {
         NBTTagCompound nbttagcompound = (NBTTagCompound)p_77624_1_.stackTagCompound.getTag("Chunk");
         int beginX = nbttagcompound.getInteger("X") * 16;
         int beginZ = nbttagcompound.getInteger("Z") * 16;
         int endX = beginX + 16;
         int endZ = beginZ + 16;
         p_77624_3_.add("X : " + beginX + " to " + endX);
         p_77624_3_.add("Z : " + beginZ + " to " + endZ);
      }

   }
}
