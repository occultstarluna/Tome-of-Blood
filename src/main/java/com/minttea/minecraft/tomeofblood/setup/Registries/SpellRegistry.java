package com.minttea.minecraft.tomeofblood.setup.Registries;

import com.hollingsworth.arsnouveau.api.ArsNouveauAPI;

import com.hollingsworth.arsnouveau.api.spell.AbstractSpellPart;
import com.hollingsworth.arsnouveau.api.spell.SpellSchool;
import com.minttea.minecraft.tomeofblood.TomeOfBloodMod;
import com.minttea.minecraft.tomeofblood.common.items.SentientHarmEffect;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;


@Mod.EventBusSubscriber(modid = TomeOfBloodMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
@ObjectHolder(TomeOfBloodMod.MODID)
public class SpellRegistry {

    public static List<AbstractSpellPart> registeredSpells = new ArrayList<>();
    private static final Logger LOGGER = LogManager.getLogger();
    public static SpellSchool BLOODMAGIC = new SpellSchool("blood");

    public static void registerSpells() {
        LOGGER.debug("Registering soulfire glyph");
        ArsNouveauAPI.getInstance().registerSpell(SentientHarmEffect.INSTANCE.tag,SentientHarmEffect.INSTANCE );
        registeredSpells.add(SentientHarmEffect.INSTANCE);
    }

}
