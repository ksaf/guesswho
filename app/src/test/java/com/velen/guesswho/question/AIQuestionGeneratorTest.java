package com.velen.guesswho.question;

import com.velen.guesswho.characters.CharacterGroup;
import com.velen.guesswho.features.CharacterFeatures;
import com.velen.guesswho.features.GroupFeaturesPool;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class AIQuestionGeneratorTest {

    private final int NUMBER_OF_CHARACTERS = 4;

    @Mock
    private CharacterGroup mockGroup;
    @Mock
    private GroupFeaturesPool mockPool;
    @Mock
    private CharacterFeatures mockFeatures;

    @Before
    public void setup() {
        prepareMockGroup();
        prepareMockPool();
    }

    @After
    public void tearDown() {}

    @Test
    public void shouldGenerateTheBestQuestion() {
        AIQuestionGenerator generator = new AIQuestionGenerator();
        Question q = generator.generateQuestion(mockGroup, 100);
        String expectedQuestion = "Are your character's eyes black?";
        String actualQuestion = q.getQuestionToDisplay();
        Assert.assertEquals(expectedQuestion, actualQuestion);
    }

    private void prepareMockGroup() {
        Mockito.when(mockGroup.getAllAvailableFeaturesForGroup()).thenReturn(mockPool);
        Mockito.when(mockGroup.getGroupSize()).thenReturn(NUMBER_OF_CHARACTERS);
    }

    private void prepareMockPool() {
        List<String> allTypes = Arrays.asList("name", "gender", "hairColor", "eyeColor", "shirtColor", "facialExpression", "miscellaneous");
        Mockito.when(mockPool.getAllAvailableFeatureTypes()).thenReturn(allTypes);

        addTypeAndFeatures("name", "orestis", "sotis", "mana", "mpas");
        addTypeAndFeatures("gender", "male", "male", "female", "male");
        addTypeAndFeatures("hairColor", "black", "bold", "brown");
        addTypeAndFeatures("eyeColor", "black", "brown", "black", "grey");
        addTypeAndFeatures("shirtColor", "black", "white", "blue", "yellow");
        addTypeAndFeatures("facialExpression", "smiling", "frowning");
        addTypeAndFeatures("miscellaneous", "have a hat", "wear glasses");
    }

    private List<String> setAllFeaturesForType(String[] features) {
        return Arrays.asList(features);
    }

    private void addTypeAndFeatures(String type, String... features) {
        List<String> allFeaturesForType = setAllFeaturesForType(features);
        Mockito.when(mockPool.getAllAvailableFeaturesIncludingDuplicatesFor(type)).thenReturn(allFeaturesForType);
    }

}
