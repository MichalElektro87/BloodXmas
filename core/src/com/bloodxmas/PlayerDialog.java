package com.bloodxmas;

public class PlayerDialog extends DialogBox {


    public PlayerDialog(BloodXmas game, Player player) {
        super(game, player);

        getDialogArray().add("I must get out off this hell !!!");
        getDialogArray().add("Don't you think we need a key to open this door???!!!");
    }
}
