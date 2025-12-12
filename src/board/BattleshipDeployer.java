package board;

import battleship.Battleship;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BattleshipDeployer implements IBattleshipDeployer {

    private final Random random;

    public BattleshipDeployer(Random random) {
        this.random = random;
    }

    public List<Battleship> getBattleshipsRandom(int size) {
        List<Battleship> ships = new ArrayList<>();
        boolean[][] grid = new boolean[size][size];

        int[] shipSizes = {4, 3, 3, 2, 2, 2, 2};

        for (int shipSize : shipSizes) {
            ships.add(placeShip(size, shipSize, grid));
        }

        return ships;
    }

    public Battleship placeShip(int size, int length, boolean[][] grid) {

        while (true) {
            boolean horizontal = random.nextBoolean();

            int row = random.nextInt(size);
            int col = random.nextInt(size);

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
