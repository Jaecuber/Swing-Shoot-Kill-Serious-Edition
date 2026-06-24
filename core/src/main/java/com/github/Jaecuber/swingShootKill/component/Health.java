package com.github.Jaecuber.swingShootKill.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
<<<<<<< HEAD

public class Health implements Component{
    public static final ComponentMapper<Health> MAPPER = ComponentMapper.getFor(Health.class);
=======
import com.badlogic.gdx.math.MathUtils;

public class Health implements Component{
    public static final ComponentMapper<Health> MAPPER = ComponentMapper.getFor(Health.class);

    private float maxHealth;
    private float health;
    private float regen;

    public Health(float maxHealth, float regen){
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        this.regen = regen;
    }
    public boolean died(){
        return health <= 0;
    }

    public float getMaxHealth(){
        return maxHealth;
    }

    public float getHealth(){
        return health;
    }

    public void addHealth(float val){
        this.health = MathUtils.clamp(health += val, -1.0f, maxHealth);
    }

    public void setMaxHealth(float health){
        this.maxHealth = health;
    }

    public void setHealthRegen(float regen){
        this.regen = regen;
    }

    public void setHealth(float health){
        this.health = health;
    }

    public float getRegen(){
        return regen;
    }
>>>>>>> main
}
