package com.github.Jaecuber.ui.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.github.Jaecuber.ui.model.MenuViewModel;

public class MenuView extends View<MenuViewModel>{
    Table menuTable = new Table();

    public MenuView(Stage stage, Skin skin, MenuViewModel viewModel) {
        super(stage, skin, viewModel);
    }

    @Override
    protected void setupUI() {
        Table table = new Table();
        table.setBackground(skin.getDrawable("menuBkg"));
        table.padLeft(100.0f);
        table.align(Align.left);
        table.setFillParent(true);

        Image image = new Image(skin, "SwingShootKillLogo");
        table.add(image).padBottom(50.0f);

        table.row();
        image = new Image(skin, "playButton");
        table.add(image);

        table.row();
        image = new Image(skin, "quitButton");
        table.add(image);
        stage.addActor(table);

    }

}
