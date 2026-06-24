package com.github.Jaecuber.ui.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.github.Jaecuber.swingShootKill.asset.SoundAsset;
import com.github.Jaecuber.ui.model.GameViewModel;

public class GameView extends View<GameViewModel>{
    //Top Info
    private Table infoTable;

    //health
    private Table hpTable;
    private Image hp1;
    private Image hp2;
    private Image hp3;
    private Image hp4;
    private Image hp5;

    //GameOver
    private Table gameOverTable;
    private Image continueButton;
    private Image quitButton;

    public GameView(Stage stage, Skin skin, GameViewModel viewModel) {
        super(stage, skin, viewModel);
    }

    //Setting up the HUD
    @Override
    protected void setupUI() {
        setupHealthInfo();
        setupDisplayInfo();
        setupGameOver();
    }

    private void setupHealthInfo(){
        hpTable = new Table();
        hpTable.padLeft(25.0f);
        hpTable.padTop(25.0f);
        hpTable.align(Align.topLeft);
        hpTable.setFillParent(true);

        hp1 = new Image(skin, "heart");
        hp1.setName("hp1");
        hpTable.add(hp1);

        hp2 = new Image(skin, "heart");
        hp2.setName("hp2");
        hpTable.add(hp2);

        hp3 = new Image(skin, "heart");
        hp3.setName("hp3");
        hpTable.add(hp3);

        hp4 = new Image(skin, "heart");
        hp4.setName("hp4");
        hpTable.add(hp4);

        hp5 = new Image(skin, "heart");
        hp5.setName("hp5");
        hpTable.add(hp5);
        stage.addActor(hpTable);
    }

    private void setupDisplayInfo(){
        infoTable = new Table();
        infoTable.padTop(25.0f);
        infoTable.align(Align.top);
        infoTable.setFillParent(true);

        infoTable.add();

        Label label = new Label("WAVE 1", skin, "Title");
        infoTable.add(label);

        infoTable.add();
        stage.addActor(infoTable);
    }

    private void setupGameOver(){
        gameOverTable = new Table();
        gameOverTable.setTouchable(Touchable.disabled);
        gameOverTable.setBackground(skin.getDrawable("gameOverBkg"));
        gameOverTable.padBottom(100.0f);
        gameOverTable.align(Align.bottom);
        gameOverTable.setFillParent(true);

        continueButton = new Image(skin, "continueButton");
        continueButton.setOrigin(continueButton.getWidth()/2, continueButton.getHeight()/2);
        gameOverTable.add(continueButton);
        onClick(continueButton, viewModel::continueGame);
        continueButton.addListener(new InputListener(){
            boolean hovering = false;
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor){
                if(pointer != -1 || hovering) return;
                hovering = true;
                continueButton.clearActions();
                viewModel.playSound(SoundAsset.HOVER);
                continueButton.addAction(
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
                continueButton.clearActions();
                continueButton.addAction(
                    Actions.parallel(
                        Actions.rotateTo(0f, 0.15f, Interpolation.swingOut),
                        Actions.scaleTo(1.0f, 1.0f, 0.15f, Interpolation.swingOut)
                    )
                );
            }
        });
        stage.addActor(gameOverTable);

        gameOverTable.row();
        quitButton = new Image(skin, "quitButton");
        quitButton.setOrigin(quitButton.getWidth()/2, quitButton.getHeight()/2);
        gameOverTable.add(quitButton);
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
        gameOverTable.setVisible(false);
        gameOverTable.getColor().a = 0f;
        stage.addActor(gameOverTable);
    }

    //HUD Functionality
    @Override
    protected void setupPropertyChanges() {
        viewModel.onPropertyChange(GameViewModel.HEALTH, Integer.class, this::updateHealth);
        viewModel.onPropertyChange(GameViewModel.GAME_OVER, Boolean.class, this::gameOverScreen);
    }

    private void updateHealth(int health){
        Image hpImg = hpTable.findActor("hp1");
        int count = 1;
        while(hpImg != null){
            hpImg.setVisible(false);
            count++;
            hpImg = hpTable.findActor("hp" + count);
        }
        for(int i = 1; i <= health; i++){
            Image hp = hpTable.findActor("hp" + i);
            hp.setVisible(true);
        }
    }

    private void gameOverScreen(boolean bool){
        gameOverTable.setVisible(bool);
        gameOverTable.setTouchable(Touchable.enabled);
        gameOverTable.addAction(Actions.sequence(
            Actions.delay(1.0f),
            Actions.fadeIn(1.0f)
        ));
    }

}
