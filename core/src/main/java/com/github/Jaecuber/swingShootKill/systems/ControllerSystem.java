package com.github.Jaecuber.swingShootKill.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.physics.box2d.Body;
import com.github.Jaecuber.swingShootKill.component.Controller;
import com.github.Jaecuber.swingShootKill.component.Move;
import com.github.Jaecuber.swingShootKill.component.Physics;
import com.github.Jaecuber.swingShootKill.input.Command;

public class ControllerSystem extends IteratingSystem{
    public ControllerSystem(){
        super(Family.all(Controller.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Controller controller = Controller.MAPPER.get(entity);
        Move move = Move.MAPPER.get(entity);
        Body body = Physics.MAPPER.get(entity).getBody();

        if(controller.getPressedCommands().isEmpty() && controller.getReleasedCommands().isEmpty() && move == null) return;
        if(body == null) return;

        for(Command command : controller.getPressedCommands()){
            switch (command) {
                case UP -> moveEntity(entity, 0f, 1f);
                case DOWN -> moveEntity(entity, 0f, -1f);
                case LEFT -> moveEntity(entity, -1f, 0f);
                case RIGHT -> moveEntity(entity, 1f, 0f);
            }
        }
        controller.getPressedCommands().clear();
        for(Command command : controller.getReleasedCommands()){
            switch (command) {
                case UP -> moveEntity(entity, 0f, -1f);
                case DOWN -> moveEntity(entity, 0f, 1f);
                case LEFT -> moveEntity(entity, 1f, 0f);
                case RIGHT -> moveEntity(entity, -1f, 0f);
            }
        }
        controller.getReleasedCommands().clear();

    }

    private void moveEntity(Entity entity, float directionX, float directionY) {
        Move move = Move.MAPPER.get(entity);
        if(move == null) return;

        move.getDirection().x += directionX;
        move.getDirection().y += directionY;
    }
}
