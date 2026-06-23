package com.github.Jaecuber.swingShootKill.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.github.Jaecuber.swingShootKill.Launcher;
import com.github.Jaecuber.swingShootKill.asset.AssetService;
import com.github.Jaecuber.swingShootKill.asset.AtlasAsset;
import com.github.Jaecuber.swingShootKill.asset.SkinAsset;
import com.github.Jaecuber.swingShootKill.asset.SoundAsset;

public class LoadingScreen extends ScreenAdapter{
    private final Launcher launcher;
    private final AssetService assetService;

    public LoadingScreen(Launcher launcher, AssetService assetService){
        this.launcher = launcher;
        this.assetService = assetService;
    }

    @Override //note: whoever adds an asset, make sure to iterate throug the values and queue them to load
    //to the note above: THIS ONLY APPLIES TO ASSETS THAT MUST BE LOADED BEFORE SWITCHING TO THE MENU
    public void show(){
        for(AtlasAsset atlas : AtlasAsset.values()){
            assetService.queue(atlas);
        }
        for(SoundAsset sound : SoundAsset.values()){
            assetService.queue(sound);
        }
    
        for(AtlasAsset atlas : AtlasAsset.values()){
            assetService.queue(atlas);
        }


        assetService.queue(SkinAsset.MENU_SCREEN);
        
    }

    @Override
    public void render(float delta){
        if (this.assetService.update()){
            Gdx.app.debug("Loading Screen", "Finished asset loading");
            createScreens();
            this.launcher.removeScreen(this);
            this.dispose();
            this.launcher.setScreen(MenuScreen.class);
        }
    }

    private void createScreens() {
        this.launcher.addScreen(new MenuScreen(this.launcher));
    }
}
