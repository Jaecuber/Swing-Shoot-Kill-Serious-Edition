package com.github.Jaecuber.swingShootKill.data;

public class UpgradeBag {
    private UpgradeClass upgrades;

    public UpgradeBag(){};
    public UpgradeBag(UpgradeClass upgrades){
        this.upgrades = upgrades;
    }
    public void setUpgradeTypes(UpgradeClass upgrades){
        this.upgrades = upgrades;
    }
    public UpgradeClass getUpgradeTypes(){
        return this.upgrades;
    }
}
