package com.github.Jaecuber.swingShootKill.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.github.Jaecuber.swingShootKill.component.DamageListener;
import com.github.Jaecuber.swingShootKill.component.Enemy;
import com.github.Jaecuber.swingShootKill.component.Health;
import com.github.Jaecuber.swingShootKill.component.Melee;
import com.github.Jaecuber.swingShootKill.component.Physics;
import com.github.Jaecuber.swingShootKill.component.Shooter;
import com.github.Jaecuber.swingShootKill.component.Stamina;
import com.github.Jaecuber.swingShootKill.component.Transform;
import com.github.Jaecuber.swingShootKill.helpers.Helpers;

public class MeleeSystem extends IteratingSystem{

    

    public MeleeSystem(){
        super(Family.all(Melee.class, Transform.class).get());
        
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Melee melee = Melee.MAPPER.get(entity);
        boolean isSpinning = melee.isSpinning();

        Body meleeBody = Physics.MAPPER.get(entity).getBody();

        Entity sourceEntity = Melee.MAPPER.get(entity).getOwnerEntity();

        Transform ownerTransform = Transform.MAPPER.get(sourceEntity);
        Transform meleeTransform = Transform.MAPPER.get(entity);

        Vector2 ownerPosition = ownerTransform.getPosition();
        Vector2 ownerSize = ownerTransform.getSize();
        Vector2 ownerCenter = new Vector2(ownerPosition.x + (ownerSize.x * 0.5f), ownerPosition.y + (ownerSize.y * 0.5f));

        Stamina stamina = Stamina.MAPPER.get(sourceEntity);

        if(isSpinning && stamina.getCurrentStamina() - melee.getStamConsume() > 0f){
            melee.setCurrSpinSpeed(melee.getCurrSpinSpeed() + melee.getAcceleration());
            melee.setAccumulator(melee.getAccumulator() + deltaTime);
            
            if(melee.getAccumulator() >= 1f){
                stamina.updateStamina(-melee.getStamConsume());
                melee.setAccumulator(0);
            }

        } else {
            melee.setCurrSpinSpeed(melee.getCurrSpinSpeed() - melee.getAcceleration());
        }

        //Adding RotationDeg is Clockwise, Subtracting is CounterClockwise
        meleeTransform.setRotationDeg(meleeTransform.getRotationDeg() - (melee.getCurrSpinSpeed() * deltaTime));

        float angleRad = (meleeTransform.getRotationDeg() + 45) * MathUtils.degreesToRadians;
        float spawnOffset = 1.75f;


        float swordCenterX = ownerCenter.x + (MathUtils.cos(angleRad) * spawnOffset);
        float swordCenterY = ownerCenter.y + (MathUtils.sin(angleRad) * spawnOffset);

        float spriteBottomLeftX = swordCenterX - (meleeTransform.getSize().x * 0.5f);
        float spriteBottomLeftY = swordCenterY - (meleeTransform.getSize().y * 0.5f);
        meleeTransform.getPosition().set(spriteBottomLeftX, spriteBottomLeftY);

        meleeBody.setTransform(swordCenterX, swordCenterY, angleRad - (MathUtils.PI / 4));
        collisionLogic(entity, angleRad);
        
    }

    private void collisionLogic(Entity swordEntity, float angleRad){
        Entity hitEntity = Melee.MAPPER.get(swordEntity).getHitEntity();

        if(hitEntity == null) return;

        Enemy enemy = Enemy.MAPPER.get(hitEntity);
        Health health = Health.MAPPER.get(hitEntity);

        Body swordBody = Physics.MAPPER.get(swordEntity).getBody();
        Body hitBody = Physics.MAPPER.get(hitEntity).getBody();

        if(!enemy.isDead() && !health.died()){
            enemy.applyKnockback(0.3f);
           Vector2 knockbackDirection = new Vector2(
                hitBody.getPosition().x - swordBody.getPosition().x,
                hitBody.getPosition().y - swordBody.getPosition().y
            ).nor().scl(20f);
            hitBody.setLinearVelocity(new Vector2(0,0));
            hitBody.applyLinearImpulse(knockbackDirection, hitBody.getWorldCenter(), true);
        }

        DamageListener damageListener = DamageListener.MAPPER.get(hitEntity);
        float damage = Melee.MAPPER.get(swordEntity).getDamage();

        if (damageListener == null) {
            hitEntity.add(new DamageListener(damage));
        } else {
            damageListener.addDamage(damage);
        }
    }

   

}
