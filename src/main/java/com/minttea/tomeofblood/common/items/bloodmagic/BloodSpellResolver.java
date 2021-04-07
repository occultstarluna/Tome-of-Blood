package com.minttea.tomeofblood.common.items.bloodmagic;

import com.hollingsworth.arsnouveau.api.spell.AbstractCastMethod;
import com.hollingsworth.arsnouveau.api.spell.AbstractSpellPart;
import com.hollingsworth.arsnouveau.api.spell.SpellContext;
import com.hollingsworth.arsnouveau.api.spell.SpellResolver;
import com.hollingsworth.arsnouveau.api.util.ManaUtil;
import com.hollingsworth.arsnouveau.common.util.PortUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.Util;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.event.world.NoteBlockEvent;
import wayoftime.bloodmagic.core.data.SoulNetwork;
import wayoftime.bloodmagic.core.data.SoulTicket;
import wayoftime.bloodmagic.util.helper.NetworkHelper;

import javax.sound.sampled.Port;
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
    public boolean canCast(LivingEntity entity){
        int numMethods = 0;
        if(spell == null || !spell.isValid() || castType == null) {
            if(!silent)
                entity.sendMessage(new StringTextComponent("Invalid Spell."), Util.DUMMY_UUID);
            return false;
        }
        for(AbstractSpellPart spellPart : spell.recipe){
            if(spellPart instanceof AbstractCastMethod)
                numMethods++;
        }
        if(numMethods > 1 && !silent) {
            PortUtil.sendMessage(entity,new TranslationTextComponent("ars_nouveau.alert.duplicate_method"));

            return false;
        }

        if(entity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) entity;
            int totalCost = getCastingCost(this.spell,player);
            SoulNetwork soulNetwork = NetworkHelper.getSoulNetwork(player.getUniqueID());
            //LOGGER.debug("Got soulnetwork for " + soulNetwork.getPlayer().getDisplayName().getString());
            int pool = soulNetwork.getCurrentEssence();
            if(pool < this.getCastingCost(spell,entity))
            {
                entity.sendMessage(new TranslationTextComponent("toomanytomes.alert.lack_lp"), Util.DUMMY_UUID);
                return false;
            }

        }

        return true;
    }


    @Override
    public void expendMana(LivingEntity entity)
    {
        if(entity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) entity;
            int totalCost = getCastingCost(this.spell,player);
            SoulNetwork soulNetwork = NetworkHelper.getSoulNetwork(player.getUniqueID());
            //LOGGER.debug("Got soulnetwork for " + soulNetwork.getPlayer().getDisplayName().getString());
            SoulTicket ticket = new SoulTicket(new StringTextComponent("Spell cast"), totalCost);
            soulNetwork.syphonAndDamage(player, ticket);
        }
    }
}
