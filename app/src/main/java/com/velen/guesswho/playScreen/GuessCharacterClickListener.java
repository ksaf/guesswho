package com.velen.guesswho.playScreen;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.velen.guesswho.R;
import com.velen.guesswho.animations.AnimatedViewHandler;
import com.velen.guesswho.animations.ViewColorAnimator;
import com.velen.guesswho.gameDialogs.SwitchableDialogDisplayer;
import com.velen.guesswho.characters.Character;
import com.velen.guesswho.gameDialogs.GuessConfirmationDialog;
import com.velen.guesswho.player.Player;

import static com.velen.guesswho.gameStrings.GameStringLiterals.DO_YOU_WANT_TO_GUESS_THIS_CHARACTER;

public class GuessCharacterClickListener implements View.OnClickListener, AnimatedViewHandler{

    private View convertView;
    private Context context;
    private Player player;
    private Character character;

    public GuessCharacterClickListener(View convertView , Player player, Character character, Context context) {
        this.convertView = convertView;
        this.context = context;
        this.player = player;
        this.character = character;
    }


    @Override
    public void onClick(View v) {
        ViewColorAnimator.animateImageColor(this, (ImageView) convertView.findViewById(R.id.charImageID));
    }

    @Override
    public void onAnimationEnd() {
        final SwitchableDialogDisplayer displayer = new SwitchableDialogDisplayer(context);
        GuessConfirmationDialog dialog = new GuessConfirmationDialog(displayer, context, player, character);
        dialog.openDialog(DO_YOU_WANT_TO_GUESS_THIS_CHARACTER);
    }
}
