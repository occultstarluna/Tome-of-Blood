package com.minttea.tomeofblood.common.utils;

import com.hollingsworth.arsnouveau.api.spell.AbstractCastMethod;
import com.hollingsworth.arsnouveau.api.spell.AbstractSpellPart;
import com.hollingsworth.arsnouveau.api.spell.SpellContext;
import com.hollingsworth.arsnouveau.api.spell.SpellResolver;
import com.hollingsworth.arsnouveau.api.util.ManaUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import wayoftime.bloodmagic.core.data.SoulNetwork;
import wayoftime.bloodmagic.core.data.SoulTicket;
import wayoftime.bloodmagic.util.helper.NetworkHelper;

import java.util.List;

public class BloodSpellResolver extends SpellResolver {
    public BloodSpellResolver(AbstractCastMethod cast, List<AbstractSpellPart> spell_recipe, SpellContext context) {
        super(cast, spell_recipe, context);
    }

    public BloodSpellResolver(List<AbstractSpellPart> currentRecipe, SpellContext context) {
        super(currentRecipe, context);
    }


    public void expendMana(LivingEntity entity)
    {
        int totalCost = ManaUtil.getCastingCost(spell_recipe, entity)*10;
        SoulNetwork soulNetwork = NetworkHelper.getSoulNetwork(entity.getUniqueID());
        SoulTicket ticket = new SoulTicket(ITextComponent.getTextComponentOrEmpty("Spell cast"),totalCost);
        soulNetwork.syphon(ticket);
    }
}
