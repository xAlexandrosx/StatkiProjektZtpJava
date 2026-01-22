package matchhistory;
import ServiceLocator.ServiceLocator;
import observer.*;
import observer.notifications.*;
import players.IPlayer;
import battleship.IBattleship;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MatchHistoryService implements IMatchHistoryService, Subscriber { // Klasa odpowiadająca za obsługę historii rozgrywek
    private MatchRecord current;
    private final String FILE_PATH = "src/matchhistory/match_history.json";

    public MatchHistoryService() { // Konstruktor
        ServiceLocator.getInstance().getNotificationManager().subscribe(this);
    }

    void recordPlayers(IPlayer p1, IPlayer p2) { // Zapisywanie graczy
        current = new MatchRecord();
        current.turns = new ArrayList<>();

        List<MatchRecord> matches = loadExistingMatches();
        current.gameID = String.valueOf(matches.size() + 1);

        current.date = LocalDate.now().toString();
        current.time = LocalTime.now().withNano(0).toString();
        current.player1 = p1.getName();
        current.player2 = p2.getName();
        current.boardSize = ServiceLocator.getInstance().getGlobalVariables().getBoardSize();
    }

    // Private methods providing internal functionality
    void recordShips(List<IBattleship> p1Ships, List<IBattleship> p2Ships) { // Zapisanie statków
        current.ships1 = extractShipCoordinates(p1Ships);
        current.ships2 = extractShipCoordinates(p2Ships);
    }
    void recordTurn(String player, int x, int y) {
        current.turns.add(new TurnRecord(player, x, y));
    } // Zapisanie tury
    void setWinner(String winner) {
        current.winner = winner;
    } // Metoda ustalająca zwyciężce
    void saveMatchToFile() { // Metoda zapisująca mecz do pliku
        List<MatchRecord> matches = loadExistingMatches();
        matches.add(current);

        try (Writer writer = new FileWriter(FILE_PATH)) {
            ServiceLocator.getInstance().getGson().toJson(matches, writer);
        } catch (IOException e) {
            System.out.println("Error: could not save match history.");
        }
    }

    public List<List<int[]>> extractShipCoordinates(List<IBattleship> ships) { // Funkcja odczytująca współrzędne statków
        List<List<int[]>> list = new ArrayList<>();

        for (IBattleship ship : ships) {
            List<int[]> coords = new ArrayList<>();
            for (int[] t : ship.getTiles()) {
                coords.add(new int[]{ t[0], t[1] });
            }
            list.add(coords);
        }
        return list;
    }

    public List<MatchRecord> loadExistingMatches() { // Metoda wczytująca mecze
        List<MatchRecord> matches = new ArrayList<>();

        try (Reader reader = new FileReader(FILE_PATH)) {
            MatchRecord[] existing = ServiceLocator.getInstance().getGson().fromJson(reader, MatchRecord[].class);
            if (existing != null) {
                matches.addAll(Arrays.asList(existing));
            }
        } catch (IOException e) {
            System.out.println("Warning: match history file not found or unreadable.");
        }

        return matches;
    }
    /**
    public void displayHistory() {
        List<MatchRecord> matches;

        try (Reader reader = new FileReader(FILE_PATH)) {
            MatchRecord[] existing = sl.gson.fromJson(reader, MatchRecord[].class);
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
    **/

    @Override
    public void update(Notification notification) { // Aktualizacja powiadomień wzorca Observer
        if (notification instanceof TurnTakenNotification n){
            recordTurn(n.player().getName(), n.x(), n.y());
        }
        else if (notification instanceof MatchConfiguredNotification n){
            recordPlayers(n.player1(), n.player2());
            recordShips(n.player1Ships(), n.player2Ships());
        }
        else if (notification instanceof MatchFinishedNotification n){
            setWinner(n.winner().getName());
            saveMatchToFile();
        }
    }
}
