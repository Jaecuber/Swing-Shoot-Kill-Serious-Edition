package com.github.Jaecuber.swingShootKill.asset;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public enum MapAsset implements Asset<TiledMap>{
    MAIN_MAP("MainMap.tmx");

    private final AssetDescriptor<TiledMap> descriptor;

    MapAsset(String mapName){
        TmxMapLoader.Parameters parameters = new TmxMapLoader.Parameters();
        parameters.projectFilePath = "map/SSKTiledMap.tiled-project"; 
        this.descriptor = new AssetDescriptor<>("map/" + mapName, TiledMap.class, parameters);
    }

    @Override
    public AssetDescriptor<TiledMap> getDescriptor(){
        return descriptor;
    }
}
