package com.github.Jaecuber.swingShootKill.data;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;

public class BulletBag {
    private Array<BulletEntry> bulletTypes = new Array<>();
    

    private final ObjectMap<String, BulletEntry> bulletMap = new ObjectMap<>();

    public BulletBag() {}

    public void initializeMap() {
        bulletMap.clear();
        for (BulletEntry bullet : bulletTypes) {
            bulletMap.put(bullet.getName(), bullet);
        }
    }

   
    public BulletEntry getBulletConfig(String id) {
        return bulletMap.get(id);
    }

    public Array<BulletEntry> getBulletTypes() {
        return bulletTypes;
    }

    public void setBulletTypes(Array<BulletEntry> bulletTypes) {
        this.bulletTypes = bulletTypes;
        initializeMap(); 
    }
}