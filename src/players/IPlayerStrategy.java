package players;

import java.util.Map;

public interface IPlayerStrategy {

    IPlayer getAiDifficulty(int option);

    Map<Integer, IPlayer> getAiDifficultyMap();

}
