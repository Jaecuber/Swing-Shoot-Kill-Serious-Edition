package com.github.Jaecuber.swingShootKill.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.utils.ObjectMap;
import com.github.Jaecuber.swingShootKill.component.Melee;
import com.github.Jaecuber.swingShootKill.component.Move;
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
        calcShootingCooldown(entity);
        calcMoveSpeed(entity);
        addSpecialBullets(entity);
        calcStamConsume(entity);
        mutualDestruction(entity);
    }
    
    private void addSpecialBullets(Entity entity) {
        UpgradeTags upgradeTags = UpgradeTags.MAPPER.get(entity);
        ObjectMap<String, Integer> upgradesOwned = upgradeTags.getTags();

        Entity shooterEntity = engine.getEntitiesFor(Family.all(Shooter.class).get()).first();
        Shooter shooter = Shooter.MAPPER.get(shooterEntity);

        if(upgradesOwned.containsKey("Heavy Bullet")){
            shooter.addSpecialBullet("Heavy Bullet");
        }
        if(upgradesOwned.containsKey("Bullet of Life")){
            shooter.addSpecialBullet("Healing Bullet");
        }
        if(upgradesOwned.containsKey("Confusing Firepower?")){
            shooter.addSpecialBullet("Distraction Bullet");
        }
    }

    private void calcShootingCooldown(Entity entity){
        UpgradeTags upgradeTags = UpgradeTags.MAPPER.get(entity);
        ObjectMap<String, Integer> upgradesOwned = upgradeTags.getTags();
        float totalMultiplier = 1.0f;

        Entity shooterEntity = engine.getEntitiesFor(Family.all(Shooter.class).get()).first();
        Shooter shooter = Shooter.MAPPER.get(shooterEntity);

        if(upgradesOwned.containsKey("Steady Hand")){
            totalMultiplier *= Math.pow(0.90f, upgradesOwned.get("Steady Hand"));
        }
        if(upgradesOwned.containsKey("Expert Handling")){
            totalMultiplier *= Math.pow(0.75f, upgradesOwned.get("Expert Handling"));
        }
        if(upgradesOwned.containsKey("Overclocked Revolver")){
            totalMultiplier *= Math.pow(0.65f, upgradesOwned.get("Overclocked Revolver"));
        }
        if(upgradesOwned.containsKey("Cursed Revolver")){
            totalMultiplier *= 0.10;
            shooter.setCapacity(24);
        }

        float finalCooldown = shooter.getDefaultCooldown() * totalMultiplier;
        shooter.setCooldown(finalCooldown);
    }

    private void calcMoveSpeed(Entity entity){
        UpgradeTags upgradeTags = UpgradeTags.MAPPER.get(entity);
        ObjectMap<String, Integer> upgradesOwned = upgradeTags.getTags();
        float totalMultiplier = 1.0f;

        Move move = Move.MAPPER.get(entity);

        if(upgradesOwned.containsKey("Haste")){
            totalMultiplier *= Math.pow(1.15f, upgradesOwned.get("Haste"));
        }
        if(upgradesOwned.containsKey("Dexterity")){
            totalMultiplier *= Math.pow(1.35f, upgradesOwned.get("Dexterity"));
        }
        if(upgradesOwned.containsKey("Accelerator")){
            totalMultiplier *= 2;
        }

        float finalMoveSpeed = move.getDefaultMaxSpeed() * totalMultiplier; 
        move.setMaxSpeed(finalMoveSpeed);
    }

    private void calcStamConsume(Entity entity){
        UpgradeTags upgradeTags = UpgradeTags.MAPPER.get(entity);
        ObjectMap<String, Integer> upgradesOwned = upgradeTags.getTags();
        float totalMultiplier = 1.0f;

        Entity meleeEntity = engine.getEntitiesFor(Family.all(Melee.class).get()).first();
        Melee melee = Melee.MAPPER.get(meleeEntity);

        if(upgradesOwned.containsKey("Resilience")){
            totalMultiplier *= Math.pow(0.90f, upgradesOwned.get("Resilience"));
        }
        if(upgradesOwned.containsKey("Tenacity")){
            totalMultiplier *= Math.pow(0.65f, upgradesOwned.get("Tenacity"));
        }
        if(upgradesOwned.containsKey("Endless Spin")){
            totalMultiplier *= Math.pow(0.30f, upgradesOwned.get("Endless Spin"));
        }
        if(upgradesOwned.containsKey("Chained Greatsword")){
            totalMultiplier *= 1.90;
        }

        float finalStamConsume = melee.getDefaultStamConsume() * totalMultiplier;
        melee.setStamConsume(finalStamConsume);
    }

    private void mutualDestruction(Entity entity){
        UpgradeTags upgradeTags = UpgradeTags.MAPPER.get(entity);
        ObjectMap<String, Integer> upgradesOwned = upgradeTags.getTags();

        Entity meleeEntity = engine.getEntitiesFor(Family.all(Melee.class).get()).first();
        Melee melee = Melee.MAPPER.get(meleeEntity);

        Entity shooterEntity = engine.getEntitiesFor(Family.all(Shooter.class).get()).first();
        Shooter shooter = Shooter.MAPPER.get(shooterEntity);

        if(upgradesOwned.containsKey("Mutual Destruction")){
            melee.setDamage(999.0f);
            shooter.setDamage(999.0f);
        } 
    }

}
