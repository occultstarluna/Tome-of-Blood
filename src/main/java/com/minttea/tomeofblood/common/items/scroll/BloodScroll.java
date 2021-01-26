package com.minttea.tomeofblood.common.items.scroll;

import com.hollingsworth.arsnouveau.ArsNouveau;
import com.hollingsworth.arsnouveau.api.item.IScribeable;
import com.hollingsworth.arsnouveau.api.spell.AbstractSpellPart;
import com.hollingsworth.arsnouveau.api.util.SpellRecipeUtil;
import com.hollingsworth.arsnouveau.common.block.tile.ScribesTile;
import com.minttea.tomeofblood.common.client.gui.ScrollContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import java.util.List;

public class BloodScroll extends Item {


    public BloodScroll() {
        super(new Item.Properties().maxStackSize(1).group(ArsNouveau.itemGroup));
    }

    public static ScrollInventory getInventory(ItemStack stack) {

        return new ScrollInventory(stack, 27);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity player, Hand handIn)
    {

        if(!worldIn.isRemote){
            ItemStack stack = player.getHeldItem(handIn);

            RayTraceResult result = player.pick(5, 0 , false);
            if(player.isSneaking())
            {
                if(result instanceof BlockRayTraceResult && worldIn.getTileEntity(((BlockRayTraceResult) result).getPos()) instanceof ScribesTile)
                    return new ActionResult<>(ActionResultType.SUCCESS, stack);
            }
            else
            {
                INamedContainerProvider container = new SimpleNamedContainerProvider((w, p, pl) -> new ScrollContainer(w, p, stack), stack.getDisplayName());
                NetworkHooks.openGui((ServerPlayerEntity) player, container, buf -> buf.writeBoolean(handIn == Hand.MAIN_HAND));
            }
        }
        return ActionResult.resultSuccess(player.getHeldItem(handIn));
    }

    public static void setSpell(ItemStack stack, String spellRecipe)
    {
        stack.getTag().putString("spell", spellRecipe);
    }
    public static List<AbstractSpellPart> getSpellRecipe(ItemStack stack)
    {
        if(!stack.hasTag())
            return null;
        return SpellRecipeUtil.getSpellsFromString(stack.getTag().getString("spell"));
    }

}
