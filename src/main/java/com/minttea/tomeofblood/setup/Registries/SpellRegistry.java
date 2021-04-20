package com.minttea.tomeofblood.setup.Registries;

import com.hollingsworth.arsnouveau.api.ArsNouveauAPI;
import com.minttea.tomeofblood.TomeOfBloodMod;
import com.minttea.tomeofblood.common.items.NewGlyphLib;
import com.minttea.tomeofblood.common.items.bloodmagic.SentientHarmEffect;
import com.minttea.tomeofblood.common.items.eidolon.WarlockEmpowerSpell;
import elucent.eidolon.recipe.WorktableRecipe;
import elucent.eidolon.recipe.WorktableRegistry;
import elucent.eidolon.spell.Signs;
import elucent.eidolon.spell.Spells;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;


@Mod.EventBusSubscriber(modid = TomeOfBloodMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
@ObjectHolder(TomeOfBloodMod.MODID)
public class SpellRegistry {

    public static void registerSpells()
    {

            ArsNouveauAPI.getInstance().registerSpell(NewGlyphLib.EffectSentientHarmID, new SentientHarmEffect());

            Spells.register(new WarlockEmpowerSpell(new ResourceLocation(TomeOfBloodMod.MODID, "warlock_empower"), Signs.WICKED_SIGN,Signs.MIND_SIGN,Signs.SOUL_SIGN));

//            OccultismRituals.RITUALS.register("craft_occult_tome_one", CraftOccultTomeOneRitual::new);
        //OccultismRituals.RITUALS.;


    }
}
