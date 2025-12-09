package players;

import Game.Game;
import registrationservice.PlayerProfile;

import java.util.Scanner;

public class HumanPlayer extends Player {

    public HumanPlayer(String name, Game g) {
        super(name, g);
    }
    public HumanPlayer(String name, Game g, PlayerProfile playerProfile) {
        super(name, g);
        this.playerProfile = playerProfile;
    }

    @Override
    public void takeTurn() {
        System.out.println(getName() + ", it's your turn!");
        enemyBoard.displayBoard(true);

        int x = readInt("Enter X: ");
        int y = readInt("Enter Y: ");

        g.matchHistoryService.recordTurn(getName(), x, y);

        enemyBoard.registerShot(x, y);
    }

    private int readInt(String prompt) {
        System.out.print(prompt);
        while (!g.scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a number.");
            g.scanner.next();
            System.out.print(prompt);
        }
        return g.scanner.nextInt();
    }
}
