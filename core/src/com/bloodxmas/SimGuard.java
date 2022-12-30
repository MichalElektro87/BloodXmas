package com.bloodxmas;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class SimGuard extends GroupActor {

    private final BloodXmas game;
    private Arm arm, undergroundArm;
    private Corps corps;
    private Eye eye;
    private Gear gear1, gear2, gear3;
    private Head head;
    private Leg1 leg1;
    private Leg2 leg2;
    private Hand hand;
    private Shadow shadow;
    private TargetingSprite targetingSpriteActor;
    private Rectangle collisionRectangle;
    private Polygon handPolygon;
    private TextureAtlas simGuardTextureAtlas;

    private boolean enableAction1 = true;
    private boolean enableAction2 = false;
    private boolean enableAction3 = false;
    private boolean enableAction4 = false;
    private boolean enableAction5 = false;
    private boolean enableAction6 = false;
    private boolean enableAction7 = false;

    public SimGuard (final BloodXmas game) {
        this.game = game;

        arm = new Arm();
        undergroundArm = new Arm();
        undergroundArm.setRotation(180f);
        corps = new Corps();
        eye = new Eye();
        gear1 = new Gear();
        gear2 = new Gear();
        gear3 = new Gear();
        head = new Head();
        leg1 = new Leg1();
        leg2 = new Leg2();
        hand = new Hand();
        shadow = new Shadow();
        targetingSpriteActor = new TargetingSprite();


        simGuardTextureAtlas = game.assets.getAssetManager().get("sim_guard/sim_guard.txt",TextureAtlas.class);

        undergroundArm.setTexture(simGuardTextureAtlas.findRegion("arm"));
        arm.setTexture(simGuardTextureAtlas.findRegion("arm"));
        corps.setTexture(simGuardTextureAtlas.findRegion("corps"));
        eye.setTexture(simGuardTextureAtlas.findRegion("eye"));
        gear1.setTexture(simGuardTextureAtlas.findRegion("gear"));
        gear2.setTexture(simGuardTextureAtlas.findRegion("gear"));
        gear3.setTexture(simGuardTextureAtlas.findRegion("gear"));
        head.setTexture(simGuardTextureAtlas.findRegion("head"));
        leg1.setTexture(simGuardTextureAtlas.findRegion("leg1"));
        leg2.setTexture(simGuardTextureAtlas.findRegion("leg2"));
        hand.setTexture(simGuardTextureAtlas.findRegion("hand"));
        shadow.setTexture(simGuardTextureAtlas.findRegion("shadow"));
        targetingSpriteActor.setTexture(simGuardTextureAtlas.findRegion("target"));
        targetingSpriteActor.setVisible(false);

        arm.setOrigin(arm.getCenterX(),arm.getHeight());
        undergroundArm.setOrigin(undergroundArm.getCenterX(), undergroundArm.getHeight());
        gear1.setOrigin(gear1.getCenterX(), gear1.getCenterY());
        gear2.setOrigin(gear2.getCenterX(), gear2.getCenterY());
        gear3.setOrigin(gear3.getCenterX(), gear3.getCenterY());

        collisionRectangle = new Rectangle();


        handPolygon = new Polygon();
        handPolygon.setVertices(new float[] {0,0,hand.getWidth(),0,hand.getWidth(),hand.getHeight(),0,hand.getHeight()});
        handPolygon.setOrigin(arm.getWidth() / 2, arm.getHeight());



        addActor(shadow);
        addActor(leg1);
        addActor(leg2);
        addActor(corps);
        addActor(arm);
        addActor(gear1);
        addActor(gear2);
        addActor(gear3);
        addActor(head);
        addActor(eye);
        addActor(targetingSpriteActor);
        shadow.setVisible(false);

        setSize(corps.getTextureRegion().getRegionWidth(), corps.getTextureRegion().getRegionHeight() + head.getTextureRegion().getRegionWidth() + leg1.getHeight() / 2f);
        collisionRectangle.setSize(getWidth(), getHeight());

        leg2.setPosition(leg1.getX() + corps.getWidth() - leg2.getWidth(), leg1.getY());
        corps.setPosition(leg1.getX(), leg1.getY() + leg1.getHeight());
        head.setPosition(corps.getCenterX() - head.getWidth() / 2, corps.getY() + corps.getHeight() - head.getHeight() / 2);
        eye.setPosition(head.getX() + head.getWidth() / 7, head.getCenterY() + head.getHeight() / 4);
        arm.setPosition(corps.getCenterX() - arm.getWidth() / 2, corps.getY() + corps.getHeight() - arm.getHeight());
        gear1.setPosition(head.getCenterX() - gear1.getWidth() / 2 , head.getCenterY() - gear1.getHeight() / 4);
        gear2.setPosition(gear1.getX() + gear1.getWidth(), gear1.getY() + gear1.getHeight() / 5);
        gear3.setPosition(gear2.getX() - gear3.getWidth() / 2 - 5f, gear2.getCenterY() + gear3.getHeight() / 3 - 5f);
        shadow.setPosition(corps.getCenterX() - shadow.getWidth() / 2, leg1.getY() - shadow.getHeight() / 2);
        targetingSpriteActor.setPosition(corps.getWidth() / 2f - targetingSpriteActor.getWidth() / 2f, corps.getHeight());

        gear1.addAction(Actions.forever(Actions.rotateBy(-3f)));
        gear2.addAction(Actions.forever(Actions.rotateBy(3f)));
        gear3.addAction(Actions.forever(Actions.rotateBy(-3f)));

        leg1.addAction(Actions.forever(Actions.sequence(Actions.moveBy(0f, 30f, 0.5f), Actions.moveBy(0f, -30f, 0.5f))));
        leg2.addAction(Actions.forever(Actions.sequence(Actions.moveBy(0f, 30f, 0.3f), Actions.moveBy(0f, -30f, 0.3f))));

        setPosition(game.screenWidth + 50f, 20f);


    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

    public void update() {

        collisionRectangle.setX(getX());
        collisionRectangle.setY(getY());

        if (enableAction1) {
            enableAction1 = false;

            addAction(Actions.delay(2f));
            addAction(Actions.repeat(1,
                    Actions.sequence(Actions.rotateBy(30f,3f),
                    Actions.rotateBy(-30f,1f))));
            addAction(Actions.after(Actions.after(Actions.moveTo(game.screenWidth - game.screenWidth, getY(),2f))));
            addAction(Actions.after(Actions.run(new Runnable() {
                @Override
                public void run() {
                    flipX();
                }
            })));
            addAction(Actions.after(Actions.after(Actions.moveTo(game.screenWidth - corps.getWidth(), getY(),2f))));
            addAction(Actions.after(Actions.run(new Runnable() {
                @Override
                public void run() {
                    flipX();
                }
            })));
            addAction(Actions.after(Actions.after(Actions.moveTo(game.screenWidth / 2f - corps.getWidth() / 2f, getY(),1f))));
            addAction(Actions.after(Actions.delay(1f)));
            addAction(Actions.after(Actions.run(new Runnable() {
                @Override
                public void run() {
                    enableAction2 = true;
                }
            })));
        }

        if (enableAction2) {
            enableAction2 = false;

            targetingSpriteActor.addAction(Actions.repeat(3,Actions.sequence(Actions.visible(true),Actions.delay(0.5f),Actions.visible(false), Actions.delay(0.5f))));
            targetingSpriteActor.addAction(Actions.after(Actions.run(new Runnable() {
                @Override
                public void run() {
                    enableAction3 = true;
                }
            })));
        }

        if (enableAction3) {
            enableAction3 = false;
        }

    }

    @Override
    public void act(float delta) {
        super.act(delta);
        update();
    }



    public class Arm extends BaseActor {

    }

    public class Corps extends BaseActor {

    }

    public class Eye extends BaseActor {
        @Override
        public void draw(Batch batch, float parentAlpha) {
            super.draw(batch, parentAlpha);
        }
    }

    public class Gear extends BaseActor {
        public Gear () {
        }
        @Override
        public String toString() {
            return "gear";
        }
    }

    public class Hand extends BaseActor {
        public Hand () {
        }
    }

    public class Head extends BaseActor {
        public Head () {
        }
    }

    public class Leg1 extends BaseActor {
        public Leg1 () {
        }

        @Override
        public String toString() {
            return "leg";
        }
    }

    public class Leg2 extends BaseActor {
        public Leg2 () {
        }
        @Override
        public String toString() {
            return "leg";
        }

    }

    public class Shadow extends BaseActor {

    }

    public class TargetingSprite extends BaseActor {
    }

    public void flipX () {
        if (!head.getTextureRegion().isFlipX()) {
            gear1.moveBy(-40f, 0);
            gear2.moveBy(-40f, 0);
            gear3.moveBy(-40f, 0);
            eye.moveBy(80f, 0f);
        }
        else {
            gear1.moveBy(40f, 0);
            gear2.moveBy(40f, 0);
            gear3.moveBy(40f, 0);
            eye.moveBy(-80f, 0f);
        }
        head.setFlipX();
    }

    public Rectangle getCollisionRectangle () {
        return collisionRectangle;
    }

    public Corps getCorps() {
        return corps;
    }

    @Override
    public String toString() {
        return "boss";
    }

}
