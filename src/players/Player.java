package players;

import ServiceLocator.ServiceLocator;
import board.Board;
import registrationservice.PlayerProfile;

public abstract class Player implements IPlayer { // Klasa gracza biorącego udział w grze
    protected Board ownBoard;
    protected Board enemyBoard;
    protected PlayerProfile playerProfile;

    public Player(String name) {
        playerProfile = new PlayerProfile(name);
    } // Konstruktor

    public abstract void takeTurn();

    public String getName() {
        return playerProfile.getName();
    } // Metoda zwracająca nazwę gracza
    public void setName(String name) {
        playerProfile.setName(name);
    } // Metoda ustawiająca nazwę gracza

    public Board getOwnBoard() {
        return ownBoard;
    } // Metoda zwracająca planszę
    public void setOwnBoard(Board ownBoard) {
        this.ownBoard = ownBoard;
    } // Metoda ustawiająca planszę

    public Board getEnemyBoard() {
        return enemyBoard;
    } // Metoda zwracająca planszę przeciwnika
    public void setEnemyBoard(Board enemyBoard) { // Metoda ustawiająca planszę przeciwnika
        this.enemyBoard = enemyBoard;
    }

    public PlayerProfile getPlayerProfile() {
        return playerProfile;
    } // Metoda zwracająca profil gracza
    public void setPlayerProfile(PlayerProfile playerProfile) { // Metoda ustawiająca profil gracza
        this.playerProfile = playerProfile;
    }
}
