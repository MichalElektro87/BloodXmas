package com.bloodxmas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.bloodxmas.misc.Heart;
import com.bloodxmas.weapons.Axe;
import com.bloodxmas.weapons.BloodSnowBall;

public class Elven extends BaseActor implements Disposable {


    private BloodXmas game;
    private Player player;
    private Array<BloodSnowBall> bloodSnowBallsLeft;
    private Array<BloodSnowBall> bloodSnowBallsRight;

    public TextureAtlas enemyTextureAtlas[];
    public float enemyAnimationFrameRate[];

    private boolean stop = false;
    private boolean shootingRight = false;
    private boolean shootingLeft = false;
    private boolean enableShooting = true;
    protected boolean isFlippedMiner = false;
    public boolean timer1 = true, timer2 = true;
    private int left = 0;
    private int up = 0;
    private int countMissilesLeft = 0;
    private int countMissilesRight = 0;
    private float leftRightSpeed = 1.0f;
    private float upDownSpeed = 0;
    private float shootTimeSpawnRight = 0.0f;
    private float shootTimeSpawnLeft = 0.0f;
    private float shootingTime = 0.0f;
    private float enemyShootTimeSpawn = 0.0f;
    private float enemyStartX = 0.0f;
    private float enemyStartY = 0.0f;



    public Elven(Player player, int enemyHorizontalDirection, int enemyVerticalDirection, float enemyShootTimeSpawn, BloodXmas game) {
        super();
        this.game = game;
        this.player = player;
        left = enemyHorizontalDirection;
        up = enemyVerticalDirection;

        if (enemyShootTimeSpawn < 1f)
            this.enemyShootTimeSpawn = 1f;
        else
            this.enemyShootTimeSpawn = enemyShootTimeSpawn;

    }

    public void setAnimation () {

        enemyTextureAtlas = new TextureAtlas[8];
        enemyAnimationFrameRate = new float[enemyTextureAtlas.length];
        enemyTextureAtlas[0] = game.assets.getAssetManager().get("elven/animation/no_move_right/no_move_right.atlas",TextureAtlas.class);
        enemyTextureAtlas[1] = game.assets.getAssetManager().get("elven/animation/no_move_left/no_move_left.atlas",TextureAtlas.class);
        enemyTextureAtlas[2] = game.assets.getAssetManager().get("elven/animation/move_right/move_right.atlas",TextureAtlas.class);
        enemyTextureAtlas[3] = game.assets.getAssetManager().get("elven/animation/move_left/move_left.atlas",TextureAtlas.class);
        enemyTextureAtlas[4] = game.assets.getAssetManager().get("elven/animation/shoot_right/shoot_right.atlas",TextureAtlas.class);
        enemyTextureAtlas[5] = game.assets.getAssetManager().get("elven/animation/shoot_left/shoot_left.atlas",TextureAtlas.class);
        enemyTextureAtlas[6] = game.assets.getAssetManager().get("elven/animation/death_right/death_right.atlas",TextureAtlas.class);
        enemyTextureAtlas[7] = game.assets.getAssetManager().get("elven/animation/death_left/death_left.atlas",TextureAtlas.class);

        enemyAnimationFrameRate[0] = 1 / 10f;
        enemyAnimationFrameRate[1] = 1 / 10f;
        enemyAnimationFrameRate[2] = 1 / 15f;
        enemyAnimationFrameRate[3] = 1 / 15f;
        enemyAnimationFrameRate[4] = 1/10f;
        enemyAnimationFrameRate[5] = 1/10f;

        setAnimationFromTextureAtlas(enemyTextureAtlas, enemyAnimationFrameRate);
        setAnimationLoopType(Animation.PlayMode.NORMAL);
        setAnimationLoopType(Animation.PlayMode.LOOP_PINGPONG,0);
        setAnimationLoopType(Animation.PlayMode.LOOP_PINGPONG,1);

        setCurrentAnimation(2);
        setEnableControls(true);
    }


    public void setMissiles() {

        bloodSnowBallsLeft = new Array<>();
        for (int i = 0; i < 10; ++i) {
            bloodSnowBallsLeft.add(new BloodSnowBall(game));
        }

        bloodSnowBallsRight = new Array<>();
        for (int i = 0; i < 10; ++i) {
            bloodSnowBallsRight.add(new BloodSnowBall(game));
        }

    }

    @Override
    public void boundToWorld() {

        if (isEnableControls()) {

            if (getX() < 0f) {
                left = 0;
                setX(0f);
            }
            if (getX() + getWidth() > getWorldBounds().getWidth()) {
                left = 1;
                setX(getWorldBounds().getWidth() - getWidth());
            }
            if (getY() < 0f)
                setY(0f);
            if (getY() + getHeight() > getWorldBounds().getHeight())
                setY(getWorldBounds().getHeight() - getHeight());
        }
    }



