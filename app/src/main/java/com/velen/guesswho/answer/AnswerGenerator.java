package com.velen.guesswho.answer;

import com.velen.guesswho.characters.Character;
import com.velen.guesswho.question.Question;

import java.util.HashMap;
import java.util.Map;

import static com.velen.guesswho.gameStrings.GameStringLiterals.*;
import static com.velen.guesswho.question.QuestionMenuChoices.EYE_COLOR;
import static com.velen.guesswho.question.QuestionMenuChoices.FACIAL_EXPRESSION;
import static com.velen.guesswho.question.QuestionMenuChoices.GENDER;
import static com.velen.guesswho.question.QuestionMenuChoices.HAIR_COLOR;
import static com.velen.guesswho.question.QuestionMenuChoices.MISCELLANEOUS;
import static com.velen.guesswho.question.QuestionMenuChoices.SHIRT_COLOR;

public class AnswerGenerator {

    private Map<String, String> typeToString;

    public AnswerGenerator() {
        typeToString = new HashMap<>();
        typeToString.put(GENDER, THE_SPY_IS);
        typeToString.put(HAIR_COLOR, THE_SPY_HAIR_IS);
        typeToString.put(EYE_COLOR, THE_SPY_EYES_ARE);
        typeToString.put(SHIRT_COLOR, THE_SPY_SHIRT_IS);
        typeToString.put(FACIAL_EXPRESSION, THE_SPY_IS);
        typeToString.put(MISCELLANEOUS, THE_SPY_DOES);
    }

    public String generateYesOrNoAnswer(Question question, Character character) {
        return character.hasFeature(question.getQuestionTopic(), question.getSpecification())? YES : NO;
    }

    public String generateLongAnswer(Question question, Character character) {
        boolean outcome = character.hasFeature(question.getQuestionTopic(), question.getSpecification());
        if (outcome){
            return YES + ", " + typeToString.get(question.getQuestionTopic()) + " " + question.getSpecification() + ".";
        } else {
            return NO + ", "+ typeToString.get(question.getQuestionTopic()) + " " + NOT + " " + question.getSpecification() + ".";
        }
    }

    public boolean isCorrect(Question question, Character character) {
        return character.hasFeature(question.getQuestionTopic(), question.getSpecification());
    }

}
