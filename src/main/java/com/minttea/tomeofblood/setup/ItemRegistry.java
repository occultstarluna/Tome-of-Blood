package com.minttea.tomeofblood.setup;

import com.minttea.tomeofblood.TomeOfBloodMod;
import com.minttea.tomeofblood.common.items.BloodTome;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

import java.util.HashSet;
import java.util.Set;

@ObjectHolder(TomeOfBloodMod.MODID)
public class ItemRegistry {
    @ObjectHolder("tome_of_blood")public static BloodTome bloodTome;
        //public static Item bloodTome = new Item(new Item.Properties().maxStackSize(1).group(TomeOfBloodMod.itemGroup)).setRegistryName("tome_of_blood");
    @Mod.EventBusSubscriber(modid = TomeOfBloodMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistrationHandler{
        public static final Set<Item> ITEMS = new HashSet<>();

        @SubscribeEvent
        public static void registerItems(final RegistryEvent.Register<Item> event)
        {
            Item[] items =
                    {
                    new BloodTome().setRegistryName("tome_of_blood"),
                   // bloodTome
                    };

            final IForgeRegistry<Item> registry = event.getRegistry();
            for(final Item item: items)
            {
                registry.register(item);
                ITEMS.add(item);
            }



        }
    }
}
