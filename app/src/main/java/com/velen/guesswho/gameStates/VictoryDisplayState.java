package com.velen.guesswho.gameStates;

import android.support.v7.app.AppCompatActivity;

import com.velen.guesswho.gameDialogs.SwitchableDialogDisplayer;
import com.velen.guesswho.player.Player;
import com.velen.guesswho.question.Question;
import com.velen.guesswho.gameDialogs.VictoryConfirmationDialog;

public class VictoryDisplayState implements GameState {

    private Game game;
    private AppCompatActivity playScreenActivity;

    public VictoryDisplayState(Game game) {
        this.game = game;
    }

    @Override
    public void initialiseState(AppCompatActivity playScreenActivity) {
        this.playScreenActivity = playScreenActivity;
    }

    @Override
    public void flipCoin() {
        game.getTurnManager().restartGame();
        game.setGameState(game.getCoinFlipState());
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
        final SwitchableDialogDisplayer displayer = new SwitchableDialogDisplayer(playScreenActivity);
        VictoryConfirmationDialog dialog = new VictoryConfirmationDialog(displayer, playScreenActivity, game, player);
        dialog.openDialog();
    }
}
