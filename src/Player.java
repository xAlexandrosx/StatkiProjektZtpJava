public abstract class Player {

    protected final String name;
    protected Board ownBoard;
    protected Board enemyBoard;

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
