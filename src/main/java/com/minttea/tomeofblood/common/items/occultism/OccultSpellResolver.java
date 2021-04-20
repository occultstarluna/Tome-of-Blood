package com.minttea.tomeofblood.common.items.occultism;

import com.hollingsworth.arsnouveau.api.spell.AbstractCastMethod;
import com.hollingsworth.arsnouveau.api.spell.AbstractSpellPart;
import com.hollingsworth.arsnouveau.api.spell.SpellContext;
import com.hollingsworth.arsnouveau.api.spell.SpellResolver;
import com.hollingsworth.arsnouveau.common.util.PortUtil;
import com.minttea.tomeofblood.common.items.occultism.BookOfCasting;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Util;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import wayoftime.bloodmagic.core.data.SoulNetwork;
import wayoftime.bloodmagic.core.data.SoulTicket;
import wayoftime.bloodmagic.util.helper.NetworkHelper;

import javax.sound.sampled.Port;
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
                entity.sendMessage(new TranslationTextComponent("tomeofblood.alert.no_spirits"), Util.DUMMY_UUID);
                return false;
            } else {
                return true;
            }

        }

        return false;
    }


    @Override
    public void expendMana(LivingEntity entity)
    {
        if(entity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) entity;
            int totalCost = getCastingCost(this.spell,player);
            expendCapacity(player, totalCost);
        }

    }

    public ArrayList<ItemStack> getCastingBooks(PlayerEntity player)
    {
        ///player.sendMessage(new StringTextComponent("Getting all the books you have"), Util.DUMMY_UUID);
        ArrayList<ItemStack> castingBooks = new ArrayList<>();
        PlayerInventory inventory = player.inventory;
        ItemStack offhand = player.getHeldItemOffhand();
        if(offhand.getItem() instanceof BookOfCasting)
        {
            castingBooks.add(offhand);
        //    player.sendMessage(new StringTextComponent("Found book in offhand!"), Util.DUMMY_UUID);
        }


        int i = 0;
        for (ItemStack buffer : inventory.mainInventory
             ) {
            i = inventory.getSlotFor(buffer);
            if(buffer.getItem()  instanceof BookOfCasting) {
                castingBooks.add(buffer);
            //    player.sendMessage(new StringTextComponent("Found book in slot + " + i +"!"), Util.DUMMY_UUID);
            }
        }

        return castingBooks;
    }

    public int getCapacity(PlayerEntity player)
    {

        ArrayList<ItemStack> bookList = getCastingBooks(player);
        int capacity = 0;
        for(ItemStack stack : bookList)
        {
            capacity += stack.getMaxDamage() - stack.getDamage();

        }
        //player.sendMessage(new StringTextComponent("Capacity is " + capacity), Util.DUMMY_UUID);
        return capacity;

    }

    public void expendCapacity(PlayerEntity player, int totalCost)
    {

        ArrayList<ItemStack> bookList = getCastingBooks(player);
        int workingCost = totalCost;
        for(ItemStack stack : bookList)
        {
            int curDam = stack.getMaxDamage() - stack.getDamage();
            if(workingCost > curDam)
            {
            //    player.sendMessage(new StringTextComponent("Working cost is" + workingCost + " and capacity is " + curDam + ", removing book."), Util.DUMMY_UUID);
                workingCost -= curDam;
                stack.shrink(1);
            } else {
            //    player.sendMessage(new StringTextComponent("Working cost is" + workingCost + " and capacity is " + curDam + ", damaging book."), Util.DUMMY_UUID);
                stack.damageItem(workingCost, player, (playerIn) -> playerIn.sendBreakAnimation(EquipmentSlotType.MAINHAND));
                stack.attemptDamageItem(workingCost, player.world.rand, (ServerPlayerEntity) player);
                break;
            }
        }
    }
}
