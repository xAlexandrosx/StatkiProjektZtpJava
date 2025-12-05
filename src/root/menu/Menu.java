package root.menu;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import root.board.BattleshipDeployer;
import root.board.Board;
import root.game.Game;
import root.matchhistory.MatchRecord;
import root.players.AiPlayer;
import root.players.HumanPlayer;
import root.players.Player;
import root.registrationservice.RegistrationService;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
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
        System.out.println("3. Watch AI vs AI");
        System.out.println("4. Match history");
        System.out.println("5. Change board size");
        System.out.println("6. Log out");
        System.out.println("7. Log in");
        System.out.println("8. Register new player");
        System.out.println("9. Exit");
        System.out.println("================================================");
        System.out.print("Choose an option: ");
    }

    public int handleInput() {
        int choice = sc.nextInt();
        sc.nextLine();

        switch (choice) {
            case 1 -> startMatchPlayers();
            case 2 -> startMatchAI();
            case 3 -> watchAIvsAI();
            case 4 -> showMatchHistory();
            case 5 -> changeBoardSize();
            case 6 -> logOutMenu();
            case 7 -> logInMenu();
            case 8 -> registerMenu();
            case 9 -> {
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

    private void watchAIvsAI() {
        System.out.println("Starting AI vs AI simulation...");

        Game g = new Game();

        Board ai1Board = new Board(boardSize);
        Board ai2Board = new Board(boardSize);

        Player ai1 = new AiPlayer("Computer 1");
        Player ai2 = new AiPlayer("Computer 2");

        ai1.setBoards(ai1Board, ai2Board);
        ai2.setBoards(ai2Board, ai1Board);

        // Deploy random ships
        ai1Board.importShips(BattleshipDeployer.getBattleshipsRandom(boardSize));
        ai2Board.importShips(BattleshipDeployer.getBattleshipsRandom(boardSize));

        g.setupGame(boardSize, ai1, ai2);

        // Optional: Print initial boards
        System.out.println("\nInitial Boards:");
        ai1Board.displayBoard(false);
        ai2Board.displayBoard(false);

        // Run the game automatically
        g.runGame();
    }

    public void showMatchHistory() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        List<MatchRecord> matches;

        // Read match history from JSON
        try (Reader reader = new FileReader("match_history.json")) {
            MatchRecord[] existing = gson.fromJson(reader, MatchRecord[].class);
            if (existing == null || existing.length == 0) {
                System.out.println("No match history found.");
                return;
            }
            matches = Arrays.asList(existing);
        } catch (IOException e) {
            System.out.println("Error reading match history.");
            e.printStackTrace();
            return;
        }

        Collections.reverse(matches);

        for (MatchRecord match : matches) {
            System.out.println("Match ID: " + match.gameID);
            System.out.println(match.date + ", " + match.time);

            String player1 = match.player1;
            String player2 = match.player2;

            if (match.winner != null) {
                if (match.winner.equals(player1)) player1 = "*" + player1 + "*";
                else if (match.winner.equals(player2)) player2 = "*" + player2 + "*";
            }

            System.out.println(player1 + " vs " + player2);
            System.out.println();
        }
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
