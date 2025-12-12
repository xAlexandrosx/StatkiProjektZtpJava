package Game;

import board.BattleshipDeployer;
import board.IBattleshipDeployer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import matchhistory.IMatchHistoryService;
import matchhistory.MatchHistoryService;
import menu.ConsoleMenu;
import menu.IMenu;
import players.HumanPlayer;
import players.IPlayer;
import players.playerstrategy.IPlayerSupplier;
import registrationservice.IRegistrationService;
import registrationservice.RegistrationService;
import registrationservice.RegistrationServiceAccessProxy;

import java.util.Random;
import java.util.Scanner;

public class Game {

    public Game(IPlayerSupplier playerSupplier) {
        this.playerSupplier = playerSupplier;
    }

    public final int PLAYER_VS_PLAYER = 0;
    public final int PLAYER_VS_COMPUTER = 1;
    public final int COMPUTER_VS_COMPUTER = 2;

    public final int HUMAN_PLAYER = 0;

    public final int AI_DELAY = 0;

    public final Random random = new Random();
    public final Scanner scanner = new Scanner(System.in);
    public final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public final IBattleshipDeployer battleshipDeployer = new BattleshipDeployer(this);
    public final IMenu consoleMenu = new ConsoleMenu(this);
    public final IRegistrationService registrationService =
            new RegistrationServiceAccessProxy(new RegistrationService(this));
    public final IMatchHistoryService matchHistoryService = new MatchHistoryService(this);
    public final IPlayerSupplier playerSupplier;

    private IPlayer player1;
    private IPlayer player2;

    private int boardSize = 10;

    public IPlayer getPlayer(int index) {
        if (index == 1) {
            if (player1 == null) {
                IPlayer newPlayer = playerSupplier.createPlayer(HUMAN_PLAYER);
                newPlayer.setName("Guest 1");
                newPlayer.setGame(this);
                return newPlayer;
            }
            return player1;
        } else if (index == 2) {
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

        System.out.print("Enter new board size: ");
        int size = scanner.nextInt();
        scanner.nextLine();
        if (size < 10) {
            System.out.println("Boardsize must be 10 or greater.");
        } else {
            this.boardSize = size;
            System.out.println("Board size updated.");
        }
    }
}
