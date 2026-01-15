package players;

import Game.Game;

public class AiPlayerDifficult extends AiPlayerBase {

    public AiPlayerDifficult(String name, Game g) {
        super(name, g);
    }

    @Override
    protected void sleepDelay() {
        // np. szybciej albo bez opóźnienia — zależnie jak chcesz
        try {
            Thread.sleep(g.AI_DELAY);
        } catch (InterruptedException ignored) {}
    }
}
