package board;

public interface IBattleshipDeployer { // Interfejs klasy BattleshipDeployer

    Board getBattleshipsRandom(int size);
    void placeShip(BoardBuilder bb, int size, int length, boolean[][] grid);
}
