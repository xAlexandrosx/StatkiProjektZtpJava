package players.playerstrategy;

import players.IPlayer;

public class PlayerSupplier implements IPlayerSupplier { // Klasa łącząca strategię z graczem
    private IPlayerStrategy playerStrategy;

    public PlayerSupplier(IPlayerStrategy strategy) {
        this.playerStrategy = strategy;
    }

    public IPlayerStrategy getPlayerStrategy() {
        return playerStrategy;
    }

    public void setPlayerStrategy(IPlayerStrategy playerStrategy) {
        this.playerStrategy = playerStrategy;
    }

    public IPlayer createPlayer(int choice) {
        return playerStrategy.getPlayer(choice);
    }


}
