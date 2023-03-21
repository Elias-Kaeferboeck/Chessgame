package at.elias.chessgame.utility;

import at.elias.chessgame.ui.screens.GameScreen;

public class GameManager {
    private float turnTimeWhite;
    private float turnTimeBlack;
    private long lastPieceBlink;

    private boolean isWhiteTurn;
    private boolean isSelectedInUpPosition;

    private static GameManager instance;

    private GameScreen activeGameScreen;

    private int winner;

    public GameManager(GameScreen gameScreen) {
        this.isWhiteTurn = true;
        this.isSelectedInUpPosition = false;
        this.turnTimeWhite = -1;
        this.turnTimeBlack = -1;
        this.lastPieceBlink = 0;
        this.winner = 0;
        this.activeGameScreen = gameScreen;
        instance = this;
    }

    public void setTurnTimeWhite(float turnTimeWhite) {
        this.turnTimeWhite = turnTimeWhite;
    }

    public float getTurnTimeWhite() {
        return turnTimeWhite;
    }

    public float getTurnTimeBlack() {
        return turnTimeBlack;
    }

    public void setTurnTimeBlack(float turnTimeBlack) {
        this.turnTimeBlack = turnTimeBlack;
    }

    public boolean isWhiteTurn() {
        return isWhiteTurn;
    }


    public long getLastPieceBlink() {
        return lastPieceBlink;
    }

    public int getWinner() {
        return winner;
    }

    public void setLastPieceBlink(long lastPieceBlink) {
        this.lastPieceBlink = lastPieceBlink;
    }

    public boolean isSelectedInUpPosition() {
        return isSelectedInUpPosition;
    }

    public void setSelectedInUpPosition(boolean selectedInUpPosition) {
        isSelectedInUpPosition = selectedInUpPosition;
    }

    public static GameManager getInstance() {
        return instance;
    }

    public void makeMove() {
        isWhiteTurn = !isWhiteTurn();
    }

    public void setActiveGameScreen(GameScreen activeGameScreen) {
        this.activeGameScreen = activeGameScreen;
    }

    public void setWinner(int winner) {
        this.winner = winner;
    }

    public GameScreen getActiveGameScreen() {
        return activeGameScreen;
    }
}
