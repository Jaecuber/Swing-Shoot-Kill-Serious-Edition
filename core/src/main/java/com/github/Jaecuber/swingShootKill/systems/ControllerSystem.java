package com.github.Jaecuber.swingShootKill.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.physics.box2d.Body;
import com.github.Jaecuber.swingShootKill.component.AttackMode;
import com.github.Jaecuber.swingShootKill.component.Controller;
import com.github.Jaecuber.swingShootKill.component.Move;
import com.github.Jaecuber.swingShootKill.component.Physics;
import com.github.Jaecuber.swingShootKill.component.Shooter;
import com.github.Jaecuber.swingShootKill.component.AttackMode.ATTACK_MODE;
import com.github.Jaecuber.swingShootKill.component.Shooter.ShooterState;
import com.github.Jaecuber.swingShootKill.helpers.Helpers;
import com.github.Jaecuber.swingShootKill.input.Command;
import com.github.Jaecuber.swingShootKill.input.KeyboardController;

public class ControllerSystem extends IteratingSystem{

    private boolean swordSpinning = false;

    public ControllerSystem(){
        super(Family.all(Controller.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Controller controller = Controller.MAPPER.get(entity);
        Move move = Move.MAPPER.get(entity);
        Body body = Physics.MAPPER.get(entity).getBody();

        controller.getMousePosition().set(KeyboardController.getMousePos());

        if(controller.getPressedCommands().isEmpty() && controller.getReleasedCommands().isEmpty() && move == null) return;
        if(body == null) return;

        for(Command command : controller.getPressedCommands()){
            switch (command) {
                case UP -> moveEntity(entity, 0f, 1f);
                case DOWN -> moveEntity(entity, 0f, -1f);
                case LEFT -> moveEntity(entity, -1f, 0f);
                case RIGHT -> moveEntity(entity, 1f, 0f);
                case ATTACK -> attackEntity(entity);
                case SWITCH_WEAPONS -> weaponSwitch(entity);
            }
        }
        controller.getPressedCommands().clear();
        for(Command command : controller.getReleasedCommands()){
            switch (command) {
                case UP -> moveEntity(entity, 0f, -1f);
                case DOWN -> moveEntity(entity, 0f, 1f);
                case LEFT -> moveEntity(entity, 1f, 0f);
                case RIGHT -> moveEntity(entity, -1f, 0f);
                case ATTACK -> attackEntity(entity);
            }
        }
        controller.getReleasedCommands().clear();

    }

    private void weaponSwitch(Entity entity){
        ATTACK_MODE mode = AttackMode.MAPPER.get(entity).getAttackMode();
        switch (mode) {
            case SWORD ->  AttackMode.MAPPER.get(entity).setAttackMode(ATTACK_MODE.GUN);
            case GUN -> AttackMode.MAPPER.get(entity).setAttackMode(ATTACK_MODE.SWORD);
        }
    }

    private void attackEntity(Entity entity){
        ATTACK_MODE mode = AttackMode.MAPPER.get(entity).getAttackMode();
        Entity currentWeapon = AttackMode.MAPPER.get(entity).getCurrentWeaponEntity();

        switch(mode){
            case GUN -> shootGun(currentWeapon);
            case SWORD -> spinSword(currentWeapon);
        }

    }

    private void shootGun(Entity currentWeapon){
        Shooter shooter = Shooter.MAPPER.get(currentWeapon);

        if(shooter.getShooterState() == ShooterState.IDLE){
            shooter.setShooterState(ShooterState.SHOOTING);
            shooter.incrementTimesShot();
        }

        if(shooter.timeToRoll()){
            System.out.print("Rolling");
        }
    }

    private void spinSword(Entity currentWeapon){
        swordSpinning = !swordSpinning;

        if(swordSpinning){
        }

    }

    private void moveEntity(Entity entity, float directionX, float directionY) {
        Move move = Move.MAPPER.get(entity);
        if(move == null) return;

        move.getDirection().x += directionX;
        move.getDirection().y += directionY;
    }
}
