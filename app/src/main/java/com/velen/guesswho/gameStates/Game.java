package com.velen.guesswho.gameStates;

import android.support.v7.app.AppCompatActivity;

import com.velen.guesswho.player.Player;
import com.velen.guesswho.question.Question;
import com.velen.guesswho.turn.TurnManager;

import java.util.List;

public class Game {

    private GameState coinFlipState;
    private GameState turnDisplayState;
    private GameState beforeAskingOrGuessingState;
    private GameState answerAnnouncementState;
    private GameState victoryDisplayState;
    private GameState beforeFlippingCharactersState;
    private GameState afterFlippingCharactersState;
    private GameState duringOpponentTurnState;
    private GameState aIQuestionAnnouncementState;
    private GameState aiFlippingCharactersAnimationState;
    private GameState guessingState;
    private GameState aIGuessingState;

    private GameState gameState;
    private AppCompatActivity playScreenActivity;
    private TurnManager turnManager;

    private static Game instance = null;

    public static Game getInstance() {
        if(instance == null) {
            instance = new Game();
        }
        return instance;
    }

    private Game() {
        coinFlipState = new CoinFlipState(this);
        turnDisplayState = new TurnDisplayState(this);
        beforeAskingOrGuessingState = new BeforeAskingOrGuessingState(this);
        answerAnnouncementState = new AnswerAnnouncementState(this);
        victoryDisplayState = new VictoryDisplayState(this);
        beforeFlippingCharactersState = new BeforeFlippingCharactersState(this);
        afterFlippingCharactersState = new AfterFlippingCharactersState(this);
        duringOpponentTurnState = new DuringOpponentTurnState(this);
        aIQuestionAnnouncementState = new AIQuestionAnnouncementState(this);
        aiFlippingCharactersAnimationState = new AIFlippingCharactersAnimationState(this);
        guessingState = new GuessingState(this);
        aIGuessingState = new AIGuessingState(this);
    }

    void setGameState(GameState newState) {
        this.gameState = newState;
        gameState.initialiseState(playScreenActivity);
    }

    public void startGame(AppCompatActivity playScreenActivity, List<Player> players) {
        turnManager = new TurnManager(players, null);
        this.playScreenActivity = playScreenActivity;
        setGameState(getCoinFlipState());
    }

    public void flipCoin() {
        gameState.flipCoin();
    }
    public void sayOkToTurnDisplay(Player nextPlayer) {
        gameState.sayOkToTurnDisplay(nextPlayer);
    }
    public void askedQuestion(Question question) {
        gameState.askQuestion(question);
    }
    public void clickGuess() {
        gameState.clickGuess();
    }
    public void finishAnswerAnimation(Question question) {
        gameState.finishAnswerAnimation(question);
    }
    public void finishGuessAnimation(boolean correct) {
        gameState.finishGuessAnimation(correct);
    }
    public void flipCharacters() {
        gameState.flipCharacters();
    }
    public void passTurn() {
        gameState.passTurn();
    }
    public void unclickableTimeInAITurnPassed() {
        gameState.unclickableTimeInAITurnPassed();
    }
    public void answerToAIQuestionAnimation(Question question) {
        gameState.answerToAIQuestionAnimation(question);
    }
    public void finishAIFlippingCharactersAnimation() {
        gameState.finishAIFlippingCharactersAnimation();
    }
    public void finishChangingBoardAnimation() {
        gameState.finishChangingBoardAnimation();
    }
    public void surrender() {
        gameState.surrender();
    }
    public void victory(Player player) { gameState.victory(player);}

    public GameState getCoinFlipState() {
        return coinFlipState;
    }
    public GameState getTurnDisplayState() {
        return turnDisplayState;
    }
    public GameState getBeforeAskingOrGuessingState() {
        return beforeAskingOrGuessingState;
    }
    public GameState getAnswerAnnouncementState() {
        return answerAnnouncementState;
    }
    public GameState getVictoryDisplayState() {
        return victoryDisplayState;
    }
    public GameState getBeforeFlippingCharactersState() {
        return beforeFlippingCharactersState;
    }
    public GameState getAfterFlippingCharactersState() {
        return afterFlippingCharactersState;
    }
    public GameState getDuringOpponentTurnState() {
        return duringOpponentTurnState;
    }
    public GameState getAIQuestionAnnouncementState() {
        return aIQuestionAnnouncementState;
    }
    public GameState getAiFlippingCharactersAnimationState() {
        return aiFlippingCharactersAnimationState;
    }
    public GameState getGuessingState() {return guessingState;}
    public GameState getAIGuessingState() {return aIGuessingState;}

    public TurnManager getTurnManager() {
        return turnManager;
    }
}
