package com.github.Jaecuber.swingShootKill.data;

public class BulletEntry {
    private String name;
    private float damageMultiplier;
    private String onHitStatusEffect;

    public BulletEntry(){};
    public BulletEntry(String name, float damageMultiplier, String onHitStatusEffect) {
        this.name = name;
        this.damageMultiplier = damageMultiplier;
        this.onHitStatusEffect = onHitStatusEffect;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getDamageMultiplier() {
        return damageMultiplier;
    }

    public String getOnHitStatusEffect() {
        return onHitStatusEffect;
    }
    
}
