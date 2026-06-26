package com.github.Jaecuber.swingShootKill.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;

public class StatusEffect implements Component {
    public static final ComponentMapper<StatusEffect> MAPPER = ComponentMapper.getFor(StatusEffect.class);
    
    public enum STATUS_EFFECT {POISON, ELECTRIC, BURN}
    private STATUS_EFFECT sEffect;


    public void setsEffect(STATUS_EFFECT sEffect) {
        this.sEffect = sEffect;
    }

    public STATUS_EFFECT getsEffect() {
        return sEffect;
    }

}
