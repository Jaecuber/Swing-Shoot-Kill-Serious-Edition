package com.github.Jaecuber.ui.model;

import java.util.Map;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.Json;
import com.github.Jaecuber.swingShootKill.Launcher;
import com.github.Jaecuber.swingShootKill.asset.JsonAsset;
import com.github.Jaecuber.swingShootKill.asset.MapAsset;
import com.github.Jaecuber.swingShootKill.asset.SoundAsset;
import com.github.Jaecuber.swingShootKill.component.Player;
import com.github.Jaecuber.swingShootKill.component.UpgradeTags;
import com.github.Jaecuber.swingShootKill.data.UpgradeBag;
import com.github.Jaecuber.swingShootKill.data.UpgradeClass;
import com.github.Jaecuber.swingShootKill.input.KeyboardController;
import com.github.Jaecuber.swingShootKill.screens.GameScreen;
import com.github.Jaecuber.swingShootKill.systems.CameraSystem;
import com.github.Jaecuber.swingShootKill.systems.CoinsSystem;
import com.github.Jaecuber.swingShootKill.systems.ControllerSystem;
import com.github.Jaecuber.swingShootKill.systems.PhysicsDebugRenderSystem;
import com.github.Jaecuber.swingShootKill.systems.RenderSystem;
import com.github.Jaecuber.swingShootKill.systems.UpgradeSystem;
import com.github.Jaecuber.swingShootKill.tiled.EntitySpawner;
import com.github.Jaecuber.swingShootKill.tiled.TiledService;

public class GameViewModel extends ViewModel{
    public static final String PLAYER_DAMAGE = "playerDamage";
    public static final String HEALTH = "health";
    public static final String MAX_HEALTH = "maxHealth";
    public static final String STAMINA = "stamina";
    public static final String MAX_STAMINA = "maxStamina";
    public static final String GAME_OVER = "gameOver";
    public static final String OPEN_SHOP = "openShop";
    public static final String COINS = "coins";
    public static final String WAVE = "wave";
    public static final String TIMER = "timer";
    public static final String DAMAGED = "damaged";
    public static final String SPIN_BULLETS = "spinBullets";
    public static final String GAME_COMPLETE = "gameComplete";

    private Map.Entry<Vector2, Integer> playerDamage;
    private int health;
    private int maxHealth;
    private int stamina;
    private int maxStamina;
    private int wave;
    private int time;
    private int coins;
    private boolean shopOpen = false;

    private final Vector2 tempVec2;
    private TiledService tiledService;
    private EntitySpawner entitySpawner;
    private KeyboardController keyboardController;
    private Engine engine;

    public GameViewModel(Launcher launcher, TiledService tiledService, EntitySpawner entitySpawner, Engine engine, KeyboardController keyboardController) {
        super(launcher);
        this.tempVec2 = new Vector2();
        this.tiledService = tiledService;
        this.entitySpawner = entitySpawner;
        this.engine = engine;
        this.keyboardController = keyboardController;
    }

    //hp functionality
    public void updateHealthInfo(float maxHealth, float health){
        setMaxHP((int) maxHealth);
        setHP((int) health);
    }

     public void setMaxHP(int maxHP) {
        if (this.maxHealth != maxHP) {
            firePropertyChange(MAX_HEALTH, this.maxHealth, maxHP);
        }
        this.maxHealth = maxHP;
    }
    
    public void setHP(int HP){
        if(this.health != HP){
            firePropertyChange(HEALTH, this.health, HP);
        }
        this.health = HP;
    }

    public int getMaxHP() {
        return maxHealth;
    }

    public void displayDamage(){
        firePropertyChange(DAMAGED, false, true);
    }

    //Stamina functionality
    public void updateStaminaInfo(float maxStamina, float stamina){
        setMaxStamina((int) maxStamina);
        setStamina((int) stamina);
    }

    private void setStamina(int stamina) {
        if(this.stamina != stamina){
            firePropertyChange(STAMINA, this.stamina, stamina);
        }
        this.stamina = stamina;
    }

