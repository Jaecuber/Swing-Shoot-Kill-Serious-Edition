package com.github.Jaecuber.swingShootKill.tiled;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.TiledMapTileSets;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.GdxRuntimeException;

public class EntitySpawner {
    private final TiledAshleyConfig tiledAshleyConfig;
    private TiledMapTileSets tileSets;

    public EntitySpawner(TiledAshleyConfig tiledAshleyConfig){
        this.tiledAshleyConfig = tiledAshleyConfig;
    }

    public void setTileSets(TiledMapTileSets tileSets){
        this.tileSets = tileSets;
    }

    public Entity spawnEntity(String name, Vector2 position){
        if (tileSets == null) throw new GdxRuntimeException("Set tilesets first");

        for(TiledMapTileSet set : tileSets) {
        System.out.println("Checking Tileset: " + set.getName());
    }

        TiledMapTile tile = findTileByName(name);
        if(tile == null) throw new GdxRuntimeException("Unknown enemy: " + name);
        return tiledAshleyConfig.spawnFromTile(tile, position.x, position.y);
    }

    private TiledMapTile findTileByName(String name) {
        for(TiledMapTileSet tileSet : tileSets){
            for(TiledMapTile tile : tileSet){
                String tileName = tile.getProperties().get("name", "", String.class);
                if(tileName.equals(name)) return tile;
            }
        }
        return null;
    }

}
