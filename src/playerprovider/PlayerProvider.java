package playerprovider;

import Game.Game;
import players.IPlayer;
import players.PlayerStrategy;

public class PlayerProvider implements IPlayerProvider {

    private final Game g;

    private PlayerStrategy playerStrategy;

    public PlayerProvider(PlayerStrategy playerStrategy, Game g) {
        this.g = g;
        this.playerStrategy = playerStrategy;
    }

    public PlayerStrategy getPlayerStrategy() {
        return playerStrategy;
    }

    public void setPlayerStrategy(PlayerStrategy playerStrategy) {
        this.playerStrategy = playerStrategy;
    }

    public IPlayer provideAi() {
        int choice;
        while (true) {
            System.out.println("Choose Ai difficulty:");
            System.out.println("1. Easy");
            System.out.println("2. Medium");
            System.out.println("3. Cheater");
            choice = g.scanner.nextInt();
            if (choice < 1 || choice > 3) {
                System.out.println("Choose correct option.");
                continue;
            }
            break;
        }

        return playerStrategy.getAiDifficulty(choice);
    }
}
