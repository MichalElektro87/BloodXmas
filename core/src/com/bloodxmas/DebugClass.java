package com.bloodxmas;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class DebugClass extends BaseActor {

    public static int missileCounter = 0;
    private BitmapFont debug1;

    public DebugClass() {
        super();
        debug1 = new BitmapFont();
        debug1.setColor(Color.BLACK);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

    }
}

