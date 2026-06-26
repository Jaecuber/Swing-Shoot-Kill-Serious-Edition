package com.github.Jaecuber.swingShootKill.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;

public class SpecialBullets implements Component{
    public static final ComponentMapper<SpecialBullets> MAPPER = ComponentMapper.getFor(SpecialBullets.class);

    private final String bulletType;

    public SpecialBullets(String bulletType){
        this.bulletType = bulletType;
    }

    public String getBulletType() {
        return bulletType;
    }


}
