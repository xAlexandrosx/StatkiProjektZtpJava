package players;

import Game.Game;
import board.Board;

public abstract class Player {

    public final Game g;

    protected final String name;
    public Board ownBoard;
    public Board enemyBoard;

    public Player(String name, Game g) {
        this.g = g;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract void takeTurn();
}
