package com.minttea.tomeofblood;

import com.minttea.tomeofblood.common.capabilities.WarlockPowerCapability;
import com.minttea.tomeofblood.common.events.LivingArmorManaBonus;
import com.minttea.tomeofblood.common.network.Networking;
import com.minttea.tomeofblood.setup.*;
//import com.minttea.tomeofblood.setup.Registries.Registry;
import com.minttea.tomeofblood.setup.Registries.ItemRegistry;
import com.minttea.tomeofblood.setup.Registries.RecipeRegistry;
import com.minttea.tomeofblood.setup.Registries.RitualRegistry;
import com.minttea.tomeofblood.setup.Registries.SpellRegistry;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(TomeOfBloodMod.MODID)
@Mod.EventBusSubscriber(modid = TomeOfBloodMod.MODID)
public class TomeOfBloodMod
{




    /**
     * TODO: Implement Botanic Tome
     * TODO: Add Astral Sorcery into the Dev Environment and work on the Astral Tome
     * TODO: Add Warlock Tome Documentation <- Reliant on Eidolon, unless I'm really dumb
     * TODO: Update Blood Tome Recipes preserving NBT data
     * TODO: Don't forget to add in a Tome of Blood carry over for those who update
     * Option Registering of items in case mods aren't present.<- Impossible, sadly
     */
    public static final String MODID = "tomeofblood";
    // Directly reference a log4j logger.
    public static final Logger LOGGER = LogManager.getLogger();

    public static IProxy proxy = DistExecutor.runForDist(() -> () -> new ClientProxy(), ()-> ()-> new ServerProxy());

    public static ItemGroup itemGroup = new ItemGroup(MODID) {
        @Override
        public ItemStack createIcon() {
            return ItemRegistry.bloodTome3.getDefaultInstance();
        }
    };

    public TomeOfBloodMod() {
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(LivingArmorManaBonus.class);

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
        LivingUpgradeRegistry.register();

    }

    public void setup(final FMLCommonSetupEvent event)
    {
        SpellRegistry.registerSpells();
        WarlockPowerCapability.register();
        //RitualRegistry.registerRituals();
        Networking.register();
        RecipeRegistry.registerRecipes();
    }
    public void clientSetup(final FMLClientSetupEvent event){
        proxy.init();
       //FMLJavaModLoadingContext.get().getModEventBus().addListener(ClientHandler::init);
    }



    // You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
    // Event bus for receiving Registry Events)
}
