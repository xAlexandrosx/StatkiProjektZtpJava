package players;

import Game.Game;
import battleship.Battleship;

public class AiPlayerCheater extends Player {

    public AiPlayerCheater(Game g) {
        super(g);
        this.setName("Computer Cheater");
    }

    @Override
    public void takeTurn() {

        int[] target = findUnhitShipTile();

        if (target == null) {
            return;
        }

        int x = target[0];
        int y = target[1];

        g.matchHistoryService.recordTurn(this.name, x, y);

        System.out.println(name + " shoots at " + x + ", " + y);

        enemyBoard.registerShot(x, y);
        enemyBoard.displayBoard(false);

        try {
            Thread.sleep(g.AI_DELAY);
        } catch (InterruptedException e) {
            System.out.println("Waiting failed for some reason...");
        }
    }

    private int[] findUnhitShipTile() {

        for (Battleship ship : enemyBoard.getShips()) {
            for (int[] t : ship.getTiles()) {
                int x = t[0];
                int y = t[1];

                int tile = enemyBoard.getTile(x, y);

                if (tile == 1) {
                    return new int[]{x, y};
                }
            }
        }

        return null;
    }
}
