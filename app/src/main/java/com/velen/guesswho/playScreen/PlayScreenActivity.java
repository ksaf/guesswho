package com.velen.guesswho.playScreen;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.velen.guesswho.R;
import com.velen.guesswho.characters.CharacterGroup;
import com.velen.guesswho.characters.CharacterGroupBuilder;
import com.velen.guesswho.gameStates.Game;
import com.velen.guesswho.player.AIPlayer;
import com.velen.guesswho.player.Player;

import java.util.Arrays;
import java.util.List;

public class PlayScreenActivity extends AppCompatActivity{

    private PlayScreenUI playScreenUI;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setContentView(R.layout.play_screen);

        CharacterGroupBuilder groupBuilder = new CharacterGroupBuilder(getApplicationContext());
        CharacterGroup characterGroup1 = groupBuilder.getCharactersInJSON("test_char_pool.json");
        CharacterGroup characterGroup2 = groupBuilder.getCharactersInJSON("test_char_pool2.json");

        Player player1 = new Player(characterGroup1, this, Player.BLUE);
        Player player2 = new AIPlayer(characterGroup2, this, Player.RED);
        player1.chooseCharacter(characterGroup2.getCharacters().get(0));
        player2.chooseCharacter(characterGroup1.getCharacters().get(7));
        List<Player> players = Arrays.asList(player1, player2);

        playScreenUI = new PlayScreenUI(this, players);

        Game.getInstance().startGame(this, players);
    }

    public PlayScreenUI getPlayScreenUI() {
        return playScreenUI;
    }
}
