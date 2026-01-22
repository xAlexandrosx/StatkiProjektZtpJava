package board;

import ServiceLocator.ServiceLocator;
import battleship.Battleship;
import battleship.BattleshipAdapter;
import battleship.IBattleship;

import java.util.ArrayList;
import java.util.List;

public class Board {

    public static final int EMPTY = 0;
    public static final int SHIP = 1;
    public static final int HIT = 2;
    public static final int MISS = 3;

    public final int size;
    private final int[][] tiles;
    private List<Battleship> ships;

    public Board() {
        this.size = ServiceLocator.getInstance().getGlobalVariables().getBoardSize();
        this.tiles = new int[size][size];
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
        ServiceLocator sl = ServiceLocator.getInstance();

        sb.append("   ");
        for (int j = 0; j < sl.getGlobalVariables().getBoardSize(); j++) sb.append(j).append(" ");
        sb.append("\n");

        for (int i = 0; i < sl.getGlobalVariables().getBoardSize(); i++) {
            sb.append(i).append("  ");
            for (int j = 0; j < sl.getGlobalVariables().getBoardSize(); j++) {
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
        if (!(x >= 0 && x < size &&
              y >= 0 && y < size)) {
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
                if (s.intersects(x, y)) {
                    if(s.isSunk()){
                        System.out.println("Already sunk!");
                        return false;
                    }
                    ship = s;
                    break;
                }
            }

            if (ship == null) {
                System.out.println("ERROR: Ship not found!");
                return false;
            }
            ship.hit();
            if (ship.isSunk()) {
                System.out.println("Sunk");
                // nie usuwamy statkow z listy poniewaz nadal korzysta z niej metoda isTileFromSunkShip
            } else {
                System.out.println("HP: " + ship.getHp() +  " / " + ship.length );
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

    public boolean isEmpty(int x, int y) {
        for (Battleship ship : ships) {
            if(ship.intersects(x, y)) {
                return false;
            }
        }
        return true;
    }

    public boolean allSunk() {
        for (Battleship ship : ships) {
            if(!ship.isSunk()) {
                return false;
            }
        }
        return true;
    }
}
