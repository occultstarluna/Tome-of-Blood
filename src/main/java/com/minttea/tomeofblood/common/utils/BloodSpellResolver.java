package com.minttea.tomeofblood.common.utils;

import com.hollingsworth.arsnouveau.api.spell.AbstractCastMethod;
import com.hollingsworth.arsnouveau.api.spell.AbstractSpellPart;
import com.hollingsworth.arsnouveau.api.spell.SpellContext;
import com.hollingsworth.arsnouveau.api.spell.SpellResolver;
import com.hollingsworth.arsnouveau.api.util.ManaUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.event.world.NoteBlockEvent;
import wayoftime.bloodmagic.core.data.SoulNetwork;
import wayoftime.bloodmagic.core.data.SoulTicket;
import wayoftime.bloodmagic.util.helper.NetworkHelper;

import java.util.List;

import static com.minttea.tomeofblood.TomeOfBloodMod.LOGGER;

public class BloodSpellResolver extends SpellResolver {
    public BloodSpellResolver(AbstractCastMethod cast, List<AbstractSpellPart> spell_recipe, SpellContext context) {
        super(cast, spell_recipe, context);
    }
    public BloodSpellResolver(SpellContext spellContext){
        this(spellContext.getSpell().recipe, spellContext);
    }
    public BloodSpellResolver(List<AbstractSpellPart> currentRecipe, SpellContext context) {
        super(currentRecipe, context);
    }

    @Override
    public void expendMana(LivingEntity entity)
    {
        if(entity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) entity;
            int totalCost = ManaUtil.getCastingCost(spell_recipe, player) * 10;
            SoulNetwork soulNetwork = NetworkHelper.getSoulNetwork(player.getUniqueID());
            //LOGGER.debug("Got soulnetwork for " + soulNetwork.getPlayer().getDisplayName().getString());
            SoulTicket ticket = new SoulTicket(new StringTextComponent("Spell cast"), totalCost);
            soulNetwork.syphonAndDamage(player, ticket);
        }
    }
}
