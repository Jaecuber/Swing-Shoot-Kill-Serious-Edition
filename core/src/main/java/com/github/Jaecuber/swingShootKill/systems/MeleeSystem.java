package com.github.Jaecuber.swingShootKill.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.github.Jaecuber.swingShootKill.component.Melee;
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
                System.out.println(stamina.getCurrentStamina());
                melee.setAccumulator(0);
            }

        } else {
            melee.setCurrSpinSpeed(melee.getCurrSpinSpeed() - melee.getAcceleration());
        }

        meleeTransform.setRotationDeg(meleeTransform.getRotationDeg() + (melee.getCurrSpinSpeed() * deltaTime));

        float angleRad = (meleeTransform.getRotationDeg() + 45) * MathUtils.degreesToRadians;
        
        float spawnOffset = 1.5f;

        float swordPosX = ownerCenter.x + (MathUtils.cos(angleRad) * spawnOffset) - (meleeTransform.getSize().x * 0.5f);
        float swordPosY = ownerCenter.y + (MathUtils.sin(angleRad) * spawnOffset) - (meleeTransform.getSize().y * 0.5f);

        meleeTransform.getPosition().set(swordPosX, swordPosY);
        
    }

   

}
