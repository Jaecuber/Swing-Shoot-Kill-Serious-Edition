package com.github.Jaecuber.ui.view;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.github.Jaecuber.swingShootKill.asset.SoundAsset;
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
        menuTable.padLeft(25.0f);
        menuTable.padBottom(200.0f);
        menuTable.align(Align.left);
        menuTable.setFillParent(true);

        logo = new Image(skin, "SwingShootKillLogo");
        menuTable.add(logo).padBottom(50.0f);

        menuTable.row();
        playButton = new Image(skin, "playButton");
        playButton.setOrigin(playButton.getWidth()/2, playButton.getHeight()/2);
        menuTable.add(playButton);
        onClick(playButton, viewModel::startGame);
        playButton.addListener(new InputListener(){
            boolean hovering = false;
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor){
                if(pointer != -1 || hovering) return;
                hovering = true;
                playButton.clearActions();
                viewModel.playSound(SoundAsset.HOVER);
                playButton.addAction(
                    Actions.parallel(
                        Actions.rotateTo(7f, 0.15f, Interpolation.swingOut),
                        Actions.scaleTo(1.1f, 1.1f, 0.15f, Interpolation.swingOut)
                    )
                );
            }
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                if(pointer != -1 || !hovering) return;
                hovering = false;
                playButton.clearActions();
                playButton.addAction(
                    Actions.parallel(
                        Actions.rotateTo(0f, 0.15f, Interpolation.swingOut),
                        Actions.scaleTo(1.0f, 1.0f, 0.15f, Interpolation.swingOut)
                    )
                );
            }
        });
        
        menuTable.row();
        quitButton = new Image(skin, "quitButton");
        quitButton.setOrigin(playButton.getWidth()/2, playButton.getHeight()/2);
        menuTable.add(quitButton);
        onClick(quitButton, viewModel::quitGame);
        quitButton.addListener(new InputListener(){
            boolean hovering = false;
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor){
                if(pointer != -1 || hovering) return;
                hovering = true;
                quitButton.clearActions();
                viewModel.playSound(SoundAsset.HOVER);
                quitButton.addAction(
                    Actions.parallel(
                        Actions.rotateTo(7f, 0.15f, Interpolation.swingOut),
                        Actions.scaleTo(1.1f, 1.1f, 0.15f, Interpolation.swingOut)
                    )
                );
            }
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                if(pointer != -1 || !hovering) return;
                hovering = false;
                quitButton.clearActions();
                quitButton.addAction(
                    Actions.parallel(
                        Actions.rotateTo(0f, 0.15f, Interpolation.swingOut),
                        Actions.scaleTo(1.0f, 1.0f, 0.15f, Interpolation.swingOut)
                    )
                );
            }
        });

        stage.addActor(menuTable);    
    }
}
