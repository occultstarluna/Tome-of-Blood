package com.minttea.tomeofblood.common.items.eidolon.codex;

import com.hollingsworth.arsnouveau.setup.ItemsRegistry;
import com.minttea.tomeofblood.setup.Registries.ItemRegistry;
import elucent.eidolon.Registry;
import elucent.eidolon.codex.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class NewCodexChapters {

    static Category TOMES;

    static Chapter INTRO, NOVICE_TOME, APPRENTICE_TOME, MASTER_TOME, TOME_INDEX;

    public static void init()
    {
        INTRO = new Chapter(
    "tomeofblood.codex.chapter.intro.title",
           new TitlePage("tomeofblood.codex.title.intro"),
           new TextPage("tomeofblood.codex.page.tome_fluff")
        );

        NOVICE_TOME = new Chapter(
          "tomeofblood.codex.chapter.novice.title",
          new TitlePage("tomeofblood.codex.title.novice"),
                new WorktablePage(new ItemStack(ItemRegistry.warlockTome1),
                        ItemStack.EMPTY,                new ItemStack(Registry.ENCHANTED_ASH.get()),   ItemStack.EMPTY,
                        new ItemStack(Registry.ENCHANTED_ASH.get()),  new ItemStack(ItemsRegistry.noviceSpellBook), new ItemStack(Registry.ENCHANTED_ASH.get()),
                        ItemStack.EMPTY,                new ItemStack(Registry.ENCHANTED_ASH.get()),   ItemStack.EMPTY,
                        new ItemStack(Registry.SOUL_SHARD.get()),
                        ItemStack.EMPTY,
                        new ItemStack(Registry.SOUL_SHARD.get()),
                        ItemStack.EMPTY
                        )
        );

        APPRENTICE_TOME = new Chapter(
                "tomeofblood.codex.chapter.apprentice.title",
                new TitlePage("tomeofblood.codex.title.apprentice"),
                new WorktablePage(new ItemStack(ItemRegistry.warlockTome2),
                        ItemStack.EMPTY,                new ItemStack(Items.BLAZE_ROD),   ItemStack.EMPTY,
                        new ItemStack(Registry.GOLD_INLAY.get()),  new ItemStack(ItemRegistry.warlockTome1), new ItemStack(Registry.GOLD_INLAY.get()),
                        ItemStack.EMPTY,                new ItemStack(Items.BLAZE_ROD),   ItemStack.EMPTY,
                        new ItemStack(Registry.UNHOLY_SYMBOL.get()),
                        new ItemStack(Items.QUARTZ),
                        new ItemStack(Registry.SHADOW_GEM.get()),
                        new ItemStack(Items.QUARTZ)
                )
        );
        MASTER_TOME = new Chapter(
                "tomeofblood.codex.chapter.master.title",
                new TitlePage("tomeofblood.codex.title.master"),
                new WorktablePage(new ItemStack(ItemRegistry.warlockTome3),
                        ItemStack.EMPTY,                ItemStack.EMPTY,   ItemStack.EMPTY,
                        ItemStack.EMPTY,  new ItemStack(ItemRegistry.warlockTome2), ItemStack.EMPTY,
                        ItemStack.EMPTY,                ItemStack.EMPTY,   ItemStack.EMPTY,
                        new ItemStack(Registry.WRAITH_HEART.get()),
                        new ItemStack(Items.NETHER_STAR),
                        new ItemStack(Registry.ZOMBIE_HEART.get()),
                        new ItemStack(Items.TOTEM_OF_UNDYING)
                )
        );
        TOME_INDEX = new Chapter(
                "tomeofblood.codex.chapter.index.title",
                new TitledIndexPage("tomeofblood.codex.chapter.index.title.0",
                        new IndexPage.IndexEntry(INTRO, new ItemStack(Registry.UNHOLY_SYMBOL.get())),
                        new IndexPage.IndexEntry(NOVICE_TOME, new ItemStack(ItemRegistry.warlockTome1)),
                        new IndexPage.IndexEntry(APPRENTICE_TOME, new ItemStack(ItemRegistry.warlockTome2)),
                        new IndexPage.IndexEntry(MASTER_TOME, new ItemStack(ItemRegistry.warlockTome3))
                        )
        );
        //CodexChapters.c
    }
}
