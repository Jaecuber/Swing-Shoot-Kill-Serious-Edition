package com.github.Jaecuber.swingShootKill.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.gdx.math.MathUtils;
import com.github.Jaecuber.swingShootKill.asset.SoundAsset;

public class Melee implements Component {
    public static final ComponentMapper<Melee> MAPPER = ComponentMapper.getFor(Melee.class);



    private float damage;
    private float damageDelay;
    private float attackTimer;


    public Melee(float damage, float damageDelay){
        this.damage = damage;
        this.damageDelay = damageDelay;

        this.attackTimer = 0f;
    }
    

    public boolean canAttack(){
        return this.attackTimer == 0f;
    }

    public boolean isAttacking(){
        return this.attackTimer > 0f;
    }

    public boolean hasAttackStarted(){
        return MathUtils.isEqual(this.attackTimer, this.damageDelay, 0.0001f);
    }

    public void startAttack(){
        this.attackTimer = this.damageDelay;
    }

    public void decAttackTimer(float deltaTime){
        attackTimer = Math.max(0f, attackTimer - deltaTime);
    }
    
    public float getDamage() {
        return damage;
    }

    
}
