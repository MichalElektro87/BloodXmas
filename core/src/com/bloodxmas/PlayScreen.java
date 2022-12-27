package com.bloodxmas;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class PlayScreen implements Screen {

    private final BloodXmas game;
    private Stage stage;
    private TextureAtlas backgroundTextureAtlas;
    private BackgroundActor backgroundActor;

    public PlayScreen (BloodXmas game) {
        this.game = game;
        stage = new Stage(new FitViewport(800f,480f));
        backgroundTextureAtlas = game.assets.getAssetManager().get("background/background.txt",TextureAtlas.class);
        backgroundActor = new BackgroundActor();
        backgroundActor.setTexture(backgroundTextureAtlas.findRegion("background"));
        stage.addActor(backgroundActor);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0f, 0f, 0f, 1);
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
