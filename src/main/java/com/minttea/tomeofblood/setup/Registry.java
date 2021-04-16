package com.minttea.tomeofblood.setup;

import com.github.klikli_dev.occultism.common.ritual.Ritual;
import com.github.klikli_dev.occultism.registry.OccultismRituals;
import com.hollingsworth.arsnouveau.api.ArsNouveauAPI;
import com.hollingsworth.arsnouveau.api.spell.ISpellTier;
import com.hollingsworth.arsnouveau.setup.ItemsRegistry;
import com.minttea.tomeofblood.TomeOfBloodMod;
import com.minttea.tomeofblood.client.gui.GuiBloodScroll;
import com.minttea.tomeofblood.common.items.NewGlyphLib;
import com.minttea.tomeofblood.common.items.bloodmagic.BloodGem;
import com.minttea.tomeofblood.common.items.bloodmagic.BloodTome;
import com.minttea.tomeofblood.common.items.bloodmagic.SentientHarmEffect;
import com.minttea.tomeofblood.common.items.bloodmagic.scroll.BloodScroll;
import com.minttea.tomeofblood.client.gui.ScrollContainer;
import com.minttea.tomeofblood.common.items.eidolon.WarlockEmpowerSpell;
import com.minttea.tomeofblood.common.items.eidolon.WarlockTome;
import com.minttea.tomeofblood.common.items.nature.NaturalTome;
import com.minttea.tomeofblood.common.items.occultism.BookOfCasting;
import com.minttea.tomeofblood.common.items.occultism.CraftOccultTomeRitual;
import com.minttea.tomeofblood.common.items.occultism.OccultTome;
import com.minttea.tomeofblood.common.items.occultism.SpiritClass;
import elucent.eidolon.spell.Signs;
import elucent.eidolon.spell.Spells;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

import java.util.HashSet;
import java.util.Set;

@ObjectHolder(TomeOfBloodMod.MODID)
public class Registry {

    @ObjectHolder("blood_tome_one")public static BloodTome bloodTome1;
    @ObjectHolder("blood_tome_two")public static BloodTome bloodTome2;
    @ObjectHolder("blood_tome_three")public static BloodTome bloodTome3;

    @ObjectHolder("natural_tome_one")public static NaturalTome naturalTome1;
    @ObjectHolder("natural_tome_two")public static NaturalTome naturalTome2;
    @ObjectHolder("natural_tome_three")public static NaturalTome naturalTome3;

    @ObjectHolder("occult_tome_one")public static OccultTome occultTome1;
    @ObjectHolder("occult_tome_two")public static OccultTome occultTome2;
    @ObjectHolder("occult_tome_three")public static OccultTome occultTome3;

    @ObjectHolder("warlock_tome_one")public static WarlockTome warlockTome1;
    @ObjectHolder("warlock_tome_two")public static WarlockTome warlockTome2;
    @ObjectHolder("warlock_tome_three")public static WarlockTome warlockTome3;

    @ObjectHolder("blood_scroll")public static BloodScroll bloodScroll;
    @ObjectHolder("blood_gem")public static BloodGem bloodGem;

    @ObjectHolder("craft_occult_tome_one") public static CraftOccultTomeRitual craftOccultTomeOneRitual;
    @ObjectHolder("craft_occult_tome_two") public static CraftOccultTomeRitual craftOccultTomeTwoRitual;
    @ObjectHolder("craft_occult_tome_three") public static CraftOccultTomeRitual craftOccultTomeThreeRitual;


