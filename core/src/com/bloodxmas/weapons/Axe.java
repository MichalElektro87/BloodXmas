package com.bloodxmas.weapons;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.bloodxmas.BaseActor;
import com.bloodxmas.BloodXmas;

public class Axe extends BaseActor {

    private final BloodXmas game;
    private TextureAtlas miscTextureAtlas;
    private boolean motion = false;

    public Axe (final BloodXmas game) {
        this.game = game;

        miscTextureAtlas = game.assets.getAssetManager().get("misc/misc.txt",TextureAtlas.class);
        setTexture(miscTextureAtlas.findRegion("axe"));
        setOrigin(getWidth() / 2f, getHeight() / 2f);
        setVisible(false);
    }

    public boolean isMotion() {
        return motion;
    }

    public void setMotion(boolean motion) {
        this.motion = motion;
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if (getY() > 50f)
            setMotion(true);
        else
            setMotion(false);
    }
}
