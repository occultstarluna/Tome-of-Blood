package com.minttea.tomeofblood.common.items.eidolon;

import com.hollingsworth.arsnouveau.api.spell.ISpellTier;
import com.hollingsworth.arsnouveau.setup.ItemsRegistry;
import com.minttea.tomeofblood.common.capabilities.IWarlockPower;
import com.minttea.tomeofblood.common.capabilities.WarlockPowerCapability;
import com.minttea.tomeofblood.setup.Registry;
import elucent.eidolon.capability.ReputationProvider;
import elucent.eidolon.deity.Deities;
import elucent.eidolon.network.MagicBurstEffectPacket;
import elucent.eidolon.network.Networking;
import elucent.eidolon.spell.Sign;
import elucent.eidolon.spell.Signs;
import elucent.eidolon.spell.StaticSpell;
import net.minecraft.entity.item.ItemEntity;
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

import javax.rmi.CORBA.Tie;
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
        player.sendMessage(new StringTextComponent("Checking if you can cast it..."), Util.DUMMY_UUID);
        if (!world.getCapability(ReputationProvider.CAPABILITY).isPresent())
            return false;
        /*if (world.getCapability(ReputationProvider.CAPABILITY).resolve().get().getReputation(player, Deities.DARK_DEITY.getId()) < 4.0)
            return false;
*/
        RayTraceResult ray = world.rayTraceBlocks(new RayTraceContext(player.getEyePosition(0), player.getEyePosition(0).add(player.getLookVec().scale(4)), RayTraceContext.BlockMode.OUTLINE, RayTraceContext.FluidMode.NONE, player));
        Vector3d v = ray.getType() == RayTraceResult.Type.BLOCK ? ray.getHitVec() : player.getEyePosition(0).add(player.getLookVec().scale(4));
        List<ItemEntity> items = world.getEntitiesWithinAABB(ItemEntity.class, new AxisAlignedBB(v.x - 1.5, v.y - 1.5, v.z - 1.5, v.x + 1.5, v.y + 1.5, v.z + 1.5));
        if (items.size() != 1)
            return false;
        ItemStack stack = items.get(0).getItem();
        return stack.getCount() == 1 && canEmpower(stack);
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
             result = new ItemStack(Registry.warlockTome1);
            result.setTag(nbt);
        }
            return result;
    }

    @Override
    public void cast(World world, BlockPos blockPos, PlayerEntity player) {
        player.sendMessage(new StringTextComponent("Casting Warlock Empower"), Util.DUMMY_UUID);
        RayTraceResult ray = world.rayTraceBlocks(new RayTraceContext(player.getEyePosition(0), player.getEyePosition(0).add(player.getLookVec().scale(4)), RayTraceContext.BlockMode.OUTLINE, RayTraceContext.FluidMode.NONE, player));
        Vector3d v = ray.getType() == RayTraceResult.Type.BLOCK ? ray.getHitVec() : player.getEyePosition(0).add(player.getLookVec().scale(4));
        List<ItemEntity> items = world.getEntitiesWithinAABB(ItemEntity.class, new AxisAlignedBB(v.x - 1.5, v.y - 1.5, v.z - 1.5, v.x + 1.5, v.y + 1.5, v.z + 1.5));

        if(items.size() == 1)
        {
            if(!world.isRemote)
            {
                /*
                ItemStack stack = items.get(0).getItem();
                if(canEmpower(stack))
                {
                    items.get(0).setItem(empowerResult(stack));
                    Vector3d p = items.get(0).getPositionVec();
                    items.get(0).setDefaultPickupDelay();
                    Networking.sendToTracking(world, items.get(0).getPosition(), new MagicBurstEffectPacket(p.x,p.y,p.z, Signs.WICKED_SIGN.getColor(),Signs.BLOOD_SIGN.getColor()));
                }*/


                IWarlockPower power = WarlockPowerCapability.getPower(player).orElse(null);

                if(power == null)
                    return;
                if(power.getMaxPower() < 10240)
                {
                    player.sendMessage(new StringTextComponent("Adjusting Max Power..."), Util.DUMMY_UUID);
                    power.setMaxPower(10240);
                }
                player.sendMessage(new StringTextComponent("Refreshed power!"), Util.DUMMY_UUID);
                power.refreshPower();

            } else {
                world.playSound(player, player.getPosition(), SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.NEUTRAL, 1.0F, 0.6F + world.rand.nextFloat() * 0.2F);
            }

        }
    }
}
