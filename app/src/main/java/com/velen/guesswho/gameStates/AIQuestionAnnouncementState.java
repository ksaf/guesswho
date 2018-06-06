package com.velen.guesswho.gameStates;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.velen.guesswho.R;
import com.velen.guesswho.answer.AnswerGenerator;
import com.velen.guesswho.gameDialogs.SwitchableDialogDisplayer;
import com.velen.guesswho.gameDialogs.OpponentQuestionDialog;
import com.velen.guesswho.player.Player;
import com.velen.guesswho.question.AIQuestionGenerator;
import com.velen.guesswho.question.Question;

public class AIQuestionAnnouncementState implements GameState {

    private Game game;

    public AIQuestionAnnouncementState(Game game) {
        this.game = game;
    }

    @Override
    public void initialiseState(AppCompatActivity playScreenActivity) {
        final SwitchableDialogDisplayer displayer = new SwitchableDialogDisplayer(playScreenActivity);
        OpponentQuestionDialog dialog = new OpponentQuestionDialog(displayer, game.getTurnManager().getCurrentPlayer());
        AIQuestionGenerator questionGenerator = new AIQuestionGenerator();
        Player nextPlayer = game.getTurnManager().getNextPlayer();
        Player currentPlayer = game.getTurnManager().getCurrentPlayer();
        final Question aiQuestion = questionGenerator.generateQuestion(currentPlayer.getCurrentCharacterGroup(), 100);
        View view = dialog.openDialog(aiQuestion);
        AnswerGenerator answerGenerator = new AnswerGenerator();
        Button answerToAIButton = (Button) view.findViewById(R.id.answerToAiButton);
        answerToAIButton.setText(answerGenerator.generateYesOrNoAnswer(aiQuestion, nextPlayer.getChosenCharacter()));
        answerToAIButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayer.closeMenu();
                answerToAIQuestionAnimation(aiQuestion);
            }
        });
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
        game.setGameState(game.getAiFlippingCharactersAnimationState());
        game.answerToAIQuestionAnimation(question);
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
