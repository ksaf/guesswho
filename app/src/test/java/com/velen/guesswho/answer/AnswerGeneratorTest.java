package com.velen.guesswho.answer;

import com.velen.guesswho.characters.CharacterBuilder;
import com.velen.guesswho.characters.Character;
import com.velen.guesswho.question.Question;
import com.velen.guesswho.question.QuestionBuilder;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static com.velen.guesswho.question.QuestionMenuChoices.*;

public class AnswerGeneratorTest {

    private final String CHAR_NAME = "aName";
    private Character myChar;
    private QuestionBuilder qb;
    private Question question;
    private AnswerGenerator answerGenerator;

    @Before
    public void setup() {
        answerGenerator = new AnswerGenerator();
        CharacterBuilder builder = new CharacterBuilder();
        myChar = builder.setName(CHAR_NAME)
                .setGender(MALE)
                .setHairColor(BLACK)
                .setEyeColor(BLUE)
                .setShirtColor(YELLOW)
                .setFacialExpression(SMILING)
                .setMiscellaneous(HAS_GLASSES)
                .buildCharacter();

        qb = QuestionBuilder.getInstance();
    }

    @After
    public void tearDown() {
        myChar = null;
    }

    @Test
    public void shouldCorrectlyAnswerCharacterIsMaleWithYes() {
        //Setup
        String expectedAnswer = "Yes";
        question = qb.getQuestion(GENDER, MALE);
        //Test
        String actualAnswer = answerGenerator.generateYesOrNoAnswer(question, myChar);
        //Verify
        Assert.assertEquals(expectedAnswer, actualAnswer);
    }

    @Test
    public void shouldCorrectlyAnswerCharacterIsMaleWithLongAnswer() {
        //Setup
        String expectedAnswer = "Yes, my character is male";
        question = qb.getQuestion(GENDER, MALE);
        //Test
        String actualAnswer = answerGenerator.generateLongAnswer(question, myChar);
        //Verify
        Assert.assertEquals(expectedAnswer, actualAnswer);
    }

    @Test
    public void shouldCorrectlyAnswerCharacterHairIsNotRedWithNo() {
        //Setup
        String expectedAnswer = "No";
        question = qb.getQuestion(HAIR_COLOR, RED);
        //Test
        String actualAnswer = answerGenerator.generateYesOrNoAnswer(question, myChar);
        //Verify
        Assert.assertEquals(expectedAnswer, actualAnswer);
    }

    @Test
    public void shouldCorrectlyAnswerCharacterHairIsNotRedWithLongAnswer() {
        //Setup
        String expectedAnswer = "No, my character's hair is not red";
        question = qb.getQuestion(HAIR_COLOR, RED);
        //Test
        String actualAnswer = answerGenerator.generateLongAnswer(question, myChar);
        //Verify
        Assert.assertEquals(expectedAnswer, actualAnswer);
    }

    @Test
    public void shouldCorrectlyAnswerCharacterHasGlassesWithYes() {
        //Setup
        String expectedAnswer = "Yes";
        question = qb.getQuestion(MISCELLANEOUS, HAS_GLASSES);
        //Test
        String actualAnswer = answerGenerator.generateYesOrNoAnswer(question, myChar);
        //Verify
        Assert.assertEquals(expectedAnswer, actualAnswer);
    }

    @Test
    public void shouldCorrectlyAnswerCharacterHasGlassesWithLongAnswer() {
        //Setup
        String expectedAnswer = "Yes, my character does wear glasses";
        question = qb.getQuestion(MISCELLANEOUS, HAS_GLASSES);
        //Test
        String actualAnswer = answerGenerator.generateLongAnswer(question, myChar);
        //Verify
        Assert.assertEquals(expectedAnswer, actualAnswer);
    }
}
