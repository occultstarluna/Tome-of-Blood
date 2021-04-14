package com.minttea.tomeofblood.common.capabilities;

public interface IWarlockPower {

    double getCurrentPower();

    int getMaxPower();

    void setMaxPower(int max);

    double setPower(final double power);

    double addPower(final double powerToAdd);

    double refreshPower();

    double spendPower(final double powerToSpend);


}
