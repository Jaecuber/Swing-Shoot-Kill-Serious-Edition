package com.github.Jaecuber.swingShootKill.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.github.Jaecuber.swingShootKill.combat.EnemyMoveset;

public class Enemy implements Component{
    public static final ComponentMapper<Enemy> MAPPER = ComponentMapper.getFor(Enemy.class);

    private EnemyAIState state;
    private EnemyMoveset moveset;
    private Entity playerEntity;

    private boolean dead;
    private boolean isAggro;
    private boolean isIdle;
    private boolean isWandering;
    private boolean isStaggered;
    private boolean attacking;
    private boolean canAttack;
    private boolean hasDamaged;
    private float speed;
    private float stateTimer;
    private float wanderTimer;
    private float knockbackTimer;
    private float cooldownTimer;
    private float attackCooldown;
    private float damage;

    public Enemy(EnemyAIState state, float speed, float cooldown, float damage){
        this.state = state;
        this.speed = speed;
        this.attackCooldown = cooldown;
        this.damage = damage;
        this.isAggro = false;
        this.isIdle = true;
        this.dead = false;
    }

    public void applyKnockback(float duration){
        this.isStaggered = true;
        this.knockbackTimer = duration;
    }

    

    public void applyAttack(){
        this.attacking = true;
        this.hasDamaged = false;
        this.cooldownTimer = attackCooldown;
    }

    public void tickAttackTimer(float deltaTime){
        if(!attacking) return;
        cooldownTimer -= deltaTime;
        if(cooldownTimer <= 0f){
            attacking = false;
        }
    }

    public void tickKnockbackTimer(float deltaTime) {
        if (!isStaggered) return;
        knockbackTimer -= deltaTime;
        if (knockbackTimer <= 0f) {
            isStaggered = false;
        }
    }

    public boolean isAttacking() {
        return attacking;
    }

    public boolean canAttack() {
        return canAttack;
    }

    public void setAttackStatus(boolean canAttack) {
        this.canAttack = canAttack;
    }
    
    public boolean isStaggered(){ 
        return isStaggered; 
    }

    public void setStateTimer(float stateTimer) { 
        this.stateTimer = stateTimer; 
    }

    public void setWanderTimer(float time){
        this.wanderTimer = time;
    }

    public void tickStateTimer(float deltaTime) { 
        this.stateTimer -= deltaTime; 
    }

    public void setCooldown(float time){
        this.cooldownTimer = time;
    }

    public void tickWanderTimer(float deltaTime){
        this.wanderTimer -= deltaTime;
    }

    public boolean isWanderTimerDone(){ 
        return this.wanderTimer <= 0f; 
    }

    public boolean isStateTimerDone(){ 
        return this.stateTimer <= 0f; 
    }

    public boolean isWandering(){
        return isWandering; 
    }

    public void setWandering(boolean wandering){ 
        this.isWandering = wandering; 
    }

    public float getSpeed() {
        return speed;
    }

    public float getCooldown(){
        return attackCooldown;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public void setAggro(boolean aggro){
        this.isAggro = aggro;
    }

    public boolean isAggro(){
        return this.isAggro;
    }

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public boolean isIdle() {
        return isIdle;
    }

    public void setIdle(boolean isIdle) {
        this.isIdle = isIdle;
    }

    public EnemyAIState getState(){
        return state;
    }

    public void setState(EnemyAIState state){
        this.state = state;
    }
    
    public EnemyMoveset getMoveset() {
        return moveset;
    }

    public void setMoveset(EnemyMoveset moveset) {
        this.moveset = moveset;
    }

    public float getDamage() {
        return damage;
    }

    public boolean hasDamaged() {
        return hasDamaged;
    }

    public void setHasDamaged(boolean hasDamaged) {
        this.hasDamaged = hasDamaged;
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }

    public float getStateTimer() {
       return stateTimer;
    }

    public Entity getPlayerEntity() {
        return playerEntity;
    }

    public void setPlayerEntity(Entity playerEntity) {
        this.playerEntity = playerEntity;
    }

    public enum EnemyAIState{
        IDLE, WANDERING, PURSUING, ATTACKING, DEAD
    }
}
