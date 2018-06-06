package com.velen.guesswho.answer;

import com.velen.guesswho.question.Question;
import com.velen.guesswho.characters.Character;
import java.util.HashMap;
import java.util.Map;

import static com.velen.guesswho.question.QuestionMenuChoices.*;

public class AnswerGenerator {

    private Map<String, String> typeToString;

    public AnswerGenerator() {
        typeToString = new HashMap<>();
        typeToString.put(GENDER, "the spy is");
        typeToString.put(HAIR_COLOR, "the spy's hair is");
        typeToString.put(EYE_COLOR, "the spy's eyes are");
        typeToString.put(SHIRT_COLOR, "the spy's shirt is");
        typeToString.put(FACIAL_EXPRESSION, "the spy is");
        typeToString.put(MISCELLANEOUS, "the spy does");
    }

    public String generateYesOrNoAnswer(Question question, Character character) {
        return character.hasFeature(question.getQuestionTopic(), question.getSpecification())? "Yes" : "No";
    }

    public String generateLongAnswer(Question question, Character character) {
        boolean outcome = character.hasFeature(question.getQuestionTopic(), question.getSpecification());
        if (outcome){
            return "Yes, " + typeToString.get(question.getQuestionTopic()) + " " + question.getSpecification() + ".";
        } else {
            return "No, "+ typeToString.get(question.getQuestionTopic()) + " not " + question.getSpecification() + ".";
        }
    }

    public boolean isCorrect(Question question, Character character) {
        return character.hasFeature(question.getQuestionTopic(), question.getSpecification());
    }

}
