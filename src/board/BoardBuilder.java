package board;

import ServiceLocator.ServiceLocator;
import battleship.Battleship;

/* BUILDER */
public class BoardBuilder {
    private final Board board;
    private boolean done = false;

    public BoardBuilder() {
        board = new Board();
        board.importShips(null);
    }

    public BoardBuilder addSingle(int x, int y) {
        return add(x, y, 1, false);
    }

    public BoardBuilder addDouble(int x, int y, boolean vert) {
        return add(x, y, 2, vert);
    }

    public BoardBuilder addTriple(int x, int y, boolean vert) {
        return add(x, y, 3, vert);
    }

    public BoardBuilder addQuad(int x, int y, boolean vert) {
        return add(x, y, 4, vert);
    }

    public BoardBuilder add(int x, int y, int len, boolean vert) {
        board.placeShip(new Battleship(x, y, len, vert));
        return this;
    }

    public Board toBoard() {
        if (done) return null;
        done = true;
        return board;
    }
}
