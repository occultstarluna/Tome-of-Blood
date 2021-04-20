package com.minttea.tomeofblood.setup.Registries;

import com.hollingsworth.arsnouveau.setup.ItemsRegistry;
import com.minttea.tomeofblood.TomeOfBloodMod;
import elucent.eidolon.Registry;
import elucent.eidolon.recipe.WorktableRecipe;
import elucent.eidolon.recipe.WorktableRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

@Mod.EventBusSubscriber(modid = TomeOfBloodMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
@ObjectHolder(TomeOfBloodMod.MODID)
public class RecipeRegistry {

    public static void registerRecipes(){


        WorktableRegistry.register(new WorktableRecipe(new Object[]
                {
                        ItemStack.EMPTY, Registry.ENCHANTED_ASH.get(), ItemStack.EMPTY,
                        Registry.ENCHANTED_ASH.get(), ItemsRegistry.noviceSpellBook, Registry.ENCHANTED_ASH.get(),
                        ItemStack.EMPTY, Registry.ENCHANTED_ASH.get(), ItemStack.EMPTY
                }, new Object[]
                {
                        Registry.SOUL_SHARD.get(),
                        ItemStack.EMPTY,
                        Registry.SOUL_SHARD.get(),
                        ItemStack.EMPTY
                },
                new ItemStack(ItemRegistry.warlockTome1)).setRegistryName(TomeOfBloodMod.MODID, "warlock_tome_one"));


        WorktableRegistry.register(new WorktableRecipe(new Object[]
                {
                        ItemStack.EMPTY, Items.BLAZE_ROD, ItemStack.EMPTY,
                        Registry.GOLD_INLAY.get(), ItemRegistry.warlockTome1, Registry.GOLD_INLAY.get(),
                        ItemStack.EMPTY, Items.BLAZE_ROD, ItemStack.EMPTY
                }, new Object[]
                {
                        Registry.UNHOLY_SYMBOL.get(),
                        Items.QUARTZ,
                        Registry.SHADOW_GEM.get(),
                        Items.QUARTZ
                },
                new ItemStack(ItemRegistry.warlockTome2)).setRegistryName(TomeOfBloodMod.MODID, "warlock_tome_two"));

        WorktableRegistry.register(new WorktableRecipe(new Object[]
                {
                        ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY,
                        ItemStack.EMPTY, ItemRegistry.warlockTome2, ItemStack.EMPTY,
                        ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY
                }, new Object[]
                {
                        Registry.WRAITH_HEART.get(),
                        Items.NETHER_STAR,
                        Registry.ZOMBIE_HEART.get(),
                        Items.TOTEM_OF_UNDYING
                },
                new ItemStack(ItemRegistry.warlockTome3)).setRegistryName(TomeOfBloodMod.MODID, "warlock_tome_three"));


    }
}
