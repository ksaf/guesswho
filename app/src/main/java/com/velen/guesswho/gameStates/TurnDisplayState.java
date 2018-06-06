package com.velen.guesswho.gameStates;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.velen.guesswho.R;
import com.velen.guesswho.gameDialogs.SwitchableDialogDisplayer;
import com.velen.guesswho.player.AIPlayer;
import com.velen.guesswho.player.Player;
import com.velen.guesswho.question.Question;
import com.velen.guesswho.gameDialogs.TurnInfoDialog;

public class TurnDisplayState implements GameState {

    private Game game;

    public TurnDisplayState(Game game) {
        this.game = game;
    }

    @Override
    public void initialiseState(AppCompatActivity playScreenActivity) {
        final SwitchableDialogDisplayer displayer = new SwitchableDialogDisplayer(playScreenActivity);
        final Player currentPlayer = game.getTurnManager().getCurrentPlayer();
        TurnInfoDialog dialog = new TurnInfoDialog(displayer, game.getTurnManager().getNextPlayer());
        String textToDisplay = game.getTurnManager().isVsAI() && !(currentPlayer instanceof AIPlayer)? "Your turn!" : currentPlayer.getColor().toUpperCase() + " player's turn!";
        View view = dialog.openDialog(textToDisplay);
        Button okButton = (Button) view.findViewById(R.id.okTurnInfoButton);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayer.closeMenu();
                sayOkToTurnDisplay(currentPlayer);
            }
        });
    }

    @Override
    public void flipCoin() {

    }

    @Override
    public void sayOkToTurnDisplay(Player nextPlayer) {
        if(nextPlayer instanceof AIPlayer) {
            game.setGameState(game.getDuringOpponentTurnState());
        } else {
            game.setGameState(game.getBeforeAskingOrGuessingState());
        }
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

    }
}
