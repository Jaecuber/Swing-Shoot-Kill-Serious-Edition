package com.github.Jaecuber.swingShootKill.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.github.Jaecuber.swingShootKill.component.DamageListener;
import com.github.Jaecuber.swingShootKill.component.Health;
import com.github.Jaecuber.swingShootKill.component.Player;
import com.github.Jaecuber.swingShootKill.component.Transform;
import com.github.Jaecuber.ui.model.GameViewModel;

public class DamageSystem extends IteratingSystem{
    private final GameViewModel viewModel;

    public DamageSystem(GameViewModel viewModel){
        super(Family.all(DamageListener.class).get());
        this.viewModel = viewModel;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        DamageListener damage = DamageListener.MAPPER.get(entity);
        entity.remove(DamageListener.class);

        Health health = Health.MAPPER.get(entity);
        if(health != null){
            health.addHealth(-damage.getDamage());
        }
        Transform transform = Transform.MAPPER.get(entity);
        Player player = Player.MAPPER.get(entity);
        if(transform != null && player == null){
            float x = transform.getPosition().x + transform.getSize().x * 0.5f;
            float y = transform.getPosition().y;
            //viewModel.playerDamage((int) damage.getDamage(), x, y);
        }
    }
}
