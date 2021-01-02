package com.minttea.tomeofblood.common.events;

import com.hollingsworth.arsnouveau.api.event.ManaRegenCalcEvent;
import com.hollingsworth.arsnouveau.api.event.MaxManaCalcEvent;
import com.hollingsworth.arsnouveau.api.event.SpellCastEvent;
import com.hollingsworth.arsnouveau.api.spell.AbstractSpellPart;
import com.minttea.tomeofblood.TomeOfBloodMod;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.world.NoteBlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import wayoftime.bloodmagic.core.living.LivingStats;
import wayoftime.bloodmagic.core.living.LivingUpgrade;
import wayoftime.bloodmagic.core.living.LivingUtil;

import java.util.List;

public class LivingArmorManaBonus {

    @SubscribeEvent
    public static void awardSpellCastXp(SpellCastEvent event)
    {
        LivingEntity entity = event.getEntityLiving();
        List<AbstractSpellPart> spell = event.spell;
        int xpAward = 1;
        if(entity instanceof PlayerEntity){

            PlayerEntity player = (PlayerEntity) entity;
            if(LivingUtil.hasFullSet(player)){
                LivingStats stats = LivingStats.fromPlayer(player);

                for (AbstractSpellPart spellpart: spell) {

                    switch (spellpart.getTier()) {
                        case THREE:
                            xpAward += 26;
                            break;
                        case TWO:
                            xpAward += 12;
                            break;
                        case ONE:
                            xpAward += 5;
                            break;
                        default:

                    }
                }
                LivingUtil.applyNewExperience(player, LivingUpgrade.DUMMY,xpAward);
            }
        }

    }
    @SubscribeEvent
    public static void maxManaForArmor(MaxManaCalcEvent event)
    {

        PlayerEntity player = (PlayerEntity) event.getEntity();
        LivingStats stats = LivingStats.fromPlayer(player);
        int level = stats.getMaxPoints();
        int max = event.getMax();
        float manaBonus = 1+ (float)level/10;
        event.setMax((int)(max*manaBonus));


    }
    @SubscribeEvent
    public static void manaRegenByLevel(ManaRegenCalcEvent event) {
        PlayerEntity player = (PlayerEntity) event.getEntity();
        LivingStats stats = LivingStats.fromPlayer(player);
        int level = stats.getMaxPoints();
        double regen = event.getRegen();
        float manaBonus = 1+ (float)level/10;
        event.setRegen((int) (regen*manaBonus));
    }
}
