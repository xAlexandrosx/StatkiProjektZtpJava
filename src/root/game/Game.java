package root.game;

import root.board.BattleshipDeployer;
import root.board.Board;
import root.players.Player;

public class Game {

    private Player p1;
    private Player p2;

    public void setupGame(int size, Player p1, Player p2) {

        Board b1 = new Board(size);
        Board b2 = new Board(size);

        this.p1 = p1;
        this.p2 = p2;

        p1.ownBoard = b1;
        p1.enemyBoard = b2;

        p2.ownBoard = b2;
        p2.enemyBoard = b1;

        b1.importShips(BattleshipDeployer.getBattleshipsRandom(size));
        b2.importShips(BattleshipDeployer.getBattleshipsRandom(size));
    }


    public void runGame() {

        while (true) {
            System.out.println("\n==========================");
            System.out.println("      " + p1.getName() + "'s turn");
            System.out.println("==========================");
            p1.takeTurn();

            if (p2.ownBoard.getShips().isEmpty()) {
                System.out.println(p1.getName() + " wins!");
                return;
            }

            System.out.println("\n==========================");
            System.out.println("      " + p2.getName() + "'s turn");
            System.out.println("==========================");
            p2.takeTurn();

            if (p1.ownBoard.getShips().isEmpty()) {
                System.out.println(p2.getName() + " wins!");
                return;
            }
        }
    }
}
