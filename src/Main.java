import Game.Game;
import players.*;
import players.playerstrategy.IPlayerStrategy;
import players.playerstrategy.IPlayerSupplier;
import players.playerstrategy.PlayerStrategy;
import players.playerstrategy.PlayerSupplier;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {

    Map<Integer, IPlayer> playerMap = new HashMap<>();
    playerMap.put(0, new HumanPlayer("", null));
    playerMap.put(1, new AiPlayerEasy("",null));
    playerMap.put(2, new AiPlayerMedium("",null));
    playerMap.put(3, new AiPlayerDifficult("",null));

    IPlayerStrategy playerStrategy = new PlayerStrategy(playerMap);
    IPlayerSupplier playerSupplier = new PlayerSupplier(playerStrategy);

    Game g = new Game(playerSupplier);

        int option;
        do {
            g.consoleMenu.display();
            option = g.consoleMenu.handleInput();
        } while (option != -1);
    }
}

