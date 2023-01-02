package com.bloodxmas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.bloodxmas.misc.Heart;
import com.bloodxmas.weapons.Axe;

public class Player extends BaseActor {

    private final BloodXmas game;
    private TextureAtlas playerTextureAtlas[];
    private float playerAnimationFrameRate[];
    private Array<Axe> axesLeft;
    private Array<Axe> axesRight;
    private Array<Heart> hearts;
    private int countAxesLeft = 0;
    private int countAxesRight = 0;
    private int numberOfLives = 3;
    private float heartPos = 0f;
    private boolean drawLives = false;
    private boolean canAttack = true;
    private boolean dead = false;

    public Player(final BloodXmas game) {
        this.game = game;
    }

    public void setAnimation() {
        playerTextureAtlas = new TextureAtlas[6];
        playerAnimationFrameRate = new float[playerTextureAtlas.length];

        playerTextureAtlas[0] = game.assets.getAssetManager().get("player/animation/no_move_left/no_move_left.atlas",TextureAtlas.class);
        playerTextureAtlas[1] = game.assets.getAssetManager().get("player/animation/no_move_right/no_move_right.atlas",TextureAtlas.class);
        playerTextureAtlas[2] = game.assets.getAssetManager().get("player/animation/move_left/move_left.atlas",TextureAtlas.class);
        playerTextureAtlas[3] = game.assets.getAssetManager().get("player/animation/move_right/move_right.atlas",TextureAtlas.class);
        playerTextureAtlas[4] = game.assets.getAssetManager().get("player/animation/attack_left/attack_left.atlas",TextureAtlas.class);
        playerTextureAtlas[5] = game.assets.getAssetManager().get("player/animation/attack_right/attack_right.atlas",TextureAtlas.class);


        playerAnimationFrameRate[0] = 1 / 10f;
        playerAnimationFrameRate[1] = 1 / 10f;
        playerAnimationFrameRate[2] = 1 / 15f;
        playerAnimationFrameRate[3] = 1 / 15f;
        playerAnimationFrameRate[4] = 1/10f;
        playerAnimationFrameRate[5] = 1/10f;

        setAnimationFromTextureAtlas(playerTextureAtlas, playerAnimationFrameRate);
        setAnimationLoopType(Animation.PlayMode.NORMAL);

        setCurrentAnimation(3);

        setXVelocity(3f);

        axesLeft = new Array<>();
        for (int i = 0; i < 10; ++i) {
            axesLeft.add(new Axe(game));
        }

        axesRight = new Array<>();
        for (int i = 0; i < 10; ++i) {
            axesRight.add(new Axe(game));
        }

        hearts = new Array<>();

        for (int i = 0; i < numberOfLives; ++i) {
            hearts.add(new Heart(game));
        }

    }

    @Override
    public void act(float delta) {
        super.act(delta);

        alignTheCamera();
        boundToWorld();

        if (isEnableControls()) {

            if (!Gdx.input.isKeyPressed(Input.Keys.A) || !Gdx.input.isKeyPressed(Input.Keys.D)) {
                if (!isAttackBlock()) {
                    if (isDirectionLeft())
                        setCurrentAnimation(0);
                    else
                        setCurrentAnimation(1);
                }
            }

            if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                setDirectionLeft(true);
                if (!isAttackBlock())
                    setCurrentAnimation(2);
                moveBy(-(getXVelocity()), 0f);

            }

            if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                setDirectionLeft(false);
                if (!isAttackBlock())
                    setCurrentAnimation(3);
                moveBy(getXVelocity(), 0f);
            }

            if (Gdx.input.isKeyJustPressed(Input.Keys.L)) {
                if (!hasActions()) {
                    addAction(Actions.sequence(Actions.moveBy(0f, 100f, 0.5f, Interpolation.fastSlow),
                            Actions.moveBy(0f, -100f, 0.5f, Interpolation.slowFast)));
                }
            }

