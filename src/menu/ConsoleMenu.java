package menu;

import ServiceLocator.ServiceLocator;
import Match.Match;
import battleship.Battleship;
import board.Board;
import command.ICommand;
import command.ShootCommand;
import matchhistory.MatchRecord;
import matchhistory.TurnRecord;
import registrationservice.PlayerProfile;

import java.util.*;

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
            case 4 -> displayHistory(); //sl.matchHistoryService.displayHistory();
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

    public void displayHistory(){
        List<MatchRecord> matches = sl.matchHistoryService.loadExistingMatches();

        if (matches.isEmpty()){
            System.out.println("There is no match history yet!");
            return;
        }

        Collections.reverse(matches);
        int start = 0;
        int end = Math.min(matches.size(), 5);

        while(true){
            for (int i = start; i < end; i++){
                MatchRecord match = matches.get(i);

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

            System.out.println("\nChoose option:");
            System.out.println("0. Exit");
            System.out.println("1. Previous page");
            System.out.println("2. Next page");
            for (int i = start; i < end; i++) {
                MatchRecord match = matches.get(i);
                System.out.println((i + 3 - start) + ". Replay match " + match.gameID);
            }

            int choice = sl.scanner.nextInt();
            if (choice == 0) {
                System.out.println("Returning to the main menu.");
                return;
            }
            else if (choice == 1){
                if (start - 5 >= 0){
                    end = start;
                    start = start - 5;
                    continue;
                }

                System.out.println("You are already on the first page of the history.");
            }
            else if(choice == 2){
                if (start + 5  <= matches.size()){
                    start = start + 5;
                    end = Math.min(end + 5, matches.size());
                    continue;
                }

                System.out.println("You are already on the last page of the history.");
            }
            else if (choice > 2 && choice < (end - start) /* Liczba dostępnych meczy */ + 3 /* Przesunięcie */){
                replayMatch(matches.get(start + choice - 3));
            }
            else{
                System.out.println("Unknown option, try again.");
            }
        }
    }
    private void replayMatch(MatchRecord matchRecord){
        sl.replayService.LoadMatch(matchRecord);

        System.out.println("Starting a replay between " + matchRecord.player1 + " and " + matchRecord.player2);

        while(true) {
            System.out.println("Round " + sl.replayService.getTurnCounter());

            System.out.println(matchRecord.player1 + "'s board");
            sl.replayService.displayBoard(matchRecord.player1);

            System.out.println(matchRecord.player2 + "'s board");
            sl.replayService.displayBoard(matchRecord.player2);

            System.out.println("\nChoose option:");
            System.out.println("0. Exit");
            System.out.println("1. Previous turn");
            System.out.println("2. Next turn");

            int choice = sl.scanner.nextInt();
            sl.scanner.nextLine();

            if (choice == 0) {
                return;
            }
            else if (choice == 1) {
                if (sl.replayService.PreviousMove()) {
                    System.out.println(matchRecord.turns.get(sl.replayService.getTurnCounter()-1).player + "'s turn");
                }
                else {
                    System.out.println("Can't replay the previous move.");
                }
            }
            else if (choice == 2){
                if (sl.replayService.NextMove()){
                    System.out.println(matchRecord.turns.get(sl.replayService.getTurnCounter()-1).player + "'s turn");
                }
                else{
                    System.out.println("Can't replay the next move.");
                }
            }
            else{
                System.out.println("Unknown option, try again.");
            }

            if(sl.replayService.getTurnCounter() == matchRecord.turns.size()){
                System.out.println("The match is over - " + matchRecord.winner + " won the game!");
            }
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
    private void displayPlayerProfile(PlayerProfile profile){
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
