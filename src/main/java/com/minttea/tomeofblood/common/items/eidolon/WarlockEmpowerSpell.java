package com.minttea.tomeofblood.common.items.eidolon;

import com.hollingsworth.arsnouveau.setup.ItemsRegistry;
import com.minttea.tomeofblood.common.capabilities.IWarlockPower;
import com.minttea.tomeofblood.common.capabilities.WarlockPowerCapability;
//import com.minttea.tomeofblood.setup.Registries.Registry;
import com.minttea.tomeofblood.setup.Registries.ItemRegistry;
import elucent.eidolon.capability.ReputationProvider;
import elucent.eidolon.deity.Deities;
import elucent.eidolon.ritual.Ritual;
import elucent.eidolon.spell.Sign;
import elucent.eidolon.spell.StaticSpell;
import elucent.eidolon.tile.EffigyTileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.Util;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

import java.util.Comparator;
import java.util.List;

public class WarlockEmpowerSpell extends StaticSpell {

    public WarlockEmpowerSpell(ResourceLocation name, Sign... signs) {
        super(name, signs);

//        MinecraftForge.EVENT_BUS.addListener(DarkTouchSpell::onHurt);
//        DistExecutor.unsafeCallWhenOn(Dist.CLIENT, () -> () -> {
//            MinecraftForge.EVENT_BUS.addListener(DarkTouchSpell::tooltip);
//            return new Object();
//        });
    }
    @Override
    public boolean canCast(World world, BlockPos blockPos, PlayerEntity player) {
        //player.sendMessage(new StringTextComponent("Checking if you can cast it..."), Util.DUMMY_UUID);
        if (!world.getCapability(ReputationProvider.CAPABILITY).isPresent())
            return false;
        if (world.getCapability(ReputationProvider.CAPABILITY).resolve().get().getReputation(player, Deities.DARK_DEITY.getId()) < 1.0)
            return false;

        RayTraceResult ray = world.rayTraceBlocks(new RayTraceContext(player.getEyePosition(0), player.getEyePosition(0).add(player.getLookVec().scale(4)), RayTraceContext.BlockMode.OUTLINE, RayTraceContext.FluidMode.NONE, player));
        Vector3d v = ray.getType() == RayTraceResult.Type.BLOCK ? ray.getHitVec() : player.getEyePosition(0).add(player.getLookVec().scale(4));
        List<EffigyTileEntity> effigies = Ritual.getTilesWithinAABB(EffigyTileEntity.class, world, new AxisAlignedBB(blockPos.add(-4, -4, -4), blockPos.add(5, 5, 5)));
        if(effigies.size() == 0) return false;
        EffigyTileEntity effigy = effigies.stream().min(Comparator.comparingDouble((e) -> e.getPos().distanceSq(blockPos))).get();
        return effigy.ready();
    }

    private boolean canEmpower(ItemStack stack) {

        return stack.getItem() == ItemsRegistry.noviceSpellBook.getItem();
    }
    private ItemStack empowerResult(ItemStack stack)
    {
        ItemStack result = stack;
        if(stack.getItem() == ItemsRegistry.noviceSpellBook.getItem())
        {
            CompoundNBT nbt = stack.getTag();
             result = new ItemStack(ItemRegistry.warlockTome1);
            result.setTag(nbt);
        }
            return result;
    }

    @Override
    public void cast(World world, BlockPos blockPos, PlayerEntity player) {
        //player.sendMessage(new StringTextComponent("Casting Warlock Empower"), Util.DUMMY_UUID);

            if(!world.isRemote)
            {

                IWarlockPower power = WarlockPowerCapability.getPower(player).orElse(null);

                if(power == null)
                    return;
                //player.sendMessage(new StringTextComponent("Refreshed power!"), Util.DUMMY_UUID);
                power.refreshPower();

            } else {
                world.playSound(player, player.getPosition(), SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.NEUTRAL, 1.0F, 0.6F + world.rand.nextFloat() * 0.2F);
            }


    }
}
