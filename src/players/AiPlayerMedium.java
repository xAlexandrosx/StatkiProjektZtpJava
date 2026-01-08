package players;

import ServiceLocator.ServiceLocator;
import command.ShootCommand;

public class AiPlayerMedium extends Player {

    public AiPlayerMedium(String name, ServiceLocator sl) {
        super(name, sl);
    }

    @Override
    public void takeTurn() {

        int size = sl.globalVariables.getBoardSize();
        int x, y;

        while (true) {
            x = sl.random.nextInt(size);
            y = sl.random.nextInt(size);

            int tile = enemyBoard.getTile(x, y);

            if (tile == 0 || tile == 1) break;
        }

        sl.matchHistoryService.recordTurn(playerProfile.getName(), x, y);

        System.out.println(playerProfile.getName() + " shoots at " + x + ", " + y);

        ShootCommand command = new ShootCommand(enemyBoard, x, y);
        command.execute();
        enemyBoard.displayBoard(false);

        try {
            Thread.sleep(sl.globalVariables.getAiDelay());
        } catch (InterruptedException e) {
            System.out.println("Waiting failed for some reason...");
        }
    }
}
