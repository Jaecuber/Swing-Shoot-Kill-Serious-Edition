package com.github.Jaecuber.swingShootKill.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;

public class Shooter implements Component {
    public static final ComponentMapper<Shooter> MAPPER = ComponentMapper.getFor(Shooter.class);

    
    private float cooldown;
    private float elapsedTime;
    private ShooterState shooterState;

    private Entity ownerEntity;


    public enum ShooterState {IDLE, SHOOTING, COOLDOWN};
    


    public Shooter(float cooldown){
        this.shooterState = ShooterState.IDLE;
        this.cooldown = cooldown;
        this.elapsedTime = 0;
        this.ownerEntity = ownerEntity;
        
    }

    public void setOwnerEntity(Entity ownerEntity) {
        this.ownerEntity = ownerEntity;
    }

    public Entity getOwnerEntity() {
        return ownerEntity;
    }

    public ShooterState getShooterState() {
        return shooterState;
    }
 
    public void setShooterState(ShooterState shooterState) {
        this.shooterState = shooterState;
    }

    public void setCooldown(float cooldown) {
        this.cooldown = cooldown;
    }

    public float getCooldown() {
        return cooldown;
    }

    public void setElapsedTime(float elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public float getElapsedTime() {
        return elapsedTime;
    }

}