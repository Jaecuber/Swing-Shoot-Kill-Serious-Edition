package com.github.Jaecuber.swingShootKill.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.github.Jaecuber.swingShootKill.ai.EnemyState;

public class Fsm implements Component{
    public static final ComponentMapper<Fsm> MAPPER = ComponentMapper.getFor(Fsm.class);

    private DefaultStateMachine<Entity, EnemyState> enemyFsm;

    public Fsm(Entity owner){
        this.enemyFsm = new DefaultStateMachine<Entity, EnemyState>(owner, EnemyState.IDLE);
    }

    public DefaultStateMachine<Entity, EnemyState> getEnemyFsm(){
        return enemyFsm;
    }
}
