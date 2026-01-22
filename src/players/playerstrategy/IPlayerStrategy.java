package players.playerstrategy;

import players.IPlayer;

import java.util.Map;

public interface IPlayerStrategy { // Interfejs klasy PlayerStrategy

    Map<Integer, IPlayer> getPlayerMap();

    IPlayer getPlayer(int difficulty);
}
