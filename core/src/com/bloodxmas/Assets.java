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
        assetManager.load("sleigh/sleigh.txt",TextureAtlas.class);
    }
}
