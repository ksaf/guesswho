package com.velen.guesswho.characters;

import android.graphics.drawable.Drawable;

import com.velen.guesswho.features.CharacterFeatures;

/**
 * This class holds the attributes of a single character.
 */
public class Character {

    private Drawable drawable;
    private Drawable currentDrawable;
    private Drawable flippedDrawable;
    private int charID;
    private static int idCount;

    private CharacterFeatures characterFeatures;

    /**
     * Constructs a new Character.
     * @param characterFeatures A {@link CharacterFeatures} instance with all the features the character will have.
     * @param drawable The location of the Character's image.
     */
    public Character(CharacterFeatures characterFeatures, Drawable drawable, Drawable flippedDrawable) {
        idCount++;
        this.characterFeatures = characterFeatures;
        this.drawable = drawable;
        this.currentDrawable = drawable;
        this.flippedDrawable = flippedDrawable;
        this.charID = idCount;
    }

    /**
     * Determines if the character has a specific feature.
     * @param featureType The type of feature you want to check for (gender, eye color, ..)
     * @param featureChoice The choice for that feature (male, green, ..).
     * @return True if the character has that feature, otherwise false.
     */
    public boolean hasFeature(String featureType, String featureChoice) {
        boolean hasFeature = false;
        String[] allTypes = characterFeatures.getContainedTypes().toArray(new String[characterFeatures.getContainedTypes().size()]);
        for(String currentType : allTypes) {
            String nextFeature = characterFeatures.getNextFeatureFor(featureType);
            if(nextFeature != null) {
                hasFeature = nextFeature.equals(featureChoice);
                if(hasFeature) {
                    return hasFeature;
                }
            }
        }
        return hasFeature;
    }

    /**
     * @return The drawable assigned to the character, which can be used for displaying the character image.
     */
    public Drawable getDrawable() {
        return currentDrawable;
    }

    public void switchDrawableToFlipped() {
        currentDrawable = flippedDrawable;
    }

    public void switchDrawableToNormal() {
        currentDrawable = drawable;
    }

    public int getCharID() {
        return charID;
    }

    /**
     * Compares two characters and determines if they are the same.
     * @param characterToCompareWith The character to compare with.
     * @return True if the compared character is the same.
     */
    public boolean isTheSame(Character characterToCompareWith) {
        return (characterToCompareWith.getCharID() == charID);
    }

    /**
     * @return The character's features.
     */
    public CharacterFeatures getFeatures() {
        return characterFeatures;
    }
}
