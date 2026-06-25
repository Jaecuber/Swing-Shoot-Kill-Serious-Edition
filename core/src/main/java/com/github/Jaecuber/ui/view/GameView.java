package com.github.Jaecuber.ui.view;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.ObjectSet;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.github.Jaecuber.swingShootKill.asset.SoundAsset;
import com.github.Jaecuber.swingShootKill.component.UpgradeTags;
import com.github.Jaecuber.swingShootKill.data.UpgradeClass;
import com.github.Jaecuber.swingShootKill.data.UpgradeEntry;
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

    //Upgrade shop
    private UpgradeClass upgradeClasses;
    private Table shopTable;

    private Table upgrade1Table;
    private Label upgrade1Name;
    private Label upgrade1Rarity;
    private Label upgrade1Desc;
    private Label upgrade1Price;

    private Table upgrade2Table;
    private Label upgrade2Name;
    private Label upgrade2Rarity;
    private Label upgrade2Desc;
    private Label upgrade2Price;

    private Table upgrade3Table;
    private Label upgrade3Name;
    private Label upgrade3Rarity;
    private Label upgrade3Desc;
    private Label upgrade3Price;

    private Table upgrade4Table;
    private Label upgrade4Name;
    private Label upgrade4Rarity;
    private Label upgrade4Desc;
    private Label upgrade4Price;

    private Table shopInfoTable;
    private Label playerBalance;

    private Table rerollTable;
    private Image rerollButton;
    private boolean rolling = false;

    public GameView(Stage stage, Skin skin, GameViewModel viewModel) {
        super(stage, skin, viewModel);
        while (this.upgradeClasses == null) {
            this.upgradeClasses = viewModel.loadUpgradeClasses();
        }
        displayUpgrades();
    }

    //Setting up the HUD
    @Override
    protected void setupUI() {
        setupHealthInfo();
        setupDisplayInfo();
        setupGameOver();
        setupUpgradeShop();
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

    private void setupUpgradeShop(){
        setupShopTable();
        setupShopInfoTable();
        setupRerollTable();
    }

    private void setupRerollTable(){
        rerollTable = new Table();
        rerollTable.padBottom(50.0f);
        rerollTable.align(Align.bottom);
        rerollTable.setFillParent(true);

        HorizontalGroup horizontalGroup = new HorizontalGroup();
        horizontalGroup.space(10.0f);

        rerollButton = new Image(skin, "rerollButton");
        rerollButton.setOrigin(quitButton.getWidth()/2, quitButton.getHeight()/2);
        rerollButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(!rolling){
                    rerollUpgrades();
                }
            }
        });
        rerollButton.addListener(new InputListener(){
            boolean hovering = false;
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor){
                if(pointer != -1 || hovering) return;
                hovering = true;
                rerollButton.clearActions();
                viewModel.playSound(SoundAsset.HOVER);
                rerollButton.addAction(
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
                rerollButton.clearActions();
                rerollButton.addAction(
                    Actions.parallel(
                        Actions.rotateTo(0f, 0.15f, Interpolation.swingOut),
                        Actions.scaleTo(1.0f, 1.0f, 0.15f, Interpolation.swingOut)
                    )
                );
            }
        });
        horizontalGroup.addActor(rerollButton);

        Label label = new Label("For 100", skin, "Title");
        horizontalGroup.addActor(label);

        Image image = new Image(skin, "coinsIcon");
        horizontalGroup.addActor(image);
        rerollTable.add(horizontalGroup);
        rerollTable.setTouchable(Touchable.disabled);
        rerollTable.getColor().a = 0.0f;
        rerollTable.setVisible(false);
        stage.addActor(rerollTable);
    }

    private void setupShopInfoTable(){
        shopInfoTable = new Table();
        shopInfoTable.padLeft(25.0f);
        shopInfoTable.padTop(25.0f);
        shopInfoTable.align(Align.topLeft);
        shopInfoTable.setFillParent(true);

        HorizontalGroup horizontalGroup = new HorizontalGroup();
        horizontalGroup.space(10.0f);

        playerBalance = new Label("69420", skin, "HugeTitle");
        horizontalGroup.addActor(playerBalance);

        Image image = new Image(skin, "coinsIcon");
        horizontalGroup.addActor(image);
        shopInfoTable.add(horizontalGroup);
        shopInfoTable.setTouchable(Touchable.disabled);
        shopInfoTable.getColor().a = 0.0f;
        shopInfoTable.setVisible(false);
        stage.addActor(shopInfoTable);
    }

    private void setupShopTable(){
        shopTable = new Table();
        shopTable.setBackground(skin.getDrawable("upgradesBkg"));
        shopTable.setFillParent(true);

        //upgrade 1
        upgrade1Table = new Table();
        upgrade1Table.setTouchable(Touchable.enabled);
        upgrade1Table.setTransform(true);
        upgrade1Table.setSize(336, 192);
        upgrade1Table.setOrigin(Align.center);
        upgrade1Table.setBackground(skin.getDrawable("BasicUpgradeBkg"));
        upgrade1Table.padLeft(10.0f);
        upgrade1Table.padTop(10.0f);
        upgrade1Table.align(Align.topLeft);
        upgrade1Table.addListener(new InputListener(){
            boolean hovering = false;
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor){
                if(pointer != -1 || hovering) return;
                if(fromActor != null && fromActor.isDescendantOf(upgrade1Table)) return;
                hovering = true;
                upgrade1Table.clearActions();
                viewModel.playSound(SoundAsset.HOVER);
                upgrade1Table.addAction(Actions.scaleTo(1.05f, 1.05f, 0.15f, Interpolation.swingOut));
            }
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                if(pointer != -1 || !hovering) return;
                if(toActor != null && toActor.isDescendantOf(upgrade1Table)) return;
                hovering = false;
                upgrade1Table.clearActions();
                upgrade1Table.addAction(Actions.scaleTo(1.0f, 1.0f, 0.15f, Interpolation.swingOut));
            }
        });

        HorizontalGroup horizontalGroup = new HorizontalGroup();
        horizontalGroup.space(10.0f);

        upgrade1Name = new Label("Sharpened Blade -", skin, "Title");
        horizontalGroup.addActor(upgrade1Name);

        upgrade1Rarity = new Label("basic", skin);
        horizontalGroup.addActor(upgrade1Rarity);
        upgrade1Table.add(horizontalGroup).space(10.0f).align(Align.left);

        upgrade1Table.row();
        upgrade1Desc = new Label("Deal +3 damage", skin);
        upgrade1Desc.setWrap(true);
        upgrade1Table.add(upgrade1Desc).space(10.0f).growX().align(Align.left);

        upgrade1Table.row();
        horizontalGroup = new HorizontalGroup();
        horizontalGroup.space(10.0f);

        upgrade1Price = new Label("150", skin);
        horizontalGroup.addActor(upgrade1Price);

        Image image = new Image(skin, "coinsIcon");
        horizontalGroup.addActor(image);
        upgrade1Table.add(horizontalGroup).space(10.0f).align(Align.left);
        shopTable.add(upgrade1Table).space(20.0f);

        //upgrade 2
        upgrade2Table = new Table();
        upgrade2Table.setTouchable(Touchable.enabled);
        upgrade2Table.setTransform(true);
        upgrade2Table.setSize(336, 192);
        upgrade2Table.setOrigin(Align.center);
        upgrade2Table.setBackground(skin.getDrawable("RareUpgradeBkg"));
        upgrade2Table.padLeft(10.0f);
        upgrade2Table.padTop(10.0f);
        upgrade2Table.align(Align.topLeft);
        upgrade2Table.addListener(new InputListener(){
            boolean hovering = false;
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor){
                if(pointer != -1 || hovering) return;
                if(fromActor != null && fromActor.isDescendantOf(upgrade2Table)) return;
                hovering = true;
                upgrade2Table.clearActions();
                viewModel.playSound(SoundAsset.HOVER);
                upgrade2Table.addAction(Actions.scaleTo(1.05f, 1.05f, 0.15f, Interpolation.swingOut));
            }
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                if(pointer != -1 || !hovering) return;
                if(toActor != null && toActor.isDescendantOf(upgrade2Table)) return;
                hovering = false;
                upgrade2Table.clearActions();
                upgrade2Table.addAction(Actions.scaleTo(1.0f, 1.0f, 0.15f, Interpolation.swingOut));
            }
        });

        horizontalGroup = new HorizontalGroup();
        horizontalGroup.space(10.0f);

        upgrade2Name = new Label("Lifesteal -", skin, "Title");
        horizontalGroup.addActor(upgrade2Name);

        upgrade2Rarity = new Label("rare", skin);
        horizontalGroup.addActor(upgrade2Rarity);
        upgrade2Table.add(horizontalGroup).space(10.0f).align(Align.left);

        upgrade2Table.row();
        upgrade2Desc = new Label("Health 5hp everytime you kill an enemy", skin);
        upgrade2Desc.setWrap(true);
        upgrade2Table.add(upgrade2Desc).space(10.0f).growX().align(Align.left);

        upgrade2Table.row();
        horizontalGroup = new HorizontalGroup();
        horizontalGroup.space(10.0f);

        upgrade2Price = new Label("250", skin);
        horizontalGroup.addActor(upgrade2Price);

        image = new Image(skin, "coinsIcon");
        horizontalGroup.addActor(image);
        upgrade2Table.add(horizontalGroup).space(10.0f).align(Align.left);
        shopTable.add(upgrade2Table).space(20.0f);

        //upgrade 3
        upgrade3Table = new Table();
        upgrade3Table.setTouchable(Touchable.enabled);
        upgrade3Table.setTransform(true);
        upgrade3Table.setSize(336, 192);
        upgrade3Table.setOrigin(Align.center);
        upgrade3Table.setBackground(skin.getDrawable("LegendaryUpgradeBkg"));
        upgrade3Table.padLeft(10.0f);
        upgrade3Table.padTop(10.0f);
        upgrade3Table.align(Align.topLeft);
        upgrade3Table.addListener(new InputListener(){
            boolean hovering = false;
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor){
                if(pointer != -1 || hovering) return;
                if(fromActor != null && fromActor.isDescendantOf(upgrade3Table)) return;
                hovering = true;
                upgrade3Table.clearActions();
                viewModel.playSound(SoundAsset.HOVER);
                upgrade3Table.addAction(Actions.scaleTo(1.05f, 1.05f, 0.15f, Interpolation.swingOut));
            }
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                if(pointer != -1 || !hovering) return;
                if(toActor != null && toActor.isDescendantOf(upgrade3Table)) return;
                hovering = false;
                upgrade3Table.clearActions();
                upgrade3Table.addAction(Actions.scaleTo(1.0f, 1.0f, 0.15f, Interpolation.swingOut));
            }
        });

        horizontalGroup = new HorizontalGroup();
        horizontalGroup.space(10.0f);

        upgrade3Name = new Label("Titan's Resolve -", skin, "Title");
        horizontalGroup.addActor(upgrade3Name);

        upgrade3Rarity = new Label("legendary", skin);
        horizontalGroup.addActor(upgrade3Rarity);
        upgrade3Table.add(horizontalGroup).space(10.0f).align(Align.left);

        upgrade3Table.row();
        upgrade3Desc = new Label("Gain the resolve of a Titan and achieve 50% less stamina drain", skin);
        upgrade3Desc.setWrap(true);
        upgrade3Table.add(upgrade3Desc).space(10.0f).growX().align(Align.left);

        upgrade3Table.row();
        horizontalGroup = new HorizontalGroup();
        horizontalGroup.space(10.0f);

        upgrade3Price = new Label("570", skin);
        horizontalGroup.addActor(upgrade3Price);

        image = new Image(skin, "coinsIcon");
        horizontalGroup.addActor(image);
        upgrade3Table.add(horizontalGroup).space(10.0f).align(Align.left);
        shopTable.add(upgrade3Table).space(20.0f);

        //upgrade 4
        upgrade4Table = new Table();
        upgrade4Table.setTouchable(Touchable.enabled);
        upgrade4Table.setTransform(true);
        upgrade4Table.setSize(336, 192);
        upgrade4Table.setOrigin(Align.center);
        upgrade4Table.setBackground(skin.getDrawable("CorruptUpgradeBkg"));
        upgrade4Table.padLeft(10.0f);
        upgrade4Table.padTop(10.0f);
        upgrade4Table.align(Align.topLeft);  
        upgrade4Table.addListener(new InputListener(){
            boolean hovering = false;
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor){
                if(pointer != -1 || hovering) return;
                if(fromActor != null && fromActor.isDescendantOf(upgrade4Table)) return;
                hovering = true;
                upgrade4Table.clearActions();
                viewModel.playSound(SoundAsset.HOVER);
                upgrade4Table.addAction(Actions.scaleTo(1.05f, 1.05f, 0.15f, Interpolation.swingOut));
            }
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                if(pointer != -1 || !hovering) return;
                if(toActor != null && toActor.isDescendantOf(upgrade4Table)) return;
                hovering = false;
                upgrade4Table.clearActions();
                upgrade4Table.addAction(Actions.scaleTo(1.0f, 1.0f, 0.15f, Interpolation.swingOut));
            }
        });

        horizontalGroup = new HorizontalGroup();
        horizontalGroup.space(10.0f);

        upgrade4Name = new Label("Blood Pact -", skin, "Title");
        horizontalGroup.addActor(upgrade4Name);

        upgrade4Rarity = new Label("corrupt", skin);
        horizontalGroup.addActor(upgrade4Rarity);
        upgrade4Table.add(horizontalGroup).space(10.0f).align(Align.left);

        upgrade4Table.row();
        upgrade4Desc = new Label("Begin every level at 25% health but immensely boost your max health by 100", skin);
        upgrade4Desc.setWrap(true);
        upgrade4Table.add(upgrade4Desc).space(10.0f).growX().align(Align.left);

        upgrade4Table.row();
        horizontalGroup = new HorizontalGroup();
        horizontalGroup.space(10.0f);

        upgrade4Price = new Label("67", skin);
        horizontalGroup.addActor(upgrade4Price);

        image = new Image(skin, "coinsIcon");
        horizontalGroup.addActor(image);
        upgrade4Table.add(horizontalGroup).space(10.0f).align(Align.left);
        shopTable.add(upgrade4Table).space(20.0f);
        shopTable.setTouchable(Touchable.disabled);
        shopTable.getColor().a = 0.0f;
        shopTable.setVisible(false);
        stage.addActor(shopTable);
    }

    //HUD Functionality
    @Override
    protected void setupPropertyChanges() {
        viewModel.onPropertyChange(GameViewModel.HEALTH, Integer.class, this::updateHealth);
        viewModel.onPropertyChange(GameViewModel.GAME_OVER, Boolean.class, this::gameOverScreen);
        viewModel.onPropertyChange(GameViewModel.OPEN_SHOP, Boolean.class, this::openShop);
    }

    private void rerollUpgrades(){
        int randRollAmt = MathUtils.random(14,16);
        float duration = 3.0f;
        Actor rollHandler = new Actor();
        stage.addActor(rollHandler);
        
        this.rolling = true;
        rerollButton.setColor(Color.GRAY);
        for(int i = 0; i < randRollAmt; i++){
            float progress = (float) i / (randRollAmt - 1);
            float delay = Interpolation.exp5In.apply(0f, duration, progress);

            rollHandler.addAction(Actions.sequence(
                Actions.delay(delay),
                Actions.run(() -> {displayUpgrades(); viewModel.playSound(SoundAsset.HOVER); animateUpgradeRoll();})
            ));
            rollHandler.addAction(Actions.sequence(
                Actions.delay(duration + 0.1f),
                Actions.run(() -> {rollHandler.remove(); this.rolling = false; rerollButton.setColor(Color.WHITE);})
            ));
        }
    }

    private void animateUpgradeRoll(){
        upgrade1Table.addAction(Actions.moveBy(0f, 10f, 0.0f));
        upgrade2Table.addAction(Actions.moveBy(0f, 10f, 0.0f));
        upgrade3Table.addAction(Actions.moveBy(0f, 10f, 0.0f));
        upgrade4Table.addAction(Actions.moveBy(0f, 10f, 0.0f));

        upgrade1Table.addAction(Actions.moveBy(0f, -10f, 0.1f, Interpolation.sine));
        upgrade2Table.addAction(Actions.moveBy(0f, -10f, 0.1f, Interpolation.sine));
        upgrade3Table.addAction(Actions.moveBy(0f, -10f, 0.1f, Interpolation.sine));
        upgrade4Table.addAction(Actions.moveBy(0f, -10f, 0.1f, Interpolation.sine));
    }

    private void displayUpgrades(){
        ObjectSet<String> usedUpgrades = new ObjectSet<>();

        //getting upgrades
        UpgradeEntry upgrade1 = getUpgrade(usedUpgrades);
        usedUpgrades.add(upgrade1.getName());
        UpgradeEntry upgrade2 = getUpgrade(usedUpgrades);
        usedUpgrades.add(upgrade2.getName());
        UpgradeEntry upgrade3 = getUpgrade(usedUpgrades);
        usedUpgrades.add(upgrade3.getName());
        UpgradeEntry upgrade4 = getUpgrade(usedUpgrades);

        //setting table backgrounds & rarities
        switch (upgrade1.getRarity()) {
            case "Basic" -> {upgrade1Table.setBackground(skin.getDrawable("BasicUpgradeBkg")); upgrade1Rarity.setText(upgrade1.getRarity());}
            case "Rare" -> {upgrade1Table.setBackground(skin.getDrawable("RareUpgradeBkg")); upgrade1Rarity.setText(upgrade1.getRarity());}
            case "Legendary" -> {upgrade1Table.setBackground(skin.getDrawable("LegendaryUpgradeBkg")); upgrade1Rarity.setText(upgrade1.getRarity());}
            case "Corrupt" -> {upgrade1Table.setBackground(skin.getDrawable("CorruptUpgradeBkg")); upgrade1Rarity.setText(upgrade1.getRarity());}
        }
        switch (upgrade2.getRarity()) {
            case "Basic" -> {upgrade2Table.setBackground(skin.getDrawable("BasicUpgradeBkg")); upgrade2Rarity.setText(upgrade2.getRarity());}
            case "Rare" -> {upgrade2Table.setBackground(skin.getDrawable("RareUpgradeBkg")); upgrade2Rarity.setText(upgrade2.getRarity());}
            case "Legendary" -> {upgrade2Table.setBackground(skin.getDrawable("LegendaryUpgradeBkg")); upgrade2Rarity.setText(upgrade2.getRarity());}
            case "Corrupt" -> {upgrade1Table.setBackground(skin.getDrawable("CorruptUpgradeBkg")); upgrade2Rarity.setText(upgrade2.getRarity());}
        }
        switch (upgrade3.getRarity()) {
            case "Basic" -> {upgrade3Table.setBackground(skin.getDrawable("BasicUpgradeBkg")); upgrade3Rarity.setText(upgrade3.getRarity());}
            case "Rare" -> {upgrade3Table.setBackground(skin.getDrawable("RareUpgradeBkg")); upgrade3Rarity.setText(upgrade3.getRarity());}
            case "Legendary" -> {upgrade3Table.setBackground(skin.getDrawable("LegendaryUpgradeBkg")); upgrade3Rarity.setText(upgrade3.getRarity());}
            case "Corrupt" -> {upgrade3Table.setBackground(skin.getDrawable("CorruptUpgradeBkg")); upgrade3Rarity.setText(upgrade3.getRarity());}
        }
        switch (upgrade4.getRarity()) {
            case "Basic" -> {upgrade4Table.setBackground(skin.getDrawable("BasicUpgradeBkg")); upgrade4Rarity.setText(upgrade4.getRarity());}
            case "Rare" -> {upgrade4Table.setBackground(skin.getDrawable("RareUpgradeBkg")); upgrade4Rarity.setText(upgrade4.getRarity());}
            case "Legendary" -> {upgrade4Table.setBackground(skin.getDrawable("LegendaryUpgradeBkg")); upgrade4Rarity.setText(upgrade4.getRarity());}
            case "Corrupt" -> {upgrade4Table.setBackground(skin.getDrawable("CorruptUpgradeBkg")); upgrade4Rarity.setText(upgrade4.getRarity());}
        }

        //setting titles
        upgrade1Name.setText(upgrade1.getName() + " -");
        upgrade2Name.setText(upgrade2.getName() + " -");
        upgrade3Name.setText(upgrade3.getName() + " -");
        upgrade4Name.setText(upgrade4.getName() + " -");

        //setting descriptions
        upgrade1Desc.setText(upgrade1.getDescription());
        upgrade2Desc.setText(upgrade2.getDescription());
        upgrade3Desc.setText(upgrade3.getDescription());
        upgrade4Desc.setText(upgrade4.getDescription());

        //setting prices
        upgrade1Price.setText(upgrade1.getPrice());
        upgrade2Price.setText(upgrade2.getPrice());
        upgrade3Price.setText(upgrade3.getPrice());
        upgrade4Price.setText(upgrade4.getPrice());
    }

    private UpgradeEntry getUpgrade(ObjectSet<String> usedUpgrades){
        int classDec = MathUtils.random(0,100);
        UpgradeEntry entry;
        int maxAtt = 20;
        Array<UpgradeEntry> entryBag;

        do{
            if (classDec < 5) {
                entryBag = upgradeClasses.getCorruptUpgrades();
            } else if (classDec < 10) {
                entryBag = upgradeClasses.getLegendaryUpgrades();
            } else if (classDec < 40) {
                entryBag = upgradeClasses.getRareUpgrades();
            } else {
                entryBag = upgradeClasses.getBasicUpgrades();
            }

            int upgDec = MathUtils.random(0, entryBag.size - 1);
            entry = entryBag.get(upgDec);
            maxAtt--;

        }while(usedUpgrades.contains(entry.getName()) && maxAtt > 0);
    
        return entry;
    }

    private void openShop(Boolean open){
        if(open){
            viewModel.pauseGame();
            upgrade1Table.addAction(Actions.moveBy(0f, -25f, 0.0f));
            upgrade2Table.addAction(Actions.moveBy(0f, -25, 0.0f));
            upgrade3Table.addAction(Actions.moveBy(0f, -25, 0.0f));
            upgrade4Table.addAction(Actions.moveBy(0f, -25, 0.0f));

            upgrade1Table.addAction(Actions.sequence(Actions.delay(0.0f), Actions.moveBy(0f, 25f, 0.25f, Interpolation.sine)));
            upgrade2Table.addAction(Actions.sequence(Actions.delay(0.05f), Actions.moveBy(0f, 25f, 0.25f, Interpolation.sine)));
            upgrade3Table.addAction(Actions.sequence(Actions.delay(0.1f), Actions.moveBy(0f, 25f, 0.25f, Interpolation.sine)));
            upgrade4Table.addAction(Actions.sequence(Actions.delay(0.15f), Actions.moveBy(0f, 25f, 0.25f, Interpolation.sine)));

            rerollTable.setVisible(open);
            rerollTable.setTouchable(Touchable.childrenOnly);
            rerollTable.addAction(Actions.fadeIn(0.25f));
            
            shopInfoTable.setVisible(open);
            shopInfoTable.setTouchable(Touchable.childrenOnly);
            shopInfoTable.addAction(Actions.fadeIn(0.25f));

            shopTable.setVisible(open);
            shopTable.setTouchable(Touchable.enabled);
            shopTable.addAction(Actions.fadeIn(0.25f));
        }else{
            viewModel.resumeGame();
            rerollTable.setVisible(open);
            rerollTable.setTouchable(Touchable.disabled);
            rerollTable.addAction(Actions.fadeOut(0.0f));

            shopInfoTable.setVisible(open);
            shopInfoTable.setTouchable(Touchable.disabled);
            shopInfoTable.addAction(Actions.fadeOut(0.0f));
            
            shopTable.setVisible(open);
            shopTable.setTouchable(Touchable.disabled);
            shopTable.addAction(Actions.fadeOut(0.0f));
        }
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
