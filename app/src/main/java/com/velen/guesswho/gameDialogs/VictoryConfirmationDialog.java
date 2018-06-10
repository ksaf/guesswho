package com.velen.guesswho.gameDialogs;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.velen.guesswho.R;
import com.velen.guesswho.gameStates.Game;
import com.velen.guesswho.gameStrings.GameStringLiterals;
import com.velen.guesswho.player.AIPlayer;
import com.velen.guesswho.player.Player;
import com.velen.guesswho.startup.StartupActivity;

public class VictoryConfirmationDialog {

    private final static int VICTORY_DIALOG_XML = R.layout.victory_display_dialog;
    private SwitchableDialogDisplayer displayer;
    private AppCompatActivity activity;
    private Player bluePlayer;
    private Player redPlayer;
    private Game game;
    private Player playerWhoWon;

    public VictoryConfirmationDialog(SwitchableDialogDisplayer displayer, Context context, Game game, Player playerWhoWon) {
        this.game = game;
        this.displayer = displayer;
        this.activity = (AppCompatActivity) context;
        this.playerWhoWon = playerWhoWon;
        if(game.getTurnManager().getCurrentPlayer().getColor().toLowerCase().equals(Player.BLUE)) {
            bluePlayer = game.getTurnManager().getCurrentPlayer();
            redPlayer = game.getTurnManager().getNextPlayer();
        } else {
            redPlayer = game.getTurnManager().getCurrentPlayer();
            bluePlayer = game.getTurnManager().getNextPlayer();
        }
    }

    public void openDialog() {
        View view = displayer.showPopupLayout(VICTORY_DIALOG_XML, false);
        TextView whoWonTxt = (TextView) view.findViewById(R.id.whoWonText);
        whoWonTxt.setTypeface(displayer.getComicSans());
        whoWonTxt.setTextColor(Color.BLACK);
        ImageView blueImg = (ImageView) view.findViewById(R.id.blueCharImg);
        TextView blueCharacterTxt = (TextView) view.findViewById(R.id.blueCharacterTxt);
        blueCharacterTxt.setTypeface(displayer.getComicSans());
        blueCharacterTxt.setTextColor(Color.BLACK);
        ImageView redImg = (ImageView) view.findViewById(R.id.redCharImg);
        TextView redCharacterTxt = (TextView) view.findViewById(R.id.redCharacterTxt);
        redCharacterTxt.setTypeface(displayer.getComicSans());
        redCharacterTxt.setTextColor(Color.BLACK);
        Button playAgainBtn = (Button) view.findViewById(R.id.playAgainBtn);
        Button exitBtn = (Button) view.findViewById(R.id.mainMenuBtn);


        blueCharacterTxt.setText(bluePlayer.getColor().toUpperCase() + GameStringLiterals.PLAYERS_CHARACTER);
        if(playerWhoWon instanceof AIPlayer) {
            whoWonTxt.setText(GameStringLiterals.YOU_LOST);
            blueCharacterTxt.setText(GameStringLiterals.YOUR_CHARACTER);
        } else if(isVsAi()) {
            whoWonTxt.setText(GameStringLiterals.YOU_WON);
        }
        whoWonTxt.setText(playerWhoWon.getColor().toUpperCase() + GameStringLiterals.PLAYER_WON);
        redCharacterTxt.setText(redPlayer.getColor().toUpperCase() + GameStringLiterals.PLAYERS_CHARACTER);
        blueImg.setImageDrawable(bluePlayer.getChosenCharacter().getDrawable());
        redImg.setImageDrawable(redPlayer.getChosenCharacter().getDrawable());
        playAgainBtn.setText(GameStringLiterals.PLAY_AGAIN);
        playAgainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayer.closeMenu();
                game.flipCoin();
            }
        });
        exitBtn.setText(GameStringLiterals.EXIT_GAME);
        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayer.closeMenu();
                Intent intent = new Intent(activity, StartupActivity.class);
                activity.startActivity(intent);
            }
        });
    }

    private boolean isVsAi() {
        return game.getTurnManager().isVsAI();
    }

}
