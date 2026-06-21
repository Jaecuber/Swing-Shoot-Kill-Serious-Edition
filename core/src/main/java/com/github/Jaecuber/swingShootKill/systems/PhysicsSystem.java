package com.github.Jaecuber.swingShootKill.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.github.Jaecuber.swingShootKill.component.Physics;
import com.github.Jaecuber.swingShootKill.component.Transform;

public class PhysicsSystem extends IteratingSystem implements EntityListener, ContactListener{
    private final World world;
    private final float interval;
    private float accumulator;

    public PhysicsSystem(World world, float interval){
        super(Family.all(Physics.class, Transform.class).get());
        this.world = world;
        this.interval = interval;
        this.accumulator = 0f;
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        engine.addEntityListener(null);
    }

    @Override
    public void removedFromEngine(Engine engine) {
        super.removedFromEngine(engine);
        engine.removeEntityListener(this);
    }

    @Override
    public void entityAdded(Entity entity) {
        
    }

    @Override
    public void entityRemoved(Entity entity) {
        Physics physics = Physics.MAPPER.get(entity);
        if(physics != null){
            this.world.destroyBody(physics.getBody());
        }
    }

    @Override
    public void update(float deltaTime) {
        this.accumulator += deltaTime;
        while (this.accumulator >= this.interval) {
            this.accumulator -= this.interval;
            super.update(deltaTime);
            this.world.step(interval, 6, 2);
        }
        world.clearForces();

        float alpha = this.accumulator/this.interval;
        for(int i = 0; i < getEntities().size(); i++){
            this.interpolateEntity(getEntities().get(i), alpha);
        }
    }
    
    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Physics physics = Physics.MAPPER.get(entity);
        physics.getPrevPosition().set(physics.getBody().getPosition());
    }

    private void interpolateEntity(Entity entity, float alpha){
        Transform transform = Transform.MAPPER.get(entity);
        Physics physics = Physics.MAPPER.get(entity);

        transform.getPosition().set(
            MathUtils.lerp(physics.getPrevPosition().x, physics.getBody().getPosition().x, alpha),
            MathUtils.lerp(physics.getPrevPosition().y, physics.getBody().getPosition().y, alpha)
        );
    }

    //Contact Listener-stub methods for now
    @Override
    public void beginContact(Contact contact) {}

    @Override
    public void endContact(Contact contact) {}

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {}

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {}
}
