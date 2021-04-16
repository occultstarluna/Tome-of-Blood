package com.minttea.tomeofblood.common.network;

import com.minttea.tomeofblood.TomeOfBloodMod;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class Networking {
    public static SimpleChannel INSTANCE;

    private static int ID = 0;
    public static int nextID() {return ID++;}

    public static void register(){
        INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation(TomeOfBloodMod.MODID, "network"), ()->"1.0", s->true, s->true);

        INSTANCE.registerMessage(nextID(),
        PacketUpdatePower.class,
        PacketUpdatePower::toBytes,
        PacketUpdatePower::new,
        PacketUpdatePower::handle
                );


    }


}
