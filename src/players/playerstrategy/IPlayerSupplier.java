package players.playerstrategy;

import players.IPlayer;

public interface IPlayerSupplier {

    IPlayerStrategy getPlayerStrategy();

    void setPlayerStrategy(IPlayerStrategy playerStrategy);

    IPlayer createPlayer(int choice);

}
