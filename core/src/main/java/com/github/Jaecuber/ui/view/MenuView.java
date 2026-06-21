package com.github.Jaecuber.ui.view;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.github.Jaecuber.ui.model.MenuViewModel;

public class MenuView extends View<MenuViewModel>{
    private Table menuTable;
    private Image logo;
    private Image playButton;
    private Image quitButton;

    public MenuView(Stage stage, Skin skin, MenuViewModel viewModel) {
        super(stage, skin, viewModel);
    }

    @Override
    protected void setupUI() {
        menuTable = new Table();
        menuTable.setBackground(skin.getDrawable("menuBkg"));
        menuTable.padLeft(100.0f);
        menuTable.align(Align.left);
        menuTable.setFillParent(true);

        logo = new Image(skin, "SwingShootKillLogo");
        menuTable.add(logo).padBottom(50.0f);

        menuTable.row();
        playButton = new Image(skin, "playButton");
        menuTable.add(playButton);

        onClick(playButton, viewModel::startGame);
        menuTable.row();
        quitButton = new Image(skin, "quitButton");
        menuTable.add(quitButton);
        stage.addActor(menuTable);
        
    }

}
