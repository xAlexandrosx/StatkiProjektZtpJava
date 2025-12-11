package playerprovider;

import players.IPlayer;
import players.PlayerStrategy;

public interface IPlayerProvider {

    PlayerStrategy getPlayerStrategy();

    void setPlayerStrategy(PlayerStrategy playerStrategy);

    IPlayer provideAi();
}
