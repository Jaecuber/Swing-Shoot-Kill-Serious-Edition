package com.github.Jaecuber.swingShootKill.screens;

import javax.swing.undo.StateEdit;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.github.Jaecuber.swingShootKill.Launcher;

public class MenuScreen extends ScreenAdapter{
    private final Launcher launcher;
    private final Stage stage;
    private final Skin skin;
    private final Viewport uiViewport;

    public MenuScreen (Launcher launcher){
        this.launcher = launcher;
        this.uiViewport = new FitViewport(1920f, 1152f);
        this.stage = new Stage(uiViewport, launcher.getBatch());
        this.skin = launcher.getAssetService().get(null);
    }
}
