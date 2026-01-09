package matchhistory;

import battleship.Battleship;
import players.IPlayer;
import battleship.IBattleship;
import players.Player;

import java.util.List;

public interface IMatchHistoryService {
    void recordPlayers(IPlayer p1, IPlayer p2);

    //void recordShips(List<IBattleship> p1Ships, List<IBattleship> p2Ships);

    List<List<int[]>> extractShipCoordinates(List<IBattleship> ships);

    //void recordTurn(String player, int x, int y);

    //void setWinner(String winner);

    //void saveMatchToFile();

    List<MatchRecord> loadExistingMatches();

    //void displayHistory();
}
