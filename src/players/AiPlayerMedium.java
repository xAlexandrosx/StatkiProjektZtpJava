package players;

import Game.Game;

public class AiPlayerMedium extends AiPlayerBase {

    public AiPlayerMedium(String name, Game g) {
        super(name, g);
    }

    @Override
    protected void sleepDelay() {
        // średnie tempo
        try {
            Thread.sleep(g.AI_DELAY);
        } catch (InterruptedException ignored) {}
    }
}
