package com.minttea.tomeofblood.common.items.occultism;

import com.github.klikli_dev.occultism.Occultism;
import com.github.klikli_dev.occultism.common.ritual.Ritual;
import com.github.klikli_dev.occultism.common.ritual.pentacle.Pentacle;
import com.github.klikli_dev.occultism.common.tile.GoldenSacrificialBowlTileEntity;
import com.github.klikli_dev.occultism.registry.OccultismItems;
import com.github.klikli_dev.occultism.registry.OccultismRituals;
import com.hollingsworth.arsnouveau.setup.ItemsRegistry;
import com.minttea.tomeofblood.setup.Registry;
import com.minttea.tomeofblood.util.NBTHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.items.ItemHandlerHelper;

public class CraftOccultTomeRitual extends Ritual {

    private Item result;

    public CraftOccultTomeRitual(Pentacle pentacle, Ingredient i, String recipe, Item result) {
        super(pentacle,i,recipe,60);
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
