package com.velen.guesswho.player;

import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.velen.guesswho.R;
import com.velen.guesswho.characters.Character;
import com.velen.guesswho.characters.CharacterGroup;

/**
 * This class contains information about the character set the player is going to use, and the character he has chosen.
 */
public class Player {

    public final static String RED = "red";
    public final static String BLUE = "blue";
    private CharacterGroup currentCharacterGroup;
    private final CharacterGroup originalCharacterGroup;
    private Character chosenCharacter;
    private boolean isPlayersTurn = false;
    private final String color;
    private final Drawable colorBackground;
    /**
     * Creates a new player.
     * @param currentCharacterGroup The character set this player will be using.
     */
    public Player(CharacterGroup currentCharacterGroup, AppCompatActivity activity, String color) {
        this.currentCharacterGroup = currentCharacterGroup;
        this.originalCharacterGroup =  CharacterGroup.makeFromList(currentCharacterGroup.getCharacters());
        //this.originalCharacterGroup = currentCharacterGroup.cl
        this.color = color;
        colorBackground = color == BLUE? ContextCompat.getDrawable(activity, R.drawable.generic_blue_background_rounded_corners)
                : ContextCompat.getDrawable(activity, R.drawable.generic_red_background_rounded_corners);
    }

    /**
     * @return The player's color.
     */
    public String getColor() {
        return color;
    }

    /**
     * Set the character the player chose and his opponents are trying to clickGuess.
     * @param chosenCharacter The chosen character for this player.
     */
    public void chooseCharacter(Character chosenCharacter) {
        this.chosenCharacter = chosenCharacter;
    }

    public CharacterGroup getCurrentCharacterGroup() {
        return currentCharacterGroup;
    }

    public CharacterGroup getOriginalCharacterGroup() {
        return originalCharacterGroup;
    }

    /**
     * @return The character the player chose and his opponents are trying to clickGuess.
     */
    public Character getChosenCharacter() {
        return chosenCharacter;
    }

    /**
     * Set this player's turn.
     */
    public void startTurn() {
        isPlayersTurn = true;
    }

    /**
     * End this player's turn.
     */
    public void endTurn() {
        isPlayersTurn = false;
    }

    /**
     * @return True if it is this player's turn to play, or False if not.
     */
    public boolean isHisTurn() {
        return isPlayersTurn;
    }

    /**
     * Resets the player to his original state.
     */
    public void resetPlayer() {
        for(Character c : originalCharacterGroup.getCharacters()) {
            c.switchDrawableToNormal();
        }
        currentCharacterGroup = originalCharacterGroup;
        isPlayersTurn = false;
    }

    public Drawable getColorBackground() {
        return colorBackground;
    }
}
