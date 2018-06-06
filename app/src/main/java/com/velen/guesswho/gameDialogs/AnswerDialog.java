package com.velen.guesswho.gameDialogs;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import com.velen.guesswho.R;
import com.velen.guesswho.animations.TypeWriter;
import com.velen.guesswho.player.Player;

public class AnswerDialog {

    private SwitchableDialogDisplayer displayer;
    private Drawable groupLeader;
    private Player playerWhoAnswers;
    private final static int OPPONENT_ANSWER_XML = R.layout.opponent_answer_dialog;

    public AnswerDialog(SwitchableDialogDisplayer displayer, Player playerWhoAnswers) {
        this.displayer = displayer;
        this.playerWhoAnswers = playerWhoAnswers;
        this.groupLeader = playerWhoAnswers.getCurrentCharacterGroup().getGroupLeader();
    }

    public View openDialog(String characterConfirmationText) {
        View view = displayer.showPopupLayout(OPPONENT_ANSWER_XML, false);
        view.setBackgroundDrawable(playerWhoAnswers.getColorBackground());
        ImageView leaderImage = (ImageView)view.findViewById(R.id.leader);
        leaderImage.setImageDrawable(groupLeader);
        TypeWriter tw = (TypeWriter) view.findViewById(R.id.answerText);
        tw.setTypeface(displayer.getComicSans());
        tw.setTextColor(Color.BLACK);
        tw.setCharacterDelay(50);
        tw.animateText(characterConfirmationText);
        return view;
    }

}
