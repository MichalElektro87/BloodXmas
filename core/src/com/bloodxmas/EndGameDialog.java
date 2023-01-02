package com.bloodxmas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class EndGameDialog extends DialogBox {

    private final BloodXmas game;
    public EndGameDialog(BloodXmas game) {
        super(game);
        this.game = game;
        setPosition(10f, 440f);
        setSpeedLetterAppearance(0.1f);
        setFont(new BitmapFont(Gdx.files.internal("font/titleFont60.fnt")));
        getFont().setColor(Color.RED);
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        getFont().draw(batch, getText(), getX(), getY(), 800f - 20f,1,true);
    }

    public void setDialog() {
        getDialogArray().add("Congratulations!!! You have survived!" +
                "" + " Now you can go and do something useful!");
        getDialogDuration(0);
    }

    @Override
    public void update() {

        if (getElapsedTime() > dialogDuration + 5f) {
            dialogEnded = true;
        }
    }
}