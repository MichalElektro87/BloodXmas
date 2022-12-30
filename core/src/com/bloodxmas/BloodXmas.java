package com.bloodxmas;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.math.RandomXS128;
import com.badlogic.gdx.math.Rectangle;

public class BloodXmas extends Game {

	public Assets assets;
	public Player player;
	public Santa santa;
	public float screenWidth = 800f;
	public float screenHeight = 480f;
	public RandomXS128 randomXS128;
	private boolean assetsLoaded = false;

	public BloodXmas () {
		assets = new Assets();
		assets.loadAssetsManager();
		randomXS128 = new RandomXS128();

	}

	@Override
	public void create () {

	}

	@Override
	public void render () {
		super.render();

		if (assets.getAssetManager().update() && !assetsLoaded) {
			setScreen(new TitleScreen(this));
			assetsLoaded = true;
		}
	}


	
	@Override
	public void dispose () {
		this.dispose();
	}
}
