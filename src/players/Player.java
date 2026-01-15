package players;

import ServiceLocator.ServiceLocator;
import board.Board;
import registrationservice.PlayerProfile;

public abstract class Player implements IPlayer {

    protected ServiceLocator sl;
    protected Board ownBoard;
    protected Board enemyBoard;
    protected PlayerProfile playerProfile;

    public Player(String name, ServiceLocator sl) {
        this.sl = sl;

        playerProfile = new PlayerProfile(name);
    }

    public abstract void takeTurn();

    public String getName() {
        return playerProfile.getName();
    }
    public void setName(String name) {
        playerProfile.setName(name);
    }

    public ServiceLocator getGame() {
        return sl;
    }
    public void setGame(ServiceLocator sl) {
        this.sl = sl;
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
