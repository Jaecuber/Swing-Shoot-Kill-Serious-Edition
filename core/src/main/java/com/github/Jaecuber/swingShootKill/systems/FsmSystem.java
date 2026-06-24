package com.github.Jaecuber.swingShootKill.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.github.Jaecuber.swingShootKill.component.Fsm;

public class FsmSystem extends IteratingSystem{
    public FsmSystem(){
        super(Family.all(Fsm.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Fsm fsm = Fsm.MAPPER.get(entity);
        if(fsm.getEnemyFsm() != null){
            fsm.getEnemyFsm().update();
        }
    }
}
