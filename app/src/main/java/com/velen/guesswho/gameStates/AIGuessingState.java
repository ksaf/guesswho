package com.velen.guesswho.gameStates;

import android.support.v7.app.AppCompatActivity;

import com.velen.guesswho.gameDialogs.SwitchableDialogDisplayer;
import com.velen.guesswho.characters.Character;
import com.velen.guesswho.gameDialogs.AIGuessConfirmationDialog;
import com.velen.guesswho.player.Player;
import com.velen.guesswho.question.Question;

import java.util.List;
import java.util.Random;

public class AIGuessingState implements GameState {

    private Game game;

    public AIGuessingState(Game game) {
        this.game = game;
    }

    @Override
    public void initialiseState(AppCompatActivity playScreenActivity) {
        Player player = game.getTurnManager().getNextPlayer();
        List<Character> characters = player.getCurrentCharacterGroup().getCharacters();
        Random rn = new Random();
        int random = rn.nextInt(characters.size());
        final SwitchableDialogDisplayer displayer = new SwitchableDialogDisplayer(playScreenActivity);
        AIGuessConfirmationDialog dialog = new AIGuessConfirmationDialog(displayer, playScreenActivity, player, characters.get(random));
        dialog.openDialog("Is this your character?");
    }

    @Override
    public void flipCoin() {

    }

    @Override
    public void sayOkToTurnDisplay(Player nextPlayer) {

    }

    @Override
    public void askQuestion(Question question) {

    }

    @Override
    public void clickGuess() {

    }

    @Override
    public void finishAnswerAnimation(Question question) {

    }

    @Override
    public void finishGuessAnimation(boolean correct) {

    }

    @Override
    public void flipCharacters() {

    }

    @Override
    public void passTurn() {
        Game.getInstance().getTurnManager().startNextTurn();
        Game.getInstance().setGameState(game.getTurnDisplayState());
    }

    @Override
    public void unclickableTimeInAITurnPassed() {

    }

    @Override
    public void answerToAIQuestionAnimation(Question question) {

    }

    @Override
    public void finishAIFlippingCharactersAnimation() {

    }

    @Override
    public void finishChangingBoardAnimation() {

    }

    @Override
    public void surrender() {

    }

    @Override
    public void victory(Player player) {
        game.setGameState(game.getVictoryDisplayState());
        game.victory(player);
    }
}
