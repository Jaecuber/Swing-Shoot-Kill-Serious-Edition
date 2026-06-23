package com.github.Jaecuber.swingShootKill.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.github.Jaecuber.swingShootKill.asset.SoundAsset;

public class Projectile implements Component {
    public static final ComponentMapper<Projectile> MAPPER = ComponentMapper.getFor(Projectile.class);

    private float lifetime;
    private float maxLifeTime;
    private Entity hitEntity;
    private Entity sourceEntity;



    public Projectile(float maxLifeTime){
        this.maxLifeTime = maxLifeTime;
        lifetime = 0;
        hitEntity = null;
    }

    public Entity getSourceEntity() {
        return sourceEntity;
    }

    public void setSourceEntity(Entity sourceEntity) {
        this.sourceEntity = sourceEntity;
    }

    public void setLifetime(float lifetime) {
        this.lifetime += lifetime;
    }

    public boolean isActive(){
        return lifetime < maxLifeTime;
    }
   
    public float getLifetime() {
        return lifetime;
    }

    public float getMaxLifeTime() {
        return maxLifeTime;
    }

    public void setHitEntity(Entity hitEntity) {
        this.hitEntity = hitEntity;
    }

    public Entity getHitEntity() {
        return hitEntity;
    }

    
}
