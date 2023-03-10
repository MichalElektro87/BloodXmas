package com.bloodxmas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.bloodxmas.misc.Blood;

public class TitleScreen implements Screen {

    private final BloodXmas game;
    private Stage stage;
    private TitleSign titleSign;
    private Table uitable;
    private TextButtonStyle textButtonStyle;
    private Texture buttonTexture;
    private NinePatch ninePatch;
    private TextButton startButton, exitButton;
    private Blood blood;


    public TitleScreen(final BloodXmas game) {
        this.game = game;
        stage = new Stage(new FitViewport(800f,480f));

        game.gameSound = new GameSound(game);
        game.player = new Player(game);
        game.santa = new Santa(game);

        game.elvenArray = new Array<>();
        game.evenDeathAnimationLeft = new Array<>();
        game.evenDeathAnimationRight = new Array<>();


        for (int i = 0; i < 25; i++) {
            game.elvenArray.add(new Elven(game.player, game.randomXS128.nextInt(2), game.randomXS128.nextInt(2), game.randomXS128.nextFloat()*10.0f,game));
            game.elvenArray.get(i).setAnimation();
            game.elvenArray.get(i).setMissiles();
        }

        for (int i = 0; i < game.elvenArray.size; i++) {
            game.evenDeathAnimationLeft.add(new EvenDeathAnimation(game));
            game.evenDeathAnimationLeft.get(i).setVisible(false);
            game.evenDeathAnimationLeft.get(i).setAnimation(true);
            game.evenDeathAnimationRight.add(new EvenDeathAnimation(game));
            game.evenDeathAnimationRight.get(i).setVisible(false);
            game.evenDeathAnimationRight.get(i).setAnimation(false);
        }

        game.player.setAnimation();
        game.santa.setAnimation();
        titleSign = new TitleSign(game);
        titleSign.setFont(new BitmapFont(Gdx.files.internal("font/titleFont60.fnt")));

        textButtonStyle = new TextButtonStyle();
        buttonTexture = new Texture(Gdx.files.internal("buttons/button1.png"));
        ninePatch = new NinePatch(buttonTexture);
        textButtonStyle.up = new NinePatchDrawable(ninePatch);
        textButtonStyle.font = new BitmapFont(Gdx.files.internal("font/titleFont60.fnt"));


        startButton = new TextButton(" Start ", textButtonStyle);
        exitButton = new TextButton(" Exit ", textButtonStyle);

        startButton.addListener(
                (Event e) ->
                {
                    if (!(e instanceof InputEvent) || !((InputEvent)e).getType().equals(InputEvent.Type.touchDown))
                        return false;
                    startButton.remove();
                    exitButton.remove();
                    game.setScreen(new GameScreen1(game));
                    return false;
                }
        );

        exitButton.addListener(
                (Event e) ->
                {
                    if (!(e instanceof InputEvent) || !((InputEvent)e).getType().equals(InputEvent.Type.touchDown))
                        return false;

                    startButton.remove();
                    exitButton.remove();
                    Gdx.app.exit();
                    return false;
                }
        );

        uitable = new Table();
        uitable.setFillParent(true);
        //uitable.setDebug(true);


        uitable.add(titleSign).center().padBottom(200);
        uitable.row();
        uitable.add(startButton).expandX();
        uitable.add(exitButton).expandX();
        uitable.row();

        stage.addActor(uitable);

        blood = new Blood(game);
        stage.addActor(blood);


        Gdx.input.setInputProcessor(stage);

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
