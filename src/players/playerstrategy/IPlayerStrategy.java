package players.playerstrategy;

import players.IPlayer;

import java.util.Map;

public interface IPlayerStrategy {

    Map<Integer, IPlayer> getPlayerMap();

    IPlayer getPlayer(int difficulty);
}
