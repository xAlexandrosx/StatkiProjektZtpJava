package players;

import ServiceLocator.ServiceLocator;
import board.Board;
import registrationservice.PlayerProfile;

public abstract class Player implements IPlayer {
    protected Board ownBoard;
    protected Board enemyBoard;
    protected PlayerProfile playerProfile;

    public Player(String name) {
        playerProfile = new PlayerProfile(name);
    }

    public abstract void takeTurn();

    public String getName() {
        return playerProfile.getName();
    }
    public void setName(String name) {
        playerProfile.setName(name);
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
