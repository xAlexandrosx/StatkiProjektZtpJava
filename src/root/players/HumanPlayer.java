package root.players;

import java.util.Scanner;

public class HumanPlayer extends Player {

    private final Scanner sc = new Scanner(System.in);

    public HumanPlayer(String name) {
        super(name);
    }

    @Override
    public void takeTurn() {
        System.out.println(name + ", it's your turn!");
        enemyBoard.displayBoard(true);

        int x = readInt("Enter X: ");
        int y = readInt("Enter Y: ");

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
