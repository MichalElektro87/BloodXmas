package com.bloodxmas;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Disposable;
import com.bloodxmas.BloodXmas;

public class GameSound implements Disposable {

    public BloodXmas game;
    private Sound damage1;
    private Sound damage2;
    private Sound damage3;
    private Sound throwAxe;
    private Sound ding;

    public GameSound (BloodXmas game) {
        this.game = game;
        damage1 = game.assets.getAssetManager().get("sound/damage1.mp3", Sound.class);
        damage2 = game.assets.getAssetManager().get("sound/damage2.mp3", Sound.class);
        damage3 = game.assets.getAssetManager().get("sound/damage3.mp3", Sound.class);
        throwAxe = game.assets.getAssetManager().get("sound/throw_axe.mp3", Sound.class);
        ding = game.assets.getAssetManager().get("sound/ding.mp3", Sound.class);
    }

    public Sound getDamage1() {
        return damage1;
    }

    public Sound getDamage2() {
        return damage2;
    }

    public Sound getDamage3() {
        return damage3;
    }

    public Sound getThrowAxe() {
        return throwAxe;
    }



    public Sound getDing () {
        return ding;
    }


    @Override
    public void dispose() {
        damage1.dispose();
        damage2.dispose();
        damage3.dispose();
        throwAxe.dispose();
        ding.dispose();
    }
}
