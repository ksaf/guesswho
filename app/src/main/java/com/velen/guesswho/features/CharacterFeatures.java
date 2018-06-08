package com.velen.guesswho.features;

import com.velen.guesswho.question.QuestionMenuChoices;
import java.util.ArrayList;
import java.util.List;

/**
 * This class contains all the {@link CharacterFeature} a character has.
 */
public class CharacterFeatures {

    private final List<CharacterFeature> simpleFeatures = new ArrayList<>();
    private List<String> miscFeatures = new ArrayList<>();

    /**
     * Adds a {@link CharacterFeature} to the character's group of simple (non miscellaneous) features.
     * @param type Type of the feature to add (gender, hair color, ..).
     * @param feature Choice for the feature to add (male, black, ..).
     */
    public void addSimpleFeature(String type, String feature) {
        simpleFeatures.add(new CharacterFeature(type, feature));
    }

    /**
     * Sets the character's group of miscellaneous features.
     * @param miscFeatures A list of Strings containing the character's miscellaneous feature (wear glasses, have a hat..).
     */
    public void setMiscFeatures(List<String> miscFeatures) {
        this.miscFeatures = miscFeatures;
    }

    public boolean hasFeature(String featureType, String featureChoice) {
        for(CharacterFeature simpleFeature : simpleFeatures) {
            if(simpleFeature.is(featureType, featureChoice)) {
                return true;
            }
        }
        return featureType.equals("miscellaneous") && miscFeatures.contains(featureChoice);
    }

    public String getFeatureForSimpleType(String type) {
        for(CharacterFeature simpleFeature : simpleFeatures) {
            if(simpleFeature.type().equals(type)) {
                return simpleFeature.actual();
            }
        }
        return null;
    }

    public List<String> getMiscFeatures() {
        return miscFeatures;
    }


    /**
     * @return A list of all the types of features this character has. Multiple types of {@link QuestionMenuChoices#MISCELLANEOUS}
     * will be returned if available for the character.
     */
    public List<String> getContainedTypes() {
        List<String> types = new ArrayList<>();
        for(CharacterFeature feature : simpleFeatures) {
            types.add(feature.type());
        }
        if(!miscFeatures.isEmpty()) {
            types.add("miscellaneous");
        }
        return types;
    }

}
