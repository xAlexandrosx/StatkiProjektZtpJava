package players.playerstrategy;

import players.IPlayer;

public interface IPlayerSupplier { // Interfejs klasy PlayerSupplier

    IPlayerStrategy getPlayerStrategy();

    void setPlayerStrategy(IPlayerStrategy playerStrategy);

    IPlayer createPlayer(int choice);

}
