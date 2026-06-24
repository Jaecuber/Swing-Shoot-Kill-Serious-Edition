package com.github.Jaecuber.swingShootKill.systems;

import com.github.Jaecuber.swingShootKill.component.Health;
import com.github.Jaecuber.swingShootKill.component.Move;
import com.github.Jaecuber.swingShootKill.component.Projectile;
import com.github.Jaecuber.swingShootKill.component.Transform;
import com.github.Jaecuber.swingShootKill.tiled.EntitySpawner;
import com.github.Jaecuber.ui.model.GameViewModel;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.physics.box2d.Body;

public class ProjectileSystem extends IteratingSystem{
    

    


    public ProjectileSystem(){
        super(Family.all(Projectile.class, Move.class, Transform.class).get());
        
        
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
       Projectile projectile = Projectile.MAPPER.get(entity);
       Transform transform = Transform.MAPPER.get(entity);
       Move move = Move.MAPPER.get(entity);

    
        if(projectile.getHitEntity() != null){
            collisionLogic(projectile.getHitEntity(), entity);
        }


       projectile.setLifetime(deltaTime);
       
       if(!projectile.isActive()){
            getEngine().removeEntity(entity);
            return;
       }

    }

    private void collisionLogic(Entity hitEntity, Entity projEntity){
        Health health = Health.MAPPER.get(hitEntity);

        if(health == null){
           getEngine().removeEntity(projEntity);
            return;
        }

        //Damage damage = Damage.MAPPER.get(projEntity);
        //if(damage == null) return;

        //int damageVal = (int) damage.getDmgAmount();

        //health.addHealth(-damageVal);

       // Body body = Physic.MAPPER.get(hitEntity).getBody();


        //System.out.println(health.getHealth());
        getEngine().removeEntity(projEntity);
    }
}