package com.github.Jaecuber.swingShootKill.tiled;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
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
import com.github.Jaecuber.swingShootKill.Launcher;
import com.github.Jaecuber.swingShootKill.asset.AssetService;
import com.github.Jaecuber.swingShootKill.asset.AtlasAsset;
import com.github.Jaecuber.swingShootKill.combat.BasicAttack;
import com.github.Jaecuber.swingShootKill.component.AttackMode;
import com.github.Jaecuber.swingShootKill.component.CameraFollow;
import com.github.Jaecuber.swingShootKill.component.Controller;
import com.github.Jaecuber.swingShootKill.component.Enemy;
import com.github.Jaecuber.swingShootKill.component.Enemy.EnemyAIState;
import com.github.Jaecuber.swingShootKill.component.Facing;
import com.github.Jaecuber.swingShootKill.component.Graphic;
import com.github.Jaecuber.swingShootKill.component.Health;
import com.github.Jaecuber.swingShootKill.component.MapEntity;
import com.github.Jaecuber.swingShootKill.component.Move;
import com.github.Jaecuber.swingShootKill.component.Physics;
import com.github.Jaecuber.swingShootKill.component.Player;
import com.github.Jaecuber.swingShootKill.component.Transform;
import com.github.Jaecuber.swingShootKill.component.AttackMode.ATTACK_MODE;
import com.github.Jaecuber.swingShootKill.component.Facing.FacingDirection;
import com.github.Jaecuber.swingShootKill.component.Fsm;

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

    public Entity spawnFromTile(TiledMapTile tile, float x, float y){

        //CORRESPONDS TO ACTUAL GAME COORDINATES INSTEAD OF MAP COORDINATES
        //HELPS WITH RELATIVE SPAWNING
        x /= Launcher.UNIT_SCALE;
        y /= Launcher.UNIT_SCALE;


        Entity entity = this.engine.createEntity();
        TextureRegion textureRegion = getTextureRegion(tile);
        int z = tile.getProperties().get("z", 1, Integer.class);

        entity.add(new Graphic(textureRegion, Color.WHITE.cpy()));
        addEntityTransform(x, y, z, 
            textureRegion.getRegionWidth(), textureRegion.getRegionHeight(),
            1f, 1f, entity);


        addEntityMove(tile, entity);
        
        BodyDef.BodyType bodyType = getObjectBodyType(tile);
        addEntityPhysics(tile.getObjects(), bodyType, Vector2.Zero, entity);
        addEntityFacing(tile, entity);
        

        entity.add(new MapEntity());

        this.engine.addEntity(entity);
        return entity;
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

        addEntityController(tileMapObject, entity);
        addEntityMove(tile, entity);
        BodyDef.BodyType bodyType = getObjectBodyType(tile);
        addEntityPhysics(tile.getObjects(), bodyType, Vector2.Zero, entity);
        addEntityCameraFollow(tileMapObject, entity);
        addEntityPlayer(tileMapObject, entity);
        addEntityMapEntity(tileMapObject, entity);
        addEntityFacing(tile, entity);
        addEntityHealth(tile, entity);
        addEntityEnemy(tile, entity);
        addEntityAttackMode(tileMapObject, entity);

        this.engine.addEntity(entity);
    }

    private void addEntityAttackMode(TiledMapTileMapObject tileMapObject, Entity entity){
        String attackModeStr = tileMapObject.getProperties().get("attackMode", "", String.class);
        if(attackModeStr.isBlank()) return;

        entity.add(new AttackMode(ATTACK_MODE.valueOf(attackModeStr)));
    }

    private void addEntityFacing(TiledMapTile tile, Entity entity){
        FileTextureData textureData = (FileTextureData) tile.getTextureRegion().getTexture().getTextureData();
        String atlasKey = textureData.getFileHandle().nameWithoutExtension();

        entity.add(new Facing(FacingDirection.DOWN, atlasKey));
    }

    private void addEntityHealth(TiledMapTile tile, Entity entity) {
        float health = tile.getProperties().get("health", 0.0f, Float.class);
        if(health == 0.0f) return;

        float regen = tile.getProperties().get("regen", 0.0f, Float.class);
        entity.add(new Health(health, regen));
    }

    private void addEntityEnemy(TiledMapTile tile, Entity entity) {
        boolean enemy = tile.getProperties().get("enemy", false, Boolean.class);
        if(!enemy) return;

        String stateStr = tile.getProperties().get("state", null, String.class);
        EnemyAIState state = EnemyAIState.valueOf(stateStr);
        float speed = tile.getProperties().get("speed", 0f, Float.class);
        float cooldown = tile.getProperties().get("cooldown", 0f, Float.class);
        float damage = tile.getProperties().get("damage", 0.0f, Float.class);

        String type = tile.getProperties().get("type", null, String.class);

        Enemy enemyComponent = new Enemy(state, speed, cooldown, damage);
        
        switch (type) {
            case "basic" -> enemyComponent.setMoveset(new BasicAttack());
        }

        Fsm fsm = new Fsm(entity);
        entity.add(fsm);

        entity.add(enemyComponent);
    }

    private void addEntityPlayer(TiledMapTileMapObject tileMapObject, Entity entity) {
        if ("Player".equals(tileMapObject.getName())) {
            entity.add(new Player());
        }else if ("playerSpawn".equals(tileMapObject.getName())) {
            ImmutableArray<Entity> players = engine.getEntitiesFor(Family.all(Player.class).get());
            if (players.size() == 0) return;
            
            Entity player = players.first();
            Transform transform = Transform.MAPPER.get(player);
            Physics physics = Physics.MAPPER.get(player);
            
            transform.getPosition().set(tileMapObject.getX() * Launcher.UNIT_SCALE, tileMapObject.getY() * Launcher.UNIT_SCALE);
            physics.getBody().setTransform(transform.getPosition(), 0f);
            physics.getBody().setLinearVelocity(Vector2.Zero);
            
            engine.removeEntity(entity);
        }
    }

    private void addEntityMapEntity(TiledMapTileMapObject tileMapObject, Entity entity) {
        if (!"Player".equals(tileMapObject.getName())) {
            entity.add(new MapEntity());
        }
    }

    private void addEntityCameraFollow(TiledMapTileMapObject mapObject, Entity entity) {
        boolean camFollow = mapObject.getProperties().get("camFollow", false, Boolean.class);
        if(!camFollow) return;

        entity.add(new CameraFollow());
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

    private void addEntityMove(TiledMapTile tile, Entity entity) {
        float speed = tile.getProperties().get("speed", 0f, Float.class);
        if(speed == 0f) return;

        entity.add(new Move(speed));
    }

    private void addEntityController(TiledMapTileMapObject tileMapObject, Entity entity) {
        boolean controller = tileMapObject.getProperties().get("controller", false, Boolean.class);
        if(!controller) return;

        entity.add(new Controller());
    }

    private void addEntityTransform(float x, float y, int z, int w, int h, float scaleX, float scaleY, Entity entity) {
        Vector2 position = new Vector2(x,y);
        Vector2 size = new Vector2(w,h);
        Vector2 scaling = new Vector2(scaleX, scaleY);

        position.scl(Launcher.UNIT_SCALE);
        size.scl(Launcher.UNIT_SCALE);

        entity.add(new Transform(position, z, size, scaling, 0f));
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
