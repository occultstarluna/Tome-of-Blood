package com.minttea.tomeofblood.common.capabilities;

import net.minecraft.entity.LivingEntity;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class WarlockPower implements IWarlockPower{

    private final LivingEntity entity;

    private double power;

    private int maxPower;


    public WarlockPower(@Nullable final LivingEntity entity)
    {
        this.entity = entity;
    }

    @Override
    public double getCurrentPower() {
        return power;
    }

    @Override
    public int getMaxPower() {
        return maxPower;
    }

    @Override
    public void setMaxPower(int max) {
        this.maxPower = max;
    }

    @Override
    public double setPower(double newPower) {
        if( newPower > getMaxPower())
            this.power = getMaxPower();
        else if(newPower<0)
            this.power = 0;
        else
            this.power = newPower;
        return this.getCurrentPower();
    }

    @Override
    public double addPower(double powerToAdd) {
        return this.setPower(this.getCurrentPower() + powerToAdd);
    }

    @Override
    public double refreshPower() {
        return this.setPower(this.getMaxPower());
    }

    @Override
    public double spendPower(double powerToSpend) {
        if(powerToSpend < 0)
            powerToSpend = 0;
        return this.setPower(this.getCurrentPower()-powerToSpend);
    }
}
