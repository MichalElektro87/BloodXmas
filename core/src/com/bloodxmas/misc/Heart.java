package com.bloodxmas.misc;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.bloodxmas.BaseActor;
import com.bloodxmas.BloodXmas;

public class Heart extends BaseActor {
    private final BloodXmas game;
    private TextureAtlas miscTextureAtlas;

    public Heart(final  BloodXmas game) {
        this.game = game;
        miscTextureAtlas = game.assets.getAssetManager().get("misc/misc.txt",TextureAtlas.class);
        setTexture(miscTextureAtlas.findRegion("heart"));
        setOrigin(getWidth() / 2f, getHeight() / 2f);
    }
}
