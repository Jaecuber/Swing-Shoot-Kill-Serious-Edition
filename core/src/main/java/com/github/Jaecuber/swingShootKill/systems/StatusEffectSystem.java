package com.github.Jaecuber.swingShootKill.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.utils.Json;
import com.github.Jaecuber.swingShootKill.asset.AssetService;
import com.github.Jaecuber.swingShootKill.asset.JsonAsset;
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
        StatusEntry config = statusBag.getBulletConfig(statusEffect.getStatusEffect());

        float duration = config.getDuration();
    }

}
