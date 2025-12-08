package registrationservice;
import Game.Game;
import players.HumanPlayer;
import players.Player;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class RegistrationService {

    private final Game g;

    public RegistrationService(Game g) {
        this.g = g;
    }

    private List<String> readPlayers() {
        try {
            return Files.readAllLines(Paths.get("src/players/players.csv"));
        } catch (IOException e) {
            System.out.println("Couldn't read file.");
        }
        return new ArrayList<>();
    }

    private void writePlayer(String name) {
        try (FileWriter fw = new FileWriter("src/players/players.csv", true)) {
            fw.write(name + System.lineSeparator());
        } catch (IOException e) {
            System.out.println("Couldn't write to file.");
        }
    }

    private boolean isLoggedIn(String playerName) {
        Player p1 = g.getPlayer(1);
        Player p2 = g.getPlayer(2);

        boolean isPlayer1 = p1 != null && p1.getName().equals(playerName);
        boolean isPlayer2 = p2 != null && p2.getName().equals(playerName);

        return isPlayer1 || isPlayer2;
    }


    public void logIn(String playerName) {
        List<String> players = readPlayers();

        if (!players.contains(playerName)) {
            System.out.println("No such account exists.");
            return;
        }

        if (isLoggedIn(playerName)) {
            System.out.println("players.Player already logged in.");
            return;
        }

        if (g.getPlayer(1) == null) {
            g.setPlayer(new HumanPlayer(playerName, g) ,1);
            System.out.println(playerName + " logged in as players.Player 1.");
        }
        else if (g.getPlayer(2) == null) {
            g.setPlayer(new HumanPlayer(playerName, g) ,2);
            System.out.println(playerName + " logged in as players.Player 2.");
        }
        else {
            System.out.println("Two players are already logged in.");
        }
    }

    public void logOut(int playerIndex) {
        if (playerIndex == 0 && g.getPlayer(1) != null) {
            System.out.println(g.getPlayer(1).getName() + " logged out.");
            g.setPlayer(null ,1);
        }
        else if (playerIndex == 1 && g.getPlayer(2) != null) {
            System.out.println(g.getPlayer(2).getName() + " logged out.");
            g.setPlayer(null ,2);
        }
        else {
            System.out.println("No player logged in at index " + playerIndex + ".");
        }
    }

    public void signIn(String playerName) {
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
