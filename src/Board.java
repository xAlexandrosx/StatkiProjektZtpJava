import java.util.List;

public class Board {

    private final int[][] tiles;
    private final int size;
    private List<Battleship> ships;

    public int getSize() {
        return size;
    }

    public List<Battleship> getShips() {
        return ships;
    }

    private static final int EMPTY = 0;
    private static final int SHIP  = 1;
    private static final int HIT   = 2;
    private static final int MISS  = 3;

    public Board(int size) {
        this.size = size;
        this.tiles = new int[size][size];
    }

    public void importShips(List<Battleship> ships) {
        this.ships = ships;

        for (Battleship s : ships) {
            for (int[] pos : s.getTiles()) {
                tiles[pos[0]][pos[1]] = SHIP;
            }
        }
    }

    public void displayBoard(boolean isEnemy) {
        StringBuilder sb = new StringBuilder();

        sb.append("   ");
        for (int j = 0; j < size; j++) sb.append(j).append(" ");
        sb.append("\n");

        for (int i = 0; i < size; i++) {
            sb.append(i).append("  ");

            for (int j = 0; j < size; j++) {
                int tile = tiles[i][j];

                if (isEnemy && tile == SHIP) {
                    tile = EMPTY;
                }

                sb.append(getSymbol(tile, i, j, isEnemy)).append(" ");            }

            sb.append("\n");
        }

        System.out.println(sb);
    }

    private char getSymbol(int tile, int x, int y, boolean isEnemy) {

        return switch (tile) {
            case EMPTY -> '.';
            case MISS  -> 'O';
            case SHIP  -> isEnemy ? '.' : 'S';
            case HIT -> {
                if (isTileFromSunkShip(x, y)) yield 'X';
                yield 'x';
            }
            default -> '?';
        };
    }

    public int getTile(int x, int y) {
        return tiles[x][y];
    }

    public void registerShot(int x, int y) {

        if (!inBounds(x, y)) {
            System.out.println("Shot out of bounds!");
            return;
        }

        if (tiles[x][y] == EMPTY) {
            tiles[x][y] = MISS;
            System.out.println("Miss");
            return;
        }

        if (tiles[x][y] == SHIP) {
            tiles[x][y] = HIT;
            System.out.println("Hit");

            Battleship ship = findShipAt(x, y);

            if (ship == null) {
                System.out.println("ERROR: Ship not found!");
                return;
            }

            // Check if sunk
            if (isSunk(ship)) {
                System.out.println("Sunk");
                ships.remove(ship);
            } else {
                System.out.println("Not sunk");
            }
        }
    }

    private boolean isSunk(Battleship ship) {
        for (int[] pos : ship.getTiles()) {
            if (tiles[pos[0]][pos[1]] != HIT) {
                return false;
            }
        }
        return true;
    }

    private Battleship findShipAt(int x, int y) {
        for (Battleship s : ships) {
            for (int[] pos : s.getTiles()) {
                if (pos[0] == x && pos[1] == y) return s;
            }
        }
        return null;
    }

    private boolean inBounds(int x, int y) {
        return x >= 0 && x < size && y >= 0 && y < size;
    }

    private boolean isTileFromSunkShip(int x, int y) {
        if (ships == null) return false;

        for (Battleship ship : ships) {
            boolean sunk = true;
            for (int[] t : ship.getTiles()) {
                if (tiles[t[0]][t[1]] != HIT) {
                    sunk = false;
                    break;
                }
            }

            if (!sunk) continue;

            for (int[] t : ship.getTiles()) {
                if (t[0] == x && t[1] == y) return true;
            }
        }
        return false;
    }

}
