package com.github.Jaecuber.swingShootKill.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.ai.pfa.Graph;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.github.Jaecuber.swingShootKill.asset.AssetService;
import com.github.Jaecuber.swingShootKill.asset.AtlasAsset;
import com.github.Jaecuber.swingShootKill.component.Facing;
import com.github.Jaecuber.swingShootKill.component.Graphic;
import com.github.Jaecuber.swingShootKill.component.Facing.FacingDirection;
import com.github.Jaecuber.swingShootKill.component.Move;

public class FacingSystem extends IteratingSystem{
    private AssetService assetService;

    public FacingSystem(AssetService assetService){
        super(Family.all(Facing.class, Move.class).get());
        this.assetService = assetService;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Move move = Move.MAPPER.get(entity);
        Vector2 moveDirection = move.getDirection();

        if(moveDirection.isZero() || move.isRooted()) return;
        
        Facing facing = Facing.MAPPER.get(entity);
        if(Math.abs(moveDirection.y) >= Math.abs(moveDirection.x)){
            if(moveDirection.y > 0f){
                facing.setDirection(FacingDirection.UP);
            }else{
                facing.setDirection(FacingDirection.DOWN);
            }
        }else{
            if(moveDirection.x > 0f){
                facing.setDirection(FacingDirection.RIGHT);
            }else{
                facing.setDirection(FacingDirection.LEFT);
            }
        }

        Graphic graphic = Graphic.MAPPER.get(entity);
        if(graphic != null){
            switch (facing.getDirection()) {
                case UP -> updateGraphic(facing, FacingDirection.UP, graphic);
                case DOWN -> updateGraphic(facing, FacingDirection.DOWN, graphic);
                case LEFT -> updateGraphic(facing, FacingDirection.LEFT, graphic);
                case RIGHT -> updateGraphic(facing, FacingDirection.RIGHT, graphic);
            }
        }
    }

    private void updateGraphic(Facing facing, FacingDirection direction, Graphic graphic){
        String atlasKey = facing.getAtlasKey();
        AtlasAsset atlasAsset = AtlasAsset.valueOf("OBJECTS");
        TextureAtlas textureAtlas = this.assetService.get(atlasAsset);
        String combinedKey = atlasKey + "/" + atlasKey + "_" + direction.getAtlasKey();
        TextureAtlas.AtlasRegion region = textureAtlas.findRegion(combinedKey);
        if(region == null){
            throw new GdxRuntimeException("No atlas region found for key " + combinedKey);
        }
        graphic.setRegion(region);
    }
}
