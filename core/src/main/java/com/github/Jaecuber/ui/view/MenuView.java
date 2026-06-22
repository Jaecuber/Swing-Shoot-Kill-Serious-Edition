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
        menuTable.padLeft(100.0f);
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
            long lastEnterTime = 0;
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor){
                long currentTime = System.currentTimeMillis();
                if(currentTime - lastEnterTime > 50){
                    viewModel.playSound(SoundAsset.HOVER);
                    playButton.addAction(
                        Actions.parallel(
                            Actions.rotateTo(7f, 0.15f, Interpolation.swingOut),
                            Actions.scaleTo(1.1f, 1.1f, 0.15f, Interpolation.swingOut)
                        )
                    );
                    lastEnterTime = currentTime;
                }
            }
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                long currentTime = System.currentTimeMillis();
                if(currentTime - lastEnterTime > 50){
                    playButton.addAction(
                        Actions.parallel(
                            Actions.rotateTo(0f, 0.15f, Interpolation.swingOut),
                            Actions.scaleTo(1.0f, 1.0f, 0.15f, Interpolation.swingOut)
                        )
                    );
                    lastEnterTime = currentTime;
                }
            }
        });
        
        menuTable.row();
        quitButton = new Image(skin, "quitButton");
        quitButton.setOrigin(playButton.getWidth()/2, playButton.getHeight()/2);
        menuTable.add(quitButton);
        onClick(quitButton, viewModel::quitGame);
        quitButton.addListener(new InputListener(){
            long lastEnterTime = 0;
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor){
                long currentTime = System.currentTimeMillis();
                if(currentTime - lastEnterTime > 50){
                    viewModel.playSound(SoundAsset.HOVER);
                    quitButton.addAction(
                        Actions.parallel(
                            Actions.rotateTo(7f, 0.15f, Interpolation.swingOut),
                            Actions.scaleTo(1.1f, 1.1f, 0.15f, Interpolation.swingOut)
                        )
                    );
                    lastEnterTime = currentTime;
                }
            }
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                long currentTime = System.currentTimeMillis();
                if(currentTime - lastEnterTime > 50){
                    quitButton.addAction(
                        Actions.parallel(
                            Actions.rotateTo(0f, 0.15f, Interpolation.swingOut),
                            Actions.scaleTo(1.0f, 1.0f, 0.15f, Interpolation.swingOut)
                        )
                    );
                    lastEnterTime = currentTime;
                }
            }
        });

        stage.addActor(menuTable);    
    }
}
