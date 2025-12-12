package players;

import Game.Game;
import matchhistory.IMatchHistoryService;
import registrationservice.PlayerProfile;
import statisticsservice.StatisticsService;

import java.util.Scanner;

public class HumanPlayer extends Player {

    private final Scanner scanner;

    public HumanPlayer(String name, Game g, IMatchHistoryService mhs, Scanner sc) {
        super(name, g, mhs);
        this.scanner = sc;
    }
    public HumanPlayer(String name, Game g, PlayerProfile playerProfile, IMatchHistoryService mhs, Scanner sc) {
        super(name, g, mhs);
        this.playerProfile = playerProfile;
        this.scanner = sc;
    }

    @Override
    public void takeTurn() {
        System.out.println(getName() + ", it's your turn!");
        enemyBoard.displayBoard(true);

        int x = readInt("Enter X: ");
        int y = readInt("Enter Y: ");

        matchHistoryService.recordTurn(getName(), x, y);
        StatisticsService.getInstance().RegisterShot(this, enemyBoard.registerShot(x, y));
        StatisticsService.getInstance().RegisterMove(this);
    }

    private int readInt(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a number.");
            scanner.next();
            System.out.print(prompt);
        }
        return scanner.nextInt();
    }
}
