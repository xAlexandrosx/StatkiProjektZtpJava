package matchhistory;

import battleship.Battleship;
import players.IPlayer;
import players.Player;

import java.util.List;

public interface IMatchHistoryService {
    void recordPlayers(IPlayer p1, IPlayer p2);

    void recordShips(List<Battleship> p1Ships, List<Battleship> p2Ships);

    List<List<int[]>> extractShipCoordinates(List<Battleship> ships);

    void recordTurn(String player, int x, int y);

    void setWinner(String winner);

    void saveMatchToFile();

    List<MatchRecord> loadExistingMatches();

    void displayHistory();
}
