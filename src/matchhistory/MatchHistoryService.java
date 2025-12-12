package matchhistory;
import Game.Game;
import battleship.Battleship;
import com.google.gson.Gson;
import players.IPlayer;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MatchHistoryService implements IMatchHistoryService {

    private final String FILE_PATH = "src/matchhistory/match_history.json";

    private final Gson gson;
    private MatchRecord current;

    public MatchHistoryService(Gson gson) {
        this.gson = gson;
    }

    public void recordPlayers(IPlayer p1, IPlayer p2) {
        current = new MatchRecord();
        current.turns = new ArrayList<>();

        List<MatchRecord> matches = loadExistingMatches();
        current.gameID = String.valueOf(matches.size() + 1);
        current.date = LocalDate.now().toString();
        current.time = LocalTime.now().withNano(0).toString();
        current.player1 = p1.getName();
        current.player2 = p2.getName();
        current.boardSize = p1.getOwnBoard().getSize();
    }

    public void recordShips(List<Battleship> p1Ships, List<Battleship> p2Ships) {
        current.ships1 = extractShipCoordinates(p1Ships);
        current.ships2 = extractShipCoordinates(p2Ships);
    }

    public List<List<int[]>> extractShipCoordinates(List<Battleship> ships) {
        List<List<int[]>> list = new ArrayList<>();

        for (Battleship ship : ships) {
            List<int[]> coords = new ArrayList<>();
            for (int[] t : ship.getTiles()) {
                coords.add(new int[]{ t[0], t[1] });
            }
            list.add(coords);
        }
        return list;
    }

    public void recordTurn(String player, int x, int y) {
        current.turns.add(new TurnRecord(player, x, y));
    }

    public void setWinner(String winner) {
        current.winner = winner;
    }

    public void saveMatchToFile() {
        List<MatchRecord> matches = loadExistingMatches();
        matches.add(current);

        try (Writer writer = new FileWriter(FILE_PATH)) {
            gson.toJson(matches, writer);
        } catch (IOException e) {
            System.out.println("Error: could not save match history.");
        }
    }

    public List<MatchRecord> loadExistingMatches() {
        List<MatchRecord> matches = new ArrayList<>();

        try (Reader reader = new FileReader(FILE_PATH)) {
            MatchRecord[] existing = gson.fromJson(reader, MatchRecord[].class);
            if (existing != null) {
                matches.addAll(Arrays.asList(existing));
            }
        } catch (IOException e) {
            System.out.println("Warning: match history file not found or unreadable.");
        }

        return matches;
    }

    public void displayHistory() {
        List<MatchRecord> matches;

        try (Reader reader = new FileReader(FILE_PATH)) {
            MatchRecord[] existing = gson.fromJson(reader, MatchRecord[].class);
            if (existing == null || existing.length == 0) {
                System.out.println("No match history found.");
                return;
            }
            matches = Arrays.asList(existing);
        } catch (IOException e) {
            System.out.println("Error reading match history.");
            return;
        }

        Collections.reverse(matches);

        for (MatchRecord match : matches) {
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
    }
}
