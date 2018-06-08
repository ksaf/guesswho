package com.velen.guesswho.features;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static com.velen.guesswho.question.QuestionMenuChoices.MISCELLANEOUS;

public class CharacterFeaturesTest {

    private CharacterFeatures features;

    @Before
    public void setup() {
        features = new CharacterFeatures();
        generateFeatures();
    }

    @After
    public void tearDown() {
        features = null;
    }

    @Test
    public void shouldCorrectlyAddAFeature() {
//        features = new CharacterFeatures();
//        features.addSimpleFeature("name", "orestis");
//        String expected = "orestis";
//        String actual = features.getNextFeatureFor("name");
//        Assert.assertEquals(expected, actual);
    }

    @Test
    public void shouldReturnCorrectFeatureForType() {
//        String expected = "black";
//        String actual = features.getNextFeatureFor("hairColor");
//        Assert.assertEquals(expected, actual);
//
//        expected = "has a beard";
//        actual = features.getNextFeatureFor(MISCELLANEOUS);
//        Assert.assertEquals(expected, actual);
//
//        expected = "wears glasses";
//        actual = features.getNextFeatureFor(MISCELLANEOUS);
//        Assert.assertEquals(expected, actual);
    }

    @Test
    public void shouldReturnAllFeatureTypesTheCharacterHas() {
        int expectedNumberOfFeatures = 5;
        int actualNumberOfFeatures = features.getContainedTypes().size();
        Assert.assertEquals(expectedNumberOfFeatures, actualNumberOfFeatures);
    }

    private void generateFeatures() {
        features.addSimpleFeature("name", "orestis");
        features.addSimpleFeature("gender", "male");
        features.addSimpleFeature("hairColor", "black");
        features.addSimpleFeature(MISCELLANEOUS, "has a beard");
        features.addSimpleFeature(MISCELLANEOUS, "wears glasses");
    }

}
