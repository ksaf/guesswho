package com.velen.guesswho.question;

import com.velen.guesswho.gameStrings.GameStringLiterals;

import java.util.HashMap;
import java.util.Map;

import static com.velen.guesswho.gameStrings.GameStringLiterals.*;

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
        typeToString.put(GameStringLiterals.NAME, IS_THE_SPY_NAME);
        typeToString.put(GameStringLiterals.GENDER, IS_THE_SPY);
        typeToString.put(GameStringLiterals.HAIR_COLOR, IS_THE_SPY_HAIR);
        typeToString.put(GameStringLiterals.EYE_COLOR, ARE_THE_SPY_EYES);
        typeToString.put(GameStringLiterals.SHIRT_COLOR, IS_THE_SPY_SHIRT);
        typeToString.put(GameStringLiterals.FACIAL_EXPRESSION, IS_THE_SPY);
        typeToString.put(GameStringLiterals.MISCELLANEOUS, DOES_THE_SPY);
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
