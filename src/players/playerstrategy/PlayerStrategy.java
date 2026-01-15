package players.playerstrategy;

import Game.Game;
import players.*;

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

        IPlayer prototype = playerMap.get(difficulty);
        if (prototype == null) return null;

        // nowa instancja na podstawie typu prototypu
        // bez tego AI vs AI z tym samym difficulty będzie jednym graczem
        if (prototype instanceof HumanPlayer) {
            return new HumanPlayer("Guest", null); // game i nazwa ustawiasz potem setGame/setName
        }
        if (prototype instanceof AiPlayerEasy) {
            return new AiPlayerEasy("Computer", null);
        }
        if (prototype instanceof AiPlayerMedium) {
            return new AiPlayerMedium("Computer", null);
        }
        if (prototype instanceof AiPlayerDifficult) {
            return new AiPlayerDifficult("Computer", null);
        }

        // jeśli dojdą nowe typy
        return null;
    }
}
