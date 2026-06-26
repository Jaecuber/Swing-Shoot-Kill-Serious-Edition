package com.github.Jaecuber.swingShootKill.systems;

import com.github.Jaecuber.swingShootKill.asset.AssetService;
import com.github.Jaecuber.swingShootKill.asset.JsonAsset;
import com.github.Jaecuber.swingShootKill.component.DamageListener;
import com.github.Jaecuber.swingShootKill.component.Health;
import com.github.Jaecuber.swingShootKill.component.Move;
import com.github.Jaecuber.swingShootKill.component.Projectile;
import com.github.Jaecuber.swingShootKill.component.SpecialBullets;
import com.github.Jaecuber.swingShootKill.component.StatusEffect;
import com.github.Jaecuber.swingShootKill.component.Transform;
import com.github.Jaecuber.swingShootKill.data.BulletBag;
import com.github.Jaecuber.swingShootKill.data.BulletEntry;

import com.github.Jaecuber.ui.model.GameViewModel;

import java.io.ObjectInputFilter.Status;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;

public class ProjectileSystem extends IteratingSystem{
    
    private BulletBag bulletBag;
    private AssetService assetService;



    public ProjectileSystem(AssetService assetService){
        super(Family.all(Projectile.class, Move.class, Transform.class).get());
        
        String raw = assetService.get(JsonAsset.BULLET_BAG);
        Json json = new Json();
        BulletBag bag = json.fromJson(BulletBag.class, raw);
        bag.initializeMap();

        this.bulletBag = bag;
        
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
       Projectile projectile = Projectile.MAPPER.get(entity);

        
      
        if(projectile.getHitEntity() != null){
            collisionLogic(entity, projectile.getHitEntity());
        }

       projectile.setLifetime(deltaTime);
       
       if(!projectile.isActive()){
            getEngine().removeEntity(entity);
            return;
       }

    }

    private void collisionLogic(Entity projEntity, Entity hitEntity){
        Health health = Health.MAPPER.get(hitEntity);
        Projectile projectile = Projectile.MAPPER.get(projEntity);
        float damage = projectile.getOwnerDamage();

        if(SpecialBullets.MAPPER.get(projEntity) != null){
            applyBulletEffects(projEntity, hitEntity);
            damage *= bulletBag.getBulletConfig(SpecialBullets.MAPPER.get(projEntity).getBulletType()).getDamageMultiplier();
        }

        if(health == null){
           getEngine().removeEntity(projEntity);
            return;
        }

        DamageListener damageListener = DamageListener.MAPPER.get(hitEntity);
        if (damageListener == null) {
            hitEntity.add(new DamageListener(damage));
        } else {
            damageListener.addDamage(damage);
        }

        
        getEngine().removeEntity(projEntity);
    }

    private void applyBulletEffects(Entity projEntity, Entity hitEntity){
        BulletEntry config = bulletBag.getBulletConfig(SpecialBullets.MAPPER.get(projEntity).getBulletType());

        if(config.getOnHitStatusEffect() != null){
            hitEntity.add(new StatusEffect(config.getOnHitStatusEffect()));
            return;
        }


    }
}