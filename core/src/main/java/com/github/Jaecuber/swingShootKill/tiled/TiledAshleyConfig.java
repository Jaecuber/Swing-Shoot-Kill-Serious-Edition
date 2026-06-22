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
import com.github.Jaecuber.swingShootKill.asset.AssetService;
import com.github.Jaecuber.swingShootKill.asset.AtlasAsset;
import com.github.Jaecuber.swingShootKill.component.Graphic;

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

        entity.add(new Graphic(textureRegion, Color.WHITE.cpy()));

        //add component methods down here
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
}
