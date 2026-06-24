package com.github.Jaecuber.swingShootKill.systems;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.SortedIteratingSystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.AnimatedTiledMapTile;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.github.Jaecuber.swingShootKill.Launcher;
import com.github.Jaecuber.swingShootKill.component.Graphic;
import com.github.Jaecuber.swingShootKill.component.Transform;

public class RenderSystem extends SortedIteratingSystem implements Disposable{
    private final OrthogonalTiledMapRenderer mapRenderer;
    private final Batch batch;
    private final Viewport viewport;
    private final OrthographicCamera camera;
    private final List<MapLayer> fgdLayers;
    private final List<MapLayer> bkgLayers;

    public RenderSystem(Batch batch, Viewport viewport, OrthographicCamera camera){
        super(
            Family.all(Transform.class, Graphic.class).get(), 
            Comparator.comparing(Transform.MAPPER::get)
        );
        this.batch = batch;
        this.viewport = viewport;
        this.camera = camera;
        this.mapRenderer = new OrthogonalTiledMapRenderer(null, Launcher.UNIT_SCALE, this.batch);
        this.fgdLayers = new ArrayList<>();
        this.bkgLayers = new ArrayList<>();
    }

    @Override
    public void update(float deltaTime) {
        AnimatedTiledMapTile.updateAnimationBaseTime();
        this.viewport.apply();

        batch.begin();
        //color grading would go here if needed later--this.batch.setColor(0.7f, 0.7f, 0.8f, 1f): gloomy blue hue
        batch.setColor(Color.WHITE);
        this.mapRenderer.setView(this.camera);
        this.bkgLayers.forEach(mapRenderer::renderMapLayer);

        forceSort();
        super.update(deltaTime);
        this.batch.setColor(Color.WHITE);
        
        this.fgdLayers.forEach(mapRenderer::renderMapLayer);
        batch.end();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Transform transform = Transform.MAPPER.get(entity);
        Graphic graphic = Graphic.MAPPER.get(entity);
        if(graphic.getRegion() == null){
            return;
        }

        Vector2 position = transform.getPosition();
        Vector2 scaling = transform.getScaling();
        Vector2 size = transform.getSize();
        Vector2 origin = transform.getOriginPos();
        this.batch.setColor(graphic.getColor());
        this.batch.draw(
            graphic.getRegion(), 
            position.x,
            position.y,
            origin.x, origin.y,
            size.x, size.y,
            scaling.x, scaling.y,
            transform.getRotationDeg()
        );
    }

    public void setMap(TiledMap tiledMap){
        this.mapRenderer.setMap(tiledMap);

        this.fgdLayers.clear();
        this.bkgLayers.clear();
        List<MapLayer> currentLayers = bkgLayers;
        for(MapLayer layer : tiledMap.getLayers()){
            if("objects".equals(layer.getName())){
                currentLayers = fgdLayers;
                continue;
            }
            if(layer.getClass().equals(MapLayer.class)){
                continue;
            }
            currentLayers.add(layer);
        }
    }

    @Override
    public void dispose() {
        this.mapRenderer.dispose();
    }    
}
