package registrationservice;
import Game.Game;
import players.HumanPlayer;
import players.IPlayer;
import players.Player;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class RegistrationService implements IRegistrationService {

    private final Game g;
    private final String FILE_PATH = "src/players/players.csv";

    public RegistrationService(Game g) {
        this.g = g;
    }

    public List<String> readPlayers() {
        try {
            return Files.readAllLines(Paths.get(FILE_PATH));
        } catch (IOException e) {
            System.out.println("Couldn't read file.");
        }
        return new ArrayList<>();
    }

    public void writePlayer(String name) {
        try (FileWriter fw = new FileWriter(FILE_PATH, true)) {
            fw.write(name + System.lineSeparator());
        } catch (IOException e) {
            System.out.println("Couldn't write to file.");
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
        String playerName = g.scanner.nextLine();

        List<String> players = readPlayers();

        if (!players.contains(playerName)) {
            System.out.println("No such account exists.");
            return;
        }

        if (isLoggedIn(playerName)) {
            System.out.println("Player already logged in.");
            return;
        }

        if (!g.isPlayerExisting(1)) {
            IPlayer player = new HumanPlayer(g);
            player.setName(playerName);
            g.setPlayer(player,1);
            System.out.println(playerName + " logged in as Player 1.");
        }
        else if (!g.isPlayerExisting(2)) {
            IPlayer player = new HumanPlayer(g);
            player.setName(playerName);
            g.setPlayer(player,2);
            System.out.println(playerName + " logged in as Player 2.");
        }
        else {
            System.out.println("Two players are already logged in.");
        }
    }

    public void logOut() {
        System.out.println("Logout:");
        System.out.println("0 - Log out players.Player 1");
        System.out.println("1 - Log out players.Player 2");

        int idx = g.scanner.nextInt();
        g.scanner.nextLine();

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
        String playerName = g.scanner.nextLine();

        List<String> players = readPlayers();

        if (players.contains(playerName)) {
            System.out.println("Name already taken.");
        }
        else {
            writePlayer(playerName);
            System.out.println("Account created for: " + playerName);
        }
    }
}
