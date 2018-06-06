package com.velen.guesswho.gameStates;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.velen.guesswho.playScreen.PlayScreenActivity;
import com.velen.guesswho.playScreen.PlayScreenUI;
import com.velen.guesswho.player.Player;
import com.velen.guesswho.question.Question;

public class DuringOpponentTurnState implements GameState {

    private Game game;
    private static final int INITIAL_DELAY = 2000;

    public DuringOpponentTurnState(Game game) {
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
        ui.createChosenCharacterPortrait(null);
        ui.setAskQuestionButtonClickable(false);
        ui.setGuessButtonClickable(false);
        ui.setNextTurnButtonClickable(false);
        ui.slideGridsIn();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                unclickableTimeInAITurnPassed();
            }
        }, INITIAL_DELAY);
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
    }

    @Override
    public void unclickableTimeInAITurnPassed() {
        if(game.getTurnManager().getNextPlayer().getCurrentCharacterGroup().getCharacters().size() < 3 ||
                game.getTurnManager().getCurrentPlayer().getCurrentCharacterGroup().getCharacters().size() < 3 ) {
            game.setGameState(game.getAIGuessingState());
        } else {
            game.setGameState(game.getAIQuestionAnnouncementState());
        }
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
    public void flipCoin() {

    }

    @Override
    public void sayOkToTurnDisplay(Player nextPlayer) {

    }
    @Override
    public void victory(Player player) {

    }
}
