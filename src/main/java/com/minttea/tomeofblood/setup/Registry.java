package com.minttea.tomeofblood.setup;

import com.hollingsworth.arsnouveau.api.spell.ISpellTier;
import com.minttea.tomeofblood.TomeOfBloodMod;
import com.minttea.tomeofblood.common.client.gui.GuiBloodScroll;
import com.minttea.tomeofblood.common.items.bloodmagic.BloodGem;
import com.minttea.tomeofblood.common.items.bloodmagic.BloodTome;
import com.minttea.tomeofblood.common.items.bloodmagic.scroll.BloodScroll;
import com.minttea.tomeofblood.common.client.gui.ScrollContainer;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.Item;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

import java.util.HashSet;
import java.util.Set;

@ObjectHolder(TomeOfBloodMod.MODID)
public class Registry {
    @ObjectHolder("tome_of_blood")public static BloodTome bloodTome;
    @ObjectHolder("blood_scroll")public static BloodScroll bloodScroll;
    @ObjectHolder("blood_gem")public static BloodGem bloodGem;

    @ObjectHolder("blood_scroll")public static ContainerType<ScrollContainer> container;
        //public static Item bloodTome = new Item(new Item.Properties().maxStackSize(1).group(TomeOfBloodMod.itemGroup)).setRegistryName("tome_of_blood");
    @Mod.EventBusSubscriber(modid = TomeOfBloodMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistrationHandler{
        public static final Set<Item> ITEMS = new HashSet<>();

        @SubscribeEvent
        public static void registerItems(final RegistryEvent.Register<Item> event)
        {
            Item[] items =
                    {
                    new BloodTome(ISpellTier.Tier.ONE).setRegistryName("tome_of_blood_one"),
                    new BloodScroll().setRegistryName("blood_scroll"),
                    new BloodGem().setRegistryName("blood_gem")
                   // bloodTome
                    };

            final IForgeRegistry<Item> registry = event.getRegistry();
            for(final Item item: items)
            {
                registry.register(item);
                ITEMS.add(item);
            }



        }
        @SubscribeEvent
        public static void registerContainers(final RegistryEvent.Register<ContainerType<?>> event)
        {
            container = IForgeContainerType.create(ScrollContainer::fromNetwork);
            container.setRegistryName("blood_scroll_screen");
            event.getRegistry().register(container);
        }

        @SubscribeEvent
        public static void onClientSetupEvent(FMLClientSetupEvent event) {
            ScreenManager.registerFactory(container, GuiBloodScroll::new);
        }
    }
}
