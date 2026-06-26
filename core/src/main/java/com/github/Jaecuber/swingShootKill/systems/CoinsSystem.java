package com.github.Jaecuber.swingShootKill.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.github.Jaecuber.swingShootKill.component.Coins;
import com.github.Jaecuber.ui.model.GameViewModel;
import com.github.Jaecuber.ui.model.ViewModel;

public class CoinsSystem extends IteratingSystem{
    private int prevCoins = 0;
    private GameViewModel viewModel;

    public CoinsSystem(GameViewModel viewModel){
        super(Family.all(Coins.class).get());
        this.viewModel = viewModel;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Coins coins = Coins.MAPPER.get(entity);

        if(coins.getCoinBalance() != prevCoins){
            viewModel.updateCoins(coins.getCoinBalance());
            prevCoins = coins.getCoinBalance();
        }
    }
}
