package com.bloodxmas;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class Santa extends BaseActor {

    private final BloodXmas game;
    private TextureAtlas santaTextureAtlas[];
    private float santaAnimationFrameRate[];
    public Santa(final BloodXmas game) {
        this.game = game;

        addAction(Actions.forever(Actions.sequence(Actions.moveBy(0f,50f,2f,Interpolation.swing),
                Actions.moveBy(0f,-50f,2, Interpolation.swing))));
    }

    public void setAnimation() {
        santaTextureAtlas = new TextureAtlas[1];
        santaAnimationFrameRate = new float[santaTextureAtlas.length];
        santaTextureAtlas[0] = game.assets.getAssetManager().get("santa/animation/santa.atlas",TextureAtlas.class);
        santaAnimationFrameRate[0] = 1 / 3f;
        setAnimationFromTextureAtlas(santaTextureAtlas, santaAnimationFrameRate);
        setAnimationLoopType(Animation.PlayMode.NORMAL);
        setCurrentAnimation(0);
}

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }
}
