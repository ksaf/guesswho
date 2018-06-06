package com.velen.guesswho.animations;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ViewAnimator;

import com.velen.guesswho.R;
import com.velen.guesswho.characters.Character;
import com.velen.guesswho.characters.CharacterGroup;
import com.velen.guesswho.gameStates.Game;
import com.velen.guesswho.playScreen.PlayScreenActivity;
import com.velen.guesswho.playScreen.PlayScreenUI;
import com.velen.guesswho.player.Player;

public class AiFlippingAnimator implements Animation.AnimationListener{

    private static final long FLIP_DURATION = 300;
    private static final int DELAY_AFTER_FLIPPING = 1000;

    private Player player;
    private GridView[] gridViews;
    private int gridViewCounter = 0;
    private int childCounter = 0;
    private CharacterGroup groupToClose;
    boolean animationEnd = false;
    int numberOfCharactersToCheck = 0;
    int charactersChecked = 0;
    private Game game;

    public AiFlippingAnimator(AppCompatActivity activity, Player player, CharacterGroup groupToClose) {
        this.game = Game.getInstance();
        this.groupToClose = groupToClose;
        this.player = player;
        PlayScreenActivity playScreenActivity = (PlayScreenActivity) activity;
        PlayScreenUI ui = playScreenActivity.getPlayScreenUI();
        gridViews = ui.getLast3grids();
        for(GridView g : gridViews) {
            numberOfCharactersToCheck += g.getChildCount();
        }
    }

    public void startFlipping() {
        onAnimationEnd(null);
    }

    public void flipSingleCharacter(Character character) {
        animate(character);
    }

    @Override
    public void onAnimationStart(Animation animation) {}

    @Override
    public void onAnimationEnd(Animation animation) {
        charactersChecked++;
        if(animationEnd || charactersChecked > numberOfCharactersToCheck) {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    game.getTurnManager().startNextTurn();
                    game.finishAIFlippingCharactersAnimation();
                }
            }, DELAY_AFTER_FLIPPING);
            resetCounters();
            return;
        }
        GridView grid = gridViews[gridViewCounter];
        int i = childCounter;
        Character characterToRemove = (Character) grid.getAdapter().getItem(childCounter);
        if(!groupToClose.isInGroup(characterToRemove)) {
            nextCharacter(grid);
            onAnimationEnd(null);
            return;
        }
        ViewAnimator animator = flipCharacter(grid, i);
        if(!nextCharacter(grid)) {
            animator = flipCharacter(grid, childCounter);
            animationEnd = true;
            AnimationFactory.flipTransition(animator, AnimationFactory.FlipDirection.LEFT_RIGHT, FLIP_DURATION, getDelayAfterHandler(getFinishMultipleFlippingRunnable()));
            resetCounters();
            return;
        }
        AnimationFactory.flipTransition(animator, AnimationFactory.FlipDirection.LEFT_RIGHT, FLIP_DURATION, this);
    }

    private void animate(Character character) {
        resetCounters();
        for(GridView grid : gridViews) {
            for(int childNumber = 0; childNumber < grid.getAdapter().getCount(); childNumber++) {
                if(grid.getAdapter().getItem(childNumber) == null) {
                    continue;
                }
                if( ((Character) grid.getAdapter().getItem(childNumber)).isTheSame(character) ) {
                    ViewAnimator animator = flipCharacter(grid, childNumber);
                    AnimationFactory.flipTransition(animator, AnimationFactory.FlipDirection.LEFT_RIGHT, FLIP_DURATION, getDelayAfterHandler(getFinishSingleFlippingRunnable()));
                }
            }
        }
    }

    private void resetCounters() {
        charactersChecked = 0;
        childCounter = 0;
    }

    private boolean nextCharacter(GridView grid) {
        boolean notLast = false;
        if(childCounter < grid.getChildCount()-1) {
            childCounter++;
            notLast = true;
        } else if(gridViewCounter < gridViews.length-1) {
            childCounter = 0;
            gridViewCounter++;
            notLast = true;
        }
        return notLast;
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    private ViewAnimator flipCharacter(GridView grid, int childNumber) {
        Character characterToRemove = (Character) grid.getAdapter().getItem(childNumber);
        player.getCurrentCharacterGroup().removeCharacter(characterToRemove);
        characterToRemove.switchDrawableToFlipped();
        ViewGroup v = (ViewGroup)grid.getChildAt(childNumber);
        ImageView flippedCharImageIV = (ImageView) v.findViewById(R.id.flippedImageId);
        flippedCharImageIV.setImageDrawable(characterToRemove.getDrawable());
        return (ViewAnimator) v.findViewById(R.id.flippingAnimator);
    }

    private Animation.AnimationListener getDelayAfterHandler(final Runnable r) {
        return new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}
            @Override
            public void onAnimationEnd(Animation animation) {
                Handler handler = new Handler();
                handler.postDelayed(r, DELAY_AFTER_FLIPPING);
            }
            @Override
            public void onAnimationRepeat(Animation animation) {}
        };
    }

    private Runnable getFinishMultipleFlippingRunnable() {
        return new Runnable() {
            @Override
            public void run() {
                game.getTurnManager().startNextTurn();
                game.finishAIFlippingCharactersAnimation();
            }
        };
    }

    private Runnable getFinishSingleFlippingRunnable() {
        return new Runnable() {
            @Override
            public void run() {
                game.passTurn();
            }
        };
    }
}
