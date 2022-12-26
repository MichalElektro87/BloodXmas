package com.bloodxmas;

public class SantaDialog extends DialogBox {


    public SantaDialog(BloodXmas game, Sleigh sleigh) {
        super(game, sleigh);

        getDialogArray().add("Ho! Ho! Ho! Merry Xmas!");
        getDialogArray().add("Time is ticking! I must deliver all this gifts to the people!");
        getDialogArray().add("Except of the libGdx programmers! They were naughty this year!");
        getDialogArray().add("WTF!!!! Who turned the lights off!");


    }
}
