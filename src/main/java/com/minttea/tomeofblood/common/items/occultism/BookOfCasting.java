package com.minttea.tomeofblood.common.items.occultism;

import com.hollingsworth.arsnouveau.ArsNouveau;
import net.minecraft.item.Item;

public class BookOfCasting extends Item {

    private SpiritClass spiritClass;


    static int[] spiritToMana = new int[]{1024, 4096, 16384, 65536};
    public BookOfCasting(SpiritClass spirit) {
        super(new Item.Properties().maxStackSize(1).maxDamage(getDur(spirit)).group(ArsNouveau.itemGroup));
        this.spiritClass = spirit;

    }

    public static int getDur(SpiritClass spiritClass)
    {
        return spiritToMana[spiritClass.ordinal()];
    }

}
enum SpiritClass
{
    FOLIOT, DJINNI, AFRIT,MARID
}