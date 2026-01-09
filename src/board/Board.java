package board;

import ServiceLocator.ServiceLocator;
import battleship.Battleship;
import battleship.BattleshipAdapter;
import battleship.IBattleship;

import java.util.ArrayList;
import java.util.List;

public class Board {

    private static final int EMPTY = 0;
    private static final int SHIP = 1;
    private static final int HIT = 2;
    private static final int MISS = 3;

    private final ServiceLocator sl;
    private final int[][] tiles;
    private List<Battleship> ships;

    public Board(ServiceLocator sl) {
        this.sl = sl;
        this.tiles = new int[sl.globalVariables.getBoardSize()][sl.globalVariables.getBoardSize()];
    }

    public List<Battleship> getShips() {
        return ships;
    }

    public List<IBattleship> adaptShips() {
        List<IBattleship> ret = new ArrayList<>();
        for(Battleship s : ships){
            ret.add(new BattleshipAdapter(s));
        }
        return ret;
    }

    public void placeShip(Battleship s) {
        ships.add(s);
        int x = s.posX;
        int y = s.posY;
        if (s.vertical) {
            for (; y < s.posY + s.length; y++) {
                tiles[x][y] = SHIP;
            }
        } else {
            for (; x < s.posX + s.length; x++) {
                tiles[x][y] = SHIP;
            }
        }
    }

    public void importShips(List<Battleship> ships) {
        if(this.ships == null)
            this.ships = new ArrayList<>();
        if(ships == null) return;
        for (Battleship s : ships) {
            placeShip(s);
        }
    }

    public void displayBoard(boolean isEnemy) {
        StringBuilder sb = new StringBuilder();

        sb.append("   ");
        for (int j = 0; j < sl.globalVariables.getBoardSize(); j++) sb.append(j).append(" ");
        sb.append("\n");

        for (int i = 0; i < sl.globalVariables.getBoardSize(); i++) {
            sb.append(i).append("  ");
            for (int j = 0; j < sl.globalVariables.getBoardSize(); j++) {
                int tile = tiles[i][j];
                if (isEnemy && tile == SHIP) {
                    tile = EMPTY;
                }
                sb.append(getTileSymbol(tile, i, j, isEnemy)).append(" ");
            }
            sb.append("\n");
        }
        System.out.println(sb);
    }

    private char getTileSymbol(int tile, int x, int y, boolean isEnemy) {
        return switch (tile) {
            case EMPTY -> '.';
            case MISS -> 'O';
            case SHIP -> isEnemy ? '.' : 'S';
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
    public void setTile(int x, int y, int tile) { tiles[x][y] = tile; }

    public boolean registerShot(int x, int y) {
        if (!(x >= 0 && x < sl.globalVariables.getBoardSize() && y >= 0 && y < sl.globalVariables.getBoardSize())) {
            System.out.println("Shot out of bounds!");
            return false;
        }

        if (tiles[x][y] == EMPTY) {
            tiles[x][y] = MISS;
            System.out.println("Miss");
            return false;
        }

        if (tiles[x][y] == SHIP) {
            tiles[x][y] = HIT;
            System.out.println("Hit");

            Battleship ship = null;
            for (Battleship s : ships) {
                // poniewaz nie usuwamy statkow, w iteracji ignorujemy zatopione??
                if(s.isSunk()) return false;
                if (s.intersects(x, y)) {
                    ship = s;
                    break;
                }
            }

            if (ship == null) {
                System.out.println("ERROR: Ship not found!");
                return false;
            }

            if (ship.isSunk()) {
                System.out.println("Sunk");
                // nie usuwamy statkow z listy poniewaz nadal korzysta z niej metoda isTileFromSunkShip
            } else {
                System.out.println("Not sunk");
            }
        }
        return true;
    }

    private boolean isTileFromSunkShip(int x, int y) {
        if (ships == null) return false;
        for (Battleship ship : ships) {
            if (ship.isSunk() && ship.intersects(x, y)) {
                return true;
            }
        }
        return false;
    }
}
