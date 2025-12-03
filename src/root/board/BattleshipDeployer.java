package root.board;

import root.battleship.Battleship;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BattleshipDeployer {

    private static final Random rn = new Random();

    public static List<Battleship> getBattleshipsRandom(int size) {
        List<Battleship> ships = new ArrayList<>();
        boolean[][] grid = new boolean[size][size];

        int[] shipSizes = {4, 3, 3, 2, 2, 2, 2};

        for (int shipSize : shipSizes) {
            ships.add(placeShip(size, shipSize, grid));
        }

        return ships;
    }

    private static Battleship placeShip(int size, int length, boolean[][] grid) {

        while (true) {
            boolean horizontal = rn.nextBoolean();

            int row = rn.nextInt(size);
            int col = rn.nextInt(size);

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

            List<int[]> tiles = new ArrayList<>();
            for (int i = 0; i < length; i++) {
                int r = horizontal ? row : row + i;
                int c = horizontal ? col + i : col;
                grid[r][c] = true;
                tiles.add(new int[]{r, c});
            }

            return new Battleship(tiles);
        }
    }
}
