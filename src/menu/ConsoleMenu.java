package menu;

import Game.Game;
import game.Match;
import java.util.Scanner;

public class ConsoleMenu {

    private final Game g;
    private final Scanner sc = new Scanner(System.in);

    public ConsoleMenu(Game g) {
        this.g = g;
    }

    // MENU DISPLAY
    public void display() {
        System.out.println("\n===================== MENU =====================");
        System.out.println("P1: " + (g.getPlayer(1) == null ? "" : g.getPlayer(2).getName()));
        System.out.println("P2: " + (g.getPlayer(2) == null ? "" : g.getPlayer(2).getName()));
        System.out.println("Board Size: " + g.getBoardSize());
        System.out.println();

        boolean twoPlayersLogged = (g.getPlayer(1) != null && g.getPlayer(2) != null);

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
            case 4 -> g.mhService.displayHistory();
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

        System.out.println("Starting match between " + g.getPlayer(1).getName() + " and " + g.getPlayer(2).getName());
        Match match = new Match(g, g.PLAYER_VS_PLAYER);
        match.playMatch();
    }

    private void startMatchAI() {
        System.out.println("Starting match VS AI...");
        Match match = new Match(g, g.PLAYER_VS_COMPUTER);
        match.playMatch();
    }

    private void watchAIvsAI() {
        System.out.println("Starting AI vs AI simulation...");

        Match match = new Match(g, g.COMPUTER_VS_COMPUTER);
        match.playMatch();
    }

    private void changeBoardSize() {
        System.out.print("Enter new board size: ");
        g.setBoardSize(sc.nextInt());
        sc.nextLine();
        System.out.println("board.Board size updated.");
    }

    private void logOutMenu() {
        System.out.println("Logout:");
        System.out.println("0 - Log out players.Player 1");
        System.out.println("1 - Log out players.Player 2");

        int idx = sc.nextInt();
        sc.nextLine();

        g.rService.logOut(idx);
    }
    private void logInMenu() {
        System.out.print("Enter player name to log in: ");
        String name = sc.nextLine();

        g.rService.logIn(name);
    }
    private void registerMenu() {
        System.out.print("Enter a new username: ");
        String name = sc.nextLine();

        g.rService.signIn(name);
    }
}
