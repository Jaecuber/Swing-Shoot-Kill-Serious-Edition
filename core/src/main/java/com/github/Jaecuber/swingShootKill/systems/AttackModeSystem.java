package com.github.Jaecuber.swingShootKill.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

import com.github.Jaecuber.swingShootKill.component.AttackMode;
import com.github.Jaecuber.swingShootKill.component.Transform;
import com.github.Jaecuber.swingShootKill.tiled.EntitySpawner;
import com.github.Jaecuber.swingShootKill.component.AttackMode.ATTACK_MODE;

public class AttackModeSystem extends IteratingSystem{

    private Entity currentWeapon;
    private EntitySpawner entitySpawner;

    public AttackModeSystem(EntitySpawner entitySpawner){
        super(Family.all(AttackMode.class, Transform.class).get());
        currentWeapon = null;
        this.entitySpawner = entitySpawner;
    }

    @Override
    public void update(float deltaTime) {
    
        super.update(deltaTime);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        AttackMode attackModeComp = AttackMode.MAPPER.get(entity);
        ATTACK_MODE mode = attackModeComp.getAttackMode(); 

        Transform transform = Transform.MAPPER.get(entity);

        if(currentWeapon == null){
            switch(mode){
                case GUN -> currentWeapon = entitySpawner.spawnEntity("playergun", transform.getPosition());
                case SWORD -> currentWeapon = entitySpawner.spawnEntity("playersword", transform.getPosition());
            };
        }

        Transform.MAPPER.get(currentWeapon).getPosition().set(Transform.MAPPER.get(entity).getPosition());
        
    }

    

}
