package com.github.Jaecuber.swingShootKill.tiled;

import java.util.function.Consumer;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.github.Jaecuber.swingShootKill.Launcher;
import com.github.Jaecuber.swingShootKill.asset.AssetService;
import com.github.Jaecuber.swingShootKill.asset.MapAsset;

public class TiledService {
    private final AssetService assetService;
    private final World physicsWorld;

    private TiledMap currentMap;

    private Consumer<TiledMap> mapChangeConsumer;
    private Consumer<TiledMapTileMapObject> loadObjectConsumer;
    private LoadTileConsumer loadTileConsumer;

    private Runnable mapCleanupRunnable;

    public TiledService(AssetService assetService, World physicsWorld){
        this.assetService = assetService;
        this.physicsWorld = physicsWorld;
        this.mapChangeConsumer = null;
        this.loadObjectConsumer = null;
        this.currentMap = null;
        this.loadTileConsumer = null;
    }

    public TiledMap loadMap(MapAsset mapAsset){
        TiledMap tiledMap = this.assetService.load(mapAsset);
        tiledMap.getProperties().put("mapAsset", mapAsset);
        return tiledMap;
    }

    public void setMapCleanupRunnable(Runnable mapCleanupRunnable){
        this.mapCleanupRunnable = mapCleanupRunnable;
    }

    public void setMap(TiledMap map){
        if(this.mapChangeConsumer != null){
            this.mapChangeConsumer.accept(map);
        }

        if(this.currentMap != null){
            this.assetService.unload(this.currentMap.getProperties().get("mapAsset", MapAsset.class));
        }

        if(this.mapCleanupRunnable != null){
            this.mapCleanupRunnable.run();
        }

        Array<Body> bodies = new Array<>();
        physicsWorld.getBodies(bodies);
        for(Body body : bodies){
            if("environment".equals(body.getUserData())){
                physicsWorld.destroyBody(body);
            }
        }

        this.currentMap = map;
        loadMapObjects(map);
    }

    public Array<Vector2> getSpawns(){
        Array<Vector2> spawns = new Array<>();

        for(MapLayer mapLayer : currentMap.getLayers()){
            if(mapLayer instanceof TiledMapTileLayer || !mapLayer.getName().equals("spawns")) continue;
            for(MapObject object : mapLayer.getObjects()){
                if(object.getName().equals("spawn")){
                    TiledMapTileMapObject obj = (TiledMapTileMapObject) object;
                    spawns.add(new Vector2(
                        obj.getX(),
                        obj.getY()
                    ));
                }
            }
        }
        return spawns;
    }

    private void loadMapObjects(TiledMap tiledMap) {
       for(MapLayer layer : tiledMap.getLayers()){
            if("objects".equals(layer.getName())){
                loadObjectLayer(layer);
            }else if(layer instanceof TiledMapTileLayer tiledLayer){
                loadTiledLayer(tiledLayer);
            }
        }
        spawnMapBoundary(tiledMap);
    }

    private void spawnMapBoundary(TiledMap tiledMap){
        Integer width = tiledMap.getProperties().get("width", 0, Integer.class);
        Integer tildW = tiledMap.getProperties().get("tilewidth", 0, Integer.class);
        Integer height = tiledMap.getProperties().get("height", 0, Integer.class);
        Integer tileH = tiledMap.getProperties().get("tileheight", 0, Integer.class);
        float mapW = width * tildW * Launcher.UNIT_SCALE;
        float mapH = height * tileH * Launcher.UNIT_SCALE;
        float halfW = mapW * 0.5f;
        float halfH = mapH * 0.5f;
        float boxThickness = 0.5f;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.setZero();
        bodyDef.fixedRotation = true;
        Body body = physicsWorld.createBody(bodyDef);
        body.setUserData("environment");

        //left
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(boxThickness, halfH, new Vector2(-boxThickness, halfH), 0f);
        body.createFixture(shape, 0f).setFriction(0f);
        shape.dispose();

        //right
        shape = new PolygonShape();
        shape.setAsBox(boxThickness, halfH, new Vector2(mapW + boxThickness, halfH), 0f);
        body.createFixture(shape, 0f).setFriction(0f);
        shape.dispose();
        //bottom
        shape = new PolygonShape();
        shape.setAsBox(halfW, boxThickness, new Vector2(halfW, -boxThickness), 0f);
        body.createFixture(shape, 0f).setFriction(0f);
        shape.dispose();
        //top
        shape = new PolygonShape();
        shape.setAsBox(halfW, boxThickness, new Vector2(halfW, mapH + boxThickness), 0f);
        body.createFixture(shape, 0f).setFriction(0f);
        shape.dispose();
    }

    private void loadObjectLayer(MapLayer objectLayer) {
         if(loadObjectConsumer == null) return;

        for(MapObject mapObject : objectLayer.getObjects()){
            if(mapObject instanceof TiledMapTileMapObject tileMapObject){
                loadObjectConsumer.accept(tileMapObject);
            }else{
                throw new GdxRuntimeException("Unsupported object:" + mapObject.getClass().getSimpleName()) ;
            }
        }
    }

    private void loadTiledLayer(TiledMapTileLayer tiledLayer) {
        if(loadTileConsumer == null) return;

        for(int y = 0; y < tiledLayer.getHeight(); y++){
            for(int x = 0; x < tiledLayer.getWidth(); x++){
                TiledMapTileLayer.Cell cell = tiledLayer.getCell(x, y);
                if(cell == null) continue;

                loadTileConsumer.accept(cell.getTile(), x, y);
            }
        }
    }

    public void setMapChangeConsumer(Consumer<TiledMap> mapChangeConsumer){
        this.mapChangeConsumer = mapChangeConsumer;
    }

    public void setLoadObjectConsumer(Consumer<TiledMapTileMapObject> loadObjectConsumer){
        this.loadObjectConsumer = loadObjectConsumer;
    }

    public void setLoadTileConsumer(LoadTileConsumer loadTileConsumer){
        this.loadTileConsumer = loadTileConsumer;
    }

    @FunctionalInterface
    public interface LoadTileConsumer{
        void accept(TiledMapTile tile, float x, float y);
    }
}
