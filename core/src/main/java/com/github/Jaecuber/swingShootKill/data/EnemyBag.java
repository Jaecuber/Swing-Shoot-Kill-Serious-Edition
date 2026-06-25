package com.github.Jaecuber.swingShootKill.data;

import com.badlogic.gdx.utils.Array;

public class EnemyBag {
    private Array<EnemyEntry> enemies = new Array<>();

    public EnemyBag(){};
    public EnemyBag(Array<EnemyEntry> enemies) {
        this.enemies = enemies;
    }

    public Array<EnemyEntry> getEnemies() {
        return enemies;
    }

    public void setEnemies(Array<EnemyEntry> enemies) {
        this.enemies = enemies;
    }
}
