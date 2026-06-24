package com.github.Jaecuber.swingShootKill.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.gdx.math.MathUtils;

public class Stamina implements Component {
    public static final ComponentMapper<Stamina> MAPPER = ComponentMapper.getFor(Stamina.class);

    private float maxStamina;
    private float currentStamina;

    public Stamina(float maxStamina){
        this.maxStamina = maxStamina;
        this.currentStamina = maxStamina;
    }

    public void updateStamina(float amount){
        currentStamina = MathUtils.clamp(currentStamina + amount, 0, maxStamina);
    }

    public float getCurrentStamina() {
        return currentStamina;
    }
}
