package com.github.Jaecuber.swingShootKill.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.gdx.math.Vector2;

public class Move implements Component{
    public static final ComponentMapper<Move> MAPPER = ComponentMapper.getFor(Move.class);

    private float maxSpeed;
    private final Vector2 direction;
    private boolean isRooted;
    private float defaultMaxSpeed;

    public Move(float maxSpeed){
        this.maxSpeed = maxSpeed;
        this.defaultMaxSpeed = maxSpeed;
        this.direction = new Vector2();
    }

    public float getMaxSpeed(){
        return maxSpeed;
    }

    public void setMaxSpeed(float speed){
        this.maxSpeed = speed;
    }

    public float getDefaultMaxSpeed() {
        return defaultMaxSpeed;
    }

    public void setDefaultMaxSpeed(float defaultMaxSpeed) {
        this.defaultMaxSpeed = defaultMaxSpeed;
    }

    public Vector2 getDirection(){
        return direction;
    }

    public void setDirection(float x, float y) {
        this.direction.set(x, y);
    }

    public void setRooted(boolean rooted){
        this.isRooted = rooted;
    }

    public boolean isRooted(){
        return isRooted;
    }
}
