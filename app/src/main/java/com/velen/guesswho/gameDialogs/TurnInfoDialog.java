package com.velen.guesswho.gameDialogs;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import com.velen.guesswho.R;
import com.velen.guesswho.animations.TypeWriter;
import com.velen.guesswho.player.Player;

public class TurnInfoDialog {

    private SwitchableDialogDisplayer displayer;
    private Drawable leader;
    private Player playerWhoseTurnIs;
    private final static int TURN_DISPLAY_XML = R.layout.turn_display_dialog;

    public TurnInfoDialog(SwitchableDialogDisplayer displayer, Player playerWhoseTurnIs) {
        this.displayer = displayer;
        this.playerWhoseTurnIs = playerWhoseTurnIs;
        this.leader = playerWhoseTurnIs.getCurrentCharacterGroup().getGroupLeader();
    }

    public View openDialog(String textToDisplay) {
        View view = displayer.showPopupLayout(TURN_DISPLAY_XML, false);
        view.setBackgroundDrawable(playerWhoseTurnIs.getColorBackground());
        ImageView leaderImage = (ImageView) view.findViewById(R.id.leader);
        leaderImage.setImageDrawable(leader);
        TypeWriter tw = (TypeWriter) view.findViewById(R.id.turnInfoText);
        tw.setTypeface(displayer.getComicSans());
        tw.setTextColor(Color.BLACK);
        tw.setCharacterDelay(50);
        tw.animateText(textToDisplay);
        return view;
    }

}
