package com.github.Jaecuber.swingShootKill.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.github.Jaecuber.swingShootKill.component.AttackMode;
import com.github.Jaecuber.swingShootKill.component.Transform;
import com.github.Jaecuber.swingShootKill.tiled.EntitySpawner;
import com.github.Jaecuber.swingShootKill.component.AttackMode.ATTACK_MODE;
import com.github.Jaecuber.swingShootKill.component.Melee;
import com.github.Jaecuber.swingShootKill.component.Shooter;

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
        Transform weaponTransform = Transform.MAPPER.get(entity);

        if(currentWeapon == null){
            switch(mode){
                case GUN -> createGun(entity);
                case SWORD -> createSword(entity);
            };
        }

        
        
    }

    private void createGun(Entity originEntity){
        if(currentWeapon != null && currentWeapon.getComponent(Melee.class) != null){
            getEngine().removeEntity(currentWeapon);
            currentWeapon = null;
        } 

        if(currentWeapon != null) return;


        Transform transform = Transform.MAPPER.get(originEntity);
        Vector2 weaponPosition = new Vector2();
        weaponPosition.set(transform.getPosition());

        weaponPosition.x += transform.getSize().x * 0.8f;
        weaponPosition.y += transform.getSize().y * 0.35f;

        currentWeapon = entitySpawner.spawnEntity("playergun", weaponPosition);

        Transform.MAPPER.get(originEntity).getPosition().set(weaponPosition);

        Shooter.MAPPER.get(currentWeapon).setOwnerEntity(originEntity);
    }

    private void createSword(Entity originEntity){
        if(currentWeapon != null && currentWeapon.getComponent(Shooter.class) != null){
            getEngine().removeEntity(currentWeapon);
            currentWeapon = null;
        }

        if(currentWeapon != null) return;

        Transform transform = Transform.MAPPER.get(originEntity);
        currentWeapon = entitySpawner.spawnEntity("playersword", transform.getPosition());


        
    }

    

    

}
