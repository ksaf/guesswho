package com.velen.guesswho.gameStates;

import android.support.v7.app.AppCompatActivity;

import com.velen.guesswho.playScreen.PlayScreenActivity;
import com.velen.guesswho.playScreen.PlayScreenUI;
import com.velen.guesswho.player.Player;
import com.velen.guesswho.question.Question;

public class AfterFlippingCharactersState implements GameState {

    private Game game;

    public AfterFlippingCharactersState(Game game) {
        this.game = game;
    }

    @Override
    public void initialiseState(AppCompatActivity playScreenActivity) {
        Player currentPlayer = game.getTurnManager().getCurrentPlayer();
        Player nextPlayer = game.getTurnManager().getNextPlayer();
        PlayScreenUI ui = ((PlayScreenActivity)playScreenActivity).getPlayScreenUI();

//        ui.createAndSetUnclickableCharactersGrid(currentPlayer);
//        ui.createMiniatureGrid(nextPlayer);
//        ui.createChosenCharacterPortrait(currentPlayer.getChosenCharacter());
        ui.initialisePlayScreenButtons(nextPlayer.getCurrentCharacterGroup());
        ui.startNextButtonFlashingAnimation();
        ui.setAskQuestionButtonClickable(false);
        ui.setGuessButtonClickable(false);
        ui.setNextTurnButtonClickable(true);
    }

    @Override
    public void flipCharacters() {

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
    public void passTurn() {
        game.getTurnManager().startNextTurn();
        game.setGameState(game.getTurnDisplayState());
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
