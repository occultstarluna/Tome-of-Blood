package com.minttea.tomeofblood.common.capabilities;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Util;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.Nullable;

public class WarlockPower implements IWarlockPower{

    private final LivingEntity entity;

    private int power;

    private int maxPower;


    public WarlockPower(@Nullable final LivingEntity entity)
    {
        this.entity = entity;
    }

    @Override
    public int getCurrentPower() {
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
    public int setPower(int newPower) {
        if( newPower > getMaxPower())
            this.power = getMaxPower();
        else if(newPower<0)
            this.power = 0;
        else
            this.power = newPower;
        return this.getCurrentPower();
    }

    @Override
    public int addPower(int powerToAdd) {
        return this.setPower(this.getCurrentPower() + powerToAdd);
    }

    @Override
    public int refreshPower() {
        this.entity.sendMessage(new TranslationTextComponent("tomeofblood.alert.power_refresh"), Util.DUMMY_UUID);
        return this.setPower(this.getMaxPower());
    }

    @Override
    public int spendPower(int powerToSpend) {
        if(powerToSpend < 0)
            powerToSpend = 0;
        int newPower = this.getCurrentPower()-powerToSpend;
        if(this.getPowerPercentage() > .75 && (double)newPower/this.getMaxPower() < .75)
            this.entity.sendMessage(new TranslationTextComponent("tomeofblood.alert.power_75"), Util.DUMMY_UUID);
        else if(this.getPowerPercentage() > .5 && (double)newPower/this.getMaxPower() < .5)
            this.entity.sendMessage(new TranslationTextComponent("tomeofblood.alert.power_50"), Util.DUMMY_UUID);
        else if(this.getPowerPercentage() > .25 && (double)newPower/this.getMaxPower() < .25)
            this.entity.sendMessage(new TranslationTextComponent("tomeofblood.alert.power_25"), Util.DUMMY_UUID);
        else if((double)newPower/this.getMaxPower() < .05)
            this.entity.sendMessage(new TranslationTextComponent("tomeofblood.alert.power_0"), Util.DUMMY_UUID);

        return this.setPower(this.getCurrentPower()-powerToSpend);
    }

    private double getPowerPercentage() {
        return (double) this.power/(double) this.maxPower;
    }
}
