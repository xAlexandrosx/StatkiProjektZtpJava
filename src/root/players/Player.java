package root.players;

import root.board.Board;

public abstract class Player {

    protected final String name;
    public Board ownBoard;
    public Board enemyBoard;

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract void takeTurn();

    public void setBoards(Board p1Board, Board p2Board) {
        ownBoard = p1Board;
        enemyBoard = p2Board;
    }
}
