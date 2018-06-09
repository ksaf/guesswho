package com.velen.guesswho.playScreen;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.velen.guesswho.R;
import com.velen.guesswho.gameDialogs.SwitchableDialogDisplayer;
import com.velen.guesswho.askQuestionMenu.TypeButtonHandlerClickListener;
import com.velen.guesswho.assetLoader.AssetLoader;
import com.velen.guesswho.characters.CharacterGroup;

import java.util.ArrayList;
import java.util.List;

import static com.velen.guesswho.gameStrings.GameStringLiterals.NAME;
import static com.velen.guesswho.gameStrings.GameStringLiterals.PNG_EXTENSION;
import static com.velen.guesswho.gameStrings.GameStringLiterals.QUESTION_ICONS_PATH;

public class AskQuestionClickListener implements View.OnClickListener{

    private static final int ASK_QUESTION_TYPE_FRAGMENT_XML = R.layout.ask_question_type_fragment;
    private AlphaAnimation buttonClickAnimation = new AlphaAnimation(1F, 0.8F);
    private SwitchableDialogDisplayer switchableDialogDisplayer;
    private LayoutInflater li;
    private View view;
    private CharacterGroup characterGroup;
    private AppCompatActivity activity;

    public AskQuestionClickListener(AppCompatActivity activity, SwitchableDialogDisplayer switchableDialogDisplayer, CharacterGroup characterGroup) {
        buttonClickAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}
            @Override
            public void onAnimationEnd(Animation animation) {
                openMenu();
            }
            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
        this.activity = activity;
        this.characterGroup = characterGroup;
        this.switchableDialogDisplayer = switchableDialogDisplayer;
        li=(LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = li.inflate(ASK_QUESTION_TYPE_FRAGMENT_XML, null);
    }


    @Override
    public void onClick(View v) {
        v.startAnimation(buttonClickAnimation);
    }
    /**
     * Set the click listeners for all the buttons on the question type part of the menu, and show the menu.
     */
    public void openMenu() {
        view = li.inflate(ASK_QUESTION_TYPE_FRAGMENT_XML, null);
        addMenuItems();
        switchableDialogDisplayer.showPopupView(view, true);
    }

    private void addMenuItems() {
        LinearLayout layoutScroll = (LinearLayout) view.findViewById(R.id.questionTypeScrollView);
        List<Button> buttonlist = new ArrayList<>();
        //TextView questionTV = (TextView) view.findViewById(R.id.questionTypeHeader);

        for(String type : characterGroup.getAllAvailableFeaturesForGroup().getAllAvailableFeatureTypes()) {

            if(type.equals(NAME) || characterGroup.getAllAvailableFeaturesForGroup().getAllAvailableFeaturesIfMoreThanOneFor(type).size() < 1){continue;}

            View eachItemView = activity.getLayoutInflater().inflate(R.layout.ask_question_menu_each_item,null);

            ImageView img = (ImageView) eachItemView.findViewById(R.id.ask_question_menu_item_image);
            img.setImageDrawable(AssetLoader.loadDrawableFromAssets(activity, QUESTION_ICONS_PATH + (type + PNG_EXTENSION).toLowerCase()));

            Button btn = (Button) eachItemView.findViewById(R.id.ask_question_menu_item_text);
            btn.setText(type);
            btn.setOnClickListener(new TypeButtonHandlerClickListener(activity, switchableDialogDisplayer, characterGroup, type));
            buttonlist.add(btn);

            layoutScroll.addView(eachItemView);
        }

    }
}
