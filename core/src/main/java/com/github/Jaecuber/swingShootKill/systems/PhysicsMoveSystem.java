package com.github.Jaecuber.swingShootKill.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.github.Jaecuber.swingShootKill.component.Enemy;
import com.github.Jaecuber.swingShootKill.component.Move;
import com.github.Jaecuber.swingShootKill.component.Physics;

public class PhysicsMoveSystem extends IteratingSystem{
    private final Vector2 normalizedDirection = new Vector2();

    public PhysicsMoveSystem(){
        super(Family.all(Physics.class, Move.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Move move = Move.MAPPER.get(entity);
        Body body = Physics.MAPPER.get(entity).getBody();
        Enemy enemy = Enemy.MAPPER.get(entity);

        if(enemy != null && enemy.isStaggered()) return;
        if(move.isRooted() || move.getDirection().isZero()){
            body.setLinearVelocity(0f,0f);
            return;
        }

        normalizedDirection.set(move.getDirection()).nor();
        body.setLinearVelocity(
            move.getMaxSpeed() * normalizedDirection.x,
            move.getMaxSpeed() * normalizedDirection.y
        );
    }
}
