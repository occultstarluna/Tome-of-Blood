package com.minttea.minecraft.tomeofblood;

import com.minttea.minecraft.tomeofblood.setup.ClientProxy;
import com.minttea.minecraft.tomeofblood.setup.IProxy;
import com.minttea.minecraft.tomeofblood.setup.LivingUpgradeRegistry;
import com.minttea.minecraft.tomeofblood.setup.Registries.SpellRegistry;
import com.minttea.minecraft.tomeofblood.setup.ServerProxy;
import com.minttea.minecraft.tomeofblood.common.events.LivingArmorManaBonus;


//import com.minttea.tomeofblood.setup.Registries.Registry;
import com.minttea.minecraft.tomeofblood.setup.Registries.RecipeRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.DistExecutor;
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




    public TomeOfBloodMod() {
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(LivingArmorManaBonus.class);

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
        LivingUpgradeRegistry.register();

        SpellRegistry.registerSpells();
    }

    public void setup(final FMLCommonSetupEvent event)
    {

        //RecipeRegistry.registerRecipes();
    }
    public void clientSetup(final FMLClientSetupEvent event){
        proxy.init();
       //FMLJavaModLoadingContext.get().getModEventBus().addListener(ClientHandler::init);
    }



    // You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
    // Event bus for receiving Registry Events)
}
