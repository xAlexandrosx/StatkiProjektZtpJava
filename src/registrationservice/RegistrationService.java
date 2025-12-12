package registrationservice;
import Game.Game;
import com.google.gson.Gson;
import matchhistory.IMatchHistoryService;
import players.HumanPlayer;
import players.IPlayer;
import players.Player;

import java.io.*;
import java.util.*;

public class RegistrationService implements IRegistrationService {

    private final Game g;
    private final Gson gson;
    private final Scanner scanner;
    private final IMatchHistoryService matchHistoryService;
    private final String FILE_PATH = "src/registrationservice/player_profiles.csv";

    public RegistrationService(Game g, Gson gson, Scanner sc, IMatchHistoryService mhs) {
        this.g = g;
        this.gson = gson;
        this.scanner = sc;
        this.matchHistoryService = mhs;
    }

    public List<PlayerProfile> loadPlayers() {
        List<PlayerProfile> playerProfiles = new ArrayList<>();

        try(Reader reader = new FileReader(FILE_PATH)) {
            PlayerProfile[] existing = gson.fromJson(reader, PlayerProfile[].class);

            if (existing != null){
                playerProfiles.addAll(Arrays.asList(existing));
            }
        } catch (IOException e) {
            System.out.println("Warning: player profiles file not found or unreadable.");
        }

        return playerProfiles;
    }

    public void writePlayer(PlayerProfile playerProfile) {
        List<PlayerProfile> playerProfiles = loadPlayers();
        playerProfiles.add(playerProfile);

        try (Writer writer = new FileWriter(FILE_PATH)) {
            gson.toJson(playerProfiles, writer);
        } catch (IOException e) {
            System.out.println("Error: could not save player profiles.");
        }
    }

    public void updatePlayer(PlayerProfile newProfile){
        List<PlayerProfile> playerProfiles = loadPlayers();

        PlayerProfile oldProfile = playerProfiles
                .stream()
                .filter(p -> p.getName().equals(newProfile.getName()))
                .findFirst()
                .orElse(null);

        if(oldProfile != null){
            playerProfiles.set(playerProfiles.indexOf(oldProfile), newProfile);

            try (Writer writer = new FileWriter(FILE_PATH)) {
                gson.toJson(playerProfiles, writer);
            } catch (IOException e) {
                System.out.println("Error: could not save player profiles.");
            }
        }

    }

    public boolean isLoggedIn(String playerName) {
        IPlayer p1 = g.getPlayer(1);
        IPlayer p2 = g.getPlayer(2);

        boolean isPlayer1 = p1 != null && p1.getName().equals(playerName);
        boolean isPlayer2 = p2 != null && p2.getName().equals(playerName);

        return isPlayer1 || isPlayer2;
    }

    public void logIn() {
        System.out.print("Enter player name to log in: ");
        String playerName = scanner.nextLine();

        List<PlayerProfile> players = loadPlayers();

        PlayerProfile playerProfile = players
                .stream()
                .filter(p -> p.getName().equals(playerName))
                .findFirst()
                .orElse(null);

        if (playerProfile == null) {
            System.out.println("No such account exists.");
            return;
        }

        if (isLoggedIn(playerName)) {
            System.out.println("Player already logged in.");
            return;
        }

//     HumanPlayer(String name, Game, PlayerProfile playerProfile, IMatchHistoryService, Scanner) {

            if (!g.isPlayerExisting(1)) {
            g.setPlayer(new HumanPlayer(playerName, g, playerProfile, matchHistoryService, scanner) ,1);
            System.out.println(playerName + " logged in as Player 1.");
        }
        else if (!g.isPlayerExisting(2)) {
            g.setPlayer(new HumanPlayer(playerName, g, playerProfile, matchHistoryService, scanner) ,2);
            System.out.println(playerName + " logged in as Player 2.");
        }
        else {
            System.out.println("Two players are already logged in.");
        }
    }

    public void logOut() {
        System.out.println("Logout:");
        System.out.println("0 - Log out account of Player 1");
        System.out.println("1 - Log out account of Player 2");

        int idx = scanner.nextInt();
        scanner.nextLine();

        if (idx == 0 && g.isPlayerExisting(1)) {
            System.out.println(g.getPlayer(1).getName() + " logged out.");
            g.setPlayer(null ,1);
        }
        else if (idx == 1 && g.isPlayerExisting(2)) {
            System.out.println(g.getPlayer(2).getName() + " logged out.");
            g.setPlayer(null ,2);
        }
        else {
            System.out.println("No player logged in at index " + idx + ".");
        }
    }

    public void signIn() {
        System.out.print("Enter a new username: ");
        String playerName = scanner.nextLine();

        List<PlayerProfile> players = loadPlayers();
        boolean profileExists = players
                .stream()
                .anyMatch(p -> p.getName().equals(playerName));


        if (profileExists) {
            System.out.println("Name already taken.");
        }
        else {
            PlayerProfile newProfile = new PlayerProfile(playerName);

            writePlayer(newProfile);
            System.out.println("Account created for: " + playerName);
        }
    }
}
