package com.minttea.minecraft.tomeofblood.common.items;

import com.hollingsworth.arsnouveau.api.ArsNouveauAPI;
import com.hollingsworth.arsnouveau.api.spell.*;
import com.hollingsworth.arsnouveau.common.util.PortUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Util;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import wayoftime.bloodmagic.core.data.SoulNetwork;
import wayoftime.bloodmagic.core.data.SoulTicket;
import wayoftime.bloodmagic.util.helper.NetworkHelper;

import java.util.List;

public class BloodSpellResolver extends SpellResolver {

    private final ISpellValidator spellValidator;

    public BloodSpellResolver(SpellContext spellContext){
        super(spellContext);
        this.spellValidator = ArsNouveauAPI.getInstance().getSpellCastingSpellValidator();
    }

    @Override
    public boolean canCast(LivingEntity entity){

        List<SpellValidationError> validationErrors = spellValidator.validate(spell.recipe);

        if(validationErrors.isEmpty())
        {
            return enoughMana(entity);
        } else {

            if(!silent && entity.isServerWorld())
            {
                PortUtil.sendMessageCenterScreen(entity, validationErrors.get(0).makeTextComponentExisting());
            }
            return false;
        }

    }

    private boolean enoughMana(LivingEntity entity){
        if(entity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) entity;
            if(player.isCreative()) return true;
            int totalCost = getCastingCost(this.spell,player);
            SoulNetwork soulNetwork = NetworkHelper.getSoulNetwork(player.getUniqueID());
            //LOGGER.debug("Got soulnetwork for " + soulNetwork.getPlayer().getDisplayName().getString());
            int pool = soulNetwork.getCurrentEssence();
            if(pool < this.getCastingCost(spell,entity))
            {
                PortUtil.sendMessageCenterScreen(player, new TranslationTextComponent("toomanytomes.alert.lack_lp"));
                return true;
            }
            return true;
        }
        return false;
    }

    @Override
    public void expendMana(LivingEntity entity)
    {
        if(entity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) entity;
            if(!player.isCreative()) {
            int totalCost = getCastingCost(this.spell,player);
            SoulNetwork soulNetwork = NetworkHelper.getSoulNetwork(player.getUniqueID());
            //LOGGER.debug("Got soulnetwork for " + soulNetwork.getPlayer().getDisplayName().getString());
            SoulTicket ticket = new SoulTicket(new StringTextComponent("Spell cast"), totalCost);
            soulNetwork.syphonAndDamage(player, ticket);}
        }
    }
}
