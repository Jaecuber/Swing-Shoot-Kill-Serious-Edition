package com.github.Jaecuber.swingShootKill.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.Array;


public class Shooter implements Component {
    public static final ComponentMapper<Shooter> MAPPER = ComponentMapper.getFor(Shooter.class);

    
    private float cooldown;
    private float elapsedTime;
    private ShooterState shooterState;

    private float damage;

    private Entity ownerEntity;

    private int currentBullets;
    private float reloadTime;
    private int capacity;

    private Array<String> ownedSpecialBullets;


    public enum ShooterState {IDLE, SHOOTING, COOLDOWN, RELOADING};
    


    public Shooter(float cooldown, float damage, float reloadTime, int capacity){
        this.shooterState = ShooterState.IDLE;
        this.cooldown = cooldown;
        this.elapsedTime = 0;
        this.ownerEntity = null;
        

        this.reloadTime = reloadTime;
        this.capacity = capacity;
        this.currentBullets = capacity;

        this.damage = damage;

        this.ownedSpecialBullets = new Array<>();
        populateBasicBullets(ownedSpecialBullets);
    }

    private void populateBasicBullets(Array<String> ownedSpecialBullets){
        ownedSpecialBullets.add("poison");
        ownedSpecialBullets.add("electric");
        ownedSpecialBullets.add("exploding");
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

    public float getCooldown() {
        return cooldown;
    }

    public void setElapsedTime(float elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public float getElapsedTime() {
        return elapsedTime;
    }

    public void decreaseBullets(){
        currentBullets--;
    }

    public boolean timeToRoll(){
        return currentBullets == 1;
    }

    public String getRandomizedSpecials(){
        return ownedSpecialBullets.random();
    }

    public float getDamage() {
        return damage;
    }

    public float getReloadTime() {
        return reloadTime;
    }

    public boolean needToReload(){
        return currentBullets <= 0;
    }

    public void setCurrentBullets(int currentBullets) {
        this.currentBullets = currentBullets;
    }

    public int getCapacity() {
        return capacity;
    }

}