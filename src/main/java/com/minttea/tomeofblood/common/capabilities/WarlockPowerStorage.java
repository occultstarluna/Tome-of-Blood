package com.minttea.tomeofblood.common.capabilities;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public class WarlockPowerStorage implements Capability.IStorage<IWarlockPower> {

    @Nullable
    @Override
    public INBT writeNBT(Capability<IWarlockPower> capability, IWarlockPower instance, Direction side) {
        CompoundNBT tag = new CompoundNBT();
        tag.putDouble("current", instance.getCurrentPower());
        tag.putInt("max", instance.getMaxPower());
        return tag;
    }

    @Override
    public void readNBT(Capability<IWarlockPower> capability, IWarlockPower instance, Direction side, INBT nbt) {
        if(!(nbt instanceof CompoundNBT))
            return;
        CompoundNBT tag = (CompoundNBT) nbt;
        instance.setMaxPower(tag.getInt("max"));
        instance.setPower(tag.getInt("current"));

    }
}
