package players;

import Game.Game;
import board.Board;
import matchhistory.IMatchHistoryService;
import registrationservice.PlayerProfile;

import java.util.Scanner;

public abstract class Player implements IPlayer {

    protected Game g;
    protected Board ownBoard;
    protected Board enemyBoard;
    protected PlayerProfile playerProfile;
    protected IMatchHistoryService matchHistoryService;

    public Player(String name, Game g, IMatchHistoryService mhs) {
        this.g = g;
        this.matchHistoryService = mhs;
        playerProfile = new PlayerProfile(name);
    }

    public abstract void takeTurn();

    public String getName() {
        return playerProfile.getName();
    }
    public void setName(String name) {
        playerProfile.setName(name);
    }

    public Game getGame() {
        return g;
    }
    public void setGame(Game g) {
        this.g = g;
    }

    public Board getOwnBoard() {
        return ownBoard;
    }
    public void setOwnBoard(Board ownBoard) {
        this.ownBoard = ownBoard;
    }

    public Board getEnemyBoard() {
        return enemyBoard;
    }
    public void setEnemyBoard(Board enemyBoard) {
        this.enemyBoard = enemyBoard;
    }

    public PlayerProfile getPlayerProfile() {
        return playerProfile;
    }
    public void setPlayerProfile(PlayerProfile playerProfile) {
        this.playerProfile = playerProfile;
    }
}
