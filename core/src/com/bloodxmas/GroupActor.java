package com.bloodxmas;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;


public class GroupActor extends Group {
    private TextureRegion textureRegion;
    private Rectangle rectangle;
    private Stage stage;

    private Pixmap pixmap;
    private Texture texture;
    private TextureRegion region;
    private Color color;
    private boolean healthBar = false;
    private float health;
    private float maxHealth;
    private float normalization;
    private float lineWidth = 3f;


    public float getHealth() {
        return health;
    }
    public void setHealth(float health) {
        this.health = health;
    }
    public void setMaxHealth (float maxHealth) {
        this.maxHealth = maxHealth;
    }
    public float getMaxHealth () {
        return maxHealth;
    }
    public void setNormalization (float normalization) {
        this.normalization = normalization;
    }
    public float getNormalization () {
        return this.normalization;
    }
    public float getLineWidth () {
        return lineWidth;
    }

    public GroupActor() {
        super();

        textureRegion = new TextureRegion();
        rectangle = new Rectangle();

        pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.drawPixel(0, 0);
        texture = new Texture(pixmap); //remember to dispose of later
        pixmap.dispose();
        region = new TextureRegion(texture, 0, 0, 1, 1);
        color = Color.GRAY;
    }


    public void setTexture(Texture t) {
        textureRegion.setRegion(t);
        setSize(t.getWidth(), t.getHeight());
        rectangle.setSize(t.getWidth(), t.getHeight());
    }

    public void setTexture(TextureRegion t) {
        textureRegion.setRegion(t);
        setSize(t.getRegionWidth(), t.getRegionHeight());
        rectangle.setSize(t.getRegionWidth(), t.getRegionHeight());
    }

    public Rectangle getRectangle() {
        rectangle.setPosition(getX(), getY());
        return rectangle;
    }

    public boolean overlaps(BaseActor other) {
        return this.getRectangle().overlaps(other.getRectangle());
    }

    public boolean overlaps(GroupActor other) {
        return this.getRectangle().overlaps(other.getRectangle());
    }



    public void setActualStage(Stage stage) {
        this.stage = stage;
    }

    public Stage getStage() {
        return stage;
    }

    public float getCenterX() {
        return getX() + getWidth()/2;
    }

    public float getCenterY() {
        return getY() + getHeight()/2;
    }


    public Color getColor () {
        return color;
    }

    public void enableHealthBar () {
        healthBar = true;
    }

    public void disableHealthBar () {
        healthBar = false;
    }

    public boolean isHealthBarEnabled () {
        return healthBar;
    }

    public void setColor (Color color) {
        this.color = color;
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if (getNormalization() <= 0.4f)
            setColor(Color.FIREBRICK);
        else if (getNormalization() > 0.4f && getNormalization() <= 0.7f)
            setColor(Color.YELLOW);
        else if (getNormalization() > 0.7f)
            setColor(Color.FOREST);

    }
}
