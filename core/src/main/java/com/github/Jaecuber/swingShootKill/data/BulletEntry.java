package com.github.Jaecuber.swingShootKill.data;

public class BulletEntry {
    private String name;
    private float dmgMultipler;
    private String onHitEffect;

    public BulletEntry(){};
    public BulletEntry(String name, float dmgMultipler, String onHitEffect) {
        this.name = name;
        this.dmgMultipler = dmgMultipler;
        this.onHitEffect = onHitEffect;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getDmgMultipler() {
        return dmgMultipler;
    }

    public String getOnHitEffect() {
        return onHitEffect;
    }
    
}
