package players.playerstrategy;

import players.IPlayer;

import java.util.Map;

public class PlayerStrategy implements IPlayerStrategy {
    private final Map<Integer, IPlayer> playerMap;

    public PlayerStrategy(Map<Integer, IPlayer> playerMap) {
        this.playerMap = playerMap;
    }

    @Override
    public Map<Integer, IPlayer> getPlayerMap() {
        return playerMap;
    }

    @Override
    public IPlayer getPlayer(int difficulty) {

        return playerMap.getOrDefault(difficulty, null);
    }
}
