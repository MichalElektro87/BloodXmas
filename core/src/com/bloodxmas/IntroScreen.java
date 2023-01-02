package com.bloodxmas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class IntroScreen implements Screen {

    private final BloodXmas game;
    private Stage stage;
    private SimGuard simGuard;
    private CompanySign companySign;
    private PresentsSign presentsSign;
    private DebugClass debugClass;

    private boolean startSimGuardFall = false;
    private boolean startSimGuardExitFromStage = false;
    private boolean canEnterNextStage = false;
    private boolean simGuardOverlappedWithPresentsSign = false;

    public IntroScreen (final BloodXmas game) {

        debugClass = new DebugClass();
        this.game = game;
        stage = new Stage(new FitViewport(800f,480f));
        simGuard = new SimGuard(game);


        companySign = new CompanySign(game);
        companySign.setFont(new BitmapFont(Gdx.files.internal("font/titleFont60.fnt")));

        companySign.setPosition(game.screenWidth / 2 - companySign.getGlyphLayout(companySign.getText(), companySign.getFont()).width / 2f, 480f + companySign.getFont().getLineHeight());
        companySign.setSize(companySign.getGlyphLayout(companySign.getText(), companySign.getFont()).width, -companySign.getFont().getCapHeight());
        companySign.getRectangle().setSize(companySign.getWidth(), companySign.getHeight());
        companySign.addAction(Actions.delay(13f));
        companySign.addAction(Actions.after(Actions.moveTo(companySign.getX(), game.screenHeight / 2f + companySign.getGlyphLayout().height + companySign.getFont().getLineHeight(), 3f, Interpolation.bounceOut)));

        presentsSign = new PresentsSign(game);
        presentsSign.setFont(new BitmapFont(Gdx.files.internal("font/titleFont60.fnt")));
        presentsSign.setPosition(game.screenWidth + 30f, game.screenHeight / 3f);
        presentsSign.setSize(presentsSign.getGlyphLayout(presentsSign.getText(), presentsSign.getFont()).width, -presentsSign.getFont().getCapHeight());
        presentsSign.getRectangle().setSize(presentsSign.getWidth(), presentsSign.getHeight());

        stage.addActor(simGuard);
        stage.addActor(companySign);
        stage.addActor(presentsSign);

    }

    @Override
    public void show() {
        stage.addActor(debugClass);
        debugClass.setPosition(100f, 400f);
    }

    @Override
    public void render(float delta) {

        ScreenUtils.clear(0.3f, 0.3f, 0.3f, 1);
        stage.act();
        stage.draw();

        //debugClass.setText(simGuard.getRotation() + "");

        if (simGuard.getCollisionRectangle().overlaps(companySign.getRectangle()) && !startSimGuardFall) {
            startSimGuardFall = true;
            simGuard.addAction(Actions.rotateBy(90f, 1f, Interpolation.bounceOut));
        }

        if (simGuard.getRotation() > 70f && !startSimGuardExitFromStage) {
            startSimGuardExitFromStage = true;
            presentsSign.addAction(Actions.moveTo(game.screenWidth / 2 - presentsSign.getGlyphLayout(presentsSign.getText(), presentsSign.getFont()).width / 2f, game.screenHeight / 3, 3f, Interpolation.bounceOut));
        }

        if (simGuard.getCollisionRectangle().overlaps(presentsSign.getRectangle()) && !simGuardOverlappedWithPresentsSign && startSimGuardExitFromStage) {
            simGuardOverlappedWithPresentsSign = true;
            simGuard.addAction(Actions.moveBy(-1000f, 0f, 2f));
            simGuard.addAction(Actions.after(Actions.sequence(Actions.delay(4f), Actions.addAction(Actions.run(new Runnable() {
                @Override
                public void run() {
                    canEnterNextStage = true;
                }
            })))));
        }

        if (canEnterNextStage) {
            canEnterNextStage = false;
            game.setScreen(new TitleScreen(game));
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
        stage.dispose();
    }
}
