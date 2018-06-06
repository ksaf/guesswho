package com.velen.guesswho.question;

import java.util.HashMap;
import java.util.Map;

/**
 * This singleton creates a question instance when provided with the user's selections on the Select Question menu.
 */
public class QuestionBuilder {

    private Map<String, String> typeToString;
    public static QuestionBuilder instance;

    /**
     * Gives the single instance of the QuestionBuilder
     * @return The class's instance.
     */
    public static QuestionBuilder getInstance() {
        if(instance == null) {
            return new QuestionBuilder();
        }
        return instance;
    }

    private QuestionBuilder() {
        typeToString = new HashMap<>();
        typeToString.put(QuestionMenuChoices.NAME, "Is the spy's name");
        typeToString.put(QuestionMenuChoices.GENDER, "Is the spy");
        typeToString.put(QuestionMenuChoices.HAIR_COLOR, "Is the spy's hair");
        typeToString.put(QuestionMenuChoices.EYE_COLOR, "Are the spy's eyes");
        typeToString.put(QuestionMenuChoices.SHIRT_COLOR, "Is the spy's shirt");
        typeToString.put(QuestionMenuChoices.FACIAL_EXPRESSION, "Is the spy");
        typeToString.put(QuestionMenuChoices.MISCELLANEOUS, "Does the spy");
    }

    /**
     * Creates a Question instance.
     * @param primarySelection The user's selection on the first submenu (gender, hair..).
     * @param secondarySelection The user's selection on the following submenu (blond, has beard..).
     * @return The created instance.
     */
    public Question getQuestion(String primarySelection, String secondarySelection) {
        String questionForDisplay = getQuestionFirstPart(primarySelection) + " " + secondarySelection + "?";
        return new Question(primarySelection, secondarySelection, questionForDisplay);
    }

    /**
     * Gives the question string, without the specific choice attached to it.
     * @param primarySelection The question type.
     * @return the first part of the question string.
     */
    public String getQuestionFirstPart(String primarySelection) {
        return typeToString.get(primarySelection);
    }

}
