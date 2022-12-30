package com.bloodxmas;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class PlayScreen implements Screen {

    private final BloodXmas game;
    private Stage stage;
    private TextureAtlas backgroundTextureAtlas;
    private BackgroundActor backgroundActor;
    private boolean enableStageAction1 = true;
    private boolean enableStageAction2 = false;
    private boolean enableStageAction3 = false;


    public PlayScreen (BloodXmas game) {
        this.game = game;
        stage = new Stage(new FitViewport(800f,480f));
        backgroundTextureAtlas = game.assets.getAssetManager().get("background/background.txt",TextureAtlas.class);
        backgroundActor = new BackgroundActor();
        backgroundActor.setTexture(backgroundTextureAtlas.findRegion("background"));
        stage.addActor(backgroundActor);

        game.player.setPosition(-game.player.getWidth(), 40f);
        game.player.setStage(stage);
        game.player.setWorldBounds(backgroundActor);
        game.santa.setStage(stage);

        stage.addActor(game.santa);
        game.santa.setPosition(game.screenWidth / 2 - game.santa.getWidth() / 2,
                               game.screenHeight + game.santa.getHeight());

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0f, 0f, 0f, 1);
        stage.act();
        stage.draw();

        if (enableStageAction1) {
            enableStageAction1 = false;
            game.santa.addAction(Actions.moveTo(game.santa.getX(), 40f, 2f, Interpolation.slowFast));
            game.santa.addAction(Actions.after(Actions.parallel(Actions.moveBy(-700f,100f,2f),
                                 Actions.repeat(2,Actions.rotateBy(360f,2f)))));
            game.santa.addAction(Actions.after(Actions.run(()->enableStageAction2 = true)));
        }

        if (enableStageAction2) {
            enableStageAction2 = false;
            stage.addActor(game.player);
            game.player.addAction(Actions.delay(2f));
            game.player.addAction(Actions.after(Actions.moveBy(300f,0f,3f)));
            game.player.addAction(Actions.after(Actions.run(()->game.player.setEnableControls(true))));
        }

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
