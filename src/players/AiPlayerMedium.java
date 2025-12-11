package players;

import Game.Game;
import java.util.LinkedList;
import java.util.Queue;

public class AiPlayerMedium extends Player {

    private final Queue<int[]> targetQueue = new LinkedList<>();
    private final boolean[][] visited;

    public AiPlayerMedium(Game g) {
        super(g);
        this.setName("Computer Medium");
        this.visited = new boolean[g.getBoardSize()][g.getBoardSize()];
    }

    @Override
    public void takeTurn() {
        int[] shot;

        if (!targetQueue.isEmpty()) {
            shot = getNextTargetTile();
        } else {
            shot = getHuntTile();
        }

        int x = shot[0];
        int y = shot[1];
        visited[x][y] = true;

        g.matchHistoryService.recordTurn(this.name, x, y);

        System.out.println(name + " shoots at " + x + ", " + y);
        boolean wasHit = enemyBoard.registerShot(x, y);

        enemyBoard.displayBoard(false);

        if (wasHit) {
            addAdjacentTargets(x, y);
        }

        try {
            Thread.sleep(g.AI_DELAY);
        } catch (InterruptedException e) {
            System.out.println("Waiting failed for some reason...");
        }
    }

    private int[] getHuntTile() {
        int size = g.getBoardSize();

        while (true) {
            int x = g.random.nextInt(size);
            int y = g.random.nextInt(size);

            if ((x + y) % 2 != 0) continue;

            if (!visited[x][y]) {
                int tile = enemyBoard.getTile(x, y);
                if (tile == 0 || tile == 1) return new int[]{x, y};
            }
        }
    }

    private int[] getNextTargetTile() {
        while (!targetQueue.isEmpty()) {
            int[] tile = targetQueue.poll();
            int x = tile[0];
            int y = tile[1];

            if (isValidTile(x, y) && !visited[x][y]) {
                return tile;
            }
        }
        return getHuntTile();
    }

    private boolean isValidTile(int x, int y) {
        int size = g.getBoardSize();
        return x >= 0 && x < size && y >= 0 && y < size;
    }

    private void addAdjacentTargets(int x, int y) {
        targetQueue.add(new int[]{x+1, y});
        targetQueue.add(new int[]{x-1, y});
        targetQueue.add(new int[]{x, y+1});
        targetQueue.add(new int[]{x, y-1});
    }
}

