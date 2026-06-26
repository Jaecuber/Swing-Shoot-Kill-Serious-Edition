package com.github.Jaecuber.swingShootKill.ai;

import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.Telegram;
import com.github.Jaecuber.swingShootKill.logic.RunManager;
import com.github.Jaecuber.swingShootKill.logic.RunManager.RunState;

public enum GameState implements State<RunManager>{
    LEVEL_INTERMISSION{
        @Override
        public void enter(RunManager entity) {
            entity.setState(RunState.LEVEL_INTERMISSION);
        }

        @Override
        public void update(RunManager entity) {
            if(entity.intermissionTimerOver()){
                entity.getGameFsm().changeState(STARTING_WAVE);
            }
        }

        @Override public void exit(RunManager entity) {}
        @Override public boolean onMessage(RunManager entity, Telegram telegram) {return false;}
    },
    STARTING_WAVE{
        @Override
        public void enter(RunManager entity) {
            entity.setState(RunState.STARTING_WAVE);
        }

        @Override
        public void update(RunManager entity) {
            if(entity.getPlayingState()){
                entity.getGameFsm().changeState(PLAYING);
            }
        }

        @Override public void exit(RunManager entity) {}
        @Override public boolean onMessage(RunManager entity, Telegram telegram) {return false;}
    },
    PLAYING{
        @Override
        public void enter(RunManager entity) {
            entity.setState(RunState.PLAYING);
        }

        @Override
        public void update(RunManager entity) {
            if(entity.waveComplete()){
                entity.getGameFsm().changeState(LEVEL_INTERMISSION);
            }
        }

        @Override public void exit(RunManager entity) {}
        @Override public boolean onMessage(RunManager entity, Telegram telegram) {return false;}
    },
}
