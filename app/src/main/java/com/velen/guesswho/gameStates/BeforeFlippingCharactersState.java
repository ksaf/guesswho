package com.velen.guesswho.gameStates;

import android.support.v7.app.AppCompatActivity;

import com.velen.guesswho.answer.AnswerGenerator;
import com.velen.guesswho.characters.CharacterGroup;
import com.velen.guesswho.playScreen.PlayScreenActivity;
import com.velen.guesswho.playScreen.PlayScreenUI;
import com.velen.guesswho.player.Player;
import com.velen.guesswho.question.Question;

public class BeforeFlippingCharactersState implements GameState {

    private Game game;
    private PlayScreenUI ui;
    private Player currentPlayer;
    private Player nextPlayer;

    public BeforeFlippingCharactersState(Game game) {
        this.game = game;
    }

    @Override
    public void initialiseState(AppCompatActivity playScreenActivity) {
        currentPlayer = game.getTurnManager().getCurrentPlayer();
        nextPlayer = game.getTurnManager().getNextPlayer();
        ui = ((PlayScreenActivity)playScreenActivity).getPlayScreenUI();


        ui.createMiniatureGrid(nextPlayer);
        ui.createChosenCharacterPortrait(currentPlayer.getChosenCharacter());
        ui.initialisePlayScreenButtons(currentPlayer.getCurrentCharacterGroup());
        ui.setAskQuestionButtonClickable(false);
        ui.setGuessButtonClickable(false);
        ui.setNextTurnButtonClickable(false);
    }

    @Override
    public void flipCharacters() {
        game.setGameState(game.getAfterFlippingCharactersState());
    }

    @Override
    public void askQuestion(Question question) {

    }

    @Override
    public void clickGuess() {

    }

    @Override
    public void finishAnswerAnimation(Question question) {
        AnswerGenerator generator = new AnswerGenerator();
        CharacterGroup closableGroup = generator.isCorrect(question, nextPlayer.getChosenCharacter())?
                currentPlayer.getCurrentCharacterGroup().getCharactersWithoutFeature(question.getQuestionTopic(), question.getSpecification())
                :currentPlayer.getCurrentCharacterGroup().getCharactersWithFeature(question.getQuestionTopic(), question.getSpecification());
        ui.createAndSetClickableCharactersGrid(currentPlayer, closableGroup, false);
    }

    @Override
    public void finishGuessAnimation(boolean correct) {

    }

    @Override
    public void passTurn() {

    }

    @Override
    public void unclickableTimeInAITurnPassed() {

    }

    @Override
    public void answerToAIQuestionAnimation(Question question) {

    }

    @Override
    public void finishAIFlippingCharactersAnimation() {

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

    @Override
    public void flipCoin() {

    }

    @Override
    public void sayOkToTurnDisplay(Player nextPlayer) {

    }
}
