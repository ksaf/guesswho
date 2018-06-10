package com.velen.guesswho.question;

import com.velen.guesswho.characters.CharacterGroup;

/** An Interface to be implemented by the class that will provide the questions the AI will ask.*/
public interface AIQuestionGenerator {

    /**
     * Generates an AI {@link Question} to be asked against a player. How powerful the question
     * is depends on the difficulty provided.
     * @param againstGroup The {@link CharacterGroup} containing all the remaining characters of the player to whom the question will be asked.
     * @param difficulty An integer deciding how powerful the question will be. Difficulty of 100 will always give the best possible
     *                   question, should never be 0 or lower.
     * @return The generated {@link Question}.
     */
    Question generateQuestion(CharacterGroup againstGroup, int difficulty);
}
