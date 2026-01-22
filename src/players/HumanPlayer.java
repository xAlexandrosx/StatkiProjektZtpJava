package players;

import ServiceLocator.ServiceLocator;
import command.ICommand;
import command.ShootCommand;
import observer.notifications.TurnTakenNotification;
import registrationservice.PlayerProfile;
import statisticsservice.StatisticsService;

public class HumanPlayer extends Player { // Klasa dziedzicząca po Player dla ludzkich graczy

    public HumanPlayer(String name) {
        super(name);
    }
    public HumanPlayer(String name, PlayerProfile playerProfile) {
        super(name);
        this.playerProfile = playerProfile;
    }

    @Override
    public void takeTurn() { // Metoda obsługująca turę
        System.out.println(getName() + ", it's your turn!");
        System.out.println(enemyBoard.displayBoard(true));

        int x = readInt("Enter X: ");
        int y = readInt("Enter Y: ");
        hitTarget(x, y);
    }

    public boolean hitTarget(int x, int y) { // Metoda obsługująca strzał
        ShootCommand command = new ShootCommand(enemyBoard, x, y);
        boolean shotResult = command.execute();
        ServiceLocator.getInstance().getNotificationManager().publish(new TurnTakenNotification(this, x, y, shotResult));
        return shotResult;
    }

    private int readInt(String prompt) {
        System.out.print(prompt);
        while (!ServiceLocator.getInstance().getScanner().hasNextInt()) {
            System.out.println("Invalid input. Please enter a number.");
            ServiceLocator.getInstance().getScanner().next();
            System.out.print(prompt);
        }
        return ServiceLocator.getInstance().getScanner().nextInt();
    }
}
