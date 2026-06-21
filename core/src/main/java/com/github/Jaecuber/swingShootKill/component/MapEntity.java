package com.github.Jaecuber.swingShootKill.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;

public class MapEntity implements Component{
    public static final ComponentMapper<MapEntity> MAPPER = ComponentMapper.getFor(MapEntity.class);
}
