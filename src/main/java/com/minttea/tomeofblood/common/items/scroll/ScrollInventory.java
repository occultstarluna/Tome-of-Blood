package com.minttea.tomeofblood.common.items.scroll;

import com.hollingsworth.arsnouveau.api.spell.AbstractEffect;
import com.hollingsworth.arsnouveau.api.spell.AbstractSpellPart;
import com.hollingsworth.arsnouveau.common.items.Glyph;
import com.minttea.tomeofblood.util.NBTHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import org.lwjgl.system.CallbackI;

import java.util.ArrayList;
import java.util.List;

public class ScrollInventory extends Inventory {

    private static final String SPELL_TAG = "Items";
    private final ItemStack stack;


    public ScrollInventory(ItemStack stack, int expectedSize) {
        super(expectedSize);
        this.stack = stack;
        ListNBT nbt = NBTHelper.Item.getList(stack, SPELL_TAG, 10);
        int i = 0;
        for(; i < expectedSize && i < nbt.size(); i++)
        {
            setInventorySlotContents(i, ItemStack.read(nbt.getCompound(i)));
        }
    }

    public List<AbstractSpellPart> getSpellRecipe()
    {
        List<AbstractSpellPart> spellParts = new ArrayList<>();
        for(int i = 0; i < this.getSizeInventory(); i++)
        {
            ItemStack item = this.getStackInSlot(i);
            if( item.getItem() instanceof Glyph)
            {
                for(int c = 0; c< item.getCount(); c++)
                {
                    spellParts.add(((Glyph) item.getItem()).spellPart);
                }
            }
        }
        return spellParts;
    }

    @Override
    public boolean isUsableByPlayer(PlayerEntity playerEntity)
    {
        return !stack.isEmpty();
    }
    @Override
    public void markDirty() {
        super.markDirty();
        ListNBT nbt = new ListNBT();
        for(int i = 0; i< getSizeInventory(); i++)
        {
            nbt.add(getStackInSlot(i).write(new CompoundNBT()));
        }
        NBTHelper.Item.setList(stack, SPELL_TAG, nbt);
    }

}
