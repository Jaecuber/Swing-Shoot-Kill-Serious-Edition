package com.github.Jaecuber.swingShootKill.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;

public class StatusEffect implements Component {
    public static final ComponentMapper<StatusEffect> MAPPER = ComponentMapper.getFor(StatusEffect.class);

    private String statusEffect;

    private float tickTimer;
    private float timeElapsed;
    private float duration;

    public StatusEffect(String statusEffect){
        this.statusEffect = statusEffect;
        this.timeElapsed = 0;
        this.tickTimer = 0;
        duration = 0;
    }

    public String getStatusEffect() {
        return statusEffect;
    }

    public float getTickTimer() {
        return tickTimer;
    }

    public void setTickTimer(float tickTimer) {
        this.tickTimer = tickTimer;
    }

    public void addTickTimer(float value){
        this.tickTimer += value;
    }

    public void addTimeElapsed(float value){
        this.timeElapsed += value;
    }

    public void setDuration(float duration) {
        this.duration = duration;
    }

    public boolean isDone(){
        return timeElapsed >= duration;
    }

    public float getTimeElapsed() {
        return timeElapsed;
    }

}
