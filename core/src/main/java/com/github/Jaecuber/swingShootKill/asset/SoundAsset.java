package com.github.Jaecuber.swingShootKill.asset;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.audio.Sound;

public enum SoundAsset implements Asset<Sound>{
    CLICK("click.mp3"), //CHANGE LATER
    HOVER("hover.mp3"),
    ERROR("error.mp3"),
    GUNSHOT("gunshot.mp3"),
    REVOLVER_SPIN("revolverSpin.mp3"),
    SWORD_SPIN("swordSpin.mp3"),
    TIMER_TICK("timerTick.mp3"),
    WAVE_BWAAM("waveBwaam.mp3"),
    WAVE_DRUM("waveDrum.mp3"),
    SHOP_OPEN("shopOpen.mp3"),
    WOOSH("woosh.mp3");

    private final AssetDescriptor<Sound> descriptor;

    SoundAsset(String sfxFile){
        this.descriptor = new AssetDescriptor<>("audio/sfx/" + sfxFile, Sound.class);
    }

    @Override
    public AssetDescriptor<Sound> getDescriptor(){
        return descriptor;
    }
}
