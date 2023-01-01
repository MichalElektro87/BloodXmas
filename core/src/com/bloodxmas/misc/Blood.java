package com.bloodxmas.misc;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.bloodxmas.BaseActor;
import com.bloodxmas.BloodXmas;

public class Blood extends BaseActor {

    private final BloodXmas game;
    private TextureAtlas miscTextureAtlas;

    public Blood (final BloodXmas game) {
        this.game = game;

        miscTextureAtlas = game.assets.getAssetManager().get("misc/misc.txt",TextureAtlas.class);
        setTexture(miscTextureAtlas.findRegion("blood"));
        setOrigin(getWidth() / 2f, getHeight() / 2f);

        addAction(Actions.sequence(Actions.moveTo(500f, 250f),Actions.delay(1f),
                  Actions.moveTo(500f, -getHeight(), 2f, Interpolation.slowFast)));
    }

}
