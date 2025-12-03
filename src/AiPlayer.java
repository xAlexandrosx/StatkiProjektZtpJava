import java.util.*;

public class AiPlayer extends Player {

    private static final Random rn = new Random();

    public AiPlayer(String name) {
        super(name);
    }

    @Override
    public void takeTurn() {

        int size = enemyBoard.getSize();
        int x, y;

        while (true) {
            x = rn.nextInt(size);
            y = rn.nextInt(size);

            int tile = enemyBoard.getTile(x, y);

            if (tile == 0 || tile == 1) break;
        }

        System.out.println(name + " shoots at " + x + ", " + y);
        enemyBoard.registerShot(x, y);
    }


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
