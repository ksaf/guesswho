package com.velen.guesswho.characters;

import android.graphics.drawable.Drawable;

import com.velen.guesswho.features.CharacterFeatures;
import static com.velen.guesswho.question.QuestionMenuChoices.*;

/** A builder class used to create a Character.*/
public class CharacterBuilder {

    private CharacterFeatures features;
    private Drawable drawable;
    private Drawable flippedDrawable;

    public CharacterBuilder() {
        features = new CharacterFeatures();
    }

    /**
     * Creates a new character after calling all the set methods.
     * @return A new Character instance.
     */
    public Character buildCharacter() {
        Character createdCharacter = new Character(features, drawable ,flippedDrawable);
        features = new CharacterFeatures();
        drawable = null;
        return createdCharacter;
    }

    /**
     * Adds a feature to the character.
     * @param featureType The type of the feature.
     * @param featureValue The feature choice for the type.
     * @return A CharacterBuilder instance, to be used for invoking {@link #buildCharacter()} when done adding features.
     */
    public CharacterBuilder addFeature(String featureType, String featureValue) {
        if(featureType != null){
            features.addFeature(featureType, featureValue);
        }
        return this;
    }

    /**
     * @param name The character's name.
     * @return A CharacterBuilder instance.
     */
    public CharacterBuilder setName(String name) {
        features.addFeature(NAME, name);
        return this;
    }

    /**
     * @param gender The character's gender.
     * @return A CharacterBuilder instance.
     */
    public CharacterBuilder setGender(String gender) {
        features.addFeature(GENDER, gender);
        return this;
    }

    /**
     * @param hairColor The color of the character's hair.
     * @return A CharacterBuilder instance.
     */
    public CharacterBuilder setHairColor(String hairColor) {
        features.addFeature(HAIR_COLOR, hairColor);
        return this;
    }

    /**
     * @param eyeColor The color of the character's eyes.
     * @return A CharacterBuilder instance.
     */
    public CharacterBuilder setEyeColor(String eyeColor) {
        features.addFeature(EYE_COLOR, eyeColor);
        return this;
    }

    /**
     * @param shirtColor The color of the character's shirt.
     * @return A CharacterBuilder instance.
     */
    public CharacterBuilder setShirtColor(String shirtColor) {
        features.addFeature(SHIRT_COLOR, shirtColor);
        return this;
    }

    /**
     * @param facialExpression The character's facial expression.
     * @return A CharacterBuilder instance.
     */
    public CharacterBuilder setFacialExpression(String facialExpression) {
        features.addFeature(FACIAL_EXPRESSION, facialExpression);
        return this;
    }

    /**
     * @param miscellaneous All the miscellaneous features the character has (hat, beard..). Has to be called more times to add more features.
     * @return A CharacterBuilder instance.
     */
    public CharacterBuilder setMiscellaneous(String miscellaneous) {
        features.addFeature(MISCELLANEOUS, miscellaneous);
        return this;
    }

    /**
     * @param drawable An integer for the character picture.
     * @return A CharacterBuilder instance.
     */
    public CharacterBuilder setDrawable(Drawable drawable) {
        this.drawable = drawable;
        return this;
    }

    /**
     * @param drawable An integer for the flipped character picture.
     * @return A CharacterBuilder instance.
     */
    public CharacterBuilder setFlippedDrawable(Drawable drawable) {
        this.flippedDrawable = drawable;
        return this;
    }
}
