package com.github.Jaecuber.swingShootKill.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.github.Jaecuber.swingShootKill.component.UpgradeTags;

public class UpgradeSystem extends IteratingSystem {
    public UpgradeSystem(){
        super(Family.all(UpgradeTags.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        UpgradeTags upgradeTags = UpgradeTags.MAPPER.get(entity);
        if(!upgradeTags.isDirty()) return;

        applyUpgrades(entity);
        upgradeTags.setDirty(true);
    }

    private void applyUpgrades(Entity entity) {
        
    }
}
