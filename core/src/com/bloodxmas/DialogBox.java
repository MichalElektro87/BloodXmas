package com.bloodxmas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.Array;

public class DialogBox extends BaseActor {
    private BloodXmas game;
    private Array<String> dialogArray;
    private BaseActor baseActor;
    private GroupActor groupActor;
    private GlyphLayout glyphLayout;
    private String tempString;
    private boolean enableNextTextMessage = true;
    protected boolean dialogEnded = false;
    private boolean textMessageErased = false;
    private int i = 0;
    private int dialogIndex = -2;
    private int lastDialogIndex = -1;
    private float elapsedTime = 0.0f;
    protected float dialogDuration = 0f;
    private float speedLetterAppearance= 0.08f;

    public DialogBox(BloodXmas game) {
        super();
        this.game = game;
        dialogArray = new Array<>();
        glyphLayout = new GlyphLayout();
        tempString = "";
    }

    public DialogBox(BloodXmas game, BaseActor baseActor) {
        super();
        this.game = game;
        dialogArray = new Array<>();
        this.baseActor = baseActor;
        glyphLayout = new GlyphLayout();
        tempString = "";
    }

    public DialogBox(BloodXmas game, GroupActor groupActor) {
        super();
        this.game = game;
        dialogArray = new Array<>();
        this.groupActor = groupActor;
        glyphLayout = new GlyphLayout();
        tempString = "";
    }

    public Array<String> getDialogArray() {
        return dialogArray;
    }

    public void update() {

        if (baseActor != null) {
            setX(baseActor.getCenterX() - 150f);
            setY(baseActor.getY() + baseActor.getHeight() + getFont().getLineHeight() * 4);
        }
        else {
            setX(groupActor.getCenterX() - 150f);
            setY(groupActor.getY() + groupActor.getHeight() + getFont().getLineHeight() * 4);
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        update();
        elapsedTime+=Gdx.graphics.getDeltaTime();
    }

    public String getDialog (int dialogIndex) {

        this.dialogIndex = dialogIndex;

        if ((dialogIndex != lastDialogIndex)) {
            if (!textMessageErased) {
                textMessageErased = true;
                resetTempString();
            }
            if (tempString.length() < dialogArray.get(dialogIndex).length()) {
                if (elapsedTime > speedLetterAppearance) {
                    tempString += "" + dialogArray.get(dialogIndex).charAt(i);
                    elapsedTime=0.0f;
                    i++;
                }
            }
            if (tempString.length() == dialogArray.get(dialogIndex).length()) {
                i = 0;
                lastDialogIndex = dialogIndex;
                textMessageErased = false;
                elapsedTime = 0f;
            }
        }
        return tempString;
    }

    public String getDialogComplete (int index) {
        return tempString = getDialogArray().get(index);
    }

    public void resetTempString() {
        tempString = "";
    }


    public GlyphLayout getGlyphLayout(BitmapFont font) {
        glyphLayout.setText(font, tempString);
        return glyphLayout;
    }


    public float getDialogDuration(int dialogIndex) {
        dialogDuration = getDialogArray().get(dialogIndex).length() * speedLetterAppearance;
        return dialogDuration;
    }


    public void setSpeedLetterAppearance (float speedLetterAppearance) {
        this.speedLetterAppearance = speedLetterAppearance;
    }

    public float getSpeedLetterAppearance () {
        return speedLetterAppearance;
    }

    public boolean isDialogEnded () {
        return dialogEnded;
    }

    public void setDialogEnded (boolean dialogEnded) {
        this.dialogEnded = dialogEnded;
    }


    public void setEnableNextTextMessage () {
        this.enableNextTextMessage = true;
    }

    public boolean isEnableNextTextMessage () {
        return enableNextTextMessage;
    }

    public boolean differenceBetweenDialogIndexAndLastDialogIndex() {
        return lastDialogIndex != dialogIndex;
    }

    public int getDialogIndex () {
        return dialogIndex;
    }

    public int getLastDialogIndex () {
        return lastDialogIndex;
    }

    // this function prevents unfinished text drawing when player laves the stage
    public void resetDialogBox () {
        i = 0;
        lastDialogIndex = -1;
        textMessageErased = false;
        elapsedTime = 0f;
        dialogEnded = false;
        resetElapsedTime();
        resetText();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        getFont().draw(batch, getText(), getX(), getY(), 300f, 1,true);
    }

    public BaseActor getBaseActor () {
        return baseActor;
    }

    public void setBaseActor (BaseActor baseActor) {
        this.baseActor = baseActor;
    }

}
