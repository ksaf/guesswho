package com.velen.guesswho.playScreen;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;

import com.velen.guesswho.R;
import com.velen.guesswho.animations.AnimationFactory;
import com.velen.guesswho.gameDialogs.SwitchableDialogDisplayer;
import com.velen.guesswho.assetLoader.AssetLoader;
import com.velen.guesswho.characters.Character;
import com.velen.guesswho.characters.CharacterGroup;
import com.velen.guesswho.gameStates.Game;
import com.velen.guesswho.player.Player;
import com.velen.guesswho.viewResizer.ViewResizer;

import java.util.List;

import static com.velen.guesswho.viewResizer.ViewResizer.SELECTED_CHAR_HEIGHT;
import static com.velen.guesswho.viewResizer.ViewResizer.SELECTED_CHAR_WIDTH;
import static com.velen.guesswho.viewResizer.ViewResizer.resizeToDeviceDimensions;

public class PlayScreenUI {

    private static final int FIRST_ROW_MODEL = R.layout.single_character_top_row;
    private static final int SECOND_ROW_MODEL = R.layout.single_character_middle_row;
    private static final int THIRD_ROW_MODEL = R.layout.single_character_bottom_row;
    private AppCompatActivity playScreenActivity;
    private static GridView[] last3grids = new GridView[3];

    private SwitchableDialogDisplayer switchableDialogDisplayer;
    private Button askQuestionButton;
    private Button guessButton;
    private Button nextTurnButton;
    private View chosenCharacterLayout;

    private GridContainerManager gridContainerManager;


    protected PlayScreenUI(AppCompatActivity playScreenActivity, List<Player> players) {
        this.playScreenActivity = playScreenActivity;
        askQuestionButton = (Button) playScreenActivity.findViewById(R.id.askQuestionButton);
        guessButton = (Button) playScreenActivity.findViewById(R.id.guessButton);
        nextTurnButton = (Button) playScreenActivity.findViewById(R.id.nextTurnButton);
        switchableDialogDisplayer = new SwitchableDialogDisplayer(playScreenActivity);
        gridContainerManager = new GridContainerManager(playScreenActivity);
        chosenCharacterLayout = playScreenActivity.findViewById(R.id.chosenCharLayoutId);

        initiateNormalUI();
        displayCoinFlipBackground(players);
    }

    public void initiateNormalUI() {
        askQuestionButton.setVisibility(View.INVISIBLE);
        guessButton.setVisibility(View.INVISIBLE);
        nextTurnButton.setVisibility(View.INVISIBLE);
        chosenCharacterLayout.setVisibility(View.INVISIBLE);
    }

    public void displayCoinFlipBackground(List<Player> players) {
        View coinFlipBackground = playScreenActivity.findViewById(R.id.coinFlipBackground);
        coinFlipBackground.setVisibility(View.VISIBLE);
        coinFlipBackground.findViewById(R.id.left).setBackgroundDrawable(players.get(0).getCurrentCharacterGroup().getGroupBackGround());
        coinFlipBackground.findViewById(R.id.right).setBackgroundDrawable(players.get(1).getCurrentCharacterGroup().getGroupBackGround());
        ImageView leftLeader = (ImageView) coinFlipBackground.findViewById(R.id.leftLeader);
        ViewResizer.resizeToDeviceDimensions(playScreenActivity, leftLeader, 0.5, 0.25);
        leftLeader.setBackgroundDrawable(players.get(0).getCurrentCharacterGroup().getGroupLeader());
        ImageView rightLeader =(ImageView) coinFlipBackground.findViewById(R.id.rightLeader);
        ViewResizer.resizeToDeviceDimensions(playScreenActivity, rightLeader, 0.5, 0.25);
        rightLeader.setBackgroundDrawable(players.get(1).getCurrentCharacterGroup().getGroupLeader());
    }

    public void switchCoinFlipBackgroundOff(Player player) {
        initiateNormalUI();
        playScreenActivity.findViewById(R.id.coinFlipBackground).setVisibility(View.INVISIBLE);
        setBackgroundForPlayer(player);
    }

    public void createChosenCharacterPortrait(Character character) {
        chosenCharacterLayout.setVisibility(View.VISIBLE);
        ImageView chosenCharacterPortrait = (ImageView) playScreenActivity.findViewById(R.id.chosenCharacterInGameId);
        resizeToDeviceDimensions(playScreenActivity, chosenCharacterPortrait, SELECTED_CHAR_HEIGHT, SELECTED_CHAR_WIDTH);
        resizeToDeviceDimensions(playScreenActivity, chosenCharacterLayout, SELECTED_CHAR_HEIGHT, SELECTED_CHAR_WIDTH);
        if(character == null) {
            chosenCharacterPortrait.setImageDrawable(AssetLoader.loadDrawableFromAssets(playScreenActivity, "characterGroups/flipped.png"));
        } else {
            chosenCharacterPortrait.setImageDrawable(character.getDrawable());
        }
    }

