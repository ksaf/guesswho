package com.velen.guesswho.player;

import android.support.v7.app.AppCompatActivity;

import com.velen.guesswho.characters.CharacterGroup;

public class AIPlayer extends Player{
    /**
     * Creates a new AI player.
     * @param characterGroup The character set this player will be using.
     */
    public AIPlayer(CharacterGroup characterGroup, AppCompatActivity activity, String color) {
        super(characterGroup, activity, color);
    }
}
