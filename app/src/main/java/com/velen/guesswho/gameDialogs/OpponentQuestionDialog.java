package com.velen.guesswho.gameDialogs;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.velen.guesswho.R;
import com.velen.guesswho.animations.AnimationEndListener;
import com.velen.guesswho.animations.TypeWriter;
import com.velen.guesswho.player.Player;
import com.velen.guesswho.question.Question;
import com.velen.guesswho.question.QuestionBuilder;

public class OpponentQuestionDialog {

    private View view;
    private Drawable leader;
    private SwitchableDialogDisplayer displayer;
    private Player playerWhoAsks;
    private final static int OPPONENT_QUESTION_XML = R.layout.opponent_question_dialog;

    public OpponentQuestionDialog(SwitchableDialogDisplayer displayer, Player playerWhoAsks) {
        this.displayer = displayer;
        this.playerWhoAsks = playerWhoAsks;
        this.leader = playerWhoAsks.getCurrentCharacterGroup().getGroupLeader();
    }

    public View openDialog(Question question) {
        View view = displayer.showPopupLayout(OPPONENT_QUESTION_XML, false);
        view.setBackgroundDrawable(playerWhoAsks.getColorBackground());
        ImageView leaderImage = (ImageView) view.findViewById(R.id.leader);
        leaderImage.setImageDrawable(leader);
        TypeWriter tw = (TypeWriter) view.findViewById(R.id.aiQuestionText);
        tw.setTypeface(displayer.getComicSans());
        tw.setTextColor(Color.BLACK);
        Button answerToAIButton = (Button) view.findViewById(R.id.answerToAiButton);
        answerToAIButton.setVisibility(View.INVISIBLE);
        setActionAfterAnimation(tw, answerToAIButton);
        String part1 = QuestionBuilder.getInstance().getQuestionFirstPart(question.getQuestionTopic());
        String part2 = " ... ";
        String part3 = question.getSpecification() + "?";
        tw.animateThreePartText(500, part1, part2, part3, 50, 100, 50);
        return view;
    }

    private void setActionAfterAnimation(TypeWriter tw, final Button answerToAIButton) {

        AnimationEndListener action = new AnimationEndListener() {
            @Override
            public void atAnimationEnd() {
                answerToAIButton.setVisibility(View.VISIBLE);
            }
        };
        tw.setActionAfterTyping(action);
    }
}
