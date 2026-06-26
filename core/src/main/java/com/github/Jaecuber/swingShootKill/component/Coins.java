package com.github.Jaecuber.swingShootKill.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;

public class Coins implements Component{
    public static final ComponentMapper<Coins> MAPPER = ComponentMapper.getFor(Coins.class);

    private int coinBalance;

    public Coins(int coinBalance){
        this.coinBalance = coinBalance;
    }

    public int getCoinBalance() {
        return coinBalance;
    }

    public void setCoinBalance(int coinBalance) {
        this.coinBalance = coinBalance;
    }

    public void addCoins(int coins){
        this.coinBalance += coins;
    }
}
