package com.minttea.tomeofblood;

import com.minttea.tomeofblood.common.events.LivingArmorManaBonus;
import com.minttea.tomeofblood.setup.LivingUpgradeRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(TomeOfBloodMod.MODID)
public class TomeOfBloodMod
{




    /**
     * TODO: Robes of the Blood Mage
     * TODO: Consider Blood Gems and their implications
     * TODO: Consider the implications of Self-Sacrifice as a spell
     * TODO: Debate multi-tiered Tomes of Blood
     * TODO: Consider what mobs are possible
     */
    public static final String MODID = "tomeofblood";
    // Directly reference a log4j logger.
    public static final Logger LOGGER = LogManager.getLogger();

    public TomeOfBloodMod() {
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(LivingArmorManaBonus.class);
        LivingUpgradeRegistry.register();

    }


    // You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
    // Event bus for receiving Registry Events)
}
