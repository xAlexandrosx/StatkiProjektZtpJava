package board;

import ServiceLocator.ServiceLocator;

public class BattleshipDeployer implements IBattleshipDeployer { // Klasa odpowiadająca za ułożenie statków na planszy

    private final ServiceLocator sl;

    public BattleshipDeployer() {
        this.sl = ServiceLocator.getInstance();
    } // Konstruktor

    public Board getBattleshipsRandom(int boardSize) { // Metoda układająca statki losowo
        BoardBuilder bb = new BoardBuilder();
        boolean[][] grid = new boolean[boardSize][boardSize];

        int[] shipSizes = {4, 3, 3, 2, 2, 2, 2};
        for (int shipSize : shipSizes) {
            placeShip(bb, boardSize, shipSize, grid);
        }

        return bb.toBoard();
    }

    public void placeShip(BoardBuilder bb, int size, int length, boolean[][] grid) { // Metoda obsługująca ułożenie statku w danym miejscu planszy
        boolean placed = false;
        while (!placed) {
            boolean horizontal = sl.getRandom().nextBoolean();
            int row = sl.getRandom().nextInt(size);
            int col = sl.getRandom().nextInt(size);

            if (horizontal) {
                if (col + length > size) continue;
            } else {
                if (row + length > size) continue;
            }

            boolean valid = true;
            for (int i = 0; i < length; i++) {
                int r = horizontal ? row : row + i;
                int c = horizontal ? col + i : col;

                if (grid[r][c]) {
                    valid = false;
                    break;
                }
            }

            if (!valid) continue;

            for (int i = 0; i < length; i++) {
                int r = horizontal ? row : row + i;
                int c = horizontal ? col + i : col;
                grid[r][c] = true;
            }

            bb.add(row, col, length, horizontal);
            placed = true;
        }
    }
}
