package com.bloodxmas.misc;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.bloodxmas.BaseActor;
import com.bloodxmas.BloodXmas;

public class Door extends BaseActor {
    private final BloodXmas game;
    private TextureAtlas miscTextureAtlas;

    public Door(final  BloodXmas game) {
        this.game = game;
        miscTextureAtlas = game.assets.getAssetManager().get("misc/misc.txt",TextureAtlas.class);
        setTexture(miscTextureAtlas.findRegion("door"));
        setOrigin(getWidth() / 2f, getHeight() / 2f);
    }
}

