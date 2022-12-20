package com.bloodxmas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class TitleScreen implements Screen {

    private final BloodXmas game;
    private Stage stage;
    private TitleSign titleSign;
    private Table uitable;
    private TextButtonStyle textButtonStyle;
    private Texture buttonTexture;
    private NinePatch ninePatch;

    public TitleScreen(final BloodXmas game) {
        this.game = game;
        stage = new Stage(new FitViewport(800f,480f));
        titleSign = new TitleSign(game);
        titleSign.setFont(new BitmapFont(Gdx.files.internal("font/radiospace_red.fnt")));

        textButtonStyle = new TextButtonStyle();
        buttonTexture = new Texture(Gdx.files.internal("buttons/button1.png"));
        ninePatch = new NinePatch(buttonTexture);
        textButtonStyle.up = new NinePatchDrawable(ninePatch);
        textButtonStyle.font = new BitmapFont(Gdx.files.internal("font/radiospace.fnt"));

        uitable = new Table();
        uitable.setFillParent(true);
        uitable.add(titleSign);


        stage.addActor(uitable);
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
