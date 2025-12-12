package players;

import Game.Game;

public class AiPlayerEasy extends Player {

    public AiPlayerEasy(String name, Game g) {
        super(name, g);
    }

    @Override
    public void takeTurn() {

        int size = g.getBoardSize();
        int x = 0, y = 0;

        boolean found = false;

        // limiter losowych prób
        int MAX_ATTEMPTS = 200;

        for (int attempts = 0; attempts < MAX_ATTEMPTS; attempts++) {
            x = g.random.nextInt(size);
            y = g.random.nextInt(size);

            int tile = enemyBoard.getTile(x, y);

            if (tile == 0 || tile == 1) {
                found = true;
                break;
            }
        }

        // nie znalzalo pola — fallback skanowania planszy
        if (!found) {
            for (int i = 0; i < size && !found; i++) {
                for (int j = 0; j < size && !found; j++) {
                    int tile = enemyBoard.getTile(i, j);
                    if (tile == 0 || tile == 1) {
                        x = i;
                        y = j;
                        found = true;
                    }
                }
            }
        }

        // poprawne  strzały x y
        g.matchHistoryService.recordTurn(playerProfile.getName(), x, y);

        System.out.println(playerProfile.getName() + " shoots at " + x + ", " + y);

        enemyBoard.registerShot(x, y);
        enemyBoard.displayBoard(false);

        try { Thread.sleep(g.AI_DELAY); }
        catch (InterruptedException ignored) {}
    }


}
