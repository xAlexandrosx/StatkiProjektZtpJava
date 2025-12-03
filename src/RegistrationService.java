import java.io.*;
import java.nio.file.*;
import java.util.*;

public class RegistrationService {

    private static final String PLAYER_FILE = "src/players.csv";
    private final Menu m;

    public RegistrationService(Menu menu) {
        this.m = menu;
    }

    private List<String> readPlayers() {
        try {
            return Files.readAllLines(Paths.get(PLAYER_FILE));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    private void writePlayer(String name) {
        try (FileWriter fw = new FileWriter(PLAYER_FILE, true)) {
            fw.write(name + System.lineSeparator());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isLoggedIn(String playerName) {
        return (m.getP1() != null && m.getP1().getName().equals(playerName)) ||
                (m.getP2() != null && m.getP2().getName().equals(playerName));
    }

    public void logIn(String playerName) {
        List<String> players = readPlayers();

        if (!players.contains(playerName)) {
            System.out.println("No such account exists.");
            return;
        }

        if (isLoggedIn(playerName)) {
            System.out.println("Player already logged in.");
            return;
        }

        if (m.getP1() == null) {
            m.setP1(new HumanPlayer(playerName));
            System.out.println(playerName + " logged in as Player 1.");
        } else if (m.getP2() == null) {
            m.setP2(new HumanPlayer(playerName));
            System.out.println(playerName + " logged in as Player 2.");
        } else {
            System.out.println("Two players are already logged in.");
        }
    }

    public void logOut(int playerIndex) {
        if (playerIndex == 0 && m.getP1() != null) {
            System.out.println(m.getP1().getName() + " logged out.");
            m.setP1(null);
            return;
        }
        if (playerIndex == 1 && m.getP2() != null) {
            System.out.println(m.getP2().getName() + " logged out.");
            m.setP2(null);
            return;
        }

        System.out.println("No player logged in at index " + playerIndex + ".");
    }

    public void signIn(String playerName) {
        List<String> players = readPlayers();

        if (players.contains(playerName)) {
            System.out.println("Name already taken.");
            return;
        }

        writePlayer(playerName);
        System.out.println("Account created for: " + playerName);
    }
}
