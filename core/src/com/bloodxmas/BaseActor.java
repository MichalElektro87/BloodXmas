package com.bloodxmas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class BaseActor extends Actor {

    private final TextureRegion textureRegion;
    private Animation<TextureRegion>[] animation;
    private Animation<TextureRegion> animationSingle;
    private Polygon boundaryPolygon;
    private Stage stage;
    private boolean isAnimSet = false;
    private boolean isAnimSingleSet = false;
    private boolean isTextureSet = false;
    private boolean isTextSet = false;
    private int currentAnimation = 0;
    private float animDuration[];
    private float elapsedTime = 0.0f;
    private float x1, y1, x2, y2;

    private final Rectangle rectangle;
    private BitmapFont font;
    private String text;
    private GlyphLayout glyphLayout;

    public BaseActor() {
        super();
        textureRegion = new TextureRegion();
        rectangle = new Rectangle();
        font = new BitmapFont(Gdx.files.internal("font/playerDialog15.fnt"));
        text = new String();
        glyphLayout = new GlyphLayout();
    }

    public void setText(String text) {
        this.text = text;
        isTextSet = true;
    }
    public void setActualStage(Stage stage) {
        this.stage = stage;
    }
    public Stage getStage () {
        return this.stage;
    }

    public void resetText () {
        text = "";
    }

    public String getText() {
        return text;
    }

    public BitmapFont getFont() {
        return font;
    }
    public void setFont (BitmapFont font) {
        this.font = font;
    }
    public GlyphLayout getGlyphLayout(String text , BitmapFont font) {
        return new GlyphLayout(font,text);
    }

    public void setTexture(Texture t) {
        textureRegion.setRegion(t);
        setSize(t.getWidth(), t.getHeight());
        rectangle.setSize(t.getWidth(), t.getHeight());
        isTextureSet = true;
        if (boundaryPolygon == null)
            setBoundaryRectangle();
    }

    public void setTexture (TextureRegion t) {
        textureRegion.setRegion(t);
        setSize(t.getRegionWidth(), t.getRegionHeight());
        rectangle.setSize(t.getRegionWidth(), t.getRegionHeight());
        isTextureSet = true;
        if (boundaryPolygon == null)
            setBoundaryRectangle();
    }

    public void setAnimationFromTextureAtlas(TextureAtlas[] textureAtlas, float[] animDuration) {

        animation = new Animation[textureAtlas.length];

        for (int i = 0; i < textureAtlas.length; i++) {
            animation[i] = new Animation<TextureRegion>(animDuration[i], textureAtlas[i].getRegions());
        }
        setSize(animation[0].getKeyFrame(0.0f).getRegionWidth(), animation[0].getKeyFrame(0.0f).getRegionHeight());
        rectangle.setSize(animation[0].getKeyFrame(0.0f).getRegionWidth(), animation[0].getKeyFrame(0.0f).getRegionHeight());
        isAnimSet = true;
        if (boundaryPolygon == null)
            setBoundaryRectangle();

        this.animDuration = animDuration;


    }

    public void setAnimationFromTextureAtlas(TextureAtlas textureAtlas, float animDuration){
        animationSingle = new Animation<TextureRegion>(animDuration, textureAtlas.getRegions());
        isAnimSingleSet = true;
        setSize(animationSingle.getKeyFrame(0.0f).getRegionWidth(), animationSingle.getKeyFrame(0.0f).getRegionHeight());
        rectangle.setSize(animationSingle.getKeyFrame(0.0f).getRegionWidth(), animationSingle.getKeyFrame(0.0f).getRegionHeight());
        if (boundaryPolygon == null)
            setBoundaryRectangle();

    }

    public void setCurrentAnimation(int currentAnimation) {
        this.currentAnimation = currentAnimation;
    }

    public void setAnimationLoopType(Animation.PlayMode playMode) {
        for (Animation<TextureRegion> textureRegionAnimation : animation)
            textureRegionAnimation.setPlayMode(playMode);
    }

    public void setAnimationLoopType(Animation.PlayMode playMode, int currentAnimationToChange) {
        animation[currentAnimationToChange].setPlayMode(playMode);
    }

    public void setFlipX() {
        textureRegion.flip(true, false);
    }

    public void resetElapsedTime() {
        elapsedTime = 0.0f;
    }
    public void setElapsedTime(float elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public Rectangle getRectangle() {
        rectangle.setPosition(getX(), getY());
        return rectangle;
    }

    public TextureRegion getTextureRegion(){
        return textureRegion;
    }

    public Animation<TextureRegion> getCurrentAnimation() {
        return animation[currentAnimation];
    }

    public Animation<TextureRegion> getCurrentSingleAnimation() {
        return animationSingle;
    }

    public Animation<TextureRegion> getAnimationIndex(int i) {
        return animation[i];
    }

    public boolean overlaps(BaseActor other) {

        return this.getRectangle().overlaps(other.getRectangle());
    }

    public void setBoundaryRectangle() {
        float w = getWidth();
        float h = getHeight();
        float[] vertices = {0,0,w,0,w,h,0,h};
        boundaryPolygon = new Polygon(vertices);
    }

    public Polygon getBoundaryPolygon() {
        boundaryPolygon.setPosition(getX(), getY());
        boundaryPolygon.setOrigin(getOriginX(), getOriginY());
        boundaryPolygon.setRotation(getRotation());
        boundaryPolygon.setScale(getScaleX(), getScaleY());
        return boundaryPolygon;
    }

    public boolean overlapsPolygons (BaseActor other) {
        Polygon poly1 = this.getBoundaryPolygon();
        Polygon poly2 = other.getBoundaryPolygon();
        if (!poly1.getBoundingRectangle().overlaps(poly2.getBoundingRectangle()))
            return false;

        return Intersector.overlapConvexPolygons(poly1, poly2);
    }

    public void centerAtPosition(float x, float y) {
        setPosition(x - getWidth()/2, y - getHeight()/2);
    }

    public float getCenterX() {
        return getX() + getWidth()/2;
    }

    public float getCenterY() {
        return getY() + getHeight()/2;
    }

    public Animation<TextureRegion> getAnimationSingle() {
        return animationSingle;
    }

    public float getElapsedTime() {
        return elapsedTime;
    }
    public GlyphLayout getGlyphLayout() {
        return glyphLayout;
    }

    public void setBoundingArea(float x1, float y1, float x2, float y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    public float getX1() {
        return x1;
    }

    public float getX2() {
        return x2;
    }

    public float getY1() {
        return y1;
    }

    public float getY2() {
        return y2;
    }


    @Override
    public void act(float delta) {
        super.act(delta);
        elapsedTime += Gdx.graphics.getDeltaTime();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        Color c = getColor();
        batch.setColor(c.r,c.g,c.b,c.a);
        drawBaseActor(batch, parentAlpha);
    }

    public void drawBaseActor (Batch batch, float parentAlpha) {
        if (isVisible() && isTextSet)
            font.draw(batch, text, getX(), getY());

        if (isVisible() && isTextureSet) {
            batch.draw(textureRegion, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
        }

        if (isVisible() && isAnimSet) {
            batch.draw(animation[currentAnimation].getKeyFrame(elapsedTime, true), getX(), getY(), getOriginX(), getOriginY(), animation[currentAnimation].getKeyFrame(elapsedTime).getRegionWidth(), animation[currentAnimation].getKeyFrame(elapsedTime).getRegionHeight(),getScaleX(),getScaleY(),getRotation());
        }

        if (isVisible() && isAnimSingleSet) {
            batch.draw(animationSingle.getKeyFrame(elapsedTime, true), getX(), getY(), animationSingle.getKeyFrame(elapsedTime).getRegionWidth(), animationSingle.getKeyFrame(elapsedTime).getRegionHeight());
        }
    }
}