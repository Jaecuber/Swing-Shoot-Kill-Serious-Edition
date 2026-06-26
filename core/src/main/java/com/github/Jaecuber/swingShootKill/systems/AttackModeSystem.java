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
import com.github.Jaecuber.swingShootKill.component.Visible; // Import our new flag

public class AttackModeSystem extends IteratingSystem {

    private EntitySpawner entitySpawner;

    public AttackModeSystem(EntitySpawner entitySpawner) {
        super(Family.all(AttackMode.class, Transform.class).get());
        this.entitySpawner = entitySpawner;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        AttackMode attackModeComp = AttackMode.MAPPER.get(entity);
        ATTACK_MODE mode = attackModeComp.getAttackMode();

        initializeWeapons(entity, attackModeComp);

        Entity gun = attackModeComp.getGunEntity();
        Entity sword = attackModeComp.getSwordEntity();


        if (mode == ATTACK_MODE.GUN) {
            if (gun != null && gun.getComponent(Visible.class) == null) {
                gun.add(new Visible());
                Melee.MAPPER.get(sword).setSpinning(false);
                attackModeComp.setCurrentWeaponEntity(gun);
            }
            if (sword != null) {
                sword.remove(Visible.class);
            }
        } else if (mode == ATTACK_MODE.SWORD) {
            if (sword != null && sword.getComponent(Visible.class) == null) {
                sword.add(new Visible());
                
                attackModeComp.setCurrentWeaponEntity(sword);
            }
            if (gun != null) {
                gun.remove(Visible.class);
            }
        }
    }

    private void initializeWeapons(Entity player, AttackMode attackModeComp) {
        Transform transform = Transform.MAPPER.get(player);

        
        if (attackModeComp.getGunEntity() == null) {
            Vector2 weaponPosition = new Vector2(transform.getPosition());
            weaponPosition.x += transform.getSize().x * 0.8f;
            weaponPosition.y += transform.getSize().y * 0.35f;

            Entity gun = entitySpawner.spawnEntity("playergun", weaponPosition);
            Shooter.MAPPER.get(gun).setOwnerEntity(player);
            attackModeComp.setGunEntity(gun);
            
            
            if (attackModeComp.getAttackMode() != ATTACK_MODE.GUN) {
                gun.remove(Visible.class);
            } else {
                gun.add(new Visible());
            }
        }

        
        if (attackModeComp.getSwordEntity() == null) {
            Entity sword = entitySpawner.spawnEntity("playersword", transform.getPosition());
            Melee.MAPPER.get(sword).setOwnerEntity(player);
            attackModeComp.setSwordEntity(sword);

            if (attackModeComp.getAttackMode() != ATTACK_MODE.SWORD) {
                sword.remove(Visible.class);
            } else {
                sword.add(new Visible());
            }
        }
    }
}