package Match;

import Game.Game;
import battleship.Battleship;
import board.Board;
import board.IBattleshipDeployer;
import matchhistory.IMatchHistoryService;
import menu.IMenu;
import players.AiPlayerEasy;
import players.IPlayer;
import players.Player;
import players.playerstrategy.IPlayerSupplier;
import registrationservice.IRegistrationService;
import statisticsservice.StatisticsService;

import java.util.List;

public class Match implements IMatch {

    private final int PLAYER_VS_PLAYER = 0;
    private final int PLAYER_VS_COMPUTER = 1;
    private final int COMPUTER_VS_COMPUTER = 2;

    private final Game g;
    private final IPlayer p1;
    private final IPlayer p2;

    private final IPlayerSupplier playerSupplier;
    private final IMenu menu;
    private final IMatchHistoryService matchHistoryService;
    private final IBattleshipDeployer deployer;
    private final IRegistrationService registrationService;

    public Match(IMenu menu, IMatchHistoryService mhs, IBattleshipDeployer deployer, IRegistrationService rs, IPlayerSupplier ps, Game g, int variant) {
        this.playerSupplier = ps;
        this.menu = menu;
        this.matchHistoryService = mhs;
        this.registrationService = rs;
        this.deployer = deployer;
        this.g = g;

        if (variant == PLAYER_VS_PLAYER) {
            this.p1 = g.getPlayer(1);
            this.p2 = g.getPlayer(2);
        }
        else if (variant == PLAYER_VS_COMPUTER) {
            this.p1 = g.getPlayer(1);
            IPlayer ai = playerSupplier.createPlayer(menu.userChooseAiDifficulty());
            ai.setGame(g);
            ai.setName("Computer");
            this.p2 = ai;
        }
        else if (variant == COMPUTER_VS_COMPUTER ){
            IPlayer ai1 = playerSupplier.createPlayer(menu.userChooseAiDifficulty());
            ai1.setGame(g);
            ai1.setName("Computer 1");
            this.p1 = ai1;

            IPlayer ai2 = playerSupplier.createPlayer(menu.userChooseAiDifficulty());
            ai2.setGame(g);
            ai2.setName("Computer 2");
            this.p2 = ai2;
        }
        else {
            this.p1 = null;
            this.p2 = null;
            return;
        }

        Board b1 = new Board(g);
        Board b2 = new Board(g);

        p1.setOwnBoard(b1);
        p1.setEnemyBoard(b2);

        p2.setOwnBoard(b2);
        p2.setEnemyBoard(b1);

        // te dwie linijki sa do zmiany, to tylko chwilowe rozwiązanie
        List<Battleship> ships1 = deployer.getBattleshipsRandom(g.getBoardSize());
        List<Battleship> ships2 = deployer.getBattleshipsRandom(g.getBoardSize());
        // -----------------------------------------------------------

        b1.importShips(ships1);
        b2.importShips(ships2);

        matchHistoryService.recordPlayers(p1, p2);
        matchHistoryService.recordShips(ships1, ships2);
    }

    public void playMatch() {

        System.out.println("Starting match between " + p1.getName() + " and " + p2.getName());

        IPlayer winner = null;
        IPlayer loser = null;

        while (true) {
            System.out.println("\n==========================");
            System.out.println("      " + p1.getName() + "'s turn");
            System.out.println("==========================");
            p1.takeTurn();

            if (p2.getOwnBoard().getShips().isEmpty()) {
                System.out.println(p1.getName() + " wins!");
                winner = p1;
                loser = p2;
                break;
            }

            System.out.println("\n==========================");
            System.out.println("      " + p2.getName() + "'s turn");
            System.out.println("==========================");
            p2.takeTurn();

            if (p1.getOwnBoard().getShips().isEmpty()) {
                System.out.println(p2.getName() + " wins!");
                winner = p2;
                loser = p1;
                break;
            }
        }

        StatisticsService.getInstance().RegisterMatch(winner, loser);
        matchHistoryService.setWinner(winner.getName());
        matchHistoryService.saveMatchToFile();

        // Saving the player profiles to save changes -
        // we don't need to differentiate between temporary accounts and registered one -
        // function only updates existing accounts.
        registrationService.updatePlayer(winner.getPlayerProfile());
        registrationService.updatePlayer(loser.getPlayerProfile());
    }
}
