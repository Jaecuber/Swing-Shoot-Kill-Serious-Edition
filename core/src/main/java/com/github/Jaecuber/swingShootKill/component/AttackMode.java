package com.github.Jaecuber.swingShootKill.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;

public class AttackMode implements Component {
    public static final ComponentMapper<AttackMode> MAPPER = ComponentMapper.getFor(AttackMode.class);

    public enum ATTACK_MODE{GUN, SWORD};

    public ATTACK_MODE attackMode;

    private Entity currentWeaponEntity;

    public AttackMode(ATTACK_MODE attackMode){
        this.attackMode = attackMode;
        currentWeaponEntity = null;
    }

    public ATTACK_MODE getAttackMode() {
        return attackMode;
    }

    public void setAttackMode(ATTACK_MODE attackMode) {
        this.attackMode = attackMode;
    }

    public Entity getCurrentWeaponEntity() {
        return currentWeaponEntity;
    }

    public void setCurrentWeaponEntity(Entity currentWeaponEntity) {
        this.currentWeaponEntity = currentWeaponEntity;
    }

}
