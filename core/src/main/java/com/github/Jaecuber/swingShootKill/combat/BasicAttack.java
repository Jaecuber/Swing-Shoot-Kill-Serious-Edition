package com.github.Jaecuber.swingShootKill.combat;

import com.badlogic.ashley.core.Entity;
import com.github.Jaecuber.swingShootKill.component.Enemy;

public class BasicAttack implements EnemyMoveset{
    @Override
    public void attack(Entity entity, Entity player) {
        Enemy enemy = Enemy.MAPPER.get(entity);
        enemy.applyAttack();
    }
}
