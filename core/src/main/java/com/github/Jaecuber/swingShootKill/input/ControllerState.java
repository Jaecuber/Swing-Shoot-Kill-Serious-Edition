package com.github.Jaecuber.swingShootKill.input;

public interface ControllerState {
    void keyDown(Command command);

    default void keyUp(Command command){}
}
