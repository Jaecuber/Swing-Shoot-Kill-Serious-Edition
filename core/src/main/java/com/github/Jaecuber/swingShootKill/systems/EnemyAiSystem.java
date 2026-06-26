package com.github.Jaecuber.swingShootKill.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.ObjectMap;
import com.github.Jaecuber.swingShootKill.component.Coins;
import com.github.Jaecuber.swingShootKill.component.DamageListener;
import com.github.Jaecuber.swingShootKill.component.Enemy;
import com.github.Jaecuber.swingShootKill.component.Fsm;
import com.github.Jaecuber.swingShootKill.component.Light;
import com.github.Jaecuber.swingShootKill.component.Move;
import com.github.Jaecuber.swingShootKill.component.Physics;
import com.github.Jaecuber.swingShootKill.component.Player;
import com.github.Jaecuber.swingShootKill.component.Transform;
import com.github.Jaecuber.ui.model.GameViewModel;

public class EnemyAiSystem extends IteratingSystem{
    private Entity playerEntity;
    private GameViewModel viewModel;
    private final Array<Entity> deadEntityCache = new Array<>();

    public EnemyAiSystem(GameViewModel viewModel){
        super(Family.all(Enemy.class, Transform.class, Fsm.class, Move.class).get());
        this.viewModel = viewModel;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        //getting the player entity
        if (playerEntity == null) {
            ImmutableArray<Entity> players = getEngine().getEntitiesFor(
                Family.all(Player.class, Physics.class).get()
            );
            if (players.size() > 0) {
                playerEntity = players.first();
            } else {
                return;
            }
        }
        Enemy enemy = Enemy.MAPPER.get(entity);
        Physics physics = Physics.MAPPER.get(entity);
        Move move = Move.MAPPER.get(entity);
        Body body = physics.getBody();

        //configuring enemy physics body
        if(!body.isBullet()){
            body.setBullet(true);
        }
        if(body.isSleepingAllowed()){
            body.setSleepingAllowed(false);
            body.setAwake(true);
        }
        enemy.tickStateTimer(deltaTime);
        enemy.tickKnockbackTimer(deltaTime);
        enemy.tickAttackTimer(deltaTime);

        Entity playerEntity = enemy.getPlayerEntity();

        if(playerEntity != null && enemy.isAttacking() && !enemy.hasDamaged()){
            enemy.setHasDamaged(true);
            viewModel.displayDamage();
            DamageListener damage = DamageListener.MAPPER.get(playerEntity);
            if (damage == null) {
                playerEntity.add(new DamageListener(enemy.getDamage()));
            } else {
                damage.addDamage(enemy.getDamage());
            }
        }

        //knockback stuff
        if(enemy.isStaggered() && body != null){
            body.setLinearDamping(10f);
            return;
        }else if(!enemy.isStaggered() && body != null){
            body.setLinearDamping(0f);
        }

        //fsm stuff
        switch (enemy.getState()) {
            case IDLE -> enterIdle(entity);
            case ATTACKING -> attack(entity, deltaTime);
            case PURSUING -> pursuePlayer(entity);
            case WANDERING -> wander(entity, deltaTime);
            case DEAD -> death(entity);
            default -> throw new GdxRuntimeException("Unexpected value: " + enemy.getState());
        }
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        for(Entity entity : deadEntityCache){
            Light light = Light.MAPPER.get(entity);
            light.getPointLight().remove();
            
            getEngine().removeEntity(entity);
        }
        deadEntityCache.clear();
    }

    private void enterIdle(Entity entity) {
        Move move = Move.MAPPER.get(entity);
        move.setRooted(true);;
    }

    private void attack(Entity entity, float deltaTime) {
        if(playerEntity == null) return;
        Enemy enemy = Enemy.MAPPER.get(entity);
        
        if(enemy.getMoveset() != null && !enemy.isAttacking()){
            enemy.getMoveset().attack(entity, playerEntity);
        }
    }

    private void pursuePlayer(Entity entity) {
        if (playerEntity == null) return;
        Body body = Physics.MAPPER.get(entity).getBody();
        Body playerBody = Physics.MAPPER.get(playerEntity).getBody();
        Move move = Move.MAPPER.get(entity);
        move.setRooted(false);

        Vector2 playerCenter = playerBody.getWorldCenter();
        Vector2 enemyCenter = body.getWorldCenter();
        Vector2 diff = new Vector2(
            playerCenter.x - enemyCenter.x,
            playerCenter.y - enemyCenter.y
        );

        if(diff.len() < 0.1f) {
            move.setDirection(0, 0);
            return;
        }

        Vector2 separation = calcSeparation(entity);
        Vector2 finalDirection = diff.nor().add(separation.scl(0.8f));

        move.setDirection(finalDirection.x, finalDirection.y);
    }

    private void wander(Entity entity, float deltaTime) {
        Enemy enemy = Enemy.MAPPER.get(entity);
        Move move = Move.MAPPER.get(entity);
        move.setRooted(false);

        enemy.tickWanderTimer(deltaTime);

        if(enemy.isWanderTimerDone()){
            if(enemy.isWandering()){     
                move.setDirection(0,0);
                enemy.setWandering(false);
                enemy.setWanderTimer(MathUtils.random(1.f,2.0f));
            }else{
                move.setDirection(MathUtils.random(-1f, 1f), MathUtils.random(-0.5f, 0.5f));
                enemy.setWandering(true);
                enemy.setWanderTimer(MathUtils.random(1.0f,2.0f));
            }
        }
    }

    private void death(Entity entity) {
        Enemy enemy = Enemy.MAPPER.get(entity);
        Coins coins = Coins.MAPPER.get(playerEntity);
        if(coins != null){
            coins.addCoins(enemy.getValue());
        }
        if(enemy.isDead()){
            deadEntityCache.add(entity);
        }
    }

    private Vector2 calcSeparation(Entity entity){
        Body body = Physics.MAPPER.get(entity).getBody();
        Vector2 separation = new Vector2();
        float separationRad = 2.0f;

        for(Entity other : getEntities()){
            if(other == entity) continue;

            Body otherBody = Physics.MAPPER.get(other).getBody();
            Vector2 diff = new Vector2(
                body.getPosition().x - otherBody.getPosition().x,
                body.getPosition().y - otherBody.getPosition().y
            );

            float distance = diff.len();
            if (distance < separationRad && distance > 0) {
                separation.add(diff.nor().scl(separationRad - distance));
            }
        }
        return separation;
    }
}
