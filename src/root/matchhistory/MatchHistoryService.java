package root.matchhistory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import root.battleship.Battleship;
import root.players.Player;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class MatchHistoryService {

    private MatchRecord current;

    public void recordPlayers(Player p1, Player p2, int boardSize) {
        current = new MatchRecord();

        Gson gson = new Gson();
        List<MatchRecord> matches = new ArrayList<>();
        try (Reader reader = new FileReader("match_history.json")) {
            MatchRecord[] existing = gson.fromJson(reader, MatchRecord[].class);
            if (existing != null) matches.addAll(Arrays.asList(existing));
        } catch (FileNotFoundException ignored) {
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Assign game ID based on number of existing matches + 1
        current.gameID = String.valueOf(matches.size() + 1);

        current.date = LocalDate.now().toString();
        current.time = LocalTime.now().withNano(0).toString();
        current.player1 = p1.getName();
        current.player2 = p2.getName();
        current.boardSize = boardSize;
    }


    public void recordShips(List<Battleship> p1Ships, List<Battleship> p2Ships) {
        current.ships1 = extractShipCoordinates(p1Ships);
        current.ships2 = extractShipCoordinates(p2Ships);
    }

    private List<List<int[]>> extractShipCoordinates(List<Battleship> ships) {
        List<List<int[]>> list = new ArrayList<>();

        for (Battleship bs : ships) {
            List<int[]> coords = new ArrayList<>();
            for (int[] t : bs.getTiles()) {
                coords.add(new int[] { t[0], t[1] });
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
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        List<MatchRecord> matches = new ArrayList<>();

        try (Reader reader = new FileReader("match_history.json")) {
            MatchRecord[] existing = gson.fromJson(reader, MatchRecord[].class);
            if (existing != null) matches.addAll(Arrays.asList(existing));
        } catch (FileNotFoundException ignored) {}
        catch (IOException e) { e.printStackTrace(); }

        matches.add(current);

        try (FileWriter writer = new FileWriter("match_history.json")) {
            gson.toJson(matches, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
