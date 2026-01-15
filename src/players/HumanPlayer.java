package players;

import ServiceLocator.ServiceLocator;
import command.ICommand;
import command.ShootCommand;
import observer.notifications.TurnTakenNotification;
import registrationservice.PlayerProfile;
import statisticsservice.StatisticsService;

public class HumanPlayer extends Player {

    public HumanPlayer(String name, ServiceLocator sl) {
        super(name, sl);
    }
    public HumanPlayer(String name, ServiceLocator sl, PlayerProfile playerProfile) {
        super(name, sl);
        this.playerProfile = playerProfile;
    }

    @Override
    public void takeTurn() {
        System.out.println(getName() + ", it's your turn!");
        enemyBoard.displayBoard(true);

        int x = readInt("Enter X: ");
        int y = readInt("Enter Y: ");

        ShootCommand command = new ShootCommand(enemyBoard, x, y);
        boolean shotResult = command.execute();

        sl.getNotificationManager().publish(new TurnTakenNotification(this, x, y, shotResult));
    }

    private int readInt(String prompt) {
        System.out.print(prompt);
        while (!sl.getScanner().hasNextInt()) {
            System.out.println("Invalid input. Please enter a number.");
            sl.getScanner().next();
            System.out.print(prompt);
        }
        return sl.getScanner().nextInt();
    }
}
