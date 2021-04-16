package com.minttea.tomeofblood.common.events;

import com.minttea.tomeofblood.TomeOfBloodMod;
import com.minttea.tomeofblood.common.capabilities.WarlockPowerCapability;
import com.minttea.tomeofblood.common.network.Networking;
import com.minttea.tomeofblood.common.network.PacketUpdatePower;
import com.minttea.tomeofblood.common.utils.PowerUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.PacketDistributor;

@Mod.EventBusSubscriber(modid = TomeOfBloodMod.MODID)
public class EventHandler {

    @SubscribeEvent
    public static void playerRespawn(PlayerEvent.PlayerRespawnEvent e) {
        syncPlayerEvent(e.getPlayer());
    }

    @SubscribeEvent
    public static void playerClone(PlayerEvent.Clone e) {
        if(e.getOriginal().world.isRemote)
            return;

        WarlockPowerCapability.getPower((LivingEntity) e.getEntity()).ifPresent(newPower -> {
            WarlockPowerCapability.getPower(e.getOriginal()).ifPresent(origPower -> {
                newPower.setMaxPower(origPower.getMaxPower());
                Networking.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity)  e.getEntity()), new PacketUpdatePower(newPower.getCurrentPower(), newPower.getMaxPower()));
            });
        });
    }

    @SubscribeEvent
    public static void playerLoggedIn(PlayerEvent.StartTracking e) {
        syncPlayerEvent(e.getPlayer());
    }

    @SubscribeEvent
    public static void playerChangeDimension(PlayerEvent.PlayerChangedDimensionEvent e) {
        syncPlayerEvent(e.getPlayer());
    }

    public static void syncPlayerEvent(PlayerEntity player)
    {
        if(player instanceof ServerPlayerEntity){
            WarlockPowerCapability.getPower(player).ifPresent(power->
            {
                power.setMaxPower(PowerUtil.getMaxPower(player));
                Networking.INSTANCE.send(PacketDistributor.PLAYER.with(()->(ServerPlayerEntity) player), new PacketUpdatePower(power.getCurrentPower(), power.getMaxPower()));
            });
        }
    }
}
