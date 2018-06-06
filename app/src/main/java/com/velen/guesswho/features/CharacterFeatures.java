package com.velen.guesswho.features;

import com.velen.guesswho.question.QuestionMenuChoices;
import java.util.ArrayList;
import java.util.List;

/**
 * This class contains all the {@link CharacterFeature} a character has.
 */
public class CharacterFeatures {

    private static int thisTypeIteration = 0;
    private final List<CharacterFeature> featureGroup = new ArrayList<>();

    /**
     * Adds a {@link CharacterFeature} to the character's group of features.
     * @param type Type of the feature to add (gender, hair color, ..).
     * @param feature Choice for the feature to add (male, black, ..).
     */
    public void addFeature(String type, String feature) {
        featureGroup.add(new CharacterFeature(type, feature));
    }

    /**
     * Gives the choice the character has for a specific type.
     * <p>
     * Getting all the {@link QuestionMenuChoices#MISCELLANEOUS} features the character may have requires multiple CONSECUTIVE
     * calls of the method, each call giving the next feature.
     * </p>
     * <p>
     * If for example the character has a hat and wears glasses, the first invocation of {@link #getNextFeatureFor(String)}
     * will return has a hat, and the second will return wears glasses.
     * </p>
     * @param type The type of feature you want to check for.
     * @return The choice for the requested feature, or null if no choice was found for the requested type.
     */
    public String getNextFeatureFor(String type) {
        for(int i = 0; i < featureGroup.size(); i++) {
            CharacterFeature feature = moveToNextFeature(i, type);
            if(feature.type().equals(type)) {
                return feature.actual();
            }
        }
        return null;
    }

    /**
     * @return A list of all the types of features this character has. Multiple types of {@link QuestionMenuChoices#MISCELLANEOUS}
     * will be returned if available for the character.
     */
    public List<String> getContainedTypes() {
        List<String> types = new ArrayList<>();
        for(CharacterFeature feature : featureGroup) {
            types.add(feature.type());
        }
        return types;
    }

    /**
     * This method has to be used when getting features a multi-featured type and you want to stop before getting all of them.
     * <p>
     *     For example if you a character has 3 miscellaneous features, you call {@link #getNextFeatureFor(String)} for 2 of
     *     them, then you have to call <b>this</b> to be able to call {@link #getNextFeatureFor(String)} again
     *     for another type e.x. gender.
     * </p>
     */
    public void resetMultiFeaturedTypesGetting() {
        thisTypeIteration = 0;
    }

    private int numberOfTimesTypeAppearsInGroup(String type) {
        int numberOfMisc = 0;
        for(CharacterFeature feature : featureGroup) {
            if(feature.type().equals(type)) {
                numberOfMisc++;
            }
        }
        return numberOfMisc;
    }

    private CharacterFeature moveToNextFeature(int indexNow, String type) {
        CharacterFeature feature = featureGroup.get(indexNow);
        if(featureGroup.get(indexNow).type().equals(type) && thisTypeIteration < numberOfTimesTypeAppearsInGroup(type)) {
            feature = featureGroup.get(indexNow + thisTypeIteration);
            thisTypeIteration++;
        }
        if(thisTypeIteration == numberOfTimesTypeAppearsInGroup(type)) {
            thisTypeIteration = 0;
        }
        return feature;
    }
}
