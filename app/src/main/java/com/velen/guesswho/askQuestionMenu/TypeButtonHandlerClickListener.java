package com.velen.guesswho.askQuestionMenu;


import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.velen.guesswho.R;
import com.velen.guesswho.animations.AnimatedViewHandler;
import com.velen.guesswho.animations.ViewColorAnimator;
import com.velen.guesswho.assetLoader.AssetLoader;
import com.velen.guesswho.characters.CharacterGroup;
import com.velen.guesswho.gameDialogs.SwitchableDialogDisplayer;
import com.velen.guesswho.question.QuestionBuilder;
import java.util.ArrayList;
import java.util.List;

import static com.velen.guesswho.gameStrings.GameStringLiterals.PNG_EXTENSION;
import static com.velen.guesswho.gameStrings.GameStringLiterals.QUESTION_GAP;
import static com.velen.guesswho.gameStrings.GameStringLiterals.QUESTION_ICONS_PATH;

public class TypeButtonHandlerClickListener implements View.OnClickListener, AnimatedViewHandler {

    private static final int ASK_QUESTION_CHOICE_FRAGMENT_XML = R.layout.ask_question_choice_fragment;
    private SwitchableDialogDisplayer switchableDialogDisplayer;
    private View view;
    private AppCompatActivity activity;
    private CharacterGroup characterGroup;
    private LayoutInflater li;
    private String type;


    public TypeButtonHandlerClickListener(AppCompatActivity activity, SwitchableDialogDisplayer switchableDialogDisplayer, CharacterGroup characterGroup, String type) {
        this.activity = activity;
        this.characterGroup = characterGroup;
        this.switchableDialogDisplayer = switchableDialogDisplayer;
        this.type = type;

        li = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = li.inflate(ASK_QUESTION_CHOICE_FRAGMENT_XML, null);
    }

    @Override
    public void onClick(View v) {
        ViewColorAnimator.animate(this, v, R.drawable.question_menu_but, R.drawable.question_menu_but_pressed, 150);
    }

    public void onAnimationEnd() {
        openMenu();
    }

    private void openMenu() {
        view = li.inflate(ASK_QUESTION_CHOICE_FRAGMENT_XML, null);
        addMenuItems();
        switchableDialogDisplayer.showPopupView(view, true);
    }

    private void addMenuItems() {
        LinearLayout layoutScroll = (LinearLayout) view.findViewById(R.id.questionChoiceScrollView);
        List<Button> buttonlist = new ArrayList<>();

        TextView questionTV = (TextView) view.findViewById(R.id.questionChoiceHeader);
        String questionToDisplay = QuestionBuilder.getInstance().getQuestionFirstPart(type) + QUESTION_GAP;
        questionTV.setText(questionToDisplay);

        for(String choice : characterGroup.getAllAvailableFeaturesForGroup().getAllAvailableFeaturesIfMoreThanOneFor(type)) {
            View eachItemView = activity.getLayoutInflater().inflate(R.layout.ask_question_menu_each_item,null);

            ImageView img = (ImageView) eachItemView.findViewById(R.id.ask_question_menu_item_image);
            img.setImageDrawable(AssetLoader.loadDrawableFromAssets(activity, QUESTION_ICONS_PATH + (type + PNG_EXTENSION).toLowerCase()));

            Button btn = (Button) eachItemView.findViewById(R.id.ask_question_menu_item_text);
            btn.setText(choice);
            btn.setOnClickListener(new ChoiceButtonClickListener(activity, switchableDialogDisplayer, type, choice));
            buttonlist.add(btn);

            layoutScroll.addView(eachItemView);
        }

        Button backButton = (Button) view.findViewById(R.id.backButton);
        backButton.setOnClickListener(new BackButtonClickListener(activity, switchableDialogDisplayer, characterGroup));
    }
}
