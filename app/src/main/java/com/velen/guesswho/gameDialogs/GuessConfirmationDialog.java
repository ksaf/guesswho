package com.velen.guesswho.gameDialogs;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.velen.guesswho.R;
import com.velen.guesswho.animations.AiFlippingAnimator;
import com.velen.guesswho.animations.AnimationEndListener;
import com.velen.guesswho.animations.TypeWriter;
import com.velen.guesswho.characters.Character;
import com.velen.guesswho.gameStates.Game;
import com.velen.guesswho.player.Player;

import static com.velen.guesswho.gameStrings.GameStringLiterals.NEXT_TURN;
import static com.velen.guesswho.gameStrings.GameStringLiterals.NO_THIS_IS_NOT_MY_CHARACTER;
import static com.velen.guesswho.gameStrings.GameStringLiterals.VICTORY;
import static com.velen.guesswho.gameStrings.GameStringLiterals.YES_THIS_IS_MY_CHARACTER;

public class GuessConfirmationDialog implements AnimationEndListener{

    private SwitchableDialogDisplayer displayer;
    private Character character;
    private Button confButton;
    private Button cancelButton;
    private Button victoryOrNextButton;
    private Player player;
    private TypeWriter tw;
    private final static int GUESS_DIALOG_XML = R.layout.guess_confirmation_dialog;
    private AppCompatActivity activity;
    private View displayerView;

    public GuessConfirmationDialog(SwitchableDialogDisplayer displayer, Context context, Player player, Character character) {
        this.displayer = displayer;
        this.character = character;
        this.player = player;
        this.activity = (AppCompatActivity) context;
    }

    public View openDialog(String text) {
        displayerView = displayer.showPopupLayout(GUESS_DIALOG_XML, false);
        displayerView.setBackgroundDrawable(Game.getInstance().getTurnManager().getCurrentPlayer().getColorBackground());
        confButton = (Button) displayerView.findViewById(R.id.confirmButton);
        cancelButton = (Button) displayerView.findViewById(R.id.cancelGuessButton);
        victoryOrNextButton = (Button) displayerView.findViewById(R.id.victoryOrNextbutton);
        victoryOrNextButton.setVisibility(View.INVISIBLE);
        setCharacterImage(displayerView);
        setConfirmationButton();
        setCancelButton();
        tw = (TypeWriter) displayerView.findViewById(R.id.confirmationText);
        tw.setTypeface(displayer.getComicSans());
        tw.setTextColor(Color.BLACK);
        tw.setCharacterDelay(50);
        tw.animateText(text);
        return displayerView;
    }

    private void setCharacterImage(View view) {
        ImageView charImage = (ImageView) view.findViewById(R.id.guessingCharacter);
        charImage.setImageDrawable(character.getDrawable());
    }

    private void setConfirmationButton() {
        confButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confButton.setVisibility(View.INVISIBLE);
                cancelButton.setVisibility(View.INVISIBLE);
                displayerView.setBackgroundDrawable(player.getColorBackground());
                tw.resetText();
                tw.setActionAfterTyping(GuessConfirmationDialog.this);
                if(isGuessCorrect()) {
                    tw.animateText(YES_THIS_IS_MY_CHARACTER);
                } else {
                    tw.animateText(NO_THIS_IS_NOT_MY_CHARACTER);
                }
            }
        });
    }

    @Override
    public void atAnimationEnd() {
        if(isGuessCorrect()) {
            victoryOrNextButton.setText(VICTORY);
            victoryOrNextButton.setVisibility(View.VISIBLE);
            victoryOrNextButton.setOnClickListener(new VictoryClickListener());
        } else {
            victoryOrNextButton.setText(NEXT_TURN);
            victoryOrNextButton.setVisibility(View.VISIBLE);
            victoryOrNextButton.setOnClickListener(new NotCorrectClickListener());
        }
    }

    private void setCancelButton() {
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayer.closeMenu();
            }
        });
    }

    private boolean isGuessCorrect() {
        return Game.getInstance().getTurnManager().getNextPlayer().getChosenCharacter().isTheSame(character);
    }

    private class VictoryClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            displayer.closeMenu();
            Game game = Game.getInstance();
            game.victory(game.getTurnManager().getCurrentPlayer());
        }
    }

    private class NotCorrectClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            displayer.closeMenu();
            AiFlippingAnimator animator = new AiFlippingAnimator(activity, player, null);
            animator.flipSingleCharacter(character);
        }
    }
}
