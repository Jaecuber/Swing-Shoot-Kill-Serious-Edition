package com.github.Jaecuber.swingShootKill.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;
import com.github.Jaecuber.swingShootKill.component.Light;
import com.github.Jaecuber.swingShootKill.component.Transform;

import box2dLight.RayHandler;

public class LightingSystem extends IteratingSystem implements Disposable{
    private final RayHandler rayHandler;
    private final OrthographicCamera camera;

    public LightingSystem(RayHandler rayHandler, OrthographicCamera camera) {
        super(Family.all(Light.class, Transform.class).get());
        this.camera = camera;
        this.rayHandler = rayHandler;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Light light = Light.MAPPER.get(entity);
        Transform transform = Transform.MAPPER.get(entity);
        Vector2 position = transform.getPosition();
        Vector2 size = transform.getSize();
        light.getPointLight().setPosition(position.x + size.x * 0.5f, position.y + size.y * 0.5f);
    }

    public RayHandler getRayHandler() {
        return rayHandler;
    }

    @Override
    public void dispose() {
        rayHandler.dispose();
    }
}
