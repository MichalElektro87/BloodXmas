package com.bloodxmas;

import com.badlogic.gdx.graphics.g2d.Batch;

public class PresentsSign extends BaseActor {

    private final BloodXmas game;
    public PresentsSign (final BloodXmas game) {
        this.game = game;
        setText("PRESENTS");
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
