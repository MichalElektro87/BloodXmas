package com.bloodxmas;

import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class Cloud extends BaseActor {

    private boolean enableAction1 = true;
    private boolean enableAction2 = false;
    private final BloodXmas game;

    public Cloud (final BloodXmas game) {
        this.game = game;
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if (enableAction1) {
            enableAction1 = false;
            addAction(Actions.after(Actions.moveTo(game.screenWidth + getWidth(), game.randomXS128.nextFloat() * 480f)));
            addAction(Actions.after(Actions.run(()-> enableAction2=true)));
        }

        if (enableAction2) {
            enableAction2 = false;
            addAction(Actions.after(Actions.moveTo(game.screenWidth - game.screenWidth - getWidth(), getY(), game.randomXS128.nextFloat() * 5 + 1)));
            addAction(Actions.after(Actions.run(()-> enableAction1=true)));
        }
    }
}
