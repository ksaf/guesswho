package com.velen.guesswho.question;

import com.velen.guesswho.characters.CharacterGroup;
import com.velen.guesswho.features.GroupFeaturesPool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * This class contains all the logic for a random AI question to be generated.
 * The question can be the best possible or worse, depending on the difficulty.
 */
public class AIQuestionGeneratorImpl implements AIQuestionGenerator{

    private int difficulty;
    private double bestQuestionPower = 2;
    private String bestQuestionType;
    private String bestQuestionChoice;

    /**
     * Generates an AI {@link Question} to be asked against a player. How powerful the question
     * is depends on the difficulty provided.
     * @param againstGroup The {@link CharacterGroup} containing all the remaining characters of the player to whom the question will be asked.
     * @param difficulty An integer deciding how powerful the question will be. Difficulty of 100 will always give the best possible
     *                   question, should never be 0 or lower.
     * @return The generated {@link Question}.
     */
    public Question generateQuestion(CharacterGroup againstGroup, int difficulty) {
        this.difficulty = difficulty > 49? difficulty : 50;
        GroupFeaturesPool pool = againstGroup.getAllAvailableFeaturesForGroup();
        List<String> allTypes = pool.getAllAvailableFeatureTypes();
        allTypes = shuffle(allTypes);
        for(String currentType : allTypes) {
            List<String> allFeaturesForType = pool.getAllAvailableFeaturesIncludingDuplicatesFor(currentType);
            allFeaturesForType = shuffle(allFeaturesForType);
            for(String currentFeatureChoice : allFeaturesForType) {
                int occurrences = Collections.frequency(allFeaturesForType, currentFeatureChoice);
                double questionPower = (double)occurrences / (double)againstGroup.getGroupSize();
                if(isBetterThan(questionPower, bestQuestionPower)) {
                    bestQuestionPower = questionPower;
                    bestQuestionType = currentType;
                    bestQuestionChoice = currentFeatureChoice;
                }
            }
        }
        return QuestionBuilder.getInstance().getQuestion(bestQuestionType, bestQuestionChoice);
    }

    private List<String> shuffle(List<String> list) {
        List<String> shuffledList = new ArrayList<>(list);
        Collections.shuffle(shuffledList);
        return shuffledList;
    }

    private boolean shouldGiveBestPossibleQuestion() {
        Random r = new Random();
        boolean shouldGiveBestQuestion = false;
        if(r.nextInt(100)+1 < difficulty){shouldGiveBestQuestion=true;}
        return shouldGiveBestQuestion;
    }

    /* Remaining number of occurrences / remaining number of characters must be as close to 0.5 as possible. **/
    private boolean isBetterThan(double first, double second) {
        return (Math.abs(first - 0.5) < Math.abs(second - 0.5)) && shouldGiveBestPossibleQuestion();
    }
}
