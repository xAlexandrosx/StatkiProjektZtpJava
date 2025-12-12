package menu;

import Game.Game;
import Match.Match;
import board.IBattleshipDeployer;
import matchhistory.IMatchHistoryService;
import players.IPlayer;
import players.playerstrategy.IPlayerSupplier;
import registrationservice.IRegistrationService;

import java.util.Scanner;

public class ConsoleMenu implements IMenu {

    private final int PLAYER_VS_PLAYER = 0;
    private final int PLAYER_VS_COMPUTER = 1;
    private final int COMPUTER_VS_COMPUTER = 2;

    private final Game g;
    private final IMatchHistoryService matchHistoryService;
    private final IRegistrationService registrationService;
    private final IBattleshipDeployer deployer;
    private final IPlayerSupplier supplier;
    private final Scanner scanner;

    public ConsoleMenu(IPlayerSupplier supplier, IBattleshipDeployer deployer, IMatchHistoryService mhs, IRegistrationService rs, Scanner sc, Game g) {
        this.matchHistoryService = mhs;
        this.registrationService = rs;
        this.scanner = sc;
        this.deployer = deployer;
        this.supplier = supplier;
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

//    Match(IMenu, IMatchHistoryService, IBattleshipDeployer, IRegistrationService, IPlayerSupplier, Game g, int variant) {


        public int handleInput() {
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1 -> new Match(this, matchHistoryService,deployer, registrationService, supplier, g, PLAYER_VS_PLAYER).playMatch();
            case 2 -> new Match(this, matchHistoryService,deployer, registrationService, supplier, g, PLAYER_VS_COMPUTER).playMatch();
            case 3 -> new Match(this, matchHistoryService,deployer, registrationService, supplier, g, COMPUTER_VS_COMPUTER).playMatch();
            case 4 -> matchHistoryService.displayHistory();
            case 5 -> g.setBoardSize();
            case 6 -> registrationService.logOut();
            case 7 -> registrationService.logIn();
            case 8 -> registrationService.signIn();
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
            int choice = scanner.nextInt();
            scanner.nextInt();
            if (choice > 3 || choice < 1) {
                System.out.println("Wrong input");
                continue;
            }
            return choice;
        }
    }
}
