package com.github.Jaecuber.ui.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.github.Jaecuber.ui.model.MenuViewModel;

public class MenuView extends View<MenuViewModel>{

    public MenuView(Stage stage, Skin skin, MenuViewModel viewModel) {
        super(stage, skin, viewModel);
    }

    @Override
    protected void setupUI() {
        Table table = new Table();
        table.setFillParent(true);

        Label label = new Label("Click To Start", skin);
        table.add(label);
        stage.addActor(table);
    }

}
