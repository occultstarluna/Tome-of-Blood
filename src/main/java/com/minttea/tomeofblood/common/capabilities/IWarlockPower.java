package com.minttea.tomeofblood.common.capabilities;

public interface IWarlockPower {

    int getCurrentPower();

    int getMaxPower();

    void setMaxPower(int max);

    int setPower(final int power);

    int addPower(final int powerToAdd);

    int refreshPower();

    int spendPower(final int powerToSpend);


}
