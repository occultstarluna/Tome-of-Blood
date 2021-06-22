package com.minttea.minecraft.tomeofblood.setup.Registries;

import com.hollingsworth.arsnouveau.api.ArsNouveauAPI;

import com.minttea.minecraft.tomeofblood.TomeOfBloodMod;
import com.minttea.minecraft.tomeofblood.common.items.SentientHarmEffect;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@Mod.EventBusSubscriber(modid = TomeOfBloodMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
@ObjectHolder(TomeOfBloodMod.MODID)
public class SpellRegistry {

    private static final Logger LOGGER = LogManager.getLogger();

    public static void registerSpells() {
        LOGGER.debug("Registering soulfire glyph");
        //ArsNouveauAPI.getInstance().registerSpell("soulfire", new SentientHarmEffect());
    }

}
