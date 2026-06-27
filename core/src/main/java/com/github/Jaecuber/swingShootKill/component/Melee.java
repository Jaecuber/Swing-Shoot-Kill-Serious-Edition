package com.github.Jaecuber.swingShootKill.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.MathUtils;
import com.github.Jaecuber.swingShootKill.asset.SoundAsset;

public class Melee implements Component {
    public static final ComponentMapper<Melee> MAPPER = ComponentMapper.getFor(Melee.class);

    private float maxSpinSpeed;
    private boolean isSpinning;
    private float currSpinSpeed;
    private float acceleration;

    private float stamConsume;
    private float defaultStamConsume;
    private float damage;
    
    private float accumulator;
    
    private Entity ownerEntity;
    private Entity hitEntity;


    public Melee(float damage, float maxSpinSpeed, float acceleration, float stamConsume){
        this.damage = damage;
        this.maxSpinSpeed = maxSpinSpeed;

        this.isSpinning = false;

        this.ownerEntity = null;
        this.hitEntity = null;

        this.currSpinSpeed = 0;
        this.acceleration = acceleration;
        this.stamConsume = stamConsume;
        this.defaultStamConsume = stamConsume;

        accumulator = 0f;
    }

    public void setAccumulator(float accumulator) {
        this.accumulator = accumulator;
    }

    public float getAccumulator() {
        return accumulator;
    }

    public float getDamage() {
        return damage;
    }

    public float getMaxSpinSpeed() {
        return maxSpinSpeed;
    }

    public void setSpinning(boolean isSpinning) {
        this.isSpinning = isSpinning;
    }

    public boolean isSpinning() {
        return isSpinning;
    }

    public void setOwnerEntity(Entity ownerEntity) {
        this.ownerEntity = ownerEntity;
    }

    public Entity getOwnerEntity() {
        return ownerEntity;
    }

    public float getCurrSpinSpeed() {
        return currSpinSpeed;
    }

    public void setCurrSpinSpeed(float currSpinSpeed) {
        this.currSpinSpeed = MathUtils.clamp(currSpinSpeed, 0, maxSpinSpeed);
    }

    public float getAcceleration() {
        return acceleration;
    }

    public float getStamConsume() {
        return stamConsume;
    }

    public void setHitEntity(Entity hitEntity) {
        this.hitEntity = hitEntity;
    }

    public Entity getHitEntity() {
        return hitEntity;
    }
    
    public float getDefaultStamConsume(){
        return this.defaultStamConsume;
    }

    public void setStamConsume(float stamConsume){
        this.stamConsume = stamConsume;
    }

    public void setDamage(float damage){
        this.damage = damage;
    }
}
