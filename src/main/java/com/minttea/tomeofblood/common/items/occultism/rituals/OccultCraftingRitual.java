package com.minttea.tomeofblood.common.items.occultism.rituals;

import com.github.klikli_dev.occultism.common.ritual.Ritual;
import com.github.klikli_dev.occultism.common.ritual.pentacle.Pentacle;
import com.github.klikli_dev.occultism.common.tile.GoldenSacrificialBowlTileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.items.ItemHandlerHelper;

public class OccultCraftingRitual extends Ritual {

    private Item result;

    public OccultCraftingRitual(Pentacle pentacle, Ingredient i, String recipe, Item result) {
        super(pentacle,i,recipe,5);
        this.result=result;
    }

    @Override
    public void finish(World world, BlockPos goldenBowlPosition, GoldenSacrificialBowlTileEntity tileEntity,
                       PlayerEntity castingPlayer, ItemStack activationItem) {
        super.finish(world, goldenBowlPosition, tileEntity, castingPlayer, activationItem);
        //activationItem.shrink(1); //remove activation item.
        ItemStack stack = activationItem;
        activationItem.shrink(1);
        ((ServerWorld) world).spawnParticle(ParticleTypes.LARGE_SMOKE, goldenBowlPosition.getX() + 0.5,
                goldenBowlPosition.getY() + 0.5, goldenBowlPosition.getZ() + 0.5, 1, 0, 0, 0, 0);


        ItemStack result = new ItemStack(this.result);
        result.setTag(stack.getTag());
        ItemHandlerHelper.giveItemToPlayer(castingPlayer, result);
    }
}
