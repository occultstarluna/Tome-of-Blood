package com.minttea.tomeofblood.common.livingarmor;

import net.minecraft.util.ResourceLocation;
import wayoftime.bloodmagic.core.living.LivingUpgrade;

import java.util.List;
import java.util.function.Consumer;

public class ManaUpgrade extends LivingUpgrade {
    public ManaUpgrade(ResourceLocation key, Consumer<List<Level>> experienceMapper) {
        super(key, experienceMapper);

    }
}
