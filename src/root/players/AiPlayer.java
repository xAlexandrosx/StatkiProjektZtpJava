package root.players;

import root.battleship.Battleship;

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
        enemyBoard.displayBoard(false);
    }
}
