package root.menu;

import root.board.BattleshipDeployer;
import root.board.Board;
import root.game.Game;
import root.players.AiPlayer;
import root.players.HumanPlayer;
import root.players.Player;
import root.registrationservice.RegistrationService;

import java.util.Scanner;

public class Menu {

    private HumanPlayer P1;
    private HumanPlayer P2;
    private int boardSize = 10;

    private final RegistrationService rService;
    private final Scanner sc = new Scanner(System.in);

    public Menu() {
        rService = new RegistrationService(this);
    }

    public HumanPlayer getP1() { return P1; }
    public HumanPlayer getP2() { return P2; }

    public void setP1(HumanPlayer p) { this.P1 = p; }
    public void setP2(HumanPlayer p) { this.P2 = p; }

    // MENU DISPLAY
    public void display() {
        System.out.println("\n===================== MENU =====================");
        System.out.println("P1: " + (P1 == null ? "" : P1.getName()));
        System.out.println("P2: " + (P2 == null ? "" : P2.getName()));
        System.out.println("Board Size: " + boardSize);
        System.out.println();

        boolean twoPlayersLogged = (P1 != null && P2 != null);

        System.out.println("1. Start match with 2 players"
                + (twoPlayersLogged ? "" : " (unavailable)"));

        System.out.println("2. Start match vs AI");
        System.out.println("3. Change board size");
        System.out.println("4. Log out");
        System.out.println("5. Log in");
        System.out.println("6. Register new player");
        System.out.println("7. Exit");
        System.out.println("================================================");
        System.out.print("Choose an option: ");
    }

    public int handleInput() {
        int choice = sc.nextInt();
        sc.nextLine();

        switch (choice) {
            case 1 -> startMatchPlayers();
            case 2 -> startMatchAI();
            case 3 -> changeBoardSize();
            case 4 -> logOutMenu();
            case 5 -> logInMenu();
            case 6 -> registerMenu();
            case 7 -> {
                System.out.println("Goodbye!");
                return -1;
            }
            default -> System.out.println("Invalid option.");
        }
        return choice;
    }

    private void startMatchPlayers() {
        if (P1 == null || P2 == null) {
            System.out.println("Two players must be logged in!");
            return;
        }

        System.out.println("Starting match between " + P1.getName() + " and " + P2.getName());

        Game g = new Game();

        Board p1Board = new Board(boardSize);
        Board p2Board = new Board(boardSize);

        P1.setBoards(p1Board, p2Board);
        P2.setBoards(p2Board, p1Board);

        p1Board.importShips(BattleshipDeployer.getBattleshipsRandom(boardSize));
        p2Board.importShips(BattleshipDeployer.getBattleshipsRandom(boardSize));

        g.setupGame(boardSize, P1, P2);
        g.runGame();
    }

    private void startMatchAI() {
        System.out.println("Starting match VS AI...");

        Game g = new Game();

        Board p1Board = new Board(boardSize);
        Board p2Board = new Board(boardSize);

        HumanPlayer hp = (P1 != null ? P1 : new HumanPlayer("Guest"));

        hp.setBoards(p1Board, p2Board);
        Player ai = new AiPlayer("Computer");
        ai.setBoards(p2Board, p1Board);

        p1Board.importShips(BattleshipDeployer.getBattleshipsRandom(boardSize));
        p2Board.importShips(BattleshipDeployer.getBattleshipsRandom(boardSize));

        g.setupGame(boardSize, hp, ai);
        g.runGame();
    }

    private void changeBoardSize() {
        System.out.print("Enter new board size: ");
        boardSize = sc.nextInt();
        sc.nextLine();
        System.out.println("root.board.Board size updated.");
    }

    private void logOutMenu() {
        System.out.println("Logout:");
        System.out.println("0 - Log out root.players.Player 1");
        System.out.println("1 - Log out root.players.Player 2");

        int idx = sc.nextInt();
        sc.nextLine();

        rService.logOut(idx);
    }

    private void logInMenu() {
        System.out.print("Enter player name to log in: ");
        String name = sc.nextLine();

        rService.logIn(name);
    }

    private void registerMenu() {
        System.out.print("Enter a new username: ");
        String name = sc.nextLine();

        rService.signIn(name);
    }
}
