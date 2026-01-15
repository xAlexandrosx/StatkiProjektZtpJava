package players;

import Game.Game;

public class AiPlayerEasy extends AiPlayerBase {

    public AiPlayerEasy(String name, Game g) {
        super(name, g);
    }

    @Override
    protected void sleepDelay() {
        // np. wolniej, żeby było “easy”
        try {
            Thread.sleep(g.AI_DELAY);
        } catch (InterruptedException ignored) {}
    }
}
