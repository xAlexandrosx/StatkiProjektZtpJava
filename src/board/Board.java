package board;

import Game.Game;
import battleship.Battleship;

import java.util.List;

public class Board {

    private final Game g;

    private final int[][] tiles;
    private List<Battleship> ships;

    public Board(Game g) {
        this.g = g;
        this.tiles = new int[g.getBoardSize()][g.getBoardSize()];
    }

    public List<Battleship> getShips() {
        return ships;
    }

    private static final int EMPTY = 0;
    private static final int SHIP  = 1;
    private static final int HIT   = 2;
    private static final int MISS  = 3;

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
        for (int j = 0; j < g.getBoardSize(); j++) sb.append(j).append(" ");
        sb.append("\n");

        for (int i = 0; i < g.getBoardSize(); i++) {
            sb.append(i).append("  ");

            for (int j = 0; j < g.getBoardSize(); j++) {
                int tile = tiles[i][j];

                if (isEnemy && tile == SHIP) {
                    tile = EMPTY;
                }

                sb.append(getTileSymbol(tile, i, j, isEnemy)).append(" ");            }

            sb.append("\n");
        }

        System.out.println(sb);
    }

    private char getTileSymbol(int tile, int x, int y, boolean isEnemy) {

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

        if (!(x >= 0 && x < g.getBoardSize() && y >= 0 && y < g.getBoardSize())) {
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

            Battleship ship = null;
            for (Battleship s : ships) {
                for (int[] pos : s.getTiles()) {
                    if (pos[0] == x && pos[1] == y) {
                        ship = s;
                        break;
                    }
                }
            }

            if (ship == null) {
                System.out.println("ERROR: Ship not found!");
                return;
            }

            if (isSunk(ship)) {
                System.out.println("Sunk");
                ships.remove(ship);
            } else {
                System.out.println("Not sunk");
            }
        }
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
