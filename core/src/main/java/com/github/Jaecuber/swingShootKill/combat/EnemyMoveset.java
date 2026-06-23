package com.github.Jaecuber.swingShootKill.combat;

import com.badlogic.ashley.core.Entity;

public interface EnemyMoveset {
    void attack(Entity enemy, Entity player);
}
