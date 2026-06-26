package com.github.Jaecuber.swingShootKill.logic;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Queue;
import com.github.Jaecuber.swingShootKill.Launcher;
import com.github.Jaecuber.swingShootKill.asset.AssetService;
import com.github.Jaecuber.swingShootKill.asset.JsonAsset;
import com.github.Jaecuber.swingShootKill.asset.MusicAsset;
import com.github.Jaecuber.swingShootKill.audio.AudioService;
import com.github.Jaecuber.swingShootKill.component.Enemy;
import com.github.Jaecuber.swingShootKill.data.EnemyBag;
import com.github.Jaecuber.swingShootKill.data.EnemyEntry;
import com.github.Jaecuber.swingShootKill.logic.RunManager.RunState;
import com.github.Jaecuber.swingShootKill.tiled.EntitySpawner;
import com.github.Jaecuber.swingShootKill.tiled.TiledService;
import com.github.Jaecuber.ui.model.GameViewModel;

public class LevelRunner {
    private TiledService tiledService;
    private EntitySpawner entitySpawner;
    private GameViewModel viewModel;
    private Engine engine;
    private AudioService audioService;

    private Array<EnemyEntry> enemyBag;

    private float difficulty;
    private int wave;
    private int prevEnemyAmt;
    private int rand;

    public LevelRunner(TiledService tiledService, EntitySpawner entitySpawner, Engine engine, AssetService assetService, GameViewModel viewModel, AudioService audioService){
        this.tiledService = tiledService;
        this.entitySpawner = entitySpawner;
        this.viewModel = viewModel;
        this.audioService = audioService;
        this.engine = engine;
        this.wave = 0;
        this.prevEnemyAmt = engine.getEntitiesFor(Family.all(Enemy.class).get()).size();
        this.rand = MathUtils.random(1,2);

        String raw = assetService.get(JsonAsset.ENEMY_BAG);
        Json json = new Json();
        EnemyBag bag = json.fromJson(EnemyBag.class, raw);
        enemyBag = bag.getEnemies();
    }

    public void runWave(){
        this.wave++;
        this.difficulty = calcDifficulty();
        viewModel.updateWave(wave);
        spawnWave(difficulty);

        
        if(rand == 1){
            switch (wave) {
                case 1 -> audioService.playMusic(MusicAsset.AMBIENCE1);
                case 8 -> audioService.playMusic(MusicAsset.AMBIENCE2);
            }
        }else{
            switch (wave) {
                case 1 -> audioService.playMusic(MusicAsset.AMBIENCE2);
                case 8 -> audioService.playMusic(MusicAsset.AMBIENCE1);
            }
        }
    }

    private void spawnWave(float difficulty) {
        Array<Vector2> spawns = tiledService.getSpawns();
        Queue<String> enemyQueue = createQueue(difficulty);
        while(enemyQueue.notEmpty()){
            String enemy = enemyQueue.removeFirst();
            Vector2 randomSpawn = spawns.random();
            entitySpawner.spawnEntity(enemy, randomSpawn);
        }
    }

    private Queue<String> createQueue(float difficulty) {
        Queue<String> queue = new Queue<>();
        Array<String> validEnemies = getValidEnemies(difficulty);
        int numEnemies = MathUtils.round((1.0f + 1.50f) * (float) Math.pow(difficulty, 0.80f));
        for(int i = 0; i < numEnemies; i++){
            queue.addFirst(validEnemies.random());
        }
        return queue;
    }

    private Array<String> getValidEnemies(float difficulty) {
        Array<String> validEnemies = new Array<>();

        for(EnemyEntry enemy : enemyBag){
            if(enemy.getMinDiff() < difficulty){
                validEnemies.add(enemy.getName());
            }
        }

        return validEnemies;
    }

    private float calcDifficulty() {
        return (float) wave * (float) Math.pow(MathUtils.E, 0.5f * (wave / 15.0f));
    }

    public boolean waveComplete(){
        return engine.getEntitiesFor(Family.all(Enemy.class).get()).size() == 0;
    }
}
