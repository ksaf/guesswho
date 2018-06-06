package com.velen.guesswho.characters;


import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static com.velen.guesswho.question.QuestionMenuChoices.*;

public class CharacterTest {


    private final String CHAR_NAME = "aName";
    private Character myChar;

    @Before
    public void setup() {
        CharacterBuilder builder = new CharacterBuilder();
        myChar = builder.setName(CHAR_NAME)
                .setGender(MALE)
                .setHairColor(BLACK)
                .setEyeColor(BLUE)
                .setShirtColor(YELLOW)
                .setFacialExpression(SMILING)
                .setMiscellaneous(HAS_GLASSES)
                .buildCharacter();
    }

    @After
    public void tearDown() {
        myChar = null;
    }

    @Test
    public void shouldDetermineIfCharacterIsMale() {
        //Test
        boolean actualValue = myChar.hasFeature(GENDER, MALE);
        //Verify
        Assert.assertTrue(actualValue);
    }

    @Test
    public void shouldDetermineIfCharacterHasBlackHair() {
        //Test
        boolean actualValue = myChar.hasFeature(HAIR_COLOR, BLACK);
        //Verify
        Assert.assertTrue(actualValue);
    }

    @Test
    public void shouldDetermineIfCharacterHasBlueEyes() {
        //Test
        boolean actualValue = myChar.hasFeature(EYE_COLOR, BLUE);
        //Verify
        Assert.assertTrue(actualValue);
    }

    @Test
    public void shouldDetermineIfCharacterIsSmiling() {
        //Test
        boolean actualValue = myChar.hasFeature(FACIAL_EXPRESSION, SMILING);
        //Verify
        Assert.assertTrue(actualValue);
    }

    @Test
    public void shouldDetermineIfCharacterHasGlasses() {
        //Test
        boolean actualValue = myChar.hasFeature(MISCELLANEOUS, HAS_GLASSES);
        //Verify
        Assert.assertTrue(actualValue);
    }

}
