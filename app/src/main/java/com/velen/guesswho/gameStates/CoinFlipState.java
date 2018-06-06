package com.velen.guesswho.gameStates;


import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.velen.guesswho.R;
import com.velen.guesswho.gameDialogs.SwitchableDialogDisplayer;
import com.velen.guesswho.assetLoader.AssetLoader;
import com.velen.guesswho.playScreen.PlayScreenActivity;
import com.velen.guesswho.player.AIPlayer;
import com.velen.guesswho.player.Player;
import com.velen.guesswho.question.Question;

public class CoinFlipState implements GameState {

    private Game game;
    private PlayScreenActivity activity;

    public CoinFlipState(Game game) {
        this.game = game;
    }

    @Override
    public void initialiseState(AppCompatActivity playScreenActivity) {
        this.activity = ((PlayScreenActivity)playScreenActivity);
        final Player currentPlayer = game.getTurnManager().getCurrentPlayer();
        final float winFlip = currentPlayer.getColor().equals(Player.BLUE)? 0 : 180;
        final SwitchableDialogDisplayer dialogDisplayer = new SwitchableDialogDisplayer(playScreenActivity);
        View view = dialogDisplayer.showPopupLayout(R.layout.coin_flip_window, false);
        final TextView whoPlays = (TextView) view.findViewById(R.id.whoPlays);
        whoPlays.setText("Tap the coin!");
        final Button okButton = (Button) view.findViewById(R.id.endCoinFlipButton);
        okButton.setVisibility(View.INVISIBLE);
        final ImageView coinImage = (ImageView) view.findViewById(R.id.coinImage);
        coinImage.setImageDrawable(AssetLoader.loadDrawableFromAssets(playScreenActivity, "coin/coinup.png"));
        coinImage.setClickable(true);

        coinImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                coinImage.setClickable(false);
                RotateAnimation animation = new RotateAnimation(0, 3 * 360 + winFlip,
                        RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
                animation.setDuration(1000);
                animation.setFillAfter(true);
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {}
                    @Override
                    public void onAnimationEnd(Animation animation) {
                        activity.getPlayScreenUI().switchCoinFlipBackgroundOff(game.getTurnManager().getCurrentPlayer());
                        if(winFlip == 0) {
                            String youOrBlue = game.getTurnManager().isVsAI()? "You play " : currentPlayer.getColor()+ " plays ";
                            whoPlays.setText(youOrBlue + "first!");
                        } else {
                            whoPlays.setText(currentPlayer.getColor().toUpperCase() + " plays first!");
                        }
                        okButton.setVisibility(View.VISIBLE);
                    }
                    @Override
                    public void onAnimationRepeat(Animation animation) {}
                });
                coinImage.startAnimation(animation);
            }
        });

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                game.sayOkToTurnDisplay(currentPlayer);
                dialogDisplayer.closeMenu();
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
