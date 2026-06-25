package com.github.Jaecuber.swingShootKill.ai;

import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.Telegram;
import com.github.Jaecuber.swingShootKill.logic.RunManager;

public enum GameState implements State<RunManager>{
    LEVEL_INTERMISSION{

        @Override
        public void enter(RunManager entity) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'enter'");
        }

        @Override
        public void update(RunManager entity) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'update'");
        }

        @Override
        public void exit(RunManager entity) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'exit'");
        }

        @Override
        public boolean onMessage(RunManager entity, Telegram telegram) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'onMessage'");
        }
        
    }
}
