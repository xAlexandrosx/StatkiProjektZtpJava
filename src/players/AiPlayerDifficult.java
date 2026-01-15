package players;

import ServiceLocator.ServiceLocator;
import command.ShootCommand;
import observer.notifications.TurnTakenNotification;

public class AiPlayerDifficult extends AiPlayerBase {

    public AiPlayerDifficult(String name, ServiceLocator sl) {
        super(name, sl);
    }

    @Override
    public void takeTurn() {

        int size = getGame().getGlobalVariables().getBoardSize();
        int x, y;

        while (true) {
            x = sl.getRandom().nextInt(size);
            y = sl.getRandom().nextInt(size);

            int tile = enemyBoard.getTile(x, y);

            if (tile == 0 || tile == 1) break;
        }

        System.out.println(playerProfile.getName() + " shoots at " + x + ", " + y);

        ShootCommand command = new ShootCommand(enemyBoard, x, y);
        boolean shotResult = command.execute();

        sl.getNotificationManager().publish(new TurnTakenNotification(this, x, y, shotResult));

        enemyBoard.displayBoard(false);

        try {
            Thread.sleep(sl.getGlobalVariables().getAiDelay());
        } catch (InterruptedException e) {
            System.out.println("Waiting failed for some reason...");
        }
    }
}
