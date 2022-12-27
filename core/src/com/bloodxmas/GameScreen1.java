package com.bloodxmas;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class GameScreen1 implements Screen {

    private final BloodXmas game;
    private Stage stage;
    private TextureAtlas miscTextureAtlas;
    private TextureAtlas sleighTextureAtlas;
    private Array<Cloud> clouds;
    private Sleigh sleigh;
    private SantaDialog santaDialog;
    private boolean enableStageAction1 = false;
    private float red = 0.3f, green = 0.1f, blue = 0.7f, alpha = 1;

    public GameScreen1 (final BloodXmas game) {
        this.game = game;
        stage = new Stage(new FitViewport(800f, 480f));
        miscTextureAtlas = game.assets.getAssetManager().get("misc/misc.txt",TextureAtlas.class);
        sleighTextureAtlas = game.assets.getAssetManager().get("sleigh/sleigh.txt",TextureAtlas.class);
        clouds = new Array<>();
        sleigh = new Sleigh();
        sleigh.setTexture(sleighTextureAtlas.findRegion("sleigh"));
        sleigh.setPosition(game.screenWidth / 2 - sleigh.getWidth() / 2, game.screenHeight/2 - sleigh.getHeight());

        for (int i = 0; i < 5; ++i) {
            clouds.add(new Cloud(game));
            clouds.get(i).setTexture(miscTextureAtlas.findRegion("clouds"));
            stage.addActor(clouds.get(i));
        }

        stage.addActor(sleigh);

        santaDialog = new SantaDialog(game, sleigh);
        santaDialog.resetDialogBox();

        stage.addActor(santaDialog);

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        ScreenUtils.clear(red, green, blue, alpha);
        stage.act();
        stage.draw();

        if (santaDialog.getElapsedTime() > 0f && santaDialog.getElapsedTime() <= 10f) {
            santaDialog.setText(santaDialog.getDialog(0));
        }
        else if (santaDialog.getElapsedTime() > 10f && santaDialog.getElapsedTime() <= 20f) {
            santaDialog.setText(santaDialog.getDialog(1));
        }
        else if (santaDialog.getElapsedTime() > 20f && santaDialog.getElapsedTime() <= 30f) {
            santaDialog.setText(santaDialog.getDialog(2));
        }
        else if (santaDialog.getElapsedTime() > 30f && santaDialog.getElapsedTime() <= 32f) {
            santaDialog.setText("");
            red = 0;
            green = 0;
            blue = 0;
        }
        else if (santaDialog.getElapsedTime() > 32f && santaDialog.getElapsedTime() <= 42f) {
            santaDialog.setText(santaDialog.getDialog(3));
        }
        else if (santaDialog.getElapsedTime() > 42f && santaDialog.getElapsedTime() <= 44f) {
            red = 0.3f;
            green = 0.1f;
            blue = 0.7f;
            santaDialog.setText("");
        }
        else if (santaDialog.getElapsedTime() > 44f && santaDialog.getElapsedTime() <= 46f) {
            red = 0;
            green = 0;
            blue = 0;
        }

        else if (santaDialog.getElapsedTime() > 46f && !enableStageAction1) {
            enableStageAction1=true;
            sleigh.clearActions();
            for (int i = 0; i < stage.getActors().size; ++i) {
                stage.getActors().get(i).addAction(Actions.repeat(4,Actions.sequence(Actions.moveBy(10f,10f,0.1f), Actions.moveBy(-10f,-10f,0.1f))));
            }
            sleigh.addAction(Actions.after(Actions.sequence(Actions.rotateBy(40f,0.5f))));
            sleigh.addAction(Actions.after(Actions.moveBy(0f,-700f,3f, Interpolation.swing)));
            sleigh.addAction(Actions.after(Actions.run(()-> game.setScreen(new PlayScreen(game)))));
        }

        else santaDialog.setText("");


    }

    @Override
    public void resize(int width, int height) {

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
