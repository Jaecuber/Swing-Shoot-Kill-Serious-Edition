package com.github.Jaecuber.swingShootKill.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.github.Jaecuber.swingShootKill.component.Health;
import com.github.Jaecuber.swingShootKill.component.Player;
import com.github.Jaecuber.swingShootKill.input.IdleControllerState;
import com.github.Jaecuber.swingShootKill.input.KeyboardController;
import com.github.Jaecuber.ui.model.GameViewModel;

public class HealthSystem extends IteratingSystem implements EntityListener{
    private final GameViewModel viewModel;
    private final KeyboardController keyboardController;

    public HealthSystem(GameViewModel viewModel, KeyboardController keyboardController){
        super(Family.all(Health.class).get());
        this.viewModel = viewModel;
        this.keyboardController = keyboardController;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Health health = Health.MAPPER.get(entity);
        if(health.getHealth() == health.getMaxHealth()) return;


        health.addHealth(health.getRegen() * deltaTime);
        if(health.died() && Player.MAPPER.get(entity) != null){
            die(entity);
        }
        if (Player.MAPPER.get(entity) != null) {
            //viewModel.updateHealthInfo(health.getMaxHealth(), health.getHealth());
        }
    }

    private void die(Entity entity){
       keyboardController.setActiveState(IdleControllerState.class);
       //viewModel.showGameOver();
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        engine.addEntityListener(Family.all(Health.class, Player.class).get(), this);
    }

    @Override
    public void removedFromEngine(Engine engine) {
        super.removedFromEngine(engine);
        engine.removeEntityListener(this);
    }

    @Override
    public void entityAdded(Entity entity) {
        Health health = Health.MAPPER.get(entity);
        //viewModel.updateHealthInfo(health.getMaxHealth(), health.getHealth());
    }

    @Override
    public void entityRemoved(Entity entity) {
    }
}
