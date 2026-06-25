package com.github.Jaecuber.swingShootKill.data;

import com.badlogic.gdx.utils.Array;

public class UpgradeClass {
    private Array<UpgradeEntry> basicUpgrades = new Array<>();
    private Array<UpgradeEntry> rareUpgrades = new Array<>();
    private Array<UpgradeEntry> legendaryUpgrades = new Array<>();
    private Array<UpgradeEntry> corruptUpgrades = new Array<>();

    public UpgradeClass(){};
    public UpgradeClass(Array<UpgradeEntry> basicUpgrades, Array<UpgradeEntry> rareUpgrades, Array<UpgradeEntry> legendaryUpgrades, Array<UpgradeEntry> cursedUpgrades){
        this.basicUpgrades = basicUpgrades;
        this.rareUpgrades = rareUpgrades;
        this.legendaryUpgrades = legendaryUpgrades;
        this.corruptUpgrades = cursedUpgrades;
    }
    public Array<UpgradeEntry> getBasicUpgrades() {
        return basicUpgrades;
    }
    public void setBasicUpgrades(Array<UpgradeEntry> basicUpgrades) {
        this.basicUpgrades = basicUpgrades;
    }
    public Array<UpgradeEntry> getRareUpgrades() {
        return rareUpgrades;
    }
    public void setRareUpgrades(Array<UpgradeEntry> rareUpgrades) {
        this.rareUpgrades = rareUpgrades;
    }
    public Array<UpgradeEntry> getLegendaryUpgrades() {
        return legendaryUpgrades;
    }
    public void setLegendaryUpgrades(Array<UpgradeEntry> legendaryUpgrades) {
        this.legendaryUpgrades = legendaryUpgrades;
    }
    public Array<UpgradeEntry> getCorruptUpgrades() {
        return corruptUpgrades;
    }
    public void setCorruptUpgrades(Array<UpgradeEntry> corruptUpgrades) {
        this.corruptUpgrades = corruptUpgrades;
    }
}
