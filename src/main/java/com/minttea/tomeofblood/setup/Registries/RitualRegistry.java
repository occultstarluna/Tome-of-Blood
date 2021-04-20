package com.minttea.tomeofblood.setup.Registries;

import com.github.klikli_dev.occultism.common.ritual.Ritual;
import com.github.klikli_dev.occultism.registry.OccultismItems;
import com.github.klikli_dev.occultism.registry.OccultismRituals;
import com.hollingsworth.arsnouveau.setup.ItemsRegistry;
import com.minttea.tomeofblood.TomeOfBloodMod;
import com.minttea.tomeofblood.common.items.occultism.rituals.OccultCraftingRitual;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

import static com.minttea.tomeofblood.setup.Registries.ItemRegistry.*;

@Mod.EventBusSubscriber(modid = TomeOfBloodMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
@ObjectHolder(TomeOfBloodMod.MODID)
public class RitualRegistry {


    @ObjectHolder("craft_occult_tome_one") public static OccultCraftingRitual craftOccultTomeOneRitual;
    @ObjectHolder("craft_occult_tome_two") public static OccultCraftingRitual craftOccultTomeTwoRitual;
    @ObjectHolder("craft_occult_tome_three") public static OccultCraftingRitual craftOccultTomeThreeRitual;
    @ObjectHolder("craft_casting_foilot") public static OccultCraftingRitual craftCastingFoilotRitual;
    @ObjectHolder("craft_casting_djinni") public static OccultCraftingRitual craftCastingDjinniRitual;
    @ObjectHolder("craft_casting_afrit") public static OccultCraftingRitual craftCastingAfritRitual;


    @SubscribeEvent
    public static void registerRituals(final RegistryEvent.Register<Ritual> event)
    //public static void registerRituals()
    {

            Ritual[] rituals = {
                    new OccultCraftingRitual(OccultismRituals.CRAFT_FOLIOT_PENTACLE.get(),
                            Ingredient.fromItems(ItemsRegistry.noviceSpellBook),
                            "craft_occult_tome_one", occultTome1).setRegistryName("craft_occult_tome_one"),
                    new OccultCraftingRitual(OccultismRituals.CRAFT_DJINNI_PENTACLE.get(),
                            Ingredient.fromItems(occultTome1),
                            "craft_occult_tome_two", occultTome2).setRegistryName("craft_occult_tome_two"),
                    new OccultCraftingRitual(OccultismRituals.CRAFT_AFRIT_PENTACLE.get(),
                            Ingredient.fromItems(occultTome2),
                            "craft_occult_tome_three", occultTome3).setRegistryName("craft_occult_tome_three"),
                    new OccultCraftingRitual(OccultismRituals.CRAFT_FOLIOT_PENTACLE.get(),
                            Ingredient.fromItems(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()),
                            "craft_casting_foilot", occultTome1).setRegistryName("craft_casting_foilot"),
                    new OccultCraftingRitual(OccultismRituals.CRAFT_DJINNI_PENTACLE.get(),
                            Ingredient.fromItems(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()),
                            "craft_casting_djinni", occultTome2).setRegistryName("craft_casting_djinii"),
                    new OccultCraftingRitual(OccultismRituals.CRAFT_AFRIT_PENTACLE.get(),
                            Ingredient.fromItems(OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()),
                            "craft_casting_afrit", occultTome3).setRegistryName("craft_crafting_afrit")
            };

            for (Ritual r : rituals
            ) {

                    event.getRegistry().register(r);
            }

    }
}
