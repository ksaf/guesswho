package com.velen.guesswho.playScreen;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewAnimator;

import com.velen.guesswho.R;
import com.velen.guesswho.characters.Character;
import com.velen.guesswho.characters.CharacterGroup;
import com.velen.guesswho.player.Player;

import java.util.ArrayList;
import java.util.List;

import static com.velen.guesswho.viewResizer.ViewResizer.BOT_CHAR_HEIGHT;
import static com.velen.guesswho.viewResizer.ViewResizer.BOT_CHAR_WIDTH;
import static com.velen.guesswho.viewResizer.ViewResizer.BOT_MINIATURE_CHAR_HEIGHT;
import static com.velen.guesswho.viewResizer.ViewResizer.BOT_MINIATURE_CHAR_WIDTH;
import static com.velen.guesswho.viewResizer.ViewResizer.MID_CHAR_HEIGHT;
import static com.velen.guesswho.viewResizer.ViewResizer.MID_CHAR_WIDTH;
import static com.velen.guesswho.viewResizer.ViewResizer.MID_MINIATURE_CHAR_HEIGHT;
import static com.velen.guesswho.viewResizer.ViewResizer.MID_MINIATURE_CHAR_WIDTH;
import static com.velen.guesswho.viewResizer.ViewResizer.TOP_CHAR_HEIGHT;
import static com.velen.guesswho.viewResizer.ViewResizer.TOP_CHAR_WIDTH;
import static com.velen.guesswho.viewResizer.ViewResizer.TOP_MINIATURE_CHAR_HEIGHT;
import static com.velen.guesswho.viewResizer.ViewResizer.TOP_MINIATURE_CHAR_WIDTH;
import static com.velen.guesswho.viewResizer.ViewResizer.resizeToDeviceDimensions;

public class CharacterGroupDisplayAdapter extends BaseAdapter{

    private final int singleCharModel;
    private Context context;
    private CharacterGroup allCharacters;
    private CharacterGroup rowCharacters;
    private CharacterGroup clickableCharacters;
    private List<Character> characters;
    private Player player;
    private List<View> allViews = new ArrayList<>();
    private boolean forGuessing;
    private final int row;
    private final boolean mini;

    /**
     * Creates an adapter to be used to populate a list or a grid view.
     * @param context The application's context.
     * @param player The player whose character group to be used to populate the view.
     * @param clickableCharacters The characters to be set to clickable.
     */
    public CharacterGroupDisplayAdapter(int row, int totalRows, Context context, Player player, CharacterGroup clickableCharacters, int model , boolean forGuessing, boolean mini) {
        this.context = context;
        this.player = player;
        this.row = row;
        this.mini = mini;
        this.allCharacters = player.getOriginalCharacterGroup();
        rowCharacters = allCharacters.getSplitGroup(row, totalRows);
        this.clickableCharacters = clickableCharacters;
        characters = this.rowCharacters.getCharacters();
        this.singleCharModel = model;
        this.forGuessing = forGuessing;
    }

    /* Override this method to select how many characters the GridView will display*/
    @Override
    public int getCount() {
        return rowCharacters.getGroupSize();
    }

    @Override
    public Object getItem(int position) {
        return characters.size() > position ? characters.get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        allViews.add(convertView);

        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
            convertView = inflater.inflate(singleCharModel,parent, false);
            viewHolder = new ViewHolder();
            //viewHolder.charNameTV = (TextView) convertView.findViewById(R.id.nameTvID);
            viewHolder.charImageIV = (ImageView) convertView.findViewById(R.id.charImageID);
            viewHolder.flippedImageIV = (ImageView) convertView.findViewById(R.id.flippedImageId);

            viewHolder.viewAnimator = (ViewAnimator) convertView.findViewById(R.id.flippingAnimator);
            resize(viewHolder.charImageIV);
            resize(viewHolder.flippedImageIV);
            resize(viewHolder.viewAnimator);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.charImageIV.setImageDrawable(characters.get(position).getDrawable());

        if(clickableCharacters != null) {
            if(clickableCharacters.isInGroup(characters.get(position))) {
                setClickableCharacter(characters.get(position), convertView);
            }
        }

        if(forGuessing) {
            if(clickableCharacters.isInGroup(characters.get(position))) {
                setGuessClickableCharacters(characters.get(position), convertView);
            }
        }

        return convertView;
    }

    static class ViewHolder {
        private TextView charNameTV;
        private ImageView charImageIV;
        private ImageView flippedImageIV;
        private ViewAnimator viewAnimator;
    }

    private void setClickableCharacter(Character character, View convertView) {
        convertView.setClickable(true);
        convertView.setOnClickListener(new FlipCharacterClickListener(convertView, player, character, clickableCharacters));
    }

    private void setGuessClickableCharacters(Character character, View convertView) {
        convertView.setClickable(true);
        convertView.setOnClickListener(new GuessCharacterClickListener(convertView, player, character, context));
    }

    private void resize(View view) {
        if(view == null) {
            return;
        }
        double height;
        double width;
        switch (row) {
            case 1:
                height = mini? TOP_MINIATURE_CHAR_HEIGHT : TOP_CHAR_HEIGHT;
                width = mini? TOP_MINIATURE_CHAR_WIDTH : TOP_CHAR_WIDTH;
                break;
            case 2:
                height = mini? MID_MINIATURE_CHAR_HEIGHT : MID_CHAR_HEIGHT;
                width = mini? MID_MINIATURE_CHAR_WIDTH : MID_CHAR_WIDTH;
                break;
            case 3:
                height = mini? BOT_MINIATURE_CHAR_HEIGHT : BOT_CHAR_HEIGHT;
                width = mini? BOT_MINIATURE_CHAR_WIDTH : BOT_CHAR_WIDTH;
                break;
            default:
                height = 0;
                width = 0;
        }
        resizeToDeviceDimensions((AppCompatActivity) context, view, height, width);
    }

}
