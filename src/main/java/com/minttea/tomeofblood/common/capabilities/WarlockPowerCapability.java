package com.minttea.tomeofblood.common.capabilities;


import com.minttea.tomeofblood.TomeOfBloodMod;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.system.CallbackI;

import javax.annotation.Nullable;

import static com.hollingsworth.arsnouveau.setup.InjectionUtil.Null;

public class WarlockPowerCapability {


    @CapabilityInject(IWarlockPower.class)
    public static final Capability<IWarlockPower> WARLOCK_POWER = Null();

    public static final Direction DEFAULT_FACING = null;

    public static final ResourceLocation ID = new ResourceLocation(TomeOfBloodMod.MODID, "warlock_power");

    public static void register()
    {
        CapabilityManager.INSTANCE.register(IWarlockPower.class, new WarlockPowerStorage(), () -> new WarlockPower(null));
    }


    public static LazyOptional<IWarlockPower> getPower(final LivingEntity entity){
        return entity.getCapability(WARLOCK_POWER, DEFAULT_FACING);
    }

    public static ICapabilityProvider createProvider(final IWarlockPower power)
    {
        return new SerializableCapabilityProvider<>(WARLOCK_POWER, DEFAULT_FACING, power);
    }


    @SuppressWarnings("unused")
    @Mod.EventBusSubscriber(modid = TomeOfBloodMod.MODID)
    private static class EventHandler {

        @SubscribeEvent
        public static void attachCapabilities(final AttachCapabilitiesEvent<Entity> event)
        {
            if(event.getObject() instanceof PlayerEntity)
            {
                final WarlockPower power = new WarlockPower((LivingEntity) event.getObject());
                event.addCapability(ID, createProvider(power));
            }
        }

    }

    @SubscribeEvent
    public static void playerClone(final PlayerEvent.Clone event)
    {
        getPower(event.getOriginal()).ifPresent(oldMaxPower -> {
            getPower(event.getPlayer()).ifPresent(newMaxPower ->
                    {
                        newMaxPower.setMaxPower(oldMaxPower.getMaxPower());
                        newMaxPower.setPower(oldMaxPower.getCurrentPower());
                    }
                    );
                }
                );
    }
}
