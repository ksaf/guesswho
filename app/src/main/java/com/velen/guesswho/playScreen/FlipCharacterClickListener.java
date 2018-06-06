package com.velen.guesswho.playScreen;

import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.ViewAnimator;

import com.velen.guesswho.R;
import com.velen.guesswho.animations.AnimationFactory;
import com.velen.guesswho.characters.Character;
import com.velen.guesswho.characters.CharacterGroup;
import com.velen.guesswho.gameStates.Game;
import com.velen.guesswho.player.Player;

public class FlipCharacterClickListener implements View.OnClickListener, Animation.AnimationListener {

    private static final int FLIP_DURATION = 400;
    private View convertView;
    private Player player;
    private Character character;
    private CharacterGroup clickableCharacters;

    public FlipCharacterClickListener(View convertView , Player player, Character character, CharacterGroup clickableCharacters) {
        this.convertView = convertView;
        this.player = player;
        this.character = character;
        this.clickableCharacters = clickableCharacters;
    }

    @Override
    public void onClick(View v) {
        player.getCurrentCharacterGroup().removeCharacter(character);
        convertView.setClickable(false);
        character.switchDrawableToFlipped();

        ImageView flippedCharImageIV = (ImageView) convertView.findViewById(R.id.flippedImageId);
        flippedCharImageIV.setImageDrawable(character.getDrawable());
        ViewAnimator animator = (ViewAnimator) convertView.findViewById(R.id.flippingAnimator);
        AnimationFactory.flipTransition(animator, AnimationFactory.FlipDirection.LEFT_RIGHT, FLIP_DURATION, this);
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        boolean atLeastOneStillHasToBeRemoved = false;
        for(Character c : player.getCurrentCharacterGroup().getCharacters()) {
            if(clickableCharacters.isInGroup(c)) {
                atLeastOneStillHasToBeRemoved = true;
            }
        }
        if(!atLeastOneStillHasToBeRemoved) {
            Game.getInstance().flipCharacters();
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
