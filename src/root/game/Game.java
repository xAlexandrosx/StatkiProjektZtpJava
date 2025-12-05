package root.game;

import root.battleship.Battleship;
import root.board.BattleshipDeployer;
import root.board.Board;
import root.matchhistory.MatchHistoryService;
import root.players.Player;

import java.util.List;

public class Game {

    public static MatchHistoryService mhs = new MatchHistoryService();
    private Player p1;
    private Player p2;

    public void setupGame(int size, Player p1, Player p2) {

        this.p1 = p1;
        this.p2 = p2;

        mhs.recordPlayers(p1, p2, size);

        Board b1 = new Board(size);
        Board b2 = new Board(size);

        p1.ownBoard = b1;
        p1.enemyBoard = b2;

        p2.ownBoard = b2;
        p2.enemyBoard = b1;

        // Random ships
        List<Battleship> ships1 = BattleshipDeployer.getBattleshipsRandom(size);
        List<Battleship> ships2 = BattleshipDeployer.getBattleshipsRandom(size);

        b1.importShips(ships1);
        b2.importShips(ships2);

        mhs.recordShips(ships1, ships2);
    }


    public void runGame() {

        while (true) {
            System.out.println("\n==========================");
            System.out.println("      " + p1.getName() + "'s turn");
            System.out.println("==========================");
            p1.takeTurn();

            if (p2.ownBoard.getShips().isEmpty()) {
                System.out.println(p1.getName() + " wins!");
                mhs.setWinner(p1.getName());
                mhs.saveMatchToFile();
                return;
            }

            System.out.println("\n==========================");
            System.out.println("      " + p2.getName() + "'s turn");
            System.out.println("==========================");
            p2.takeTurn();

            if (p1.ownBoard.getShips().isEmpty()) {
                System.out.println(p2.getName() + " wins!");
                mhs.setWinner(p2.getName());
                mhs.saveMatchToFile();
                return;
            }
        }
    }}
