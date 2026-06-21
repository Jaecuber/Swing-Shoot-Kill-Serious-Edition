package com.github.Jaecuber.swingShootKill.systems;

import java.util.concurrent.TransferQueue;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.github.Jaecuber.swingShootKill.Launcher;
import com.github.Jaecuber.swingShootKill.component.CameraFollow;
import com.github.Jaecuber.swingShootKill.component.Transform;

public class CameraSystem extends IteratingSystem{
    private final Camera camera;
    private final Vector2 targetPosition;
    private final float smoothingFactor;
    private float mapW;
    private float mapH;

    public CameraSystem(Camera camera){
        super(Family.all(CameraFollow.class, Transform.class).get());
        this.camera = camera;
        this.targetPosition = new Vector2();
        this.smoothingFactor = 6f;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Transform transform = Transform.MAPPER.get(entity);
        calcTargetPos(transform.getPosition(), transform.getSize());

        float progress = smoothingFactor * deltaTime;
        float smoothedX = MathUtils.lerp(camera.position.x, this.targetPosition.x, progress);
        float smoothedY = MathUtils.lerp(camera.position.y, this.targetPosition.y, progress);
        camera.position.set(smoothedX, smoothedY, camera.position.z);
    }

    private void calcTargetPos(Vector2 entityPostion, Vector2 entitySize) {
        float targetX = entityPostion.x;
        float camHalfW = camera.viewportWidth * 0.5f;
        if(mapW > camHalfW){
            float min = Math.min(camHalfW, mapW - camHalfW);
            float max = Math.max(camHalfW, mapW - camHalfW) - (entitySize.x * 0.5f);
            targetX = MathUtils.clamp(targetX, min, max) + (entitySize.x * 0.5f);
        }

        float targetY = entityPostion.y;
        float camHalfH = camera.viewportHeight * 0.5f;
        if(mapH > camHalfH){
            float min = Math.min(camHalfH, mapH - camHalfH);
            float max = Math.max(camHalfH, mapH - camHalfH) - (entitySize.y * 0.5f);
            targetY = MathUtils.clamp(targetY, min, max) + (entitySize.y * 0.5f);
        }
        this.targetPosition.set(targetX, targetY);
    }
    
    public void setMap(TiledMap tiledMap){
        Integer width = tiledMap.getProperties().get("width", 0, Integer.class);
        Integer tileW = tiledMap.getProperties().get("tilewidth", 0, Integer.class);
        Integer height = tiledMap.getProperties().get("height", 0, Integer.class);
        Integer tileH = tiledMap.getProperties().get("tileheight", 0, Integer.class);

        this.mapW = width * tileW * Launcher.UNIT_SCALE;
        this.mapH = height * tileH * Launcher.UNIT_SCALE;

        if(getEntities().size() > 0){
            processEntity(getEntities().first(), 0f);
        }
    }
}
