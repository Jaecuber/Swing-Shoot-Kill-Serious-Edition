package com.github.Jaecuber.swingShootKill.data;

public class StatusEntry {

    private String name;
    private float duration;
    private float damagePerTick;
    private float tickInterval;


    public StatusEntry(String name, float duration, float damagePerTick, float tickInterval) {
        this.name = name;
        this.duration = duration;
        this.damagePerTick = damagePerTick;
        this.tickInterval = tickInterval;
    }
    public StatusEntry(){};
    
    

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getDuration() {
        return duration;
    }
    public float getDamagePerTick() {
        return damagePerTick;
    }
    public float getTickInterval() {
        return tickInterval;
    }

    

}
