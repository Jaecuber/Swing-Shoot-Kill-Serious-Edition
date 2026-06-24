package com.github.Jaecuber.swingShootKill.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.ashley.systems.IteratingSystem;
import com.github.Jaecuber.swingShootKill.component.Controller;
import com.github.Jaecuber.swingShootKill.component.Move;
import com.github.Jaecuber.swingShootKill.component.Physics;
import com.github.Jaecuber.swingShootKill.component.Projectile;
import com.github.Jaecuber.swingShootKill.component.Shooter;
import com.github.Jaecuber.swingShootKill.component.Shooter.ShooterState;
import com.github.Jaecuber.swingShootKill.helpers.Helpers;
import com.github.Jaecuber.swingShootKill.component.Transform;
import com.github.Jaecuber.swingShootKill.tiled.EntitySpawner;

public class ShooterSystem extends IteratingSystem {

    private final EntitySpawner projEntitySpawner;
    
    
    public ShooterSystem(EntitySpawner projEntitySpawner){
        super(Family.all(Shooter.class).get());
        this.projEntitySpawner = projEntitySpawner;
        
    }

    
    @Override
    protected void processEntity(Entity entity, float deltaTime) {

        Shooter shooter = Shooter.MAPPER.get(entity);
        switch(shooter.getShooterState()){
            case IDLE:
                break;
            case SHOOTING:
                spawnProjectileEntity(entity);
                shooter.setShooterState(ShooterState.COOLDOWN);
                break;
            case COOLDOWN:
                shooter.setElapsedTime(shooter.getElapsedTime() + deltaTime);
                
                if(shooter.getElapsedTime() > shooter.getCooldown()){
                    shooter.setShooterState(ShooterState.IDLE);
                    shooter.setElapsedTime(0);
                }
                break;
        }

        gunPivoting(entity);

    }

    private void gunPivoting(Entity entity){
        Entity sourceEntity = Shooter.MAPPER.get(entity).getOwnerEntity();

        Controller ownerController = Controller.MAPPER.get(sourceEntity);

        Transform ownerTransform = Transform.MAPPER.get(sourceEntity);
        Transform shooterTransform = Transform.MAPPER.get(entity);

        Vector2 ownerPosition = ownerTransform.getPosition();
        Vector2 ownerSize = ownerTransform.getSize();
        Vector2 ownerCenter = new Vector2(ownerPosition.x + (ownerSize.x * 0.5f), ownerPosition.y + (ownerSize.y * 0.5f));

        Vector3 mouseWorldCoords = Helpers.mousePosToWorld(ownerController.getMousePosition());
        float mouseX = mouseWorldCoords.x - ownerCenter.x;
        float mouseY = mouseWorldCoords.y - ownerCenter.y;

        float angleRad = MathUtils.atan2(mouseY, mouseX);
        float angleDeg = angleRad * MathUtils.radiansToDegrees;
        angleDeg %= 360;
        if(angleDeg < 0) angleDeg += 360;

        boolean needToFlip = (angleDeg > 90 && angleDeg < 270);

        float scaleX = shooterTransform.getScaling().x;
        float scaleY = Math.abs(shooterTransform.getScaling().y);

        if(needToFlip){
            shooterTransform.getScaling().set(scaleX, -scaleY);
        } else {
            shooterTransform.getScaling().set(scaleX, scaleY);
        }

        shooterTransform.setRotationDeg(angleDeg);
        

        
        float spawnOffset = 1.5f;

        float gunPosX = ownerCenter.x + (MathUtils.cos(angleRad) * spawnOffset) - (shooterTransform.getSize().x * 0.5f);
        float gunPosY = ownerCenter.y + (MathUtils.sin(angleRad) * spawnOffset) - (shooterTransform.getSize().y * 0.5f);

        shooterTransform.getPosition().set(gunPosX, gunPosY);
    }

    

    private void spawnProjectileEntity(Entity sourceEntity){
        Transform transform = Transform.MAPPER.get(sourceEntity);
        Vector2 position = transform.getPosition();
        Vector2 size = transform.getSize();

        Vector2 spawnPos = new Vector2(position.x + (size.x * 0.5f), position.y + (size.y * 0.5f));

        float directionToRadian = (transform.getRotationDeg()) * MathUtils.degreesToRadians;

        Vector2 direction = new Vector2(MathUtils.cos(directionToRadian), MathUtils.sin(directionToRadian));
        float spawnOffset = 0.5f;
        spawnPos.add(direction.x * spawnOffset, direction.y * spawnOffset);
       
        Entity projEntity = projEntitySpawner.spawnEntity("revolver_Bullet", spawnPos);

        Projectile projectile = Projectile.MAPPER.get(projEntity);
        projectile.setSourceEntity(sourceEntity);


        Move move = Move.MAPPER.get(projEntity);
        
    
        move.getDirection().set(direction);
            
    }
}
