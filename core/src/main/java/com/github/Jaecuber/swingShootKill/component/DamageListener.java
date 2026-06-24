package com.github.Jaecuber.swingShootKill.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;

public class DamageListener implements Component{
    public static final ComponentMapper<DamageListener> MAPPER = ComponentMapper.getFor(DamageListener.class);

    private float damage;

    public DamageListener(float damage){
        this.damage = damage;
    }

    public void addDamage(float amount){
        this.damage += amount;
    }

    public float getDamage(){
        return this.damage;
    }
}
