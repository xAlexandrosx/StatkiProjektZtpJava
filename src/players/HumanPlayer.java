package players;

import Game.Game;
import game.Match;

import java.util.Scanner;

public class HumanPlayer extends Player {

    private final Scanner sc = new Scanner(System.in);

    public HumanPlayer(String name, Game g) {
        super(name, g);
    }

    @Override
    public void takeTurn() {
        System.out.println(name + ", it's your turn!");
        enemyBoard.displayBoard(true);

        int x = readInt("Enter X: ");
        int y = readInt("Enter Y: ");

        g.mhService.recordTurn(this.name, x, y);

        enemyBoard.registerShot(x, y);
    }

    private int readInt(String prompt) {
        System.out.print(prompt);
        while (!sc.hasNextInt()) {
            System.out.println("Invalid input. Please enter a number.");
            sc.next();
            System.out.print(prompt);
        }
        return sc.nextInt();
    }


}
