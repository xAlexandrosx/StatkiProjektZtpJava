package root.battleship;

import java.util.List;

public class Battleship {

    private final List<int[]> tiles;

    public List<int[]> getTiles() {
        return tiles;
    }

    public Battleship(List<int[]> tiles) {
        this.tiles = tiles;
    }
}
