package com.bloodxmas;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class Sleigh extends BaseActor {

    public Sleigh() {
        addAction(Actions.forever(Actions.sequence(Actions.moveBy(0f,50f,2f,Interpolation.swing),
                Actions.moveBy(0f,-50f,2, Interpolation.swing))));
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }
}
