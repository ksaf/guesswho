package com.velen.guesswho.features;


import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class FeaturesPoolTest {

    private FeaturesPool pool;
    @Mock
    private CharacterFeatures mockFeatures;

    @Before
    public void setup() {
        pool = new FeaturesPool();
        setMockFeatures();
    }

    @After
    public void tearDown() {
        pool = null;
    }

    @Test
    public void shouldReturnEmptyListIfNoFeatureHasBeenAdded() {

    }

    @Test
    public void shouldCorrectlyAddFirstFeaturesToPool() {
        pool.addToPool(mockFeatures);
        String expectedValue = "orestis";
        String actualValue = pool.getAllAvailableFeaturesFor("name").get(0);
        Assert.assertEquals(expectedValue, actualValue);
    }

    @Test
    public void shouldCorrectlyAddDuplicateFeaturesToPool() {
        pool.addToPool(mockFeatures);
        pool.addToPool(mockFeatures);
        String expectedValue = "orestis";
        String actualValue1 = pool.getAllAvailableFeaturesIncludingDuplicatesFor("name").get(0);
        String actualValue2 = pool.getAllAvailableFeaturesIncludingDuplicatesFor("name").get(1);
        Assert.assertEquals(expectedValue, actualValue1);
        Assert.assertEquals(expectedValue, actualValue2);
    }

    @Test
    public void shouldDoNothingWhenRemovingFromEmptyPool() {
        pool.removeFromPool(mockFeatures);
        int expectedValue = 0;
        int actualValue1 = pool.getAllAvailableFeaturesFor("name").size();
        int actualValue2 = pool.getAllAvailableFeaturesIncludingDuplicatesFor("name").size();
        Assert.assertEquals(expectedValue, actualValue1);
        Assert.assertEquals(expectedValue, actualValue2);
    }

    @Test
    public void shouldCorrectlyRemoveCharacterFeatures() {
        pool.addToPool(mockFeatures);
        pool.removeFromPool(mockFeatures);
        int expectedValue = 0;
        int actualValue1 = pool.getAllAvailableFeaturesFor("name").size();
        int actualValue2 = pool.getAllAvailableFeaturesIncludingDuplicatesFor("name").size();
        Assert.assertEquals(expectedValue, actualValue1);
        Assert.assertEquals(expectedValue, actualValue2);
    }

    @Test
    public void shouldRemoveAFeatureOnceOrTwiceDependingOnDuplicatesAllowed() {
        pool.addToPool(mockFeatures);
        pool.addToPool(mockFeatures);
        pool.removeFromPool(mockFeatures);
        int expectedValue1 = 0;
        int expectedValue2 = 1;
        int actualValue1 = pool.getAllAvailableFeaturesFor("name").size();
        int actualValue2 = pool.getAllAvailableFeaturesIncludingDuplicatesFor("name").size();
        Assert.assertEquals(expectedValue1, actualValue1);
        Assert.assertEquals(expectedValue2, actualValue2);
    }



    private void setMockFeatures() {
        Mockito.when(mockFeatures.getNextFeatureFor("name")).thenReturn("orestis");
        Mockito.when(mockFeatures.getNextFeatureFor("gender")).thenReturn("male");
        Mockito.when(mockFeatures.getNextFeatureFor("hairColor")).thenReturn("black");
        Mockito.when(mockFeatures.getNextFeatureFor("eyeColor")).thenReturn("brown");
        Mockito.when(mockFeatures.getNextFeatureFor("shirtColor")).thenReturn("black");
        Mockito.when(mockFeatures.getNextFeatureFor("facialExpression")).thenReturn("is smiling");
        Mockito.when(mockFeatures.getNextFeatureFor("miscellaneous")).thenReturn("have a hat");
        List<String> featureTypesList = new ArrayList<>();
        featureTypesList.add("name");
        featureTypesList.add("gender");
        featureTypesList.add("hairColor");
        featureTypesList.add("eyeColor");
        featureTypesList.add("shirtColor");
        featureTypesList.add("facialExpression");
        featureTypesList.add("miscellaneous");
        Mockito.when(mockFeatures.getContainedTypes()).thenReturn(featureTypesList);
    }
}
