package com.minttea.tomeofblood.common.items.occultism;

import com.hollingsworth.arsnouveau.ArsNouveau;
import com.minttea.tomeofblood.TomeOfBloodMod;
import net.minecraft.item.Item;

public class BookOfCasting extends Item {

    private SpiritClass spiritClass;


    static int[] spiritToMana = new int[]{10240, 40960, 163840, 655360};
    public BookOfCasting(SpiritClass spirit) {
        super(new Item.Properties().maxStackSize(1).maxDamage(getDur(spirit)).group(TomeOfBloodMod.itemGroup));
        this.spiritClass = spirit;

    }

    public static int getDur(SpiritClass spiritClass)
    {
        return spiritToMana[spiritClass.ordinal()];
    }

}
