package globalvariables;

import ServiceLocator.ServiceLocator;
import players.IPlayer;

public class GlobalVariables implements IGlobalVariables {
    public final int HUMAN_PLAYER = 0;

    private final int AI_DELAY = 0;

    private IPlayer player1;
    private IPlayer player2;

    private int boardSize = 10;

    public IPlayer getPlayer(int index) {
        ServiceLocator sl = ServiceLocator.getInstance();

        if (index == 1) {
            if (player1 == null) {
                IPlayer newPlayer = sl.getPlayerSupplier().createPlayer(HUMAN_PLAYER);
                newPlayer.setName("Guest 1");
                return newPlayer;
            }
            return player1;
        } else if (index == 2) {
            if (player2 == null) {
                IPlayer newPlayer = sl.getPlayerSupplier().createPlayer(HUMAN_PLAYER);
                newPlayer.setName("Guest 2");
                return newPlayer;
            }
            return player2;
        }
        System.out.println("Incorrect index!");
        return null;
    }

    public boolean isPlayerExisting(int index) {
        return (index != 1 || player1 != null) && (index != 2 || player2 != null);
    }

    public void setPlayer(IPlayer player, int index) {
        if (index == 1) {
            this.player1 = player;
        } else if (index == 2) {
            this.player2 = player;

        }
        System.out.println("Incorrect index!");
    }

    public int getBoardSize() {
        return boardSize;
    }

    public void setBoardSize() {
        ServiceLocator sl = ServiceLocator.getInstance();

        System.out.print("Enter new board size: ");
        int size = sl.getScanner().nextInt();
        sl.getScanner().nextLine();
        if (size < 10) {
            System.out.println("Boardsize must be 10 or greater.");
        } else {
            this.boardSize = size;
            System.out.println("Board size updated.");
        }
    }

    @Override
    public int getAiDelay() {
        return AI_DELAY;
    }
}