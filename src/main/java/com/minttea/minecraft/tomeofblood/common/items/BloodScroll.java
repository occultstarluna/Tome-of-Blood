package com.minttea.minecraft.tomeofblood.common.items;

import com.hollingsworth.arsnouveau.ArsNouveau;
import com.hollingsworth.arsnouveau.api.item.IScribeable;
import com.hollingsworth.arsnouveau.api.spell.AbstractSpellPart;
import com.hollingsworth.arsnouveau.api.spell.ISpellCaster;
import com.hollingsworth.arsnouveau.api.spell.Spell;
import com.hollingsworth.arsnouveau.api.spell.SpellCaster;
import com.hollingsworth.arsnouveau.api.util.SpellRecipeUtil;
import com.hollingsworth.arsnouveau.common.items.SpellBook;
import com.hollingsworth.arsnouveau.common.items.SpellParchment;
import com.hollingsworth.arsnouveau.common.util.PortUtil;
import com.sun.media.jfxmedia.logging.Logger;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;

import javax.annotation.Nullable;
import java.util.List;

public class BloodScroll extends Item implements IScribeable {




    public BloodScroll() {
        super(new Item.Properties().maxStackSize(1).group(ArsNouveau.itemGroup));
    }

    @Override
    public boolean onScribe(World world, BlockPos blockPos, PlayerEntity player, Hand hand, ItemStack itemStack) {
        ItemStack held = player.getHeldItem(hand);

        if(!(held.getItem() instanceof SpellParchment || held.getItem() instanceof SpellBook || held.getTag()==null))
            return false;

        Spell spell;
        List<AbstractSpellPart> parts = null;
        if(held.getItem() instanceof SpellParchment) {
            parts = SpellParchment.getSpellRecipe(held);
        } else if(held.getItem() instanceof SpellBook) {
            parts = ((SpellBook) held.getItem()).getCurrentRecipe(held).recipe;
        }
        if(parts != null){
            spell = new Spell(parts);
            String spellTag = SpellBook.getRecipeString(held.getTag(), SpellBook.getMode(held.getTag()));
            if(parts.size() +getSpell(itemStack).getSpellSize() > 30)
            {
                PortUtil.sendMessageCenterScreen(player, new StringTextComponent("Spell too complex."));
                return false;
            } else if (parts.size() == 0) {
                itemStack.getTag().putString("spell","");
            }else {
                if (getSpell(itemStack).getSpellSize() > 0) {
                    if(addToSpell(itemStack, spellTag)) {
                        PortUtil.sendMessageCenterScreen(player, new StringTextComponent("Added parts to spell: " + spell.getDisplayString()));
                        return true;
                    }
                } else {
                    if (setSpell(itemStack,spellTag)) {
                        PortUtil.sendMessageCenterScreen(player, new StringTextComponent("Set Spell: " + spell.getDisplayString()));
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {

        ItemStack scroll = playerIn.getHeldItem(handIn);
        if(handIn == Hand.MAIN_HAND) {

            ItemStack offHand = playerIn.getHeldItem(Hand.OFF_HAND);

            if(offHand.getItem() instanceof BloodGem)
            {
                ((BloodGem) offHand.getItem()).onScribe(worldIn,null,playerIn,handIn,offHand);
            }
        }
         return new ActionResult<>(ActionResultType.SUCCESS, scroll);
    }

    @Override
    public void inventoryTick(ItemStack stack, World p_77663_2_, Entity p_77663_3_, int p_77663_4_, boolean p_77663_5_) {
        if(!stack.hasTag())
            stack.setTag(new CompoundNBT());
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag p_77624_4_) {
        if(!stack.hasTag() || stack.getTag().getString("spell").equals(""))
            return;

        //List<AbstractSpellPart> spellsFromTagString = SpellRecipeUtil.getSpellsFromTagString();
        Spell spell = Spell.deserialize(stack.getTag().getString("spell"));
        //LOGGER.debug("fuckin, okay" + spell.getDisplayString());

        tooltip.add(new StringTextComponent(spell.getDisplayString()));
    }
    private Spell getSpell(ItemStack stack){
        assert stack.getTag() != null;
        return Spell.deserialize(stack.getTag().getString("spell"));
    }
    private boolean setSpell(ItemStack itemStack, String recipe) {
        itemStack.getTag().putString("spell",recipe);
        return true;
    }
    private boolean addToSpell(ItemStack itemStack, String recipe) {
        String spellTag = itemStack.getTag().getString("spell");
        spellTag+= recipe;
        itemStack.getTag().putString("spell",spellTag);
        return true;
    }

    public List<AbstractSpellPart> getCurrentRecipe(ItemStack stack) {
        if(!stack.hasTag())
            return null;
        return SpellRecipeUtil.getSpellsFromTagString(stack.getTag().getString("spell"));
    }
}
