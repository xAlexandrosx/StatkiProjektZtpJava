package menu;

import ServiceLocator.ServiceLocator;
import Match.Match;

public class ConsoleMenu implements IMenu {

    private final ServiceLocator sl;

    private final int PLAYER_VS_PLAYER = 0;
    private final int PLAYER_VS_COMPUTER = 1;
    private final int COMPUTER_VS_COMPUTER = 2;

    public ConsoleMenu(ServiceLocator sl) {
        this.sl = sl;
    }

    // MENU DISPLAY
    public void display() {
        System.out.println("\n===================== MENU =====================");

        String p1Name = "";
        if (sl.globalVariables.isPlayerExisting(1)) p1Name = sl.globalVariables.getPlayer(1).getName();
        String p2Name = "";
        if (sl.globalVariables.isPlayerExisting(2)) p2Name = sl.globalVariables.getPlayer(2).getName();

        System.out.println("P1: " + p1Name);
        System.out.println("P2: " + p2Name);

        System.out.println("Board Size: " + sl.globalVariables.getBoardSize());
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
        int choice = sl.scanner.nextInt();
        sl.scanner.nextLine();

        switch (choice) {
            case 1 -> new Match(sl, PLAYER_VS_PLAYER).playMatch();
            case 2 -> new Match(sl, PLAYER_VS_COMPUTER).playMatch();
            case 3 -> new Match(sl, COMPUTER_VS_COMPUTER).playMatch();
            case 4 -> sl.matchHistoryService.displayHistory();
            case 5 -> sl.globalVariables.setBoardSize();
            case 6 -> sl.registrationServiceProxy.logOut();
            case 7 -> sl.registrationServiceProxy.logIn();
            case 8 -> sl.registrationServiceProxy.signIn();
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
            int choice = sl.scanner.nextInt();
            if (choice > 3 || choice < 1) {
                System.out.println("Unknown option, try again.");
                continue;
            }
            System.out.printf("AI difficulty set to %d\n", choice);
            return choice;
        }
    }
}
