package players;

import Game.Game;

public class HumanPlayer extends Player {

    public HumanPlayer(Game g) {
        super(g);
    }

    @Override
    public void takeTurn() {
        System.out.println(name + ", it's your turn!");
        enemyBoard.displayBoard(true);

        int x = readInt("Enter X: ");
        int y = readInt("Enter Y: ");

        g.matchHistoryService.recordTurn(this.name, x, y);

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
