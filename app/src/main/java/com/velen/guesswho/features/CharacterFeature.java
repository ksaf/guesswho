package com.velen.guesswho.features;

/**
 * A data class that holds information about a specific feature a character has.
 */
public class CharacterFeature {

    private final String featureType;
    private final String feature;

    /**
     * Constructs a new feature.
     * @param featureType The type of the feature (Gender, hair color, ..).
     * @param feature The choice for this feature (male, black, ..).
     */
    public CharacterFeature(String featureType, String feature) {
        this.featureType = featureType;
        this.feature = feature;
    }

    /**
     * @return The type of the feature (Gender, hair color, ..).
     */
    public String type() {
        return featureType;
    }

    /**
     * @return The choice for this feature (male, black, ..).
     */
    public String actual() {
        return feature;
    }

    public boolean is(String featureType, String feature) {
        return (featureType.equals(this.featureType) && feature.equals(this.feature));
    }
}
