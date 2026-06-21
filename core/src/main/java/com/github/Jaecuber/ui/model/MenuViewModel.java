package com.github.Jaecuber.ui.model;

import com.github.Jaecuber.swingShootKill.Launcher;
import com.github.Jaecuber.swingShootKill.asset.MapAsset;
import com.github.Jaecuber.swingShootKill.screens.GameScreen;

public class MenuViewModel extends ViewModel{

    public MenuViewModel(Launcher launcher) {
        super(launcher);
    }

    public void startGame(){
        launcher.setScreen(new GameScreen(launcher, MapAsset.MAIN_MAP));
    }

}
