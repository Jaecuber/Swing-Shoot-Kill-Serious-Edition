package com.github.Jaecuber.swingShootKill.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.ashley.systems.IteratingSystem;
import com.github.Jaecuber.swingShootKill.component.Move;
import com.github.Jaecuber.swingShootKill.component.Physics;
import com.github.Jaecuber.swingShootKill.component.Projectile;
import com.github.Jaecuber.swingShootKill.component.Shooter;
import com.github.Jaecuber.swingShootKill.component.Shooter.ShooterState;
import com.github.Jaecuber.swingShootKill.component.Transform;
import com.github.Jaecuber.swingShootKill.tiled.EntitySpawner;

public class ShooterSystem extends IteratingSystem {

    private final EntitySpawner projEntitySpawner;
    
    
    public ShooterSystem(EntitySpawner projEntitySpawner){
        super(Family.all(Shooter.class).get());
        this.projEntitySpawner = projEntitySpawner;
        
    }

    //THIS PIECE OF CREATION CODE IS BEING VERY WEIRD, FIX AT SOME POINT
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

        Transform transform = Transform.MAPPER.get(sourceEntity);

        Vector2 position = transform.getPosition();
        Vector2 size = transform.getSize();

        Vector2 spawnPos = new Vector2(position.x + (size.x * 0.5f), position.y + (size.y * 0.5f));

        float directionToRadian = (transform.getRotationDeg() + 90) * MathUtils.degreesToRadians;

        Vector2 direction = new Vector2(MathUtils.cos(directionToRadian), MathUtils.sin(directionToRadian));
        

        float spawnOffset = 1.5f;
        spawnPos.add(direction.x * spawnOffset, direction.y * spawnOffset);
       
        Entity projEntity = projEntitySpawner.spawnEntity("sparkProj", spawnPos);

        Projectile projectile = Projectile.MAPPER.get(projEntity);
        projectile.setSourceEntity(sourceEntity);

        Physics physics = Physics.MAPPER.get(projEntity);
    }

    private void spawnProjectileEntity(Entity sourceEntity){
        Transform transform = Transform.MAPPER.get(sourceEntity);
        Vector2 position = transform.getPosition();
        Vector2 size = transform.getSize();

        Vector2 spawnPos = new Vector2(position.x + (size.x * 0.5f), position.y + (size.y * 0.5f));

        float directionToRadian = (transform.getRotationDeg() + 90) * MathUtils.degreesToRadians;

        Vector2 direction = new Vector2(MathUtils.cos(directionToRadian), MathUtils.sin(directionToRadian));
        float spawnOffset = 1.5f;
        spawnPos.add(direction.x * spawnOffset, direction.y * spawnOffset);
       
        Entity projEntity = projEntitySpawner.spawnEntity("sparkProj", spawnPos);

        Projectile projectile = Projectile.MAPPER.get(projEntity);
        projectile.setSourceEntity(sourceEntity);

        Physics physics = Physics.MAPPER.get(projEntity);

        

        physics.getBody().setBullet(true);

        Move move = Move.MAPPER.get(projEntity);
        Transform transform2 = Transform.MAPPER.get(projEntity);
        
        transform2.setRotationDeg(transform.getRotationDeg() + 90);
        move.getDirection().set(direction);

        
        
    }
}