    private void setMaxStamina(int maxStamina) {
        if(this.maxStamina != maxStamina){
            firePropertyChange(MAX_STAMINA, this.maxStamina, maxStamina);
        }
        this.maxStamina = maxStamina;
    }

    //Game over functionality
    public void showGameOver(){
        //this.launcher.getAudioService().playMusic(MusicAsset.MENU);//change to game over music later
        firePropertyChange(GAME_OVER, false, true);
    }

    public void continueGame(){
        launcher.setScreen(new GameScreen(launcher, MapAsset.MAIN_MAP));
        updateHealthInfo(maxHealth, health);
    }

    public void quitGame(){
        Gdx.app.exit();
    }

    //Game Complete functionality
    public void showWinScreen(){
        this.launcher.getAudioService().stopCurrentMusic();
        playSound(SoundAsset.CLICK);
        firePropertyChange(GAME_COMPLETE, false, true);
    }

    //player damage functionality
    public void playerDamage(int amount, float x, float y){
        float randomNumX = MathUtils.random(0.0f, 2.0f);
        float randomNumY = MathUtils.random(0.0f, 2.0f);
        Vector2 position = new Vector2(x + randomNumX,y + randomNumY);
        this.playerDamage = Map.entry(position, amount);
        firePropertyChange(PLAYER_DAMAGE, null, this.playerDamage);
    }

    //upgrade shop functionality
    public void openShop(){
        if(!shopOpen){
            playSound(SoundAsset.SHOP_OPEN);
        }
        firePropertyChange(OPEN_SHOP, shopOpen, !shopOpen);
        this.shopOpen = !this.shopOpen;
    }

    public UpgradeClass loadUpgradeClasses(){
        String raw = this.launcher.getAssetService().get(JsonAsset.UPGRADE_BAG);
        Json json = new Json();
        UpgradeBag upgradeBag = json.fromJson(UpgradeBag.class, raw);
        System.out.println(upgradeBag.getUpgradeTypes());
        return upgradeBag.getUpgradeTypes();
    }

    public void addUpgradeTag(String tag, int amt){
        Entity playerEntity = getPlayerEntity();
        UpgradeTags upgradeTags = UpgradeTags.MAPPER.get(playerEntity);
        if(upgradeTags == null) throw new GdxRuntimeException("No upgrade tags for player entity");

        upgradeTags.addTag(tag, amt);
    }

    //info HUD functionality
    public void updateCoins(int coins){
        if(this.coins != coins){
            firePropertyChange(COINS, this.coins, coins);
        }
        this.coins = coins;
    }

    public void updateWave(int wave){
        if(this.wave != wave){
            firePropertyChange(WAVE, this.wave, wave);
        }
        playSound(SoundAsset.WAVE_BWAAM);
        playSound(SoundAsset.WAVE_DRUM);
        this.wave = wave;
    }

    public void updateTimer(int time){
        if(this.time != time){
            playSound(SoundAsset.TIMER_TICK);
            firePropertyChange(TIMER, this.time, time);
        }
        this.time = time;
    }

    //Revolver functionality
    public void displaySpin(String bulletName){
        firePropertyChange(SPIN_BULLETS, "a", bulletName);
    }

    //misc
    public void playSound(SoundAsset sound){
        this.launcher.getAudioService().playSound(sound);
    }

    public Entity getPlayerEntity(){
        ImmutableArray<Entity> entities = engine.getEntitiesFor(Family.all( Player.class, UpgradeTags.class).get());
        if(entities.size() > 0){
            return entities.first();
        }
        throw new GdxRuntimeException("No player/upgrade entity");
    }

    public void pauseGame(){
        for(EntitySystem system : engine.getSystems()){
            if(system instanceof UpgradeSystem || system instanceof RenderSystem || system instanceof CameraSystem || system instanceof ControllerSystem || system instanceof CoinsSystem){
                continue;
            }
            system.setProcessing(false);
        }
    }

    public void resumeGame(){
        for(EntitySystem system : engine.getSystems()){
            if(system instanceof PhysicsDebugRenderSystem) continue;
            system.setProcessing(true);
        }
    }
}
