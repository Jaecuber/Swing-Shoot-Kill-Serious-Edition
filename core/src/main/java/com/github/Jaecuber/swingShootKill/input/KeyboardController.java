package com.github.Jaecuber.swingShootKill.input;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.GdxRuntimeException;

public class KeyboardController extends InputAdapter{
    private static final Map<Integer, Command> KEY_MAPPING = Map.ofEntries(
        Map.entry(Input.Keys.W, Command.UP),
        Map.entry(Input.Keys.A, Command.LEFT),
        Map.entry(Input.Keys.S, Command.DOWN),
        Map.entry(Input.Keys.D, Command.RIGHT),
        Map.entry(Input.Keys.SHIFT_LEFT, Command.DASH),
        Map.entry(Input.Keys.ESCAPE, Command.EXIT),
        Map.entry(Input.Keys.E, Command.SWITCH_WEAPONS)
    );

    private static final Map<Integer, Command> INPUT_MAPPING = Map.ofEntries(
        Map.entry(Input.Buttons.LEFT, Command.ATTACK)
    );

    private static Vector2 mousePosition;

    private final boolean[] commandState;
    private final Map<Class<?extends ControllerState>, ControllerState> stateCache;
    private ControllerState activeState;

    public KeyboardController(Class<? extends ControllerState> initialState, Engine engine){
        this.stateCache = new HashMap<>();
        this.activeState = null;
        this.commandState = new boolean[Command.values().length];

        this.mousePosition = new Vector2();

        this.stateCache.put(IdleControllerState.class, new IdleControllerState());
        this.stateCache.put(GameControllerState.class, new GameControllerState(engine));
        setActiveState(initialState);
    }

    public void setActiveState(Class<? extends ControllerState> stateClass) {
        ControllerState controllerState = stateCache.get(stateClass);
        if(controllerState == null){
            throw new GdxRuntimeException("No state with class " + stateClass + " found in state cache");
        }
        for(Command command : Command.values()){
            if(this.activeState != null && this.commandState[command.ordinal()]){
                this.activeState.keyUp(command);
            }
            this.commandState[command.ordinal()] = false;
        }
        this.activeState = controllerState;
    }

    @Override
    public boolean keyDown(int keycode){
        Command command = KEY_MAPPING.get(keycode);
        if(command == null) return false;

        this.commandState[command.ordinal()] = true;
        this.activeState.keyDown(command);
        return true;
    }

    @Override
    public boolean keyUp(int keycode){
        Command command = KEY_MAPPING.get(keycode);
        if(command == null) return false;
        if(!this.commandState[command.ordinal()]) return false;

        this.commandState[command.ordinal()] = false;
        this.activeState.keyUp(command);
        return true;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return super.touchCancelled(screenX, screenY, pointer, button);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Command command = INPUT_MAPPING.get(button);
        if(command == null) return false;

        this.commandState[command.ordinal()] = true;
        this.activeState.keyDown(command);
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        Command command = INPUT_MAPPING.get(button);
        if(command == null) return false;
        if(!this.commandState[command.ordinal()]) return false;

        this.commandState[command.ordinal()] = false;
        this.activeState.keyUp(command);
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return super.touchDragged(screenX, screenY, pointer);
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        mousePosition.set(screenX, screenY);

        return false;
    }

    public static Vector2 getMousePos(){
        return mousePosition;
    }
}
