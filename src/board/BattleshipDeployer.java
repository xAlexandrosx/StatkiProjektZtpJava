package board;

import Game.Game;

public class BattleshipDeployer implements IBattleshipDeployer {

    private final Game g;

    public BattleshipDeployer(Game g) {
        this.g = g;
    }

    public Board getBattleshipsRandom(int boardSize) {
        BoardBuilder bb = new BoardBuilder(g);
        boolean[][] grid = new boolean[boardSize][boardSize];

        int[] shipSizes = {4, 3, 3, 2, 2, 2, 2};
        for (int shipSize : shipSizes) {
            placeShip(bb, boardSize, shipSize, grid);
        }

        return bb.toBoard();
    }

    public void placeShip(BoardBuilder bb, int size, int length, boolean[][] grid) {
        boolean placed = false;
        while (!placed) {
            boolean horizontal = g.random.nextBoolean();
            int row = g.random.nextInt(size);
            int col = g.random.nextInt(size);

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