    public void createAndSetUnclickableCharactersGrid(Player player) {
        last3grids[0] = createSingleRow(1, 3, player, null, FIRST_ROW_MODEL, R.id.topCharGridViewId, false, false);
        last3grids[1] = createSingleRow(2, 3, player, null, SECOND_ROW_MODEL, R.id.middleCharGridViewId, false, false);
        last3grids[2] = createSingleRow(3, 3, player, null, THIRD_ROW_MODEL, R.id.bottomCharGridViewId, false, false);
        gridContainerManager.createGridContainer();
    }

    public void createMiniatureGrid(Player player) {
        createSingleRow(1, 3, player, null, FIRST_ROW_MODEL, R.id.miniTopCharGridViewId, false, true);
        createSingleRow(2, 3, player, null, SECOND_ROW_MODEL, R.id.miniMiddleCharGridViewId, false, true);
        createSingleRow(3, 3, player, null, THIRD_ROW_MODEL, R.id.miniBottomCharGridViewId, false, true);
        gridContainerManager.createMiniatureGridContainer();
    }

    public void createAndSetClickableCharactersGrid(Player player, CharacterGroup clickableCharacters, boolean forGuessing) {
        createSingleRow(1, 3, player, clickableCharacters, FIRST_ROW_MODEL, R.id.topCharGridViewId, forGuessing, false);
        createSingleRow(2, 3, player, clickableCharacters, SECOND_ROW_MODEL, R.id.middleCharGridViewId, forGuessing, false);
        createSingleRow(3, 3, player, clickableCharacters, THIRD_ROW_MODEL, R.id.bottomCharGridViewId, forGuessing, false);
        gridContainerManager.createGridContainer();
    }

    public void setAskQuestionButtonClickable(boolean clickable) {
        askQuestionButton.setClickable(clickable);
    }

    public void setGuessButtonClickable(boolean clickable) {
        guessButton.setClickable(clickable);
        guessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Game.getInstance().clickGuess();
            }
        });
    }

    public void setNextTurnButtonClickable(boolean clickable) {
        nextTurnButton.setClickable(clickable);
    }

    private GridView createSingleRow(int row, int totalRows, Player player, CharacterGroup clickableCharacters, int model, int gridId, boolean forGuessing, boolean mini) {
        CharacterGroupDisplayAdapter adapter = new CharacterGroupDisplayAdapter(row, totalRows, playScreenActivity, player, clickableCharacters, model ,forGuessing , mini);
        GridView gridView = (GridView) playScreenActivity.findViewById(gridId);
        gridView.setColumnWidth(gridContainerManager.getResizedWidthForRow(row, mini));
        gridView.setAdapter(adapter);
        return gridView;
    }

    public void initialisePlayScreenButtons(CharacterGroup currentCharacterGroup) {
        askQuestionButton.setVisibility(View.VISIBLE);
        guessButton.setVisibility(View.VISIBLE);
        nextTurnButton.setVisibility(View.VISIBLE);
        askQuestionButton.setOnClickListener(new AskQuestionClickListener(playScreenActivity, switchableDialogDisplayer, currentCharacterGroup));

        nextTurnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.clearAnimation();
                Game.getInstance().passTurn();
            }
        });
    }

    public void startNextButtonFlashingAnimation() {
        final Animation animation = new AlphaAnimation(1, 0.4f); // Change alpha from fully visible to invisible
        animation.setDuration(500); // duration - half a second
        animation.setInterpolator(new LinearInterpolator()); // do not alter animation rate
        animation.setRepeatCount(Animation.INFINITE); // Repeat animation infinitely
        animation.setRepeatMode(Animation.REVERSE); // Reverse animation at the end so the button will fade back in
        nextTurnButton.startAnimation(animation);
    }

    public void slideGridsIn() {
        ConstraintLayout grids = (ConstraintLayout) playScreenActivity.findViewById(R.id.gridContainer);
        Animation animation = AnimationFactory.inFromLeftAnimation(1000, null);
        grids.startAnimation(animation);
        ConstraintLayout miniGrids = (ConstraintLayout) playScreenActivity.findViewById(R.id.miniatureGrid);
        Animation miniGridsAnimation = AnimationFactory.inFromRightAnimation(700, null);
        miniGrids.startAnimation(miniGridsAnimation);
    }

    public void slideGridsOut() {
        ConstraintLayout grids = (ConstraintLayout) playScreenActivity.findViewById(R.id.gridContainer);
        Animation animation = AnimationFactory.outToLeftAnimation(1000, null);
        grids.startAnimation(animation);
    }

    public void setBackgroundForPlayer(Player player) {
        ConstraintLayout background = (ConstraintLayout) playScreenActivity.findViewById(R.id.background);
        background.setBackgroundDrawable(player.getCurrentCharacterGroup().getGroupBackGround());
    }

    public GridView[] getLast3grids() {
        return last3grids;
    }
}
