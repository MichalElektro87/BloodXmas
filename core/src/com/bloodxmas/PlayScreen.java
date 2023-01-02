package com.bloodxmas;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.bloodxmas.misc.Door;
import com.bloodxmas.misc.Heart;
import com.bloodxmas.misc.Key;

public class PlayScreen implements Screen {

    private final BloodXmas game;
    private Stage stage;
    private TextureAtlas backgroundTextureAtlas;
    private BackgroundActor backgroundActor;
    private PlayerDialog playerDialog;
    private Door door;
    private Key key;
    private boolean enableStageAction1 = true;
    private boolean enableStageAction2 = false;
    private boolean enableStageAction3 = false;
    private boolean keyTaken = false;
    private boolean stageOver = false;
    private int elvenCounter = 0;

    private boolean enablePlayerMonolog1 = false;
    private boolean enablePlayerMonolog2 = false;


    public PlayScreen (BloodXmas game) {
        this.game = game;
        stage = new Stage(new FitViewport(800f,480f));
        backgroundTextureAtlas = game.assets.getAssetManager().get("background/background.txt",TextureAtlas.class);
        backgroundActor = new BackgroundActor();
        backgroundActor.setTexture(backgroundTextureAtlas.findRegion("background"));
        stage.addActor(backgroundActor);

        game.player.setStage(stage);
        game.player.setWorldBounds(backgroundActor);
        game.player.resetToDefaults();

        game.santa.setStage(stage);
        game.santa.clearActions();
        game.santa.setRotation(40f);

        stage.addActor(game.santa);
        game.santa.addAction(Actions.fadeIn(0f));
        game.santa.setPosition(backgroundActor.getWidth() / 2 - game.santa.getWidth() / 2,
                               backgroundActor.getHeight() + game.santa.getHeight());

        game.santa.setOrigin(game.santa.getWidth() / 2, game.santa.getHeight() / 2);


        playerDialog = new PlayerDialog(game, game.player);
        playerDialog.resetDialogBox();

        for (int i = 0; i < game.elvenArray.size; ++i) {
            game.evenDeathAnimationLeft.get(i).resetElapsedTime();
            game.evenDeathAnimationRight.get(i).resetElapsedTime();
        }

        door = new Door(game);
        key = new Key(game);
        door.setPosition(backgroundActor.getWidth() - door.getWidth() - 50f, 40f);
        key.setPosition(0f + key.getWidth(), 40f);
        key.setVisible(true);
        stage.addActor(key);
        stage.addActor(door);

        for (int i = 0; i < game.elvenArray.size; i++) {
            game.elvenArray.get(i).resetToDefaults();
            game.elvenArray.get(i).setWorldBounds(backgroundActor);
            game.elvenArray.get(i).setVisible(true);
            stage.addActor(game.elvenArray.get(i));

            if (i < game.elvenArray.size / 2)
                game.elvenArray.get(i).setPosition(game.randomXS128.nextFloat() * 500f, 37f);
            else
                game.elvenArray.get(i).setPosition(game.randomXS128.nextFloat() * game.elvenArray.get(i).getWorldBounds().getWidth() / 2 + 2000f, 37f);
        }

        elvenCounter = game.elvenArray.size;
        stage.addActor(game.player);
        game.player.setPosition(backgroundActor.getWidth() / 2, 40f);

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
            game.santa.addAction(Actions.after(Actions.parallel(Actions.rotateTo(180f, 0.3f), Actions.moveBy(-100f, 0f, 0.3f))));
            game.santa.addAction(Actions.after(Actions.run(()->enableStageAction2 = true)));
        }

        if (enableStageAction2) {
            enableStageAction2 = false;
            game.santa.addAction(Actions.delay(2f));
            game.santa.addAction(Actions.fadeOut(2f));
            game.santa.addAction(Actions.after(Actions.run(()->enableStageAction3 = true)));
        }

