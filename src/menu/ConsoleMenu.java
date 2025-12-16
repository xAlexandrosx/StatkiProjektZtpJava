package menu;

import ServiceLocator.ServiceLocator;
import Match.Match;
import registrationservice.PlayerProfile;

import java.util.Comparator;
import java.util.List;

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
        System.out.println("9. Display ranking");
        System.out.println("10. Exit");
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
            case 9 -> displayRanking();
            case 10 -> {
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

    public void displayRanking(){
        List<PlayerProfile> playerProfiles = sl.registrationServiceProxy.loadPlayers();
        playerProfiles.sort(Comparator.comparingInt(PlayerProfile::GetMatchesWon));
        List<PlayerProfile> profilesOrdered = playerProfiles.reversed();

        if (profilesOrdered.size() > 0){
            System.out.println("\n===================== RANKING =====================");
            for (int i = 0; i < profilesOrdered.size(); i++){
                PlayerProfile profile = profilesOrdered.get(i);
                System.out.println((i+1) + ". " + profile.getName() + ", " + profile.GetMatchesWon() + "/" + profile.GetMatchesPlayed() + " won matches.");
            }
        }
        else{
            System.out.println("No registered players yet");
        }


        while (true){
            System.out.println("\nChoose an option:");
            System.out.println("0. Exit.");
            for (int i = 0; i < profilesOrdered.size(); i++){
                PlayerProfile profile = profilesOrdered.get(i);
                System.out.println((i+1) + ". See " + profile.getName() + "'s player profile.");
            }

            int choice = sl.scanner.nextInt();
            if (choice == 0){
                System.out.println("Returning to the main menu.");
                return;
            }
            else if(choice > 0 && choice <= profilesOrdered.size()){
                displayPlayerProfile(profilesOrdered.get(choice-1));
            }
            else{
                System.out.println("Unknown option, try again.");
            }
        }
    }
    public void displayPlayerProfile(PlayerProfile profile){
        System.out.println("\n===================== " + profile.getName() +"'s PROFILE =====================");

        System.out.println("\nMatches played: " + profile.GetMatchesPlayed());
        System.out.println("Matches won: " + profile.GetMatchesWon());
        System.out.println("Matches lost: " + profile.GetMatchesLost());
        System.out.println("Win ratio: " + profile.GetWinRatio());

        System.out.println("\nShots hit: " + profile.GetMissCount());
        System.out.println("Shots missed: " + profile.GetMissCount());
        System.out.println("Accuracy: " + profile.GetAccuracy());

        System.out.println("\nAverage number of moves per match: " + profile.GetMovesPerCount());
        System.out.println("Total number of moves: " + profile.GetMoveCount());

        sl.scanner.next();
        sl.scanner.nextLine();
    }
}
