package com.github.Jaecuber.swingShootKill.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.physics.box2d.Body;
import com.github.Jaecuber.swingShootKill.asset.SoundAsset;
import com.github.Jaecuber.swingShootKill.audio.AudioService;
import com.github.Jaecuber.swingShootKill.component.AttackMode;
import com.github.Jaecuber.swingShootKill.component.Controller;
import com.github.Jaecuber.swingShootKill.component.Melee;
import com.github.Jaecuber.swingShootKill.component.Move;
import com.github.Jaecuber.swingShootKill.component.Physics;
import com.github.Jaecuber.swingShootKill.component.Shooter;
import com.github.Jaecuber.swingShootKill.component.AttackMode.ATTACK_MODE;
import com.github.Jaecuber.swingShootKill.component.Shooter.ShooterState;
import com.github.Jaecuber.swingShootKill.helpers.Helpers;
import com.github.Jaecuber.swingShootKill.input.Command;
import com.github.Jaecuber.swingShootKill.input.KeyboardController;
import com.github.Jaecuber.ui.model.GameViewModel;

public class ControllerSystem extends IteratingSystem {

    private GameViewModel viewModel;
    private AudioService audioService;

    public ControllerSystem(GameViewModel viewModel, AudioService audioService) {
        super(Family.all(Controller.class).get());
        this.viewModel = viewModel;
        this.audioService = audioService;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Controller controller = Controller.MAPPER.get(entity);
        Move move = Move.MAPPER.get(entity);
        Body body = Physics.MAPPER.get(entity).getBody();

        controller.getMousePosition().set(KeyboardController.getMousePos());

        if (controller.getPressedCommands().isEmpty() && controller.getReleasedCommands().isEmpty() && move == null) return;
        if (body == null) return;

        for (Command command : controller.getPressedCommands()) {
            switch (command) {
                case UP -> moveEntity(entity, 0f, 1f);
                case DOWN -> moveEntity(entity, 0f, -1f);
                case LEFT -> moveEntity(entity, -1f, 0f);
                case RIGHT -> moveEntity(entity, 1f, 0f);
                case ATTACK -> attackEntity(entity);
                case OPEN_SHOP -> openShop();
                case SWITCH_WEAPONS -> weaponSwitch(entity);
            }
        }
        controller.getPressedCommands().clear();
        for (Command command : controller.getReleasedCommands()) {
            switch (command) {
                case UP -> moveEntity(entity, 0f, -1f);
                case DOWN -> moveEntity(entity, 0f, 1f);
                case LEFT -> moveEntity(entity, 1f, 0f);
                case RIGHT -> moveEntity(entity, -1f, 0f);
            }
        }
        controller.getReleasedCommands().clear();
    }

    private void openShop() {
        viewModel.openShop();
    }

    private void weaponSwitch(Entity entity) {
        AttackMode attackModeComp = AttackMode.MAPPER.get(entity);
        ATTACK_MODE mode = attackModeComp.getAttackMode();
        switch (mode) {
            case SWORD -> attackModeComp.setAttackMode(ATTACK_MODE.GUN);
            case GUN -> attackModeComp.setAttackMode(ATTACK_MODE.SWORD);
        }
    }

    private void attackEntity(Entity playerEntity) {
        AttackMode attackModeComp = AttackMode.MAPPER.get(playerEntity);
        ATTACK_MODE mode = attackModeComp.getAttackMode();

        switch (mode) {
            case GUN -> {
                Entity gunWeapon = attackModeComp.getGunEntity();
                if (gunWeapon != null) {
                    shootGun(gunWeapon);
                }
            }
            case SWORD -> {
                Entity swordWeapon = attackModeComp.getSwordEntity();
                if (swordWeapon != null) {
                    spinSword(swordWeapon);
                }
            }
        }
    }

    private void shootGun(Entity gunEntity) {
        Shooter shooter = Shooter.MAPPER.get(gunEntity);

        if (shooter.getShooterState() == ShooterState.IDLE) {
            audioService.playSound(SoundAsset.GUNSHOT);
            shooter.setShooterState(ShooterState.SHOOTING);
        }
    }

    private void spinSword(Entity swordEntity) {
        Melee melee = Melee.MAPPER.get(swordEntity);

        boolean swordSpinning = !melee.isSpinning();
        melee.setSpinning(swordSpinning);
    }

    private void moveEntity(Entity entity, float directionX, float directionY) {
        Move move = Move.MAPPER.get(entity);
        if (move == null) return;

        move.getDirection().x += directionX;
        move.getDirection().y += directionY;
    }
}