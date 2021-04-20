package com.minttea.tomeofblood.setup.Registries;

import com.hollingsworth.arsnouveau.api.spell.ISpellTier;
import com.minttea.tomeofblood.TomeOfBloodMod;
import com.minttea.tomeofblood.common.items.bloodmagic.BloodGem;
import com.minttea.tomeofblood.common.items.bloodmagic.BloodTome;
import com.minttea.tomeofblood.common.items.eidolon.WarlockTome;
import com.minttea.tomeofblood.common.items.nature.NaturalTome;
import com.minttea.tomeofblood.common.items.occultism.BookOfCasting;
import com.minttea.tomeofblood.common.items.occultism.OccultTome;
import com.minttea.tomeofblood.common.items.occultism.SpiritClass;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;


@Mod.EventBusSubscriber(modid = TomeOfBloodMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
@ObjectHolder(TomeOfBloodMod.MODID)
public class ItemRegistry {
    @ObjectHolder("blood_tome_one")public static BloodTome bloodTome1;
    @ObjectHolder("blood_tome_two")public static BloodTome bloodTome2;
    @ObjectHolder("blood_tome_three")public static BloodTome bloodTome3;

    @ObjectHolder("natural_tome_one")public static NaturalTome naturalTome1;
    @ObjectHolder("natural_tome_two")public static NaturalTome naturalTome2;
    @ObjectHolder("natural_tome_three")public static NaturalTome naturalTome3;

    @ObjectHolder("occult_tome_one")public static OccultTome occultTome1;
    @ObjectHolder("occult_tome_two")public static OccultTome occultTome2;
    @ObjectHolder("occult_tome_three")public static OccultTome occultTome3;

    @ObjectHolder("casting_book_foilot")public static BookOfCasting foilotBook;
    @ObjectHolder("casting_book_djinni")public static BookOfCasting djinniBook;
    @ObjectHolder("casting_book_afrit")public static BookOfCasting afritBook;


    @ObjectHolder("warlock_tome_one")public static WarlockTome warlockTome1;
    @ObjectHolder("warlock_tome_two")public static WarlockTome warlockTome2;
    @ObjectHolder("warlock_tome_three")public static WarlockTome warlockTome3;

    @ObjectHolder("blood_gem")public static BloodGem bloodGem;

    @SubscribeEvent
    public static void registerItems(final RegistryEvent.Register<Item> event)
    {

        final IForgeRegistry<Item> registry = event.getRegistry();

            registry.register(new BloodTome(ISpellTier.Tier.ONE).setRegistryName("blood_tome_one"));
            registry.register(new BloodTome(ISpellTier.Tier.TWO).setRegistryName("blood_tome_two"));
            registry.register(new BloodTome(ISpellTier.Tier.THREE).setRegistryName("blood_tome_three"));
            registry.register(new BloodGem().setRegistryName("blood_gem"));

            registry.register(new NaturalTome(ISpellTier.Tier.ONE).setRegistryName("natural_tome_one"));
            registry.register(new NaturalTome(ISpellTier.Tier.TWO).setRegistryName("natural_tome_two.json"));
            registry.register(new NaturalTome(ISpellTier.Tier.THREE).setRegistryName("natural_tome_three"));

            registry.register(new OccultTome(ISpellTier.Tier.ONE).setRegistryName("occult_tome_one"));
            registry.register(new OccultTome(ISpellTier.Tier.TWO).setRegistryName("occult_tome_two"));
            registry.register(new OccultTome(ISpellTier.Tier.THREE).setRegistryName("occult_tome_three"));
            registry.register(new BookOfCasting(SpiritClass.FOLIOT).setRegistryName("casting_book_foliot"));
            registry.register(new BookOfCasting(SpiritClass.DJINNI).setRegistryName("casting_book_djinni"));
            registry.register(new BookOfCasting(SpiritClass.AFRIT).setRegistryName("casting_book_afrit"));
            registry.register(new BookOfCasting(SpiritClass.MARID).setRegistryName("casting_book_marid"));

            registry.register(new WarlockTome(ISpellTier.Tier.ONE).setRegistryName("warlock_tome_one"));
            registry.register(new WarlockTome(ISpellTier.Tier.TWO).setRegistryName("warlock_tome_two"));
            registry.register(new WarlockTome(ISpellTier.Tier.THREE).setRegistryName("warlock_tome_three"));


    }
}
