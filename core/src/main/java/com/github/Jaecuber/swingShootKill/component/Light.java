package com.github.Jaecuber.swingShootKill.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;

import box2dLight.PointLight;

public class Light implements Component{
    public static final ComponentMapper<Light> MAPPER = ComponentMapper.getFor(Light.class);

    private final PointLight pointLight;

    public Light(PointLight pointLight){
        this.pointLight = pointLight;
    }

    public PointLight getPointLight() {
        return pointLight;
    }
}
