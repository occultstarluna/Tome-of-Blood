package com.minttea.tomeofblood.common.client.gui;

import com.hollingsworth.arsnouveau.common.items.Glyph;
import com.minttea.tomeofblood.common.items.scroll.BloodScroll;
import com.minttea.tomeofblood.setup.Registry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Hand;

import javax.annotation.Nonnull;

public class ScrollContainer extends Container {

    public static ScrollContainer fromNetwork(int windowId, PlayerInventory inv, PacketBuffer buf)
    {
        Hand hand = buf.readBoolean() ? Hand.MAIN_HAND : Hand.OFF_HAND;
        return new ScrollContainer(windowId, inv, inv.player.getHeldItem(hand));
    }

    private final ItemStack scroll;
    public final IInventory scrollInv;
    public ScrollContainer(int id, PlayerInventory playerInventory, ItemStack scroll) {
        super(Registry.container, id);
        this.scroll = scroll;

        int i;
        int j;

        if(!playerInventory.player.world.isRemote) {
            scrollInv = BloodScroll.getInventory(scroll);
        } else {
            scrollInv = new Inventory(27);
        }

        for( i = 0; i < 3; i++)
        {
            for(j=0; j<9;j++)
            {
                int k = j + i * 9;
                addSlot(new Slot(scrollInv, k, 8 + j * 18, 17 + i * 18) {
                    @Override
                    public boolean isItemValid(@Nonnull ItemStack stack)
                    {
                        return stack.getItem() instanceof Glyph;
                    }
                });
            }
        }
        for( i = 0; i<3; i++)
        {
            for(j=0;j<9;j++)
            {
                addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for(i = 0; i<9; i++)
        {
            addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return true;
    }
    @Nonnull
    @Override
    public ItemStack transferStackInSlot(PlayerEntity player, int slotIndex) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = inventorySlots.get(slotIndex);

        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (slotIndex < 27) {
                if (!mergeItemStack(itemstack1, 16, 52, true)) {
                    return ItemStack.EMPTY;
                }
            }

            if (itemstack1.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, itemstack1);
        }

        return itemstack;
    }

}
