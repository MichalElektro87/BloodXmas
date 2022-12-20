package com.bloodxmas;
import com.badlogic.gdx.Game;

public class BloodXmas extends Game {

	public Assets assets;
	public float screenWidth = 800f;
	public float screenHeight = 480f;
	private boolean assetsLoaded = false;

	public BloodXmas () {
		assets = new Assets();
		assets.loadAssetsManager();
	}

	@Override
	public void create () {

	}

	@Override
	public void render () {
		super.render();

		if (assets.getAssetManager().update() && !assetsLoaded) {
			setScreen(new IntroScreen(this));
			assetsLoaded = true;
		}
	}
	
	@Override
	public void dispose () {
		this.dispose();
	}
}
