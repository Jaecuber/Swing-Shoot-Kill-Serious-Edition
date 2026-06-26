package com.github.Jaecuber.swingShootKill.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.utils.ObjectMap;
import com.github.Jaecuber.swingShootKill.component.Shooter;
import com.github.Jaecuber.swingShootKill.component.UpgradeTags;

public class UpgradeSystem extends IteratingSystem {
    private Engine engine;

    public UpgradeSystem(Engine engine){
        super(Family.all(UpgradeTags.class).get());
        this.engine = engine;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        UpgradeTags upgradeTags = UpgradeTags.MAPPER.get(entity);
        if(!upgradeTags.isDirty()) return;

        applyUpgrades(entity);
        upgradeTags.setDirty(false);
    }

    private void applyUpgrades(Entity entity) {
        
    }
}
