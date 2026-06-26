package com.github.Jaecuber.swingShootKill.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.github.Jaecuber.swingShootKill.component.AttackMode;
import com.github.Jaecuber.swingShootKill.component.Melee;
import com.github.Jaecuber.swingShootKill.component.Player;
import com.github.Jaecuber.swingShootKill.component.Stamina;
import com.github.Jaecuber.swingShootKill.component.AttackMode.ATTACK_MODE;

public class StaminaSystem extends IteratingSystem implements EntityListener{
    

    public StaminaSystem(){
        super(Family.all(Stamina.class).get());
       
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Stamina stamina = Stamina.MAPPER.get(entity);
        if(stamina.getCurrentStamina() == stamina.getMaxStamina()) return;

        AttackMode mode = AttackMode.MAPPER.get(entity);
        Entity weaponEntity = mode.getCurrentWeaponEntity();

        

        if(mode.getAttackMode() == ATTACK_MODE.GUN){
            stamina.updateStamina(stamina.getStamRegen() * deltaTime);
            System.out.println("Regenning Stam");
        } else{
            Melee melee = Melee.MAPPER.get(weaponEntity);
            if(melee != null && !melee.isSpinning()){
                stamina.updateStamina(stamina.getStamRegen() * deltaTime);
                System.out.println("Regenning Stam");
            }
        }
        
        
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        engine.addEntityListener(Family.all(Stamina.class, Player.class).get(), this);
    }
       
    @Override
    public void removedFromEngine(Engine engine) {
        super.removedFromEngine(engine);
        engine.removeEntityListener(this);
    }

    @Override
    public void entityAdded(Entity entity) {
        Stamina stamina = Stamina.MAPPER.get(entity);
        
    }

    @Override
    public void entityRemoved(Entity entity) {
    }
}