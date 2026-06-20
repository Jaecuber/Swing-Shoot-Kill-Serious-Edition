package com.github.Jaecuber.swingShootKill;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.github.Jaecuber.swingShootKill.asset.AssetService;
import com.github.Jaecuber.swingShootKill.audio.AudioService;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Launcher extends Game {
    //CHANGE THESE LATER IF NEEDED
    public static final float WORLD_WIDTH = 20f;
    public static final float WORLD_HEIGHT = 12f;
    public static final float UNIT_SCALE = 1f/16f;

    private Batch batch;
    private OrthographicCamera camera;
    private Viewport viewport;
    private AssetService assetService;
    private AudioService audioService;
    private InputMultiplexer inputMultiplexer;

    private final Map<Class<? extends Screen>, Screen> screenCache = new HashMap<>();

    @Override
    public void create() {
        this.inputMultiplexer = new InputMultiplexer();
        this.batch = new SpriteBatch();
        this.camera = new OrthographicCamera();
        this.viewport = new FitViewport(WORLD_HEIGHT, UNIT_SCALE, camera);
        this.audioService = new AudioService(this.assetService);
    }

    @Override
    public void resize(int width, int height){
        viewport.update(width, height, true);
        super.resize(width, height);
    }

    public void addScreen(Screen screen){
        screenCache.put(screen.getClass(), screen);
    }

    public void removeScreen(Screen screen){
        screenCache.remove(screen.getClass(), screen);
    }

    public void setScreen(Class<? extends Screen> screenClass){
        Screen screen = screenCache.get(screenClass);
        if(screen == null){
            throw new GdxRuntimeException("Screen of type " + screenClass + " not found in screen cache");
        }
        super.setScreen(screen);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        super.render();
    }

    @Override
    public void dispose() {
       screenCache.values().forEach(Screen::dispose);
       screenCache.clear();

       this.batch.dispose();
       this.assetService.dispose();
    }

     public Batch getBatch(){
        return batch;
    }

    public AssetService getAssetService(){
        return assetService;
    }

    public Viewport getViewport(){
        return viewport;
    }

    public OrthographicCamera getCamera(){
        return camera;
    }

    public AudioService getAudioService(){
        return audioService;
    }

    public void setInputProcessor(InputProcessor... processors){
        inputMultiplexer.clear();
        if(processors == null) return;

        for(InputProcessor processor : processors){
            inputMultiplexer.addProcessor(processor);
        }
    }
}

