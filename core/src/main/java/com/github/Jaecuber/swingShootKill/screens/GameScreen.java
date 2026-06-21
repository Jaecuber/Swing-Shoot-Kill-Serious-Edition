package com.github.Jaecuber.swingShootKill.screens;

import java.util.function.Consumer;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.github.Jaecuber.swingShootKill.Launcher;
import com.github.Jaecuber.swingShootKill.asset.MapAsset;
import com.github.Jaecuber.swingShootKill.asset.SkinAsset;
import com.github.Jaecuber.swingShootKill.audio.AudioService;
import com.github.Jaecuber.swingShootKill.input.GameControllerState;
import com.github.Jaecuber.swingShootKill.input.KeyboardController;
import com.github.Jaecuber.swingShootKill.tiled.TiledAshleyConfig;
import com.github.Jaecuber.swingShootKill.tiled.TiledService;

public class GameScreen extends ScreenAdapter{
    private final Engine engine;
    private final TiledService tiledService;
    private final TiledAshleyConfig tiledAshleyConfig;
    private final KeyboardController keyboardController;
    private final Launcher launcher;
    private final World physicsWorld;
    private final AudioService audioService;
    private final Stage stage;
    private final Viewport uiViewport;
    private final Skin skin;

    private MapAsset mapAsset;

    public GameScreen(Launcher launcher, MapAsset mapAsset){
        this.launcher = launcher;
        this.physicsWorld = new World(Vector2.Zero, true);
        this.physicsWorld.setAutoClearForces(false);
        this.engine = new Engine();
        this.tiledService = new TiledService(launcher.getAssetService(), physicsWorld);
        this.tiledAshleyConfig = new TiledAshleyConfig(this.engine, launcher.getAssetService(), physicsWorld);
        this.keyboardController = new KeyboardController(GameControllerState.class, engine);
        this.audioService = launcher.getAudioService();
        this.uiViewport = new FitViewport(1500f, 900f);
        this.stage = new Stage(uiViewport, launcher.getBatch());
        this.skin = launcher.getAssetService().get(SkinAsset.MENU_SCREEN);
        this.mapAsset = mapAsset;

    }

    @Override
    public void resize(int width, int height){
        super.resize(width, height);
        this.uiViewport.update(width, height, true);
    }

    @Override
    public void show(){
        launcher.setInputProcessor(stage, keyboardController);
        keyboardController.setActiveState(GameControllerState.class);

        
    }
}
