package com.bloodxmas;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class EvenDeathAnimation extends BaseActor {

    private BloodXmas game;
    public TextureAtlas deathAnimationTextureAtlas[];

    public EvenDeathAnimation(BloodXmas game) {
        this.game = game;
        deathAnimationTextureAtlas = new TextureAtlas[1];
    }


    @Override
    public void act(float delta) {
        super.act(delta);
        update();
    }

    public void update () {
        if (getElapsedTime() > getAnimationSingle().getAnimationDuration()) {
            setVisible(false);
            resetElapsedTime();
            remove();
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

    public void setAnimation(boolean sideLeft) {

        if (sideLeft) {
            deathAnimationTextureAtlas[0] = game.assets.getAssetManager().get("elven/animation/death_left/death_left.atlas",TextureAtlas.class);
        }
        else {
            deathAnimationTextureAtlas[0] = game.assets.getAssetManager().get("elven/animation/death_right/death_right.atlas",TextureAtlas.class);
        }
        setAnimationFromTextureAtlas(deathAnimationTextureAtlas[0], 1 / 15f);
        getCurrentSingleAnimation().setPlayMode(Animation.PlayMode.NORMAL);
    }
}