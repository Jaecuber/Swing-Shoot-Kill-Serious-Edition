package com.github.Jaecuber.swingShootKill.systems;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;

public class PhysicsDebugRenderSystem extends EntitySystem implements Disposable{
    private final World physicsWorld;
    private final Box2DDebugRenderer box2dDebugRenderer;
    private final Camera camera;

    public PhysicsDebugRenderSystem(World physicsWorld, Camera camera){
        this.box2dDebugRenderer = new Box2DDebugRenderer();
        this.physicsWorld = physicsWorld;
        this.camera = camera;
        setProcessing(false);
    }
    
    @Override
    public void update(float deltaTime) {
        this.box2dDebugRenderer.render(physicsWorld, camera.combined);
    }
    
    @Override
    public void dispose() {
        this.box2dDebugRenderer.dispose();
    }
    
}
