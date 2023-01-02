package com.bloodxmas;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Assets {

    private AssetManager assetManager;

    public Assets() {
        assetManager = new AssetManager();
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }

    public void loadAssetsManager() {
        assetManager.load("sim_guard/sim_guard.txt",TextureAtlas.class);
        assetManager.load("misc/misc.txt",TextureAtlas.class);
        assetManager.load("santa/animation/santa.atlas",TextureAtlas.class);
        assetManager.load("background/background.txt",TextureAtlas.class);

        assetManager.load("player/animation/no_move_right/no_move_right.atlas",TextureAtlas.class);
        assetManager.load("player/animation/no_move_left/no_move_left.atlas",TextureAtlas.class);
        assetManager.load("player/animation/move_right/move_right.atlas",TextureAtlas.class);
        assetManager.load("player/animation/move_left/move_left.atlas",TextureAtlas.class);
        assetManager.load("player/animation/attack_right/attack_right.atlas",TextureAtlas.class);
        assetManager.load("player/animation/attack_left/attack_left.atlas",TextureAtlas.class);

        assetManager.load("elven/animation/no_move_right/no_move_right.atlas",TextureAtlas.class);
        assetManager.load("elven/animation/no_move_left/no_move_left.atlas",TextureAtlas.class);
        assetManager.load("elven/animation/move_right/move_right.atlas",TextureAtlas.class);
        assetManager.load("elven/animation/move_left/move_left.atlas",TextureAtlas.class);
        assetManager.load("elven/animation/shoot_right/shoot_right.atlas",TextureAtlas.class);
        assetManager.load("elven/animation/shoot_left/shoot_left.atlas",TextureAtlas.class);
        assetManager.load("elven/animation/death_right/death_right.atlas",TextureAtlas.class);
        assetManager.load("elven/animation/death_left/death_left.atlas",TextureAtlas.class);

        assetManager.load("sound/damage1.mp3",Sound.class);
        assetManager.load("sound/damage2.mp3", Sound.class);
        assetManager.load("sound/damage3.mp3", Sound.class);
        assetManager.load("sound/throw_axe.mp3", Sound.class);
        assetManager.load("sound/ding.mp3", Sound.class);


    }
}