    @Mod.EventBusSubscriber(modid = TomeOfBloodMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
    @ObjectHolder(TomeOfBloodMod.MODID)
    public static class RegistrationHandler{
        //public static final Set<Item> ITEMS = new HashSet<>();
        @ObjectHolder("blood_scroll")public static ContainerType<ScrollContainer> container;

        @SubscribeEvent
        public static void registerItems(final RegistryEvent.Register<Item> event)
        {
            Item[] items =
                    {
                    new BloodTome(ISpellTier.Tier.ONE).setRegistryName("blood_tome_one"),
                    new BloodTome(ISpellTier.Tier.TWO).setRegistryName("blood_tome_two"),
                    new BloodTome(ISpellTier.Tier.THREE).setRegistryName("blood_tome_three"),
                    new NaturalTome(ISpellTier.Tier.ONE).setRegistryName("natural_tome_one"),
                    new NaturalTome(ISpellTier.Tier.TWO).setRegistryName("natural_tome_two"),
                    new NaturalTome(ISpellTier.Tier.THREE).setRegistryName("natural_tome_three"),
                    new OccultTome(ISpellTier.Tier.ONE).setRegistryName("occult_tome_one"),
                    new OccultTome(ISpellTier.Tier.TWO).setRegistryName("occult_tome_two"),
                    new OccultTome(ISpellTier.Tier.THREE).setRegistryName("occult_tome_three"),
                    new WarlockTome(ISpellTier.Tier.ONE).setRegistryName("warlock_tome_one"),
                    new WarlockTome(ISpellTier.Tier.TWO).setRegistryName("warlock_tome_two"),
                    new WarlockTome(ISpellTier.Tier.THREE).setRegistryName("warlock_tome_three"),
                    new BookOfCasting(SpiritClass.FOLIOT).setRegistryName("casting_book_foliot"),
                    new BookOfCasting(SpiritClass.DJINNI).setRegistryName("casting_book_djinni"),
                    new BookOfCasting(SpiritClass.AFRIT).setRegistryName("casting_book_afrit"),
                    new BookOfCasting(SpiritClass.MARID).setRegistryName("casting_book_marid"),
                    new BloodScroll().setRegistryName("blood_scroll"),
                    new BloodGem().setRegistryName("blood_gem")
                   // bloodTome
                    };

            final IForgeRegistry<Item> registry = event.getRegistry();
            for(final Item item: items)
            {
                registry.register(item);
                //ITEMS.add(item);
            }



        }

        public static void registerSpells()
        {
            ArsNouveauAPI.getInstance().registerSpell(NewGlyphLib.EffectSentientHarmID, new SentientHarmEffect());
            Spells.register(new WarlockEmpowerSpell(new ResourceLocation(TomeOfBloodMod.MODID, "warlock_empower"), Signs.WICKED_SIGN));//, Signs.MIND_SIGN,Signs.SOUL_SIGN));
//            OccultismRituals.RITUALS.register("craft_occult_tome_one", CraftOccultTomeOneRitual::new);
           //OccultismRituals.RITUALS.;

        }

        @SubscribeEvent
        public static void registerContainers(final RegistryEvent.Register<ContainerType<?>> event)
        {
            container = IForgeContainerType.create(ScrollContainer::fromNetwork);
            container.setRegistryName("blood_scroll_screen");
            event.getRegistry().register(container);
        }
       @SubscribeEvent
       public static void registerRituals(final RegistryEvent.Register<Ritual> event)
       {

           Ritual[] rituals = {
             new CraftOccultTomeRitual(OccultismRituals.CRAFT_FOLIOT_PENTACLE.get()
                     , Ingredient.fromItems(ItemsRegistry.noviceSpellBook),
                     "craft_occult_tome_one",occultTome1).setRegistryName("craft_occult_tome_one"),

                   new CraftOccultTomeRitual(OccultismRituals.CRAFT_DJINNI_PENTACLE.get()
                           , Ingredient.fromItems(occultTome1),
                           "craft_occult_tome_two",occultTome2).setRegistryName("craft_occult_tome_two")

                   ,

                   new CraftOccultTomeRitual(OccultismRituals.CRAFT_AFRIT_PENTACLE.get()
                           , Ingredient.fromItems(occultTome2),
                           "craft_occult_tome_three",occultTome3).setRegistryName("craft_occult_tome_three")
           };
           for (Ritual r: rituals
                ) {
               event.getRegistry().register(r);
           }

       }

        @SubscribeEvent
        public static void onClientSetupEvent(FMLClientSetupEvent event) {
            ScreenManager.registerFactory(container, GuiBloodScroll::new);
        }
    }
}
