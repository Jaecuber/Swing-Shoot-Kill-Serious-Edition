package com.github.Jaecuber.swingShootKill.tiled;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FileTextureData;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.github.Jaecuber.swingShootKill.Launcher;
import com.github.Jaecuber.swingShootKill.asset.AssetService;
import com.github.Jaecuber.swingShootKill.asset.AtlasAsset;
import com.github.Jaecuber.swingShootKill.component.CameraFollow;
import com.github.Jaecuber.swingShootKill.component.Controller;
import com.github.Jaecuber.swingShootKill.component.Graphic;
import com.github.Jaecuber.swingShootKill.component.Move;
import com.github.Jaecuber.swingShootKill.component.Physics;
import com.github.Jaecuber.swingShootKill.component.Transform;

public class TiledAshleyConfig {
    private static final Vector2 DEFAULT_PHYSICS_SCALING = new Vector2(1f, 1f);

    private final Engine engine;
    private final AssetService assetService;
    private final World physicsWorld;

    public TiledAshleyConfig(Engine engine, AssetService assetService, World physicsWorld) {
        this.engine = engine;
        this.assetService = assetService;
        this.physicsWorld = physicsWorld;
    }

    public void onLoadTile(TiledMapTile tileMapTile, float x, float y){
        boolean spawn = tileMapTile.getProperties().get("spawn", false, Boolean.class);
        if(spawn){
            createBody(
                tileMapTile.getObjects(),
                new Vector2(x, y),
                DEFAULT_PHYSICS_SCALING,
                BodyDef.BodyType.StaticBody,
                Vector2.Zero,
                "spawn"
            );
        }else{
            createBody(
                tileMapTile.getObjects(),
                new Vector2(x, y),
                DEFAULT_PHYSICS_SCALING,
                BodyDef.BodyType.StaticBody,
                Vector2.Zero,
                "environment"
            );
        }
        
    }

     private Body createBody(MapObjects mapObjects, Vector2 position, Vector2 scaling, 
        BodyType bodyType,Vector2 relativeTo, Object userData) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = bodyType;
        bodyDef.position.set(position);
        bodyDef.fixedRotation = true;

        Body body = physicsWorld.createBody(bodyDef);
        body.setUserData(userData);
        for(MapObject object : mapObjects){
            FixtureDef fixtureDef = TiledPhysics.fixtureDefOf(object, scaling, relativeTo);
            Fixture fixture = body.createFixture(fixtureDef);
            fixture.setUserData(object.getName());
            fixtureDef.shape.dispose();
        }
        return body;
    }

    public void onLoadObject(TiledMapTileMapObject tileMapObject){
        Entity entity = this.engine.createEntity();
        TiledMapTile tile = tileMapObject.getTile();
        TextureRegion textureRegion = getTextureRegion(tile);
        int z = tile.getProperties().get("z", 1, Integer.class);

       

        entity.add(new Graphic(textureRegion, Color.WHITE.cpy()));

        addEntityTransform(
            tileMapObject.getX(), tileMapObject.getY(), z, 
            textureRegion.getRegionWidth(), textureRegion.getRegionHeight(), 
            tileMapObject.getScaleX(), tileMapObject.getScaleY(), 
            entity
        );

        //add component methods down here

        addEntityMove(tile, entity);
        BodyDef.BodyType bodyType = getObjectBodyType(tile);
        addEntityPhysics(tile.getObjects(), bodyType, DEFAULT_PHYSICS_SCALING, entity);

        addEntityCamFollow(tileMapObject, entity);
        addEntityController(tileMapObject, entity);

        this.engine.addEntity(entity);
    }

    private TextureRegion getTextureRegion(TiledMapTile tile){
        String atlastAssetStr = tile.getProperties().get("atlasAsset", AtlasAsset.OBJECTS.name(), String.class);
        AtlasAsset atlasAsset = AtlasAsset.valueOf(atlastAssetStr);
        TextureAtlas textureAtlas = this.assetService.get(atlasAsset);
        FileTextureData textureData = (FileTextureData) tile.getTextureRegion().getTexture().getTextureData();
        String atlasKey = textureData.getFileHandle().nameWithoutExtension();
        TextureAtlas.AtlasRegion region = textureAtlas.findRegion(atlasKey + "/" + atlasKey);
        if(region != null){
            return region;
        }
        
        return tile.getTextureRegion();
    }

    private void addEntityTransform(
        float x, float y, int z,
        float w, float h,
        float scaleX, float scaleY,
        Entity entity
    ){
        Vector2 position = new Vector2(x,y);
        Vector2 size = new Vector2(w,h);
        Vector2 scaling = new Vector2(scaleX, scaleY);

        position.scl(Launcher.UNIT_SCALE);
        size.scl(Launcher.UNIT_SCALE);

      
        entity.add(new Transform(position, z, size, scaling, 0f));
    }

    private void addEntityCamFollow(TiledMapTileMapObject tileMapObject, Entity entity){
        boolean hasCamFollow = tileMapObject.getProperties().get("camFollow", false, Boolean.class);
        if(!hasCamFollow) return;

        entity.add(new CameraFollow());
    }

    private void addEntityController(TiledMapTileMapObject tileMapObject, Entity entity){
        boolean hasController = tileMapObject.getProperties().get("controller", false, Boolean.class);
        if(!hasController) return;

        entity.add(new Controller());
    }

    private void addEntityMove(TiledMapTile tile, Entity entity){
        float speed = tile.getProperties().get("speed", 0.0f, Float.class);
        if(speed == 0f) return;

        entity.add(new Move(speed));
    }

    private BodyType getObjectBodyType(TiledMapTile tile) {
        String classType = tile.getProperties().get("type", "", String.class);
        if("Prop".equals(classType)){
            return BodyDef.BodyType.StaticBody;
        }
        return BodyDef.BodyType.DynamicBody;
    }

    private void addEntityPhysics(MapObjects objects, BodyType bodyType, Vector2 relativeTo, Entity entity) {
        if(objects.getCount() == 0) return;
        
        Transform transform = Transform.MAPPER.get(entity);
        Body body = createBody(objects, transform.getPosition(), transform.getScaling(), bodyType, relativeTo, entity);
        entity.add(new Physics(body, transform.getPosition().cpy()));
    }



}
