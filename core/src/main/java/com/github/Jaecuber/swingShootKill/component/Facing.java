package com.github.Jaecuber.swingShootKill.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;

public class Facing implements Component{
    public static final ComponentMapper<Facing> MAPPER = ComponentMapper.getFor(Facing.class);

    private FacingDirection direction;
    private String atlasKey;

    public Facing(FacingDirection direction, String atlasKey){
        this.direction = direction;
        this.atlasKey = atlasKey;
    }
    
    public String getAtlasKey() {
        return atlasKey;
    }

    public void setAtlasKey(String atlasKey) {
        this.atlasKey = atlasKey;
    }

    public FacingDirection getDirection() {
        return direction;
    }

    public void setDirection(FacingDirection direction) {
        this.direction = direction;
    }

    public enum FacingDirection{
        UP, DOWN, LEFT, RIGHT;

        private final String atlasKey;

        FacingDirection(){
            this.atlasKey = name().toLowerCase();
        }

        public String getAtlasKey(){
            return atlasKey;
        }
    }
}
