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
import com.badlogic.gdx.utils.viewport.Viewport;

public class Player extends BaseActor {

    private final BloodXmas game;
    private TextureAtlas playerTextureAtlas[];
    private float playerAnimationFrameRate[];
    private boolean canAttack = true;
    private boolean enableControls = false;

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
                if (isDirectionLeft())
                    setCurrentAnimation(4);
                else
                    setCurrentAnimation(5);
            }

            if (getElapsedTime() > getAnimation(4).getAnimationDuration()) {
                setAttackBlock(false);
                setCanAttack(true);
            }
        }
    }

    /*
    public void calculateSwordCollisionRectanglePosition () {
        leftAttackRectangle.setPosition(getX(),getY() + 30f);
        rightAttackRectangle.setPosition(getX() + 75f, getY() + 30f);
    }

    public Rectangle getLeftAttackRectangle() {
        return leftAttackRectangle;
    }

    public void setLeftAttackRectangle(Rectangle leftAttackRectangle) {
        this.leftAttackRectangle = leftAttackRectangle;
    }

    public Rectangle getRightAttackRectangle() {
        return rightAttackRectangle;
    }

    public void setRightAttackRectangle(Rectangle rightAttackRectangle) {
        this.rightAttackRectangle = rightAttackRectangle;
    }
*/
    public boolean isCanAttack() {
        return canAttack;
    }

    public void setCanAttack(boolean canAttack) {
        this.canAttack = canAttack;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        setText("" + getStage().getCamera().position.x + " " + getStage().getCamera().position.y );
    }

}
