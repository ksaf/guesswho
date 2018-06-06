package com.velen.guesswho.gameStates;

import android.support.v7.app.AppCompatActivity;

import com.velen.guesswho.player.Player;
import com.velen.guesswho.question.Question;

interface GameState {

    void initialiseState(AppCompatActivity playScreenActivity);

    void flipCoin();

    void sayOkToTurnDisplay(Player nextPlayer);

    void askQuestion(Question question);

    void clickGuess();

    void finishAnswerAnimation(Question question);

    void finishGuessAnimation(boolean correct);

    void flipCharacters();

    void passTurn();

    void unclickableTimeInAITurnPassed();

    void answerToAIQuestionAnimation(Question question);

    void finishAIFlippingCharactersAnimation();

    void finishChangingBoardAnimation();

    void surrender();

    void victory(Player player);
}
