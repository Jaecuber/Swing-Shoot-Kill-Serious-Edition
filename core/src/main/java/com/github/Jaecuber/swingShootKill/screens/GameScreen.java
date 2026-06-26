package com.github.Jaecuber.swingShootKill.screens;

import java.util.function.Consumer;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.github.Jaecuber.swingShootKill.Launcher;
import com.github.Jaecuber.swingShootKill.asset.MapAsset;
import com.github.Jaecuber.swingShootKill.asset.SkinAsset;
import com.github.Jaecuber.swingShootKill.audio.AudioService;
import com.github.Jaecuber.swingShootKill.input.GameControllerState;
import com.github.Jaecuber.swingShootKill.input.KeyboardController;
import com.github.Jaecuber.swingShootKill.logic.RunManager;
import com.github.Jaecuber.swingShootKill.systems.AttackModeSystem;
import com.github.Jaecuber.swingShootKill.systems.CameraSystem;
import com.github.Jaecuber.swingShootKill.systems.ControllerSystem;
import com.github.Jaecuber.swingShootKill.systems.DamageSystem;
import com.github.Jaecuber.swingShootKill.systems.EnemyAiSystem;
import com.github.Jaecuber.swingShootKill.systems.FacingSystem;
import com.github.Jaecuber.swingShootKill.systems.FsmSystem;
import com.github.Jaecuber.swingShootKill.systems.HealthSystem;
import com.github.Jaecuber.swingShootKill.systems.MeleeSystem;
import com.github.Jaecuber.swingShootKill.systems.PhysicsDebugRenderSystem;
import com.github.Jaecuber.swingShootKill.systems.PhysicsMoveSystem;
import com.github.Jaecuber.swingShootKill.systems.PhysicsSystem;
import com.github.Jaecuber.swingShootKill.systems.ProjectileSystem;
import com.github.Jaecuber.swingShootKill.systems.RenderSystem;
import com.github.Jaecuber.swingShootKill.systems.ShooterSystem;
import com.github.Jaecuber.swingShootKill.systems.StaminaSystem;
import com.github.Jaecuber.swingShootKill.tiled.EntitySpawner;
import com.github.Jaecuber.swingShootKill.tiled.TiledAshleyConfig;
import com.github.Jaecuber.swingShootKill.tiled.TiledService;
import com.github.Jaecuber.ui.model.GameViewModel;
import com.github.Jaecuber.ui.view.GameView;

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
    private final RunManager runManager;
    private final GameViewModel viewModel;
    private final Skin skin;
    private final EntitySpawner entitySpawner;

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
        this.entitySpawner = new EntitySpawner(this.tiledAshleyConfig);
        this.viewModel = new GameViewModel(launcher, this.tiledService, this.entitySpawner, this.engine, this.keyboardController);
        this.runManager = new RunManager(this.tiledService, this.entitySpawner, this.engine, launcher.getAssetService(), this.viewModel, this.audioService, this.keyboardController);
        this.skin = launcher.getAssetService().get(SkinAsset.MENU_SCREEN);
        this.mapAsset = mapAsset;
        
        this.engine.addSystem(new ControllerSystem(viewModel));
        this.engine.addSystem(new PhysicsMoveSystem());
        this.engine.addSystem(new FsmSystem());
        this.engine.addSystem(new FacingSystem(launcher.getAssetService()));
        this.engine.addSystem(new PhysicsSystem(physicsWorld, 1/60f));
        this.engine.addSystem(new DamageSystem(viewModel));
        this.engine.addSystem(new HealthSystem(viewModel, keyboardController));
        this.engine.addSystem(new StaminaSystem());
        this.engine.addSystem(new EnemyAiSystem());
        this.engine.addSystem(new CameraSystem(launcher.getCamera()));
        this.engine.addSystem(new AttackModeSystem(entitySpawner));
        this.engine.addSystem(new ShooterSystem(entitySpawner));
        this.engine.addSystem(new MeleeSystem());
        this.engine.addSystem(new ProjectileSystem(launcher.getAssetService()));
        
        this.engine.addSystem(new RenderSystem(launcher.getBatch(), launcher.getViewport(), launcher.getCamera()));
        this.engine.addSystem(new PhysicsDebugRenderSystem(physicsWorld, launcher.getCamera()));
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

        this.stage.addActor(new GameView(stage, skin, this.viewModel));

        Consumer<TiledMap> renderConsumer = this.engine.getSystem(RenderSystem.class)::setMap;
        Consumer<TiledMap> cameraConsumer = this.engine.getSystem(CameraSystem.class)::setMap;

        Consumer<TiledMap> spawnConsumer = map -> entitySpawner.setTileSets(map.getTileSets());
        
        this.tiledService.setMapChangeConsumer(renderConsumer.andThen(cameraConsumer).andThen(spawnConsumer));
        this.tiledService.setLoadObjectConsumer(this.tiledAshleyConfig::onLoadObject);
        this.tiledService.setLoadTileConsumer(this.tiledAshleyConfig::onLoadTile);

        TiledMap tiledMap = this.tiledService.loadMap(mapAsset);
        this.tiledService.setMap(tiledMap);
    }

    @Override
    public void hide() {
        this.engine.removeAllEntities();
        this.stage.clear();
    }

    @Override
    public void render(float delta) {
        delta = Math.min(delta, 1/30f);

        this.engine.update(delta);

        this.runManager.update(delta);
        uiViewport.apply();
        stage.getBatch().setColor(Color.WHITE);
        stage.act(delta);
        stage.draw();
    }
    @Override
    public void dispose() {
        for (EntitySystem system : this.engine.getSystems()){
            if (system instanceof Disposable disposableSystem) {
                disposableSystem.dispose();
            }
        }
    }
}
