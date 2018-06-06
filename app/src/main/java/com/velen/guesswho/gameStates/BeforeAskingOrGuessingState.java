package com.velen.guesswho.gameStates;

import android.support.v7.app.AppCompatActivity;

import com.velen.guesswho.playScreen.PlayScreenActivity;
import com.velen.guesswho.playScreen.PlayScreenUI;
import com.velen.guesswho.player.Player;
import com.velen.guesswho.question.Question;

public class BeforeAskingOrGuessingState implements GameState {

    private Game game;

    public BeforeAskingOrGuessingState(Game game) {
        this.game = game;
    }


    @Override
    public void initialiseState(AppCompatActivity playScreenActivity) {
        Player currentPlayer = game.getTurnManager().getCurrentPlayer();
        Player nextPlayer = game.getTurnManager().getNextPlayer();
        PlayScreenUI ui = ((PlayScreenActivity)playScreenActivity).getPlayScreenUI();

        ui.createAndSetUnclickableCharactersGrid(currentPlayer);
        ui.setBackgroundForPlayer(currentPlayer);
        ui.createMiniatureGrid(nextPlayer);
        ui.createChosenCharacterPortrait(currentPlayer.getChosenCharacter());
        ui.initialisePlayScreenButtons(currentPlayer.getCurrentCharacterGroup());
        ui.setAskQuestionButtonClickable(true);
        ui.setGuessButtonClickable(true);
        ui.setNextTurnButtonClickable(false);
        ui.slideGridsIn();
    }

    @Override
    public void flipCharacters() {

    }

    @Override
    public void askQuestion(Question question) {
        game.setGameState(game.getAnswerAnnouncementState());
        game.finishAnswerAnimation(question);
    }

    @Override
    public void clickGuess() {
        game.setGameState(game.getGuessingState());
    }

    @Override
    public void finishAnswerAnimation(Question question) {

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