            if (Gdx.input.isKeyJustPressed(Input.Keys.K) && !isAttackBlock()) {
                setAttackBlock(true);
                setElapsedTime(0f);
                if (isDirectionLeft()) {
                    setCurrentAnimation(4);
                    spawnAxesLeft();
                }
                else {
                    setCurrentAnimation(5);
                    spawnAxesRight();
                }
            }

            if (getElapsedTime() > getAnimation(4).getAnimationDuration()) {
                setAttackBlock(false);
                setCanAttack(true);
            }
        }
    }

    public void spawnAxesLeft() {
        game.gameSound.getThrowAxe().play();
        axesLeft.get(countAxesLeft).setPosition(getX(), getY() + getHeight() / 2);
        getStage().addActor(axesLeft.get(countAxesLeft));
        axesLeft.get(countAxesLeft).setVisible(true);
        axesLeft.get(countAxesLeft).clearActions();
        axesLeft.get(countAxesLeft).addAction(Actions.repeat(2,Actions.rotateBy(game.randomXS128.nextFloat() * 360f,0.25f)));

        if (getY() > 80f) {
            axesLeft.get(countAxesLeft).addAction(Actions.moveTo(getX() - 400f, 40f,1f));
        }
        else {
            axesLeft.get(countAxesLeft).addAction(Actions.moveTo(getX() - 300f, 40f, 1f));
        }
        countAxesLeft++;

        if (countAxesLeft == axesLeft.size)
            countAxesLeft = 0;
    }

    public void spawnAxesRight() {
        game.gameSound.getThrowAxe().play();
        axesRight.get(countAxesRight).setPosition(getCenterX(), getY() + getHeight() / 2);
        getStage().addActor(axesRight.get(countAxesRight));
        axesRight.get(countAxesRight).setVisible(true);
        axesRight.get(countAxesRight).clearActions();
        axesRight.get(countAxesRight).addAction(Actions.repeat(2,Actions.rotateBy(game.randomXS128.nextFloat() * (-360f),0.25f)));

        if (getY() > 80) {
            axesRight.get(countAxesRight).addAction(Actions.moveTo(getX() + 400f, 40f, 1f));
        }
        else {
            axesRight.get(countAxesRight).addAction(Actions.moveTo(getX() + 300f, 40f, 1f));
        }
        countAxesRight++;

        if (countAxesRight == axesRight.size)
            countAxesRight = 0;
    }

    public boolean isCanAttack() {
        return canAttack;
    }

    public void setCanAttack(boolean canAttack) {
        this.canAttack = canAttack;
    }

    public Array<Axe> getAxesLeft() {
        return axesLeft;
    }

    public Array<Axe> getAxesRight() {
        return axesRight;
    }

    public int getNumberOfLives() {
        return numberOfLives;
    }

    public void setNumberOfLives(int numberOfLives) {
        this.numberOfLives = numberOfLives;
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        heartPos = 0;
        for (int i = 0; i < numberOfLives; ++i) {
            hearts.get(i).setPosition(getStage().getCamera().position.x + heartPos + 200f, getStage().getCamera().position.y + 200f);
            hearts.get(i).draw(getStage().getBatch(),parentAlpha);
            if (heartPos >= 150)
                heartPos = 0;
            heartPos+=50f;
        }

    }

    public void resetToDefaults () {
        setVisible(false);
        setEnableControls(false);
        clearActions();
        dead = false;
        if (getScaleX() < 1f) {
            scaleBy(0.5f);
        }
        setRotation(0f);
        numberOfLives = 3;
        remove();

        for (int i = 0; i <axesLeft.size; ++i) {
            axesLeft.get(i).clearActions();
            axesLeft.get(i).setMotion(false);
        }

        for (int i = 0; i <axesRight.size; ++i) {
            axesRight.get(i).clearActions();
            axesRight.get(i).setMotion(false);
        }
    }

    public boolean isDead () {
        return dead;
    }

    public void setDead (boolean dead) {
        this.dead = dead;
    }

}
