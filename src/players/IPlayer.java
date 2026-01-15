package players;

import ServiceLocator.ServiceLocator;
import board.Board;
import registrationservice.PlayerProfile;

public interface IPlayer {

    void takeTurn();

    String getName();

    void setName(String name);

    PlayerProfile getPlayerProfile();
    void setPlayerProfile(PlayerProfile pf);
    Board getOwnBoard();
    Board getEnemyBoard();
    void setOwnBoard(Board b);
    void setEnemyBoard(Board b);
}
