package com.github.Jaecuber.swingShootKill.asset;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.audio.Music;

public enum MusicAsset implements Asset<Music>{
    MENU("mainMenu.mp3"),
    AMBIENCE1("ambience1.mp3"),
    AMBIENCE2("ambience2.mp3");

    private final AssetDescriptor<Music> descriptor;

    MusicAsset(String musicFile){
        this.descriptor = new AssetDescriptor<>("audio/music/" + musicFile, Music.class);
    }

    @Override
    public AssetDescriptor<Music> getDescriptor(){
        return descriptor;
    }
}