        if (enableStageAction3) {
            enableStageAction3 = false;
            game.player.setEnableControls(true);
            stage.addActor(playerDialog);
            playerDialog.resetDialogBox();
            game.player.setVisible(true);
            game.player.addAction(Actions.after(Actions.run(()->enablePlayerMonolog1 = true)));
        }


            if (enablePlayerMonolog1) {
                if (playerDialog.getElapsedTime() > 0f && playerDialog.getElapsedTime() < 10f) {
                    playerDialog.setText(playerDialog.getDialog(0));
                } else {
                    playerDialog.setText("");
                    enablePlayerMonolog1 = false;
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
                        int damage = game.randomXS128.nextInt(3);
                        if (damage == 0)
                            game.gameSound.getDamage1().play();
                        else if (damage == 1)
                            game.gameSound.getDamage2().play();
                        else if (damage == 2)
                            game.gameSound.getDamage3().play();

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
                    int damage = game.randomXS128.nextInt(3);
                    if (damage == 0)
                        game.gameSound.getDamage1().play();
                    else if (damage == 1)
                        game.gameSound.getDamage2().play();
                    else if (damage == 2)
                        game.gameSound.getDamage3().play();
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

        if (game.player.isVisible()) {
            for (int i = 0; i < game.elvenArray.size; ++i) {
                for (int j = 0; j < game.elvenArray.get(i).getBloodSnowBallsLeft().size; ++j) {
                    if (game.elvenArray.get(i).getBloodSnowBallsLeft().get(j).isVisible() && game.elvenArray.get(i).getBloodSnowBallsLeft().get(j).isMotion()) {
                        if (game.player.getRectangle().overlaps(game.elvenArray.get(i).getBloodSnowBallsLeft().get(j).getRectangle())) {
                            game.player.setNumberOfLives(game.player.getNumberOfLives() - 1);
                            game.elvenArray.get(i).getBloodSnowBallsLeft().get(j).setVisible(false);
                            game.elvenArray.get(i).getBloodSnowBallsLeft().get(j).remove();
                        }
                    }
                }
            }
        }

        if (game.player.isVisible()) {
            for (int i = 0; i < game.elvenArray.size; ++i) {
                for (int j = 0; j < game.elvenArray.get(i).getBloodSnowBallsRight().size; ++j) {
                    if (game.elvenArray.get(i).getBloodSnowBallsRight().get(j).isVisible() && game.elvenArray.get(i).getBloodSnowBallsRight().get(j).isMotion()) {
                        if (game.player.getRectangle().overlaps(game.elvenArray.get(i).getBloodSnowBallsRight().get(j).getRectangle())) {
                            game.player.setNumberOfLives(game.player.getNumberOfLives() - 1);
                            game.elvenArray.get(i).getBloodSnowBallsRight().get(j).setVisible(false);
                            game.elvenArray.get(i).getBloodSnowBallsRight().get(j).remove();
                        }
                    }
                }
            }
        }

        if (game.player.getRectangle().overlaps(key.getRectangle()) && key.isVisible()) {
            key.setVisible(false);
            keyTaken = true;
            game.gameSound.getDing().play();
        }

        if (game.player.getRectangle().overlaps(door.getRectangle())) {
            if (keyTaken && !stageOver) {
                stageOver = true;
                game.setScreen(new EndGameScreen(game));
            }
            else {
                if (!enablePlayerMonolog2) {
                    enablePlayerMonolog2 = true;
                    playerDialog.resetDialogBox();
                }
            }
        }

            if (enablePlayerMonolog2) {
                if (playerDialog.getElapsedTime() < 10f) {
                    playerDialog.setText(playerDialog.getDialog(1));
                } else {
                    enablePlayerMonolog2 = false;
                    playerDialog.resetDialogBox();
                }
            }


        if (game.player.getNumberOfLives() <=0 && !game.player.isDead()) {
            game.player.setDead(true);
            game.setScreen(new PlayScreen(game));
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
