package com.velen.guesswho.askQuestionMenu;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;

import com.velen.guesswho.R;
import com.velen.guesswho.animations.AnimatedViewHandler;
import com.velen.guesswho.animations.ViewColorAnimator;
import com.velen.guesswho.gameDialogs.SwitchableDialogDisplayer;
import com.velen.guesswho.gameStates.Game;
import com.velen.guesswho.question.QuestionBuilder;

public class ChoiceButtonClickListener implements OnClickListener, AnimatedViewHandler{

    private SwitchableDialogDisplayer switchableDialogDisplayer;
    private AppCompatActivity activity;
    private String type;
    private String choice;

    public ChoiceButtonClickListener(AppCompatActivity activity, SwitchableDialogDisplayer switchableDialogDisplayer, String type, String choice) {
        this.activity = activity;
        this.switchableDialogDisplayer = switchableDialogDisplayer;
        this.type = type;
        this.choice = choice;
    }

    @Override
    public void onClick(View v) {
        ViewColorAnimator.animate(this, v, R.drawable.question_menu_but, R.drawable.question_menu_but_pressed, 150);
    }

    private void closeMenu() {
        switchableDialogDisplayer.closeMenu();
        Game.getInstance().askedQuestion(QuestionBuilder.getInstance().getQuestion(type, choice));
    }

    @Override
    public void onAnimationEnd() {
        closeMenu();
    }
}
