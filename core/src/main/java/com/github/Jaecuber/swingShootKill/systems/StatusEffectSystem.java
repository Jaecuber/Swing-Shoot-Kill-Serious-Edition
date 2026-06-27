package com.github.Jaecuber.swingShootKill.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.utils.Json;
import com.github.Jaecuber.swingShootKill.asset.AssetService;
import com.github.Jaecuber.swingShootKill.asset.JsonAsset;
import com.github.Jaecuber.swingShootKill.component.Enemy;
import com.github.Jaecuber.swingShootKill.component.Health;
import com.github.Jaecuber.swingShootKill.component.StatusEffect;
import com.github.Jaecuber.swingShootKill.data.BulletBag;
import com.github.Jaecuber.swingShootKill.data.StatusBag;
import com.github.Jaecuber.swingShootKill.data.StatusEntry;

public class StatusEffectSystem extends IteratingSystem {

    private StatusBag statusBag;

    public StatusEffectSystem(AssetService assetService){
        super(Family.all(StatusEffect.class).get());

        String raw = assetService.get(JsonAsset.STATUS_EFFECT_BAG);
        Json json = new Json();
        StatusBag bag = json.fromJson(StatusBag.class, raw);
        bag.initializeMap();

        this.statusBag = bag;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        StatusEffect statusEffect = StatusEffect.MAPPER.get(entity);
        StatusEntry config = statusBag.getStatusConfig(statusEffect.getStatusEffect());

        statusEffect.setDuration(config.getDuration());
        
        float tickInterval = config.getTickInterval();
        float damagePerTick = config.getDamagePerTick();

        statusEffect.addTimeElapsed(deltaTime);

        if(statusEffect.isDone()) {
            entity.remove(StatusEffect.class);
            return;
        }

        Enemy enemy = Enemy.MAPPER.get(entity);

        if(damagePerTick == 0){
            if(enemy.isStaggered()) return;

            if (statusEffect.getTimeElapsed() <= deltaTime) { 
                System.out.println("Stunned applied!");
                enemy.applyKnockback(config.getDuration());
            }
            enemy.tickKnockbackTimer(deltaTime);
        } else {
            statusEffect.addTickTimer(deltaTime);
            if (statusEffect.getTickTimer() >= tickInterval) {
                Health health = Health.MAPPER.get(entity);
                if (health != null) {
                    health.addHealth(-damagePerTick);
                    System.out.println("Tick Damage: " + damagePerTick);
                }
            
            statusEffect.setTickTimer(statusEffect.getTickTimer() - tickInterval); 
            }   
        }
    }
}
