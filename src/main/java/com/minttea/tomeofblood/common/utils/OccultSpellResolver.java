package com.minttea.tomeofblood.common.utils;

import com.hollingsworth.arsnouveau.api.spell.AbstractCastMethod;
import com.hollingsworth.arsnouveau.api.spell.AbstractSpellPart;
import com.hollingsworth.arsnouveau.api.spell.SpellContext;
import com.hollingsworth.arsnouveau.api.spell.SpellResolver;
import com.hollingsworth.arsnouveau.common.util.PortUtil;
import com.minttea.tomeofblood.common.items.occultism.BookOfCasting;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Util;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import wayoftime.bloodmagic.core.data.SoulNetwork;
import wayoftime.bloodmagic.core.data.SoulTicket;
import wayoftime.bloodmagic.util.helper.NetworkHelper;

import java.util.ArrayList;
import java.util.List;

public class OccultSpellResolver extends SpellResolver {
    public OccultSpellResolver(AbstractCastMethod cast, List<AbstractSpellPart> spell_recipe, SpellContext context) {
        super(cast, spell_recipe, context);
    }
    public OccultSpellResolver(SpellContext spellContext){
        this(spellContext.getSpell().recipe, spellContext);
    }

    public OccultSpellResolver(List<AbstractSpellPart> currentRecipe, SpellContext context) {
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

            int castingCapacity = getCapacity(player);
            int totalCost = getCastingCost(this.spell,player);


            if(castingCapacity < totalCost)
            {
                entity.sendMessage(new TranslationTextComponent("toomanytomes.alert.lack_lp"), Util.DUMMY_UUID);
                return false;
            }

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

    public ArrayList<ItemStack> getCastingBooks(PlayerEntity player)
    {
        ArrayList<ItemStack> castingBooks = new ArrayList<>();
        PlayerInventory inventory = player.inventory;
        ItemStack offhand = player.inventory.getStackInSlot(45);
        if(offhand.getItem() instanceof BookOfCasting)
            castingBooks.add(offhand);
        ArrayList<ItemStack> hotbar = new ArrayList<>(9);
        for(int i = 36; i< 44; i++ )
        {
            ItemStack buffer = inventory.getStackInSlot(i);
            if(buffer.getItem()  instanceof BookOfCasting)
                castingBooks.add(buffer);

        }
        for(int i = 9; i<35; i++)
        {
            ItemStack buffer = inventory.getStackInSlot(i);
            if(buffer.getItem()  instanceof BookOfCasting)
                castingBooks.add(buffer);
        }
        return castingBooks;
    }

    public int getCapacity(PlayerEntity player)
    {

        ArrayList<ItemStack> bookList = getCastingBooks(player);
        int capacity = 0;
        for(ItemStack stack : bookList)
        {
            capacity += stack.getDamage();

        }

        return capacity;

    }

    public void expendCapacity(PlayerEntity player)
    {

        ArrayList<ItemStack> bookList = getCastingBooks(player);
        int workingCost = getCastingCost(spell, player);
        for(ItemStack stack : bookList)
        {
            int curDam = stack.getDamage();
            if(workingCost > curDam)
            {
                workingCost -= curDam;
                stack.shrink(1);
            }

            workingCost = stack.getDamage();

        }

        return capacity;

    }
}
