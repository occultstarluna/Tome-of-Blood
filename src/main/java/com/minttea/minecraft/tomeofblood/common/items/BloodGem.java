package com.minttea.minecraft.tomeofblood.common.items;

import com.hollingsworth.arsnouveau.ArsNouveau;
import com.hollingsworth.arsnouveau.api.item.IScribeable;
import com.hollingsworth.arsnouveau.api.spell.*;
import com.hollingsworth.arsnouveau.api.util.MathUtil;
import com.hollingsworth.arsnouveau.api.util.SpellRecipeUtil;
import com.hollingsworth.arsnouveau.common.block.tile.IntangibleAirTile;
import com.hollingsworth.arsnouveau.common.block.tile.PhantomBlockTile;
import com.hollingsworth.arsnouveau.common.block.tile.ScribesTile;
import com.hollingsworth.arsnouveau.common.items.SpellBook;
import com.hollingsworth.arsnouveau.common.items.SpellParchment;
import com.hollingsworth.arsnouveau.common.util.PortUtil;
import com.minttea.minecraft.tomeofblood.TomeOfBloodMod;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import wayoftime.bloodmagic.common.item.IBindable;

import javax.annotation.Nullable;
import java.util.List;

public class BloodGem extends Item implements IScribeable, IBindable {
    public BloodGem() {
        super(new Item.Properties().maxStackSize(64).group(ArsNouveau.itemGroup));
    }

    @Override
    public boolean onScribe(World world, BlockPos blockPos, PlayerEntity player, Hand hand, ItemStack itemStack) {
     ItemStack held = player.getHeldItem(hand);

        if(!(held.getItem() instanceof SpellParchment || held.getItem() instanceof BloodScroll || held.getItem() instanceof SpellBook || held.getTag()==null))
            return false;

        Spell spell = new Spell();
        List<AbstractSpellPart> parts = null;
        if(held.getItem() instanceof SpellParchment) {
             parts = SpellParchment.getSpellRecipe(held);
        } else if(held.getItem() instanceof SpellBook) {
            parts = ((SpellBook) held.getItem()).getCurrentRecipe(held).recipe;
        } else if(held.getItem() instanceof BloodScroll) {
            parts = ((BloodScroll) held.getItem()).getCurrentRecipe(held);
        }
        if(parts != null){
            spell = new Spell(parts);
            String spellTag = held.getTag().getString("spell");
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
        ItemStack stack = playerIn.getHeldItem(handIn);

        if( worldIn.isRemote)
            return super.onItemRightClick(worldIn, playerIn, handIn);

        RayTraceResult result = playerIn.pick(5, 0, false);
        if(result instanceof BlockRayTraceResult && worldIn.getTileEntity(((BlockRayTraceResult) result).getPos()) instanceof ScribesTile){
            ScribesTile tile = (ScribesTile) worldIn.getTileEntity(((BlockRayTraceResult) result).getPos());

            return new ActionResult<>(ActionResultType.SUCCESS, stack);}
        if(result instanceof BlockRayTraceResult && !playerIn.isSneaking()){
            if(worldIn.getTileEntity(((BlockRayTraceResult) result).getPos()) != null &&
                    !(worldIn.getTileEntity(((BlockRayTraceResult) result).getPos()) instanceof IntangibleAirTile
                            ||(worldIn.getTileEntity(((BlockRayTraceResult) result).getPos()) instanceof PhantomBlockTile))) {

                return new ActionResult<>(ActionResultType.SUCCESS, stack);
            }


        }
        BloodSpellResolver resolver = new BloodSpellResolver(new SpellContext(getSpell(stack), playerIn));
        EntityRayTraceResult entityRes = MathUtil.getLookedAtEntity(playerIn, 25);

        if(resolver.canCast(playerIn)) {
            if (entityRes != null && entityRes.getEntity() instanceof LivingEntity) {
                resolver.onCastOnEntity(stack, playerIn, (LivingEntity) entityRes.getEntity(), handIn);
                stack.shrink(1);
                return new ActionResult<>(ActionResultType.CONSUME, stack);
            }

            if (result instanceof BlockRayTraceResult) {
                ItemUseContext context = new ItemUseContext(playerIn, handIn, (BlockRayTraceResult) result);
                stack.shrink(1);
                resolver.onCastOnBlock(context);
                return new ActionResult<>(ActionResultType.CONSUME, stack);
            }

            resolver.onCast(stack, playerIn, worldIn);
            stack.shrink(1);
            return new ActionResult<>(ActionResultType.CONSUME, stack);
        }
        return new ActionResult<>(ActionResultType.CONSUME, stack);
    }

    @Override
    public void inventoryTick(ItemStack stack, World p_77663_2_, Entity p_77663_3_, int p_77663_4_, boolean p_77663_5_) {
        if(!stack.hasTag())
            stack.setTag(new CompoundNBT());
    }

    private boolean setSpell(ItemStack itemStack, String recipe) {
        itemStack.getTag().putString("spell",recipe);
        return true;
    }
    private Spell getSpell(ItemStack stack){
        assert stack.getTag() != null;
        return Spell.deserialize(stack.getTag().getString("spell"));
    }
    private boolean addToSpell(ItemStack itemStack, String recipe) {
        String spellTag = itemStack.getTag().getString("spell");
        spellTag+= recipe;
        itemStack.getTag().putString("spell",spellTag);
        return true;
    }
    public ISpellCaster getSpellCaster(ItemStack stack){
        return SpellCaster.deserialize(stack);
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
}
