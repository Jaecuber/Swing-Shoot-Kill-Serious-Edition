package com.github.Jaecuber.swingShootKill.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;

public class AttackMode implements Component {
    public static final ComponentMapper<AttackMode> MAPPER = ComponentMapper.getFor(AttackMode.class);

    public enum ATTACK_MODE{GUN, SWORD};

    public ATTACK_MODE attackMode;

    public AttackMode(ATTACK_MODE attackMode){
        this.attackMode = attackMode;
    }

    public ATTACK_MODE getAttackMode() {
        return attackMode;
    }

    public void setAttackMode(ATTACK_MODE attackMode) {
        this.attackMode = attackMode;
    }

}
