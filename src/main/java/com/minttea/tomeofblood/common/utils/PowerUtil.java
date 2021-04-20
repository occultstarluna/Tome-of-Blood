package com.minttea.tomeofblood.common.utils;

import elucent.eidolon.capability.ReputationProvider;
import elucent.eidolon.deity.Deities;
import elucent.eidolon.item.WarlockRobesItem;
import net.minecraft.entity.player.PlayerEntity;

import java.util.concurrent.atomic.AtomicInteger;

public class PowerUtil {

    public static int getMaxPower(PlayerEntity p)
    {
        AtomicInteger max = new AtomicInteger(1024);
        p.getEquipmentAndArmor().forEach(i->{
            if(i.getItem() instanceof WarlockRobesItem)
            {
                max.addAndGet(1000);
            }
        });
        max.addAndGet((int) (p.world.getCapability(ReputationProvider.CAPABILITY).resolve().get().getReputation(p, Deities.DARK_DEITY.getId()) * 2000));



        return max.get();
    }

}
