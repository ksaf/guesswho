package com.velen.guesswho.gameStates;

import android.support.v7.app.AppCompatActivity;

import com.velen.guesswho.playScreen.PlayScreenActivity;
import com.velen.guesswho.playScreen.PlayScreenUI;
import com.velen.guesswho.player.Player;
import com.velen.guesswho.question.Question;

public class GuessingState implements GameState{

    private final Game game;

    public GuessingState(Game game) {
        this.game = game;
    }

    @Override
    public void initialiseState(AppCompatActivity playScreenActivity) {
        Player currentPlayer = game.getTurnManager().getCurrentPlayer();
        PlayScreenUI ui = ((PlayScreenActivity)playScreenActivity).getPlayScreenUI();

        ui.createAndSetClickableCharactersGrid(currentPlayer, currentPlayer.getCurrentCharacterGroup(), true);
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
        Game.getInstance().getTurnManager().startNextTurn();
        Game.getInstance().setGameState(game.getTurnDisplayState());
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
        game.setGameState(game.getVictoryDisplayState());
        game.victory(player);
    }
}
