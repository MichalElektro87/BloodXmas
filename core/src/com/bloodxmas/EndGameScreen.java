package com.bloodxmas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class EndGameScreen implements Screen {

    private final BloodXmas game;
    private Stage stage;
    private EndGameDialog endGameDialog;

    private boolean dialogEnded = false;
    private float continueTextElapsedTime = 0.0f;

    public EndGameScreen(final BloodXmas game) {
        this.game = game;
        stage = new Stage(new FitViewport(800, 480));
        endGameDialog = new EndGameDialog(game);
        endGameDialog.resetDialogBox();
        endGameDialog.setDialog();
        stage.addActor(endGameDialog);

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        continueTextElapsedTime+=Gdx.graphics.getDeltaTime();

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();

        endGameDialog.setText(endGameDialog.getDialog(0));

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
        stage.dispose();
    }
}