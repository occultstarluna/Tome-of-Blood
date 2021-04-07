package com.minttea.tomeofblood.common.items.bloodmagic;

import com.hollingsworth.arsnouveau.ArsNouveau;
import com.hollingsworth.arsnouveau.api.item.IScribeable;
import com.hollingsworth.arsnouveau.api.spell.*;
import com.hollingsworth.arsnouveau.api.util.MathUtil;
import com.hollingsworth.arsnouveau.common.block.tile.IntangibleAirTile;
import com.hollingsworth.arsnouveau.common.block.tile.PhantomBlockTile;
import com.hollingsworth.arsnouveau.common.block.tile.ScribesTile;
import com.hollingsworth.arsnouveau.common.items.SpellBook;
import com.hollingsworth.arsnouveau.common.items.SpellParchment;
import com.hollingsworth.arsnouveau.common.util.PortUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import wayoftime.bloodmagic.common.item.IBindable;

import java.util.List;

public class BloodGem extends Item implements IScribeable, IBindable {
    public BloodGem() {
        super(new Item.Properties().maxStackSize(1).group(ArsNouveau.itemGroup));
    }

    @Override
    public boolean onScribe(World world, BlockPos blockPos, PlayerEntity player, Hand hand, ItemStack itemStack) {
     ItemStack held = player.getHeldItem(hand);
        ISpellCaster caster = getSpellCaster(itemStack);

        if(caster == null)
            return false;
        if(!(held.getItem() instanceof SpellParchment || held.getItem() instanceof SpellBook || held.getTag()==null))
            return false;

        Spell spell = new Spell();
        List<AbstractSpellPart> parts = null;
        if(held.getItem() instanceof SpellParchment) {
             parts = SpellParchment.getSpellRecipe(held);
        } else if(held.getItem() instanceof SpellBook) {
            parts = ((SpellBook) held.getItem()).getCurrentRecipe(held).recipe;
        }
        if(parts != null){
            spell = new Spell(parts);
            if(caster.getSpell().getSpellSize() > 0) {
                if(addToSpell(caster,player,hand,itemStack,spell))
                {
                    sendSetMessage(player);
                    return true;
                }
            } else {
                if (setSpell(caster, player, hand, itemStack, spell)) {
                    sendSetMessage(player);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack stack = playerIn.getHeldItem(handIn);
        ISpellCaster caster = getSpellCaster(stack);

        if(caster == null || worldIn.isRemote)
            return super.onItemRightClick(worldIn, playerIn, handIn);

        RayTraceResult result = playerIn.pick(5, 0, false);
        if(result instanceof BlockRayTraceResult && worldIn.getTileEntity(((BlockRayTraceResult) result).getPos()) instanceof ScribesTile)
            return new ActionResult<>(ActionResultType.SUCCESS, stack);
        if(result instanceof BlockRayTraceResult && !playerIn.isSneaking()){
            if(worldIn.getTileEntity(((BlockRayTraceResult) result).getPos()) != null &&
                    !(worldIn.getTileEntity(((BlockRayTraceResult) result).getPos()) instanceof IntangibleAirTile
                            ||(worldIn.getTileEntity(((BlockRayTraceResult) result).getPos()) instanceof PhantomBlockTile))) {
                return new ActionResult<>(ActionResultType.SUCCESS, stack);
            }
            stack = ItemStack.EMPTY;

        }

        if(caster.getSpell() == null) {
            playerIn.sendMessage(new StringTextComponent("Invalid Spell."), Util.DUMMY_UUID);
            return new ActionResult<>(ActionResultType.CONSUME, stack);
        }
        BloodSpellResolver resolver = new BloodSpellResolver(caster.getSpell().recipe, new SpellContext(caster.getSpell(), playerIn));
        EntityRayTraceResult entityRes = MathUtil.getLookedAtEntity(playerIn, 25);

        if(entityRes != null && entityRes.getEntity() instanceof LivingEntity){
            resolver.onCastOnEntity(stack, playerIn, (LivingEntity) entityRes.getEntity(), handIn);
            return new ActionResult<>(ActionResultType.CONSUME, stack);
        }

        if(result instanceof BlockRayTraceResult){
            ItemUseContext context = new ItemUseContext(playerIn, handIn, (BlockRayTraceResult) result);
            resolver.onCastOnBlock(context);
            return new ActionResult<>(ActionResultType.CONSUME, stack);
        }

        resolver.onCast(stack,playerIn,worldIn);
        return new ActionResult<>(ActionResultType.CONSUME, stack);
    }

    private boolean setSpell(ISpellCaster caster, PlayerEntity player, Hand hand, ItemStack itemStack, Spell spell) {
        caster.setSpell(spell);
        return true;
    }
    private boolean addToSpell(ISpellCaster caster, PlayerEntity player, Hand hand, ItemStack itemStack, Spell spell) {
        Spell newSpell = caster.getSpell();
        newSpell.recipe.addAll(spell.recipe);

        caster.setSpell(newSpell);
        return true;
    }
    public ISpellCaster getSpellCaster(ItemStack stack){
        return SpellCaster.deserialize(stack);
    }
    public void sendSetMessage(PlayerEntity player){
        PortUtil.sendMessage(player, new StringTextComponent("Set spell."));
    }
}
