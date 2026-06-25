package com.github.Jaecuber.swingShootKill.data;

public class EnemyEntry {
    private String name;
    private float minDiff;

    public EnemyEntry(){};
    public EnemyEntry(String name, float minDiff) {
        this.name = name;
        this.minDiff = minDiff;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public float getMinDiff() {
        return minDiff;
    }
    public void setMinDiff(float minDiff) {
        this.minDiff = minDiff;
    }
}
