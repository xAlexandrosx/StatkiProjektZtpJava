package board;

public interface IBattleshipDeployer {

    Board getBattleshipsRandom(int size);
    void placeShip(BoardBuilder bb, int size, int length, boolean[][] grid);
}
