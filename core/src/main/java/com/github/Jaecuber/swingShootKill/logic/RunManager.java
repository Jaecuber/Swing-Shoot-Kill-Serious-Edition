package com.github.Jaecuber.swingShootKill.logic;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.badlogic.gdx.math.MathUtils;
import com.github.Jaecuber.swingShootKill.ai.GameState;
import com.github.Jaecuber.swingShootKill.asset.AssetService;
import com.github.Jaecuber.swingShootKill.audio.AudioService;
import com.github.Jaecuber.swingShootKill.input.KeyboardController;
import com.github.Jaecuber.swingShootKill.tiled.EntitySpawner;
import com.github.Jaecuber.swingShootKill.tiled.TiledService;
import com.github.Jaecuber.ui.model.GameViewModel;

public class RunManager {
    private RunState runState;
    private TiledService tiledService;
    private EntitySpawner entitySpawner;
    private LevelRunner levelRunner;
    private AssetService assetService;
    private GameViewModel viewModel;
    private AudioService audioService;
    private KeyboardController keyboardController;
    private Engine engine;
    private DefaultStateMachine<RunManager, GameState> gameFsm;

    private boolean playingState;

    private float intermissionTimer = 5.0f;//5 seconds

    public RunManager(TiledService tiledService, EntitySpawner entitySpawner, Engine engine, AssetService assetService, GameViewModel viewModel, AudioService audioService, KeyboardController keyboardController){
        this.runState = RunState.LEVEL_INTERMISSION;
        this.tiledService = tiledService;
        this.entitySpawner = entitySpawner;
        this.assetService = assetService;
        this.audioService = audioService;
        this.keyboardController = keyboardController;
        this.engine = engine;
        this.viewModel = viewModel;
        this.gameFsm = new DefaultStateMachine<RunManager, GameState>(this, GameState.LEVEL_INTERMISSION);

        this.levelRunner = new LevelRunner(this.tiledService, this.entitySpawner, this.engine, this.assetService, this.viewModel, this.audioService);
    }

    public void update(float deltaTime){
        gameFsm.update();
        switch (getState()) {
            case LEVEL_INTERMISSION -> intermission(deltaTime);
            case STARTING_WAVE -> startWave();
            case PLAYING -> playing();
        }
    }
    private void playing() {
        
    }

    private void startWave() {
        levelRunner.runWave();
        playingState = true;
    }

    public boolean waveComplete(){
        playingState = false;
        return levelRunner.waveComplete();
    }

    private void intermission(float deltaTime) {
        tickIntermissionTime(deltaTime);
    }

    private void tickIntermissionTime(float deltaTime) {
        intermissionTimer -= deltaTime;
        //viewModel.updateTimer(MathUtils.round(intermissionTimer));
    }

    public boolean intermissionTimerOver(){
        return intermissionTimer <= 0;
    }

    public boolean getPlayingState(){
        return this.playingState;
    }

    public DefaultStateMachine<RunManager, GameState> getGameFsm(){
        return this.gameFsm;
    }

    public void setState(RunState runState){
        switch (runState) {
            case LEVEL_INTERMISSION -> {this.intermissionTimer = 5.0f;}
            case STARTING_WAVE -> {}
            case PLAYING -> {}
        }
    }

    public RunState getState(){
        return this.runState;
    }

    public enum RunState{
        LEVEL_INTERMISSION, STARTING_WAVE, PLAYING
    }

    
}
