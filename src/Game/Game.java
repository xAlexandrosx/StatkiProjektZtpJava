package Game;

import board.BattleshipDeployer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import matchhistory.MatchHistoryService;
import menu.ConsoleMenu;
import players.AiPlayer;
import players.HumanPlayer;
import registrationservice.RegistrationService;

import java.util.Random;

public class Game {

    public final int PLAYER_VS_PLAYER = 0;
    public final int PLAYER_VS_COMPUTER = 1;
    public final int COMPUTER_VS_COMPUTER = 2;

    public Random random = new Random();
    public BattleshipDeployer battleshipDeployer = new BattleshipDeployer(this);
    public ConsoleMenu consoleMenu = new ConsoleMenu(this);
    public RegistrationService rService = new RegistrationService(this);
    public MatchHistoryService mhService = new MatchHistoryService(this);
    public Gson gson = new GsonBuilder().setPrettyPrinting().create();

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
        if (index == 1 && player1 == null) {
            return false;
        }
        else if (index == 2 && player2 == null) {
            return false;
        }
        else {
            return true;
        }
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

    public void setBoardSize(int boardSize) {

        if (boardSize < 10) {
            System.out.println("Boardsize must be 10 or greater.");
        }
        else {
            this.boardSize = boardSize;
            System.out.println("Boardsize updated.");
        }
    }
}
