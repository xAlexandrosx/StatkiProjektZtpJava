package players;

import Game.Game;
import board.Board;
import registrationservice.PlayerProfile;

public abstract class Player {

    public final Game g;

    public Board ownBoard;
    public Board enemyBoard;

    public PlayerProfile playerProfile;

    public Player(String name, Game g) {
        this.g = g;

        playerProfile = new PlayerProfile(name);
    }

    public String getName() {
        return playerProfile.Name();
    }

    public abstract void takeTurn();
}
