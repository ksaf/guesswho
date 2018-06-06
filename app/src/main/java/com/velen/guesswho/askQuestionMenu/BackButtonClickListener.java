package com.velen.guesswho.askQuestionMenu;

import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.velen.guesswho.R;
import com.velen.guesswho.animations.AnimatedViewHandler;
import com.velen.guesswho.animations.ViewColorAnimator;
import com.velen.guesswho.characters.CharacterGroup;
import com.velen.guesswho.gameDialogs.SwitchableDialogDisplayer;
import com.velen.guesswho.playScreen.AskQuestionClickListener;

public class BackButtonClickListener extends AskQuestionClickListener implements AnimatedViewHandler{

    public BackButtonClickListener(AppCompatActivity activity, SwitchableDialogDisplayer switchableDialogDisplayer, CharacterGroup characterGroup) {
        super(activity, switchableDialogDisplayer, characterGroup);
    }

    @Override
    public void onClick(View v) {
        ViewColorAnimator.animate(this, v, R.drawable.question_menu_but, R.drawable.question_menu_but_pressed, 150);
    }


    @Override
    public void onAnimationEnd() {
        openMenu();
    }
}
