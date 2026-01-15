import ServiceLocator.ServiceLocator;
import players.*;
import players.playerstrategy.IPlayerStrategy;
import players.playerstrategy.IPlayerSupplier;
import players.playerstrategy.PlayerStrategy;
import players.playerstrategy.PlayerSupplier;

void main() {

    Map<Integer, IPlayer> playerMap = new HashMap<>();
    playerMap.put(0, new HumanPlayer(""));
    playerMap.put(1, new AiPlayerEasy(""));
    playerMap.put(2, new AiPlayerMedium(""));
    playerMap.put(3, new AiPlayerDifficult(""));

    IPlayerStrategy playerStrategy = new PlayerStrategy(playerMap);
    IPlayerSupplier playerSupplier = new PlayerSupplier(playerStrategy);

    // We initialize the singleton instance
    ServiceLocator.init(playerSupplier);

    ServiceLocator serviceLocator = ServiceLocator.getInstance();
    int option;
    do {
        ServiceLocator.getInstance().getConsoleMenu().display();
        option = serviceLocator.getConsoleMenu().handleInput();
    } while (option != -1);
}

