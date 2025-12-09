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
import registrationservice.IRegistrationService;
import registrationservice.PlayerProfile;
import registrationservice.RegistrationService;
import statisticsservice.IStatisticsService;
import statisticsservice.StatisticsService;

import java.util.Random;
import java.util.Scanner;

public class Game {

    public final int PLAYER_VS_PLAYER = 0;
    public final int PLAYER_VS_COMPUTER = 1;
    public final int COMPUTER_VS_COMPUTER = 2;

    public final Random random = new Random();
    public final Scanner scanner = new Scanner(System.in);
    public final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public final IBattleshipDeployer battleshipDeployer = new BattleshipDeployer(this);
    public final IMenu consoleMenu = new ConsoleMenu(this);
    public final IRegistrationService registrationService = new RegistrationService(this);
    public final IMatchHistoryService matchHistoryService = new MatchHistoryService(this);
    public final IStatisticsService statisticsService = new StatisticsService(this);

    private HumanPlayer player1;
    private HumanPlayer player2;

    private int boardSize = 10;

    public HumanPlayer getPlayer(int index) {
        if (index == 1) {
            if (player1 == null) {
                return new HumanPlayer("Guest1", this);
            }
            return player1;
        }
        else if (index == 2) {
            if (player2 == null) {
                return new HumanPlayer("Guest2", this);
            }
            return player2;
        }
        System.out.println("Incorrect index!");
        return null;
    }

    public boolean isPlayerExisting(int index) {
        return (index != 1 || player1 != null) && (index != 2 || player2 != null);
    }

    public void setPlayer(HumanPlayer player, int index) {
        if (index == 1) {
            this.player1 = player;
        }
        else if (index == 2) {
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
        }
        else {
            this.boardSize = size;
            System.out.println("Board size updated.");
        }
    }
}
