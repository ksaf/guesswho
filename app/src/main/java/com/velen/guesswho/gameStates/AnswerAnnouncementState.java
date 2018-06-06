package com.velen.guesswho.gameStates;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.velen.guesswho.R;
import com.velen.guesswho.gameDialogs.AnswerDialog;
import com.velen.guesswho.answer.AnswerGenerator;
import com.velen.guesswho.gameDialogs.SwitchableDialogDisplayer;
import com.velen.guesswho.player.Player;
import com.velen.guesswho.question.Question;
import com.velen.guesswho.gameDialogs.QuestionDialog;

public class AnswerAnnouncementState implements GameState {

    private Game game;
    private AppCompatActivity playScreenActivity;

    public AnswerAnnouncementState(Game game) {
        this.game = game;
    }

    @Override
    public void initialiseState(AppCompatActivity playScreenActivity) {
        this.playScreenActivity = playScreenActivity;
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
    public void finishAnswerAnimation(final Question question) {
        final SwitchableDialogDisplayer displayer = new SwitchableDialogDisplayer(playScreenActivity);

        QuestionDialog questionDialog = new QuestionDialog(displayer, game.getTurnManager().getCurrentPlayer());
        View view = questionDialog.openDialog(question.getQuestionToDisplay());
        Button answerButton = (Button) view.findViewById(R.id.okAnswerButton);
        answerButton.setText("Answer");
        answerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnswerDialog answerDialog = new AnswerDialog(displayer, game.getTurnManager().getNextPlayer());
                AnswerGenerator generator = new AnswerGenerator();
                View view = answerDialog.openDialog(generator.generateLongAnswer(question, game.getTurnManager().getNextPlayer().getChosenCharacter()));
                Button okButton = (Button) view.findViewById(R.id.okAnswerButton);
                okButton.setText("Flip!");
                okButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        displayer.closeMenu();
                        game.setGameState(game.getBeforeFlippingCharactersState());
                        game.finishAnswerAnimation(question);
                    }
                });
            }
        });


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
