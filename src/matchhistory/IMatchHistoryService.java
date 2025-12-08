package matchhistory;

import battleship.Battleship;
import players.Player;

import java.util.List;

public interface IMatchHistoryService {

    void recordPlayers(Player p1, Player p2);

    void recordShips(List<Battleship> p1Ships, List<Battleship> p2Ships);

    List<List<int[]>> extractShipCoordinates(List<Battleship> ships);

    void recordTurn(String player, int x, int y);

    void setWinner(String winner);

    void saveMatchToFile();

    List<MatchRecord> loadExistingMatches();

    void displayHistory();
}
