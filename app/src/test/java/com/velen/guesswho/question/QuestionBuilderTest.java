package com.velen.guesswho.question;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static com.velen.guesswho.question.QuestionMenuChoices.*;

public class QuestionBuilderTest {

    private QuestionBuilder qb;

    @Before
    public void setup() {
        qb = QuestionBuilder.getInstance();
    }

    @After
    public void tearDown() {}

    @Test
    public void shouldReturnCorrectQuestionForGenderWhenMale() {
        Question q = qb.getQuestion(GENDER, MALE);
        String expectedQuestion = "Is your character male?";
        String actualQuestion = q.getQuestionToDisplay();
        Assert.assertEquals(expectedQuestion, actualQuestion);
    }

    @Test
    public void shouldReturnCorrectQuestionForHairColorWhenBlonde() {
        Question q = qb.getQuestion(HAIR_COLOR, BLONDE);
        String expectedQuestion = "Is your character's hair blonde?";
        String actualQuestion = q.getQuestionToDisplay();
        Assert.assertEquals(expectedQuestion, actualQuestion);
    }

    @Test
    public void shouldReturnCorrectQuestionForEyeColorWhenBlue() {
        Question q = qb.getQuestion(EYE_COLOR, BLUE);
        String expectedQuestion = "Are your character's eyes blue?";
        String actualQuestion = q.getQuestionToDisplay();
        Assert.assertEquals(expectedQuestion, actualQuestion);
    }

    @Test
    public void shouldReturnCorrectQuestionForShirtColorWhenBlack() {
        Question q = qb.getQuestion(SHIRT_COLOR, BLACK);
        String expectedQuestion = "Is your character's shirt black?";
        String actualQuestion = q.getQuestionToDisplay();
        Assert.assertEquals(expectedQuestion, actualQuestion);
    }

    @Test
    public void shouldReturnCorrectQuestionForFacialExpressionWhenSmiling() {
        Question q = qb.getQuestion(FACIAL_EXPRESSION, SMILING);
        String expectedQuestion = "Is your character smiling?";
        String actualQuestion = q.getQuestionToDisplay();
        Assert.assertEquals(expectedQuestion, actualQuestion);
    }

    @Test
    public void shouldReturnCorrectQuestionForMiscellaneousQuestionWhenBeard() {
        Question q = qb.getQuestion(MISCELLANEOUS, HAS_BEARD);
        String expectedQuestion = "Does your character have a beard?";
        String actualQuestion = q.getQuestionToDisplay();
        Assert.assertEquals(expectedQuestion, actualQuestion);
    }

}
