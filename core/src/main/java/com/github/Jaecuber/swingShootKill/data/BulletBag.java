package com.github.Jaecuber.swingShootKill.data;

import com.badlogic.gdx.utils.Array;

public class BulletBag {
    private Array<BulletEntry> bullets = new Array<>();

    public BulletBag(){};
    
    public BulletBag(Array<BulletEntry> bullets) {
        this.bullets = bullets;
    }

    public Array<BulletEntry> getEnemies() {
        return bullets;
    }

    public void setEnemies(Array<BulletEntry> bullets) {
        this.bullets = bullets;
    }
}
