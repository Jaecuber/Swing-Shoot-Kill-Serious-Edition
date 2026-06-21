package com.github.Jaecuber.swingShootKill.component;

import java.net.http.HttpResponse.BodyHandler;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public class Physics implements Component{
    public static final ComponentMapper<Physics> MAPPER = ComponentMapper.getFor(Physics.class);

    private final Body body;
    private final Vector2 prevPosition;
    public Physics(Body body, Vector2 prevPosition) {
        this.body = body;
        this.prevPosition = prevPosition;
    }
    public Body getBody() {
        return body;
    }
    public Vector2 getPrevPosition() {
        return prevPosition;
    }
}
