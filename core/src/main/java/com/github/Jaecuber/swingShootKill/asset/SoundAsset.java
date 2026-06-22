package com.github.Jaecuber.swingShootKill.asset;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.audio.Sound;

public enum SoundAsset implements Asset<Sound>{
    CLICK("click.mp3"), //CHANGE LATER
    HOVER("hover.mp3");

    private final AssetDescriptor<Sound> descriptor;

    SoundAsset(String sfxFile){
        this.descriptor = new AssetDescriptor<>("audio/sfx/" + sfxFile, Sound.class);
    }

    @Override
    public AssetDescriptor<Sound> getDescriptor(){
        return descriptor;
    }
}
