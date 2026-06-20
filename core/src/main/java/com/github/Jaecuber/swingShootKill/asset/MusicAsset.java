package com.github.Jaecuber.swingShootKill.asset;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.audio.Music;

public enum MusicAsset implements Asset<Music>{
    MENU("mainMenu.mp3"); //CHANGE LATER

    private final AssetDescriptor<Music> descriptor;

    MusicAsset(String musicFile){
        this.descriptor = new AssetDescriptor<>("audio/music/", Music.class);
    }

    @Override
    public AssetDescriptor<Music> getDescriptor(){
        return descriptor;
    }
}
