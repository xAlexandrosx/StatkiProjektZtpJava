package players;

import Game.Game;
import board.Board;

public abstract class Player implements IPlayer {

    public final Game g;

    protected String name;
    public Board ownBoard;
    public Board enemyBoard;

    public Player(Game g) {
        this.g = g;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public abstract void takeTurn();
}
