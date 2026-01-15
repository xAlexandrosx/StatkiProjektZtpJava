package command;

import board.Board;

public class ShootCommand implements ICommand {
    Board board;
    int x;
    int y;
    int previousState;
    boolean result;

    public ShootCommand(Board board, int x, int y){
        this.board = board;
        this.x = x;
        this.y = y;
    }

    public boolean execute() {
        previousState = board.getTile(x, y);
        result = board.registerShot(x, y);
        return result;
    }

    public void undo() {
        board.setTile(x, y, previousState);
    }
}
