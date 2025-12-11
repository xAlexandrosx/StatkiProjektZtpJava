package players;

import java.util.Map;

public class PlayerStrategy implements IPlayerStrategy {

    private final Map<Integer, IPlayer> AiDifficulties;

    public PlayerStrategy(Map<Integer, IPlayer> AiDifficulties) {
        this.AiDifficulties = AiDifficulties;
    }

    @Override
    public IPlayer getAiDifficulty(int option) {

        return AiDifficulties.getOrDefault(option, null);
    }

    @Override
    public Map<Integer, IPlayer> getAiDifficultyMap() {
        return AiDifficulties;
    }
}
