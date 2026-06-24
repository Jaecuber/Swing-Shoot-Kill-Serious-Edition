package com.github.Jaecuber.ui.model;

import java.util.Map;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.github.Jaecuber.swingShootKill.Launcher;
import com.github.Jaecuber.swingShootKill.asset.MapAsset;
import com.github.Jaecuber.swingShootKill.asset.MusicAsset;
import com.github.Jaecuber.swingShootKill.asset.SoundAsset;
import com.github.Jaecuber.swingShootKill.screens.GameScreen;
import com.github.Jaecuber.swingShootKill.tiled.EntitySpawner;
import com.github.Jaecuber.swingShootKill.tiled.TiledService;

public class GameViewModel extends ViewModel{
    public static final String PLAYER_DAMAGE = "playerDamage";
    public static final String HEALTH = "health";
    public static final String MAX_HEALTH = "maxHealth";
    public static final String GAME_OVER = "gameOver";

    private Map.Entry<Vector2, Integer> playerDamage;
    private int health;
    private int maxHealth;

    private final Vector2 tempVec2;
    private TiledService tiledService;
    private EntitySpawner entitySpawner;
    private Engine engine;

    public GameViewModel(Launcher launcher, TiledService tiledService, EntitySpawner entitySpawner, Engine engine) {
        super(launcher);
        this.tempVec2 = new Vector2();
        this.tiledService = tiledService;
        this.entitySpawner = entitySpawner;
        this.engine = engine;
    }

    //hp functionality
    public void updateHealthInfo(float maxHealth, float health){
        setMaxHP((int) maxHealth);
        setHP((int) health);
    }

     public void setMaxHP(int maxHP) {
        if (this.maxHealth != maxHP) {
            this.propertyChangeSupport.firePropertyChange(MAX_HEALTH, this.maxHealth, maxHP);
        }
        this.maxHealth = maxHP;
    }
    
    public void setHP(int HP){
        if(this.health != HP){
            this.propertyChangeSupport.firePropertyChange(HEALTH, this.health, HP);
        }
        this.health = HP;
    }

    public int getMaxHP() {
        return maxHealth;
    }

    //Game over functionality
    public void showGameOver(){
        this.launcher.getAudioService().playMusic(MusicAsset.MENU);//change to game over music later
        this.propertyChangeSupport.firePropertyChange(GAME_OVER, false, true);
    }

    public void continueGame(){
        launcher.setScreen(new GameScreen(launcher, MapAsset.MAIN_MAP));
        updateHealthInfo(maxHealth, health);
    }

    public void quitGame(){
        Gdx.app.exit();
    }

    //player damage functionality
    public void playerDamage(int amount, float x, float y){
        float randomNumX = MathUtils.random(0.0f, 2.0f);
        float randomNumY = MathUtils.random(0.0f, 2.0f);
        Vector2 position = new Vector2(x + randomNumX,y + randomNumY);
        this.playerDamage = Map.entry(position, amount);
        this.propertyChangeSupport.firePropertyChange(PLAYER_DAMAGE, null, this.playerDamage);
    }

    //misc
    public void playSound(SoundAsset sound){
        this.launcher.getAudioService().playSound(sound);
    }

}
