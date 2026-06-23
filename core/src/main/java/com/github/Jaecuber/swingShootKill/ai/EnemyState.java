package com.github.Jaecuber.swingShootKill.ai;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.math.MathUtils;
import com.github.Jaecuber.swingShootKill.component.Enemy;
import com.github.Jaecuber.swingShootKill.component.Enemy.EnemyAIState;
import com.github.Jaecuber.swingShootKill.component.Fsm;
import com.github.Jaecuber.swingShootKill.component.Move;

public enum EnemyState implements State<Entity>{
    IDLE{
        @Override
        public void enter(Entity entity) {
            Enemy.MAPPER.get(entity).setState(EnemyAIState.IDLE);
            Enemy.MAPPER.get(entity).setStateTimer(MathUtils.random(7f, 14f));
            Move.MAPPER.get(entity).setDirection(0, 0);
        }

        @Override
        public void update(Entity entity) {
            if(Enemy.MAPPER.get(entity).canAttack()){
                Fsm.MAPPER.get(entity).getEnemyFsm().changeState(ATTACKING);
                return;
            }
            if(Enemy.MAPPER.get(entity).isAggro() && !Enemy.MAPPER.get(entity).canAttack()){
                Fsm.MAPPER.get(entity).getEnemyFsm().changeState(PURSUING);
                return;
            } else if(Enemy.MAPPER.get(entity).isStateTimerDone()){
                Enemy.MAPPER.get(entity).setStateTimer(Float.MAX_VALUE);
                Fsm.MAPPER.get(entity).getEnemyFsm().changeState(WANDERING);
                return;
            }
        }
        @Override public void exit(Entity entity) {}
        @Override public boolean onMessage(Entity entity, Telegram telegram) {return false;}
    },
    WANDERING{
        @Override
        public void enter(Entity entity) {
            Enemy enemy = Enemy.MAPPER.get(entity);
            enemy.setState(EnemyAIState.WANDERING);
            enemy.setStateTimer(MathUtils.random(7f, 14f));
            enemy.setWandering(false);
            enemy.setWanderTimer(0f);
            Move.MAPPER.get(entity).setDirection(0, 0);
        }

        @Override
        public void update(Entity entity) {
            if(Enemy.MAPPER.get(entity).canAttack()){
                Fsm.MAPPER.get(entity).getEnemyFsm().changeState(ATTACKING);
                return;
            }
            if(Enemy.MAPPER.get(entity).isAggro() && !Enemy.MAPPER.get(entity).canAttack()){
                Fsm.MAPPER.get(entity).getEnemyFsm().changeState(PURSUING);
                return;
            }else if(Enemy.MAPPER.get(entity).isStateTimerDone()){
                Enemy.MAPPER.get(entity).setStateTimer(Float.MAX_VALUE);
                Fsm.MAPPER.get(entity).getEnemyFsm().changeState(IDLE);
                return;
            }
        }
        @Override public void exit(Entity entity) {}
        @Override public boolean onMessage(Entity entity, Telegram telegram) {return false;}
    },
    PURSUING{
        @Override
        public void enter(Entity entity) {
            Enemy.MAPPER.get(entity).setState(EnemyAIState.PURSUING);
        }

        @Override
        public void update(Entity entity) {
            if(Enemy.MAPPER.get(entity).canAttack()){
                Fsm.MAPPER.get(entity).getEnemyFsm().changeState(ATTACKING);
                return;
            }
            if(!Enemy.MAPPER.get(entity).isAggro()){
                Fsm.MAPPER.get(entity).getEnemyFsm().changeState(IDLE);
                return;
            }
        }
        @Override public void exit(Entity entity) {}
        @Override public boolean onMessage(Entity entity, Telegram telegram) {return false;}
    },
    ATTACKING{
        @Override
        public void enter(Entity entity) {
            Enemy.MAPPER.get(entity).setState(EnemyAIState.ATTACKING);
        }

        @Override
        public void update(Entity entity) {
            if(!Enemy.MAPPER.get(entity).canAttack()){
                Fsm.MAPPER.get(entity).getEnemyFsm().changeState(PURSUING);
                return;
            }
        }
        @Override public void exit(Entity entity) {}
        @Override public boolean onMessage(Entity entity, Telegram telegram) {return false;}
    },
     DEATH{
        @Override
        public void enter(Entity entity) {
            Enemy.MAPPER.get(entity).setState(EnemyAIState.DEAD);
            Move.MAPPER.get(entity).setRooted(true);
        }

        @Override
        public void update(Entity entity) {
            Enemy.MAPPER.get(entity).setDead(true);
        }
        @Override public void exit(Entity entity) {}
        @Override public boolean onMessage(Entity entity, Telegram telegram) {return false;}
    }
}
