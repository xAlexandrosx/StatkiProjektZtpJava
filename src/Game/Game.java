package Game;

import players.IPlayer;
import players.playerstrategy.IPlayerSupplier;
import java.util.Scanner;

public class Game {

    public final int HUMAN_PLAYER = 0;
    public final int AI_DELAY = 0;

    private final IPlayerSupplier playerSupplier;
    private final Scanner scanner;
    private IPlayer player1;
    private IPlayer player2;
    private int boardSize = 10;

    public Game(IPlayerSupplier supplier, Scanner sc) {
        this.playerSupplier = supplier;
        this.scanner = sc;
    }

    public IPlayer getPlayer(int index) {
        if (index == 1) {
            if (player1 == null) {
                IPlayer newPlayer = playerSupplier.createPlayer(HUMAN_PLAYER);
                newPlayer.setName("Guest 1");
                newPlayer.setGame(this);
                return newPlayer;
            }
            return player1;
        }
        else if (index == 2) {
            if (player2 == null) {
                IPlayer newPlayer = playerSupplier.createPlayer(HUMAN_PLAYER);
                newPlayer.setName("Guest 2");
                newPlayer.setGame(this);
                return newPlayer;
            }
            return player2;
        }
        System.out.println("Incorrect index!");
        return null;
    }

    public void setPlayer(IPlayer player, int index) {
        if (index == 1) {
            this.player1 = player;
        }
        else if (index == 2) {
            this.player2 = player;

        }
        System.out.println("Incorrect index!");
    }

    public boolean isPlayerExisting(int index) {
        return (index != 1 || player1 != null) && (index != 2 || player2 != null);
    }

    public int getBoardSize() {
        return boardSize;
    }

    public void setBoardSize() {

        System.out.print("Enter new board size: ");
        int size = scanner.nextInt();
        scanner.nextLine();
        if (size < 10) {
            System.out.println("Boardsize must be 10 or greater.");
        }
        else {
            this.boardSize = size;
            System.out.println("Board size updated.");
        }
    }
}
