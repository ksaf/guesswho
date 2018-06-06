package com.velen.guesswho.turn;

import com.velen.guesswho.player.AIPlayer;
import com.velen.guesswho.player.Player;

import java.util.List;
import java.util.Random;

/**
 * This class is responsible for deciding which player is going to play next.
 * <p>
 *     Must call {@link #startNextTurn()} every time a new turn is going to start.
 * </p>
 */
public class TurnManager {

    private List<Player> players;
    private Player currentPlayer;
    private int turnCount = 0;

    /**
     * Creates an instance of TurnManager.
     * @param players A list of the players that will participate in the game.
     * @param startingPlayer The player that is going to play first. Can be null for random selection.
     */
    public TurnManager(List<Player> players, Player startingPlayer) {
        this.players = players;
        if(startingPlayer == null) {
            currentPlayer = getRandomPlayer();
        } else {
            currentPlayer = startingPlayer;
        }
        turnCount++;
        currentPlayer.startTurn();
    }

    /**
     * @return The player that that is currently his turn.
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * @return The player that that is playing after this turn. (not really, but it is for 2 players)
     */
    public Player getNextPlayer() {
        for(Player p : players) {
            if(!p.isHisTurn()) {
                return p;
            }
        }
        return null;
    }

    /**
     * @return True if there is an AI player in the game.
     */
    public boolean isVsAI() {
        for(Player player : players) {
            if(player instanceof AIPlayer) {
                return true;
            }
        }
        return false;
    }

    /**
     * Takes all the necessary actions to correctly set what player {@link #getCurrentPlayer()} will return.
     * Has to be called every turn.
     */
    public void startNextTurn() {
        turnCount++;
        for(int i = 0; i < players.size(); i++) {
            if(players.get(i).isHisTurn()) {
                players.get(i).endTurn();
                if(i == players.size() -1) {
                    players.get(0).startTurn();
                    currentPlayer = players.get(0);
                    return;
                } else {
                    players.get(i+1).startTurn();
                    currentPlayer = players.get(i+1);
                    return;
                }
            }
        }
    }

    /**
     * @return How many turns have passed since the start of the game.
     */
    public int getTurnCount() {
        return turnCount;
    }

    private Player getRandomPlayer() {
        Random r = new Random();
        return players.get(r.nextInt(players.size()));
    }

    /**
     * Resets all players to their original states.
     */
    public void restartGame() {
        for(Player player : players) {
            player.resetPlayer();
        }
        currentPlayer = getRandomPlayer();
    }

}
