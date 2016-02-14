package laser.gui.container.machine;

import cofh.lib.gui.slot.SlotEnergy;
import cofh.thermalexpansion.gui.container.ContainerTEBase;
import laser.block.machine.TilePump;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;

public class ContainerPump extends ContainerTEBase {

   TilePump myTile;


   public ContainerPump(InventoryPlayer paramInventoryPlayer, TileEntity paramTileEntity) {
      super(paramInventoryPlayer, paramTileEntity);
      this.myTile = (TilePump)paramTileEntity;
      this.addSlotToContainer(new SlotEnergy(this.myTile, this.myTile.getChargeSlot(), 8, 53));
   }
}
