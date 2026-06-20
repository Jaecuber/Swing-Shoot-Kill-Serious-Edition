package com.github.Jaecuber.swingShootKill.screens;

import javax.swing.undo.StateEdit;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.github.Jaecuber.swingShootKill.Launcher;
import com.github.Jaecuber.swingShootKill.asset.MusicAsset;
import com.github.Jaecuber.swingShootKill.asset.SkinAsset;
import com.github.Jaecuber.ui.model.MenuViewModel;
import com.github.Jaecuber.ui.view.MenuView;

public class MenuScreen extends ScreenAdapter{
    private final Launcher launcher;
    private final Stage stage;
    private final Skin skin;
    private final Viewport uiViewport;

    public MenuScreen (Launcher launcher){
        this.launcher = launcher;
        this.uiViewport = new FitViewport(1920f, 1152f);
        this.stage = new Stage(uiViewport, launcher.getBatch());
        this.skin = launcher.getAssetService().get(SkinAsset.MENU_SCREEN);
    }

    @Override
    public void resize(int width, int height){
        uiViewport.update(width, height, true);
    }

    @Override
    public void show(){
        this.launcher.setInputProcessor(stage);
        this.stage.addActor(new MenuView(stage, skin, new MenuViewModel(launcher)));//change when i add the ui later
        this.launcher.getAudioService().playMusic(MusicAsset.MENU);
    }

    @Override
    public void hide(){
        this.stage.clear();
    }

    @Override
    public void render(float delta){
        uiViewport.apply();
        stage.getBatch();
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void dispose(){
        stage.dispose();
    }
}
