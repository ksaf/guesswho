package com.velen.guesswho.gameStates;

import android.support.v7.app.AppCompatActivity;

import com.velen.guesswho.animations.AiFlippingAnimator;
import com.velen.guesswho.answer.AnswerGenerator;
import com.velen.guesswho.characters.CharacterGroup;
import com.velen.guesswho.player.Player;
import com.velen.guesswho.question.Question;

public class AIFlippingCharactersAnimationState implements GameState {

    private Game game;
    private CharacterGroup groupToClose;
    private Player currentPlayer;
    private Player nextPlayer;
    private AppCompatActivity playScreenActivity;

    public AIFlippingCharactersAnimationState(Game game) {
        this.game = game;
    }

    @Override
    public void initialiseState(AppCompatActivity playScreenActivity) {
        this.playScreenActivity = playScreenActivity;
        currentPlayer = game.getTurnManager().getCurrentPlayer();
        nextPlayer = game.getTurnManager().getNextPlayer();
    }

    @Override
    public void flipCoin() {

    }

    @Override
    public void sayOkToTurnDisplay(Player nextPlayer) {

    }

    @Override
    public void askQuestion(Question question) {

    }

    @Override
    public void clickGuess() {

    }

    @Override
    public void finishAnswerAnimation(Question question) {

    }

    @Override
    public void finishGuessAnimation(boolean correct) {

    }

    @Override
    public void flipCharacters() {

    }

    @Override
    public void passTurn() {

    }

    @Override
    public void unclickableTimeInAITurnPassed() {

    }

    @Override
    public void answerToAIQuestionAnimation(Question question) {
        AnswerGenerator generator = new AnswerGenerator();
        groupToClose = generator.isCorrect(question, nextPlayer.getChosenCharacter())?
                currentPlayer.getCurrentCharacterGroup().getCharactersWithoutFeature(question.getQuestionTopic(), question.getSpecification())
                :currentPlayer.getCurrentCharacterGroup().getCharactersWithFeature(question.getQuestionTopic(), question.getSpecification());
        AiFlippingAnimator animator = new AiFlippingAnimator(playScreenActivity, currentPlayer, groupToClose);
        animator.startFlipping();

    }

    @Override
    public void finishAIFlippingCharactersAnimation() {
        game.setGameState(game.getTurnDisplayState());
    }

    @Override
    public void finishChangingBoardAnimation() {

    }

    @Override
    public void surrender() {

    }

    @Override
    public void victory(Player player) {

    }
}
