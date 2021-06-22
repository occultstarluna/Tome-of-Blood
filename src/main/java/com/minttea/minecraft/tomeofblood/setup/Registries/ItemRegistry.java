package com.minttea.minecraft.tomeofblood.setup.Registries;

import com.hollingsworth.arsnouveau.ArsNouveau;
import com.hollingsworth.arsnouveau.api.spell.ISpellTier;
import com.minttea.minecraft.tomeofblood.TomeOfBloodMod;
import com.minttea.minecraft.tomeofblood.common.items.BloodGem;
import com.minttea.minecraft.tomeofblood.common.items.BloodTome;


import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;


@Mod.EventBusSubscriber(modid = TomeOfBloodMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
@ObjectHolder(TomeOfBloodMod.MODID)
public class ItemRegistry {
    @ObjectHolder("blood_tome_one")public static BloodTome bloodTome1;
    @ObjectHolder("blood_tome_two")public static BloodTome bloodTome2;
    @ObjectHolder("blood_tome_three")public static BloodTome bloodTome3;
    @ObjectHolder("tome_of_blood")public static Item tome_of_blood;
    @ObjectHolder("blood_gem")public static BloodGem bloodGem;

    @SubscribeEvent
    public static void registerItems(final RegistryEvent.Register<Item> event)
    {

        final IForgeRegistry<Item> registry = event.getRegistry();

            registry.register(new BloodTome(ISpellTier.Tier.ONE).setRegistryName("blood_tome_one"));
            registry.register(new BloodTome(ISpellTier.Tier.TWO).setRegistryName("blood_tome_two"));
            registry.register(new BloodTome(ISpellTier.Tier.THREE).setRegistryName("blood_tome_three"));
            registry.register(new BloodGem().setRegistryName("blood_gem"));
            registry.register(new Item(new Item.Properties().maxStackSize(1).group(ArsNouveau.itemGroup)).setRegistryName("tome_of_blood"));



    }
}
