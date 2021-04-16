package com.minttea.tomeofblood.common.network;

import com.minttea.tomeofblood.TomeOfBloodMod;
import com.minttea.tomeofblood.common.capabilities.WarlockPowerCapability;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketUpdatePower {

    public int power;
    public int maxPower;

    public PacketUpdatePower(PacketBuffer buf)
    {
        power = buf.readInt();
        maxPower = buf.readInt();
    }

    public void toBytes(PacketBuffer buf){
        buf.writeInt(power);
        buf.writeInt(maxPower);
    }

    public PacketUpdatePower(int power, int maxPower)
    {
        this.power = power;
        this.maxPower = maxPower;
    }


    public void handle(Supplier<NetworkEvent.Context> ctx)
    {
        ctx.get().enqueueWork(()->{
            WarlockPowerCapability.getPower(TomeOfBloodMod.proxy.getPlayer()).ifPresent(power->
            {
                power.setPower(this.power);
                power.setMaxPower(this.maxPower);
            });
        });
    }
}
