package com.minttea.tomeofblood.setup;

import com.minttea.tomeofblood.common.livingarmor.ManaUpgrade;
import net.minecraft.util.ResourceLocation;
import wayoftime.bloodmagic.common.registration.impl.LivingUpgradeDeferredRegister;
import wayoftime.bloodmagic.common.registration.impl.LivingUpgradeRegistryObject;
import wayoftime.bloodmagic.core.LivingArmorRegistrar;
import wayoftime.bloodmagic.core.living.LivingUpgrade;

public class LivingUpgradeRegistry {

    public static LivingUpgrade MANA_UPGRADE =  new LivingUpgrade(new ResourceLocation("tomeofblood", "mana_bonus"), levels ->
    {
        levels.add(new LivingUpgrade.Level(30, 4));
        levels.add(new LivingUpgrade.Level(120, 7));
        levels.add(new LivingUpgrade.Level(480, 8));
        levels.add(new LivingUpgrade.Level(960, 12));
    }
    );

    public static final void register()
    {
        LivingArmorRegistrar.registerUpgrade(MANA_UPGRADE);


    }
}
