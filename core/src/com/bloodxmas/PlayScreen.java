package com.bloodxmas;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.bloodxmas.misc.Heart;

public class PlayScreen implements Screen {

    private final BloodXmas game;
    private Stage stage;
    private TextureAtlas backgroundTextureAtlas;
    private BackgroundActor backgroundActor;
    private PlayerDialog playerDialog;
    private boolean enableStageAction1 = true;
    private boolean enableStageAction2 = false;
    private boolean enableStageAction3 = false;
    private int elvenCounter = 0;

    private boolean enablePlayerMonolog1 = false;


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

        playerDialog = new PlayerDialog(game, game.player);

        for (int i = 0; i < game.elvenArray.size; i++) {
            game.elvenArray.get(i).resetToDefaults();
            game.elvenArray.get(i).setWorldBounds(backgroundActor);
            stage.addActor(game.elvenArray.get(i));
            game.elvenArray.get(i).setPosition(game.randomXS128.nextFloat() * game.elvenArray.get(i).getWorldBounds().getWidth() / 2 + 1200f, 37f);
        }

        elvenCounter = game.elvenArray.size;

        game.player.resetToDefaults();

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
            game.player.addAction(Actions.after(Actions.run(()->enablePlayerMonolog1 = true)));
            game.player.addAction(Actions.after(Actions.run(()->playerDialog.resetDialogBox())));
            game.player.addAction(Actions.after(Actions.run(()->stage.addActor(playerDialog))));
        }


        if (enablePlayerMonolog1) {
            if (playerDialog.getElapsedTime() > 0f && playerDialog.getElapsedTime() < 10f) {
                playerDialog.setText(playerDialog.getDialog(0));
            }

            else {
                playerDialog.setText("");
            }
        }

        //Collision detection
        for (int i = 0; i < game.player.getAxesLeft().size; ++i) {
            if (game.player.getAxesLeft().get(i).isVisible() && game.player.getAxesLeft().get(i).isMotion()) {
                for (int j = 0; j < game.elvenArray.size; ++j) {
                    if (game.player.getAxesLeft().get(i).getRectangle().overlaps(game.elvenArray.get(j).getRectangle()) && game.elvenArray.get(j).isVisible()) {
                        game.elvenArray.get(j).remove();
                        game.elvenArray.get(j).setVisible(false);
                        game.player.getAxesLeft().get(i).remove();
                        game.player.getAxesLeft().get(i).setVisible(false);
                        if (game.elvenArray.get(j).getEnemyDirectionLeft()) {
                            game.evenDeathAnimationLeft.get(elvenCounter - 1).setPosition(game.elvenArray.get(j).getX(), game.elvenArray.get(j).getY());
                            game.evenDeathAnimationLeft.get(elvenCounter - 1).setVisible(true);
                            stage.addActor(game.evenDeathAnimationLeft.get(elvenCounter - 1));
                        } else {
                            game.evenDeathAnimationRight.get(elvenCounter - 1).setPosition(game.elvenArray.get(j).getX(), game.elvenArray.get(j).getY());
                            game.evenDeathAnimationRight.get(elvenCounter - 1).setVisible(true);
                            stage.addActor(game.evenDeathAnimationRight.get(elvenCounter - 1));
                        }
                        elvenCounter--;
                    }
                }
            }
        }

        for (int i = 0; i < game.player.getAxesRight().size; ++i) {
            if (game.player.getAxesRight().get(i).isVisible() && game.player.getAxesRight().get(i).isMotion()) {
            for (int j = 0; j < game.elvenArray.size; ++j) {
                if (game.player.getAxesRight().get(i).getRectangle().overlaps(game.elvenArray.get(j).getRectangle()) && game.elvenArray.get(j).isVisible()) {
                    game.elvenArray.get(j).remove();
                    game.elvenArray.get(j).setVisible(false);
                    game.player.getAxesRight().get(i).remove();
                    game.player.getAxesRight().get(i).setVisible(false);
                    if (game.elvenArray.get(j).getEnemyDirectionLeft()) {
                        game.evenDeathAnimationLeft.get(elvenCounter - 1).setPosition(game.elvenArray.get(j).getX(), game.elvenArray.get(j).getY());
                        game.evenDeathAnimationLeft.get(elvenCounter - 1).setVisible(true);
                        stage.addActor(game.evenDeathAnimationLeft.get(elvenCounter - 1));
                    } else {
                        game.evenDeathAnimationRight.get(elvenCounter - 1).setPosition(game.elvenArray.get(j).getX(), game.elvenArray.get(j).getY());
                        game.evenDeathAnimationRight.get(elvenCounter - 1).setVisible(true);
                        stage.addActor(game.evenDeathAnimationRight.get(elvenCounter - 1));
                    }
                    elvenCounter--;
                }
            }
            }
        }



        for (int i = 0; i < game.elvenArray.size; ++i) {
            for (int j = 0; j < game.elvenArray.get(i).getBloodSnowBallsLeft().size; ++j) {
                if (game.elvenArray.get(i).getBloodSnowBallsLeft().get(j).isVisible() && game.elvenArray.get(i).getBloodSnowBallsLeft().get(j).isMotion() ) {
                    if (game.player.getRectangle().overlaps(game.elvenArray.get(i).getBloodSnowBallsLeft().get(j).getRectangle())) {
                        game.player.setNumberOfLives(game.player.getNumberOfLives()-1);
                        game.elvenArray.get(i).getBloodSnowBallsLeft().get(j).setVisible(false);
                        game.elvenArray.get(i).getBloodSnowBallsLeft().get(j).remove();
                    }
                }
            }
        }

        for (int i = 0; i < game.elvenArray.size; ++i) {
            for (int j = 0; j < game.elvenArray.get(i).getBloodSnowBallsRight().size; ++j) {
                if (game.elvenArray.get(i).getBloodSnowBallsRight().get(j).isVisible() && game.elvenArray.get(i).getBloodSnowBallsRight().get(j).isMotion()) {
                    if (game.player.getRectangle().overlaps(game.elvenArray.get(i).getBloodSnowBallsRight().get(j).getRectangle())) {
                        game.player.setNumberOfLives(game.player.getNumberOfLives()-1);
                        game.elvenArray.get(i).getBloodSnowBallsRight().get(j).setVisible(false);
                        game.elvenArray.get(i).getBloodSnowBallsRight().get(j).remove();
                    }
                }
            }
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
