import ServiceLocator.ServiceLocator;
import players.*;
import players.playerstrategy.IPlayerStrategy;
import players.playerstrategy.IPlayerSupplier;
import players.playerstrategy.PlayerStrategy;
import players.playerstrategy.PlayerSupplier;

void main() {

    Map<Integer, IPlayer> playerMap = new HashMap<>();
    playerMap.put(0, new HumanPlayer("", null));
    playerMap.put(1, new AiPlayerEasy("", null));
    playerMap.put(2, new AiPlayerMedium("", null));
    playerMap.put(3, new AiPlayerDifficult("", null));

    IPlayerStrategy playerStrategy = new PlayerStrategy(playerMap);
    IPlayerSupplier playerSupplier = new PlayerSupplier(playerStrategy);

    ServiceLocator sl = new ServiceLocator(playerSupplier);

    int option;
    do {
        sl.consoleMenu.display();
        option = sl.consoleMenu.handleInput();
    } while (option != -1);
}

