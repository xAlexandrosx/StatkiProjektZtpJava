package menu;

import Game.Game;
import Match.Match;
import players.IPlayer;

public class ConsoleMenu implements IMenu {

    private final Game g;

    public ConsoleMenu(Game g) {
        this.g = g;
    }

    // MENU DISPLAY
    public void display() {
        System.out.println("\n===================== MENU =====================");

        String p1Name = "";
        if (g.isPlayerExisting(1)) p1Name = g.getPlayer(1).getName();
        String p2Name = "";
        if (g.isPlayerExisting(2)) p2Name = g.getPlayer(2).getName();

        System.out.println("P1: " + p1Name);
        System.out.println("P2: " + p2Name);

        System.out.println("Board Size: " + g.getBoardSize());
        System.out.println();

        System.out.println("1. Start match with 2 players");
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
        int choice = g.scanner.nextInt();
        g.scanner.nextLine();

        switch (choice) {
            case 1 -> new Match(g, g.PLAYER_VS_PLAYER).playMatch();
            case 2 -> new Match(g, g.PLAYER_VS_COMPUTER).playMatch();
            case 3 -> new Match(g, g.COMPUTER_VS_COMPUTER).playMatch();
            case 4 -> g.matchHistoryService.displayHistory();
            case 5 -> g.setBoardSize();
            case 6 -> g.registrationService.logOut();
            case 7 -> g.registrationService.logIn();
            case 8 -> g.registrationService.signIn();
            case 9 -> {
                System.out.println("Goodbye!");
                return -1;
            }
            default -> System.out.println("Invalid option.");
        }
        return choice;
    }

    @Override
    public int userChooseAiDifficulty() {
        while (true) {
            System.out.println("Choose difficulty:");
            System.out.println("1. Easy");
            System.out.println("2. Medium");
            System.out.println("3. Hard");
            int choice = g.scanner.nextInt();
            if (choice > 3 || choice < 1) {
                System.out.println("Unknown option, try again.");
                continue;
            }
            System.out.printf("AI difficulty set to %d\n", choice);
            return choice;
        }
    }
}
