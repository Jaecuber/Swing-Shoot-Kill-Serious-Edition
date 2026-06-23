package com.github.Jaecuber.swingShootKill.component;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.gdx.math.Vector2;
import com.github.Jaecuber.swingShootKill.input.Command;

public class Controller implements Component{
    public static final ComponentMapper<Controller> MAPPER = ComponentMapper.getFor(Controller.class);

    private final List<Command> pressedCommands;
    private final List<Command> releasedCommands;
    private final Vector2 mousePosition;

    public Controller() {
        this.pressedCommands = new ArrayList<>();
        this.releasedCommands = new ArrayList<>();
        this.mousePosition = new Vector2();
    }

    public List<Command> getPressedCommands(){
        return pressedCommands;
    }

    public List<Command> getReleasedCommands(){
        return releasedCommands;
    }

    public Vector2 getMousePosition() {
        return mousePosition;
    }


}
