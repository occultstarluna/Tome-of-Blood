package com.minttea.tomeofblood.common.events;

import com.hollingsworth.arsnouveau.api.event.ManaRegenCalcEvent;
import com.hollingsworth.arsnouveau.api.event.MaxManaCalcEvent;
import com.hollingsworth.arsnouveau.api.event.SpellCastEvent;
import com.hollingsworth.arsnouveau.api.spell.AbstractSpellPart;

import com.hollingsworth.arsnouveau.api.spell.Spell;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.world.NoteBlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import wayoftime.bloodmagic.core.living.LivingStats;
import wayoftime.bloodmagic.core.living.LivingUpgrade;
import wayoftime.bloodmagic.core.living.LivingUtil;

import java.util.List;

public class LivingArmorManaBonus {
    private static final Logger LOGGER = LogManager.getLogger();
    @SubscribeEvent
    public static void awardSpellCastArmourUpgrade(SpellCastEvent event)
    {
        LivingEntity entity = event.getEntityLiving();
        Spell spell = event.spell;
        int xpAward = 1;
        if(entity instanceof PlayerEntity){

            PlayerEntity player = (PlayerEntity) entity;
            if(LivingUtil.hasFullSet(player)){

                xpAward = (int)((double) spell.getCastingCost()/100);
                LivingStats stats = LivingStats.fromPlayer(player);
                stats.addExperience(new ResourceLocation("tomeofblood", "mana_bonus"), xpAward);
                LOGGER.debug("Arcane Attunement level ", stats.getLevel(new ResourceLocation("tomeofblood", "mana_bonus")));
                LivingStats.toPlayer(player, stats);
                if(stats)
            }
        }

    }
    @SubscribeEvent
    public static void maxManaForArmor(MaxManaCalcEvent event)
    {
        PlayerEntity player = (PlayerEntity) event.getEntity();
        LivingStats stats = LivingStats.fromPlayer(player);
        if(stats != null) {
            double max = event.getMax();
            int level = stats.getLevel(new ResourceLocation("tomeofblood", "mana_bonus"));
            float manaBonus = 1 + (float) level / 10;
            event.setMax((int) (max * manaBonus));
        }

    }
}
