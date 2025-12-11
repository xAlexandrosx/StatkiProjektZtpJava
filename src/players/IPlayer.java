package players;

import Game.Game;
import board.Board;
import registrationservice.PlayerProfile;

public interface IPlayer {

    void takeTurn();

    String getName();

    void setName(String name);

    void setGame(Game g);

    PlayerProfile getPlayerProfile();
    void setPlayerProfile(PlayerProfile pf);
    Board getOwnBoard();
    Board getEnemyBoard();
    void setOwnBoard(Board b);
    void setEnemyBoard(Board b);
}
