package players;

import Game.Game;

public class AiPlayerEasy extends Player {

    public AiPlayerEasy(Game g) {
        super(g);
        this.setName("Computer Easy");
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
}
