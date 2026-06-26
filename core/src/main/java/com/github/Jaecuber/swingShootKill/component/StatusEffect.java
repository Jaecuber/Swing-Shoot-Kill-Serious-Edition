package com.github.Jaecuber.swingShootKill.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;

public class StatusEffect implements Component {
    public static final ComponentMapper<StatusEffect> MAPPER = ComponentMapper.getFor(StatusEffect.class);

    private String statusEffect;
    private float timeElapsed;
    private float duration;

    public StatusEffect(String statusEffect){
        this.statusEffect = statusEffect;
        this.timeElapsed = 0;
        //duration = Math.
    }

    public String getStatusEffect() {
        return statusEffect;
    }

    public void addTimeElapsed(float value){
        this.timeElapsed += value;
    }

    // public boolean isDone(){

    // }

}
