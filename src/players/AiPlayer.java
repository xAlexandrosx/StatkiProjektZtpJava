package players;

import Game.Game;

public class AiPlayer extends Player {

    public AiPlayer(String name, Game g) {
        super(name, g);
    }

    @Override
    public void takeTurn() {

        int size = g.getBoardSize();
        int x, y;

        while (true) {
            x = g.random.nextInt(size);
            y = g.random.nextInt(size);

            int tile = enemyBoard.getTile(x, y);

            if (tile == 0 || tile == 1) break;
        }

        g.matchHistoryService.recordTurn(playerProfile.Name(), x, y);

        System.out.println(playerProfile.Name() + " shoots at " + x + ", " + y);
        enemyBoard.registerShot(x, y);
        enemyBoard.displayBoard(false);

        try {
            Thread.sleep(0);
        } catch (InterruptedException e) {
            System.out.println("Waiting failed for some reason...");
        }
    }
}
