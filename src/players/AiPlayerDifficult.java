package players;

import Game.Game;
import matchhistory.IMatchHistoryService;

import java.util.Random;

public class AiPlayerDifficult extends Player {

    private final Random random;

    public AiPlayerDifficult(String name, Game g, IMatchHistoryService mhs, Random rn) {
        super(name, g, mhs);
        this.random = rn;
    }

    @Override
    public void takeTurn() {

        int size = getGame().getBoardSize();
        int x, y;

        while (true) {
            x = random.nextInt(size);
            y = random.nextInt(size);

            int tile = enemyBoard.getTile(x, y);

            if (tile == 0 || tile == 1) break;
        }

        matchHistoryService.recordTurn(playerProfile.getName(), x, y);

        System.out.println(playerProfile.getName() + " shoots at " + x + ", " + y);
        enemyBoard.registerShot(x, y);
        enemyBoard.displayBoard(false);

        try {
            Thread.sleep(g.AI_DELAY);
        } catch (InterruptedException e) {
            System.out.println("Waiting failed for some reason...");
        }
    }
}
