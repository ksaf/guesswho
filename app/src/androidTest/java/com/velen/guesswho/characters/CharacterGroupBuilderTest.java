package com.velen.guesswho.characters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.velen.guesswho.R;
import com.velen.guesswho.features.FeaturesPool;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.velen.guesswho.question.QuestionMenuChoices.BOLD;
import static com.velen.guesswho.question.QuestionMenuChoices.EYE_COLOR;
import static com.velen.guesswho.question.QuestionMenuChoices.GENDER;
import static com.velen.guesswho.question.QuestionMenuChoices.HAIR_COLOR;
import static com.velen.guesswho.question.QuestionMenuChoices.MALE;
import static com.velen.guesswho.question.QuestionMenuChoices.MISCELLANEOUS;
import static com.velen.guesswho.question.QuestionMenuChoices.NAME;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


@RunWith(AndroidJUnit4.class)
public class CharacterGroupBuilderTest {

    private CharacterGroupBuilder parser;

    @Before
    public void setup() {
        Context context = InstrumentationRegistry.getTargetContext();
        parser = new CharacterGroupBuilder(context);
    }

    @After
    public void tearDown() {}

    @Test
    public void shouldHaveThreeMaleCharacters() {
        CharacterGroup group = parser.getCharactersInXml(R.xml.test_character_pool);
        group = group.getCharactersWithFeature(GENDER, MALE);
        assertTrue(group.getGroupSize() == 3);
    }

    @Test
    public void shouldHaveOneBoldCharacter() {
        CharacterGroup group = parser.getCharactersInXml(R.xml.test_character_pool);
        group = group.getCharactersWithFeature(HAIR_COLOR, BOLD);
        assertTrue(group.getGroupSize() == 1);
    }

    @Test
    public void shouldImportCorrectFeaturesForGroup() {
        //Setup
        CharacterGroup group = parser.getCharactersInXml(R.xml.test_character_pool);
        FeaturesPool allFeatures = group.getAllAvailableFeaturesForGroup();
        //Test
        String expectedValue = "Orestis";
        String actualValue = allFeatures.getAllAvailableFeaturesFor(NAME).get(0);
        //Verify
        assertEquals(expectedValue, actualValue);

        //Test
        expectedValue = "have a hat";
        actualValue = allFeatures.getAllAvailableFeaturesFor(MISCELLANEOUS).get(1);
        //Verify
        assertEquals(expectedValue, actualValue);
    }

    @Test(expected=IndexOutOfBoundsException.class)
    public void shouldNotImportDuplicates() {
        CharacterGroup group = parser.getCharactersInXml(R.xml.test_character_pool);
        FeaturesPool allFeatures = group.getAllAvailableFeaturesForGroup();
        allFeatures.getAllAvailableFeaturesFor(EYE_COLOR).get(1);
    }

    @Test
    public void shouldImportDuplicates() {
        CharacterGroup group = parser.getCharactersInXml(R.xml.test_character_pool);
        FeaturesPool allFeatures = group.getAllAvailableFeaturesForGroup();
        allFeatures.getAllAvailableFeaturesIncludingDuplicatesFor(EYE_COLOR).get(1);
    }

    @Test
    public void shouldLoadImagesForCharacters() {
        CharacterGroup group = parser.getCharactersInXml(R.xml.test_character_pool);
        Character character = group.getCharactersWithFeature("name", "Orestis").getCharacters().get(0);

        Drawable image = character.getDrawable();
        Assert.assertNotNull(image);
    }
}