    public void update() {

        boundToWorld();
        if (enableShooting) {
            if (left == 0) {
                if (!shootingRight) {
                    setCurrentAnimation(2);
                    moveBy(leftRightSpeed, 0);
                    shootTimeSpawnRight+=Gdx.graphics.getDeltaTime();
                    if (shootTimeSpawnRight > enemyShootTimeSpawn) {
                        shootTimeSpawnRight = 0.0f;
                        shootingRight = true;
                    }
                }
            }

            if (left == 1) {
                if (!shootingLeft) {
                    setCurrentAnimation(3);
                    moveBy(-leftRightSpeed, 0);
                    shootTimeSpawnLeft+=Gdx.graphics.getDeltaTime();
                    if (shootTimeSpawnLeft > enemyShootTimeSpawn) {
                        shootingLeft = true;
                        shootTimeSpawnLeft = 0.0f;
                    }
                }

            }

            if (isShootingRight()) {
                if (timer1) {
                    resetElapsedTime();
                    timer1 = false;
                }
                setCurrentAnimation(4);
                if (getElapsedTime() > getAnimationIndex(5).getAnimationDuration() / 2 && timer2) {
                    timer2 = false;

                    if (!isFlippedMiner)
                        spawnMissileRight();

                }

                if (getCurrentAnimation().isAnimationFinished(getElapsedTime())) {
                    timer1 = true;
                    timer2 = true;
                    setShootingRight(false);
                }
            }

            if (isShootingLeft()) {
                if (timer1) {
                    resetElapsedTime();
                    timer1 = false;
                }
                setCurrentAnimation(5);
                if (getElapsedTime() > getAnimationIndex(4).getAnimationDuration() / 2 && timer2) {
                    timer2 = false;

                    if (!isFlippedMiner)
                        spawnMissileLeft();

                }
            }

            if (up == 0 && !shootingRight && !shootingLeft) {
                moveBy(0, -upDownSpeed);
            }

            if (up == 1 && !shootingRight && !shootingLeft) {
                moveBy(0, upDownSpeed);
            }

        }

        if (getCurrentAnimation().isAnimationFinished(getElapsedTime())) {
            timer1 = true;
            timer2 = true;
            setShootingLeft(false);

        }
    }

    public void spawnMissileLeft() {

        bloodSnowBallsLeft.get(countMissilesLeft).setPosition(getX(), getY() + getHeight() / 2);
        getStage().addActor(bloodSnowBallsLeft.get(countMissilesLeft));
        bloodSnowBallsLeft.get(countMissilesLeft).setVisible(true);
        bloodSnowBallsLeft.get(countMissilesLeft).clearActions();
        bloodSnowBallsLeft.get(countMissilesLeft).addAction(Actions.repeat(2,Actions.rotateBy(game.randomXS128.nextFloat() * 360f,0.25f)));
        bloodSnowBallsLeft.get(countMissilesLeft).addAction(Actions.moveTo(getX() - 500f, 40f,1f));
        countMissilesLeft++;

        if (countMissilesLeft == bloodSnowBallsLeft.size)
            countMissilesLeft = 0;

    }

    public void spawnMissileRight() {

        bloodSnowBallsRight.get(countMissilesRight).setPosition(getX() + getWidth(), getY() + getHeight() / 2);
        getStage().addActor(bloodSnowBallsRight.get(countMissilesRight));
        bloodSnowBallsRight.get(countMissilesRight).setVisible(true);
        bloodSnowBallsRight.get(countMissilesRight).clearActions();
        bloodSnowBallsRight.get(countMissilesRight).addAction(Actions.repeat(2,Actions.rotateBy(game.randomXS128.nextFloat() * 360f,0.25f)));
        bloodSnowBallsRight.get(countMissilesRight).addAction(Actions.moveTo(getX() + 500f, 40f,1f));
        countMissilesRight++;

        if (countMissilesRight == bloodSnowBallsRight.size)
            countMissilesRight = 0;

    }


    @Override
    public void act(float delta) {
        super.act(delta);

        if (isVisible()) {
            if (stop) {
                if (getEnemyDirectionLeft())
                    setCurrentAnimation(0);
                else
                    setCurrentAnimation(1);
            } else {
                update();
            }
        }
    }


    public boolean getEnemyDirectionLeft() {
        if (left == 1)
            return true;
        else
            return false;
    }

    public boolean isShootingLeft() {
        return shootingLeft;
    }

    public boolean isShootingRight() {
        return shootingRight;
    }

    public void setShootingRight (boolean shootingRight) {
        this.shootingRight = shootingRight;
    }
    public void setShootingLeft (boolean shootingLeft) {
        this.shootingLeft = shootingLeft;
    }



    public void setUpDownSpeed (float upDownSpeed) {
        this.upDownSpeed = upDownSpeed;
    }

    public void setLeftRightSpeed (float leftRightSpeed) {
        this.leftRightSpeed = leftRightSpeed;
    }
    public void enableShooting (boolean enableShooting) {
        this.enableShooting = enableShooting;
    }

    public int getHorizontalDirection () {
        return left;
    }

    public int getVerticalDirection () {
        return up;
    }

    public float getShootTimeSpawn () {
        return enemyShootTimeSpawn;
    }

    public void setShootTimeSpawn (float enemyShootTimeSpawn) {
        this.enemyShootTimeSpawn = enemyShootTimeSpawn;
    }

    public void setStop (boolean stop) {
        this.stop = stop;
    }
    public boolean isStopped () {
        return stop;
    }


    public Array<BloodSnowBall> getBloodSnowBallsLeft() {
        return bloodSnowBallsLeft;
    }

    public Array<BloodSnowBall> getBloodSnowBallsRight() {
        return bloodSnowBallsRight;
    }

    @Override
    public void dispose() {
    }

    @Override
    public String toString() {
        return "enemy";
    }

    public void resetToDefaults () {
        shootTimeSpawnRight = 0f;
        shootTimeSpawnLeft = 0f;
        setStop(false);
        enableShooting = true;
        clearActions();
        setRotation(0f);
        setOrigin(getWidth() / 2f, getHeight() / 2f);
        remove();
        setVisible(false);

        for (int i = 0; i <bloodSnowBallsLeft.size; ++i) {
            bloodSnowBallsLeft.get(i).setVisible(false);
            bloodSnowBallsLeft.get(i).remove();
            bloodSnowBallsLeft.get(i).setMotion(false);
        }

        for (int i = 0; i <bloodSnowBallsRight.size; ++i) {
            bloodSnowBallsRight.get(i).setVisible(false);
            bloodSnowBallsRight.get(i).remove();
            bloodSnowBallsRight.get(i).setMotion(false);
        }
    }

}