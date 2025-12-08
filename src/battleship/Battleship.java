package battleship;

import java.util.List;

public class Battleship {

    private final List<int[]> tiles;

    public Battleship(List<int[]> tiles) {
        this.tiles = tiles;
    }

    public List<int[]> getTiles() {
        return tiles;
    }
}
