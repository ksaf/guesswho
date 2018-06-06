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

public class AIGuessConfirmationDialog implements AnimationEndListener {

    private SwitchableDialogDisplayer displayer;
    private Character character;
    private Button multiButton;
    private Player player;
    private TypeWriter tw;
    private final static int OPPONENT_GUESS_DIALOG_XML = R.layout.opponent_guess_dialog;
    private AppCompatActivity activity;

    public AIGuessConfirmationDialog(SwitchableDialogDisplayer displayer, Context context, Player player, Character character) {
        this.displayer = displayer;
        this.character = character;
        this.player = player;
        this.activity = (AppCompatActivity) context;
    }

    public View openDialog(String text) {
        View view = displayer.showPopupLayout(OPPONENT_GUESS_DIALOG_XML, false);
        view.setBackgroundDrawable(player.getColorBackground());
        multiButton = (Button) view.findViewById(R.id.victoryOrNextbutton);
        multiButton.setVisibility(View.INVISIBLE);
        setCharacterImage(view);

        tw = (TypeWriter) view.findViewById(R.id.confirmationText);
        tw.setTypeface(displayer.getComicSans());
        tw.setTextColor(Color.BLACK);
        tw.setCharacterDelay(50);
        tw.setActionAfterTyping(AIGuessConfirmationDialog.this);
        tw.animateText(text);
        return view;
    }

    private boolean isGuessCorrect() {
        return player.getChosenCharacter().isTheSame(character);
    }

    private void setCharacterImage(View view) {
        ImageView charImage = (ImageView) view.findViewById(R.id.guessingCharacter);
        charImage.setImageDrawable(character.getDrawable());
    }


    @Override
    public void atAnimationEnd() {
        if(isGuessCorrect()) {
            multiButton.setText("Yes");
            multiButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    displayer.closeMenu();
                    Game game = Game.getInstance();
                    game.victory(game.getTurnManager().getCurrentPlayer());
                }
            });
        } else {
            multiButton.setText("No");
            multiButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    displayer.closeMenu();
                    AiFlippingAnimator animator = new AiFlippingAnimator(activity, player, null);
                    animator.flipSingleCharacter(character);
                }
            });
        }
        multiButton.setVisibility(View.VISIBLE);
    }
}
