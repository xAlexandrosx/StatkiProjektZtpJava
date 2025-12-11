package Match;

import Game.Game;
import battleship.Battleship;
import board.Board;
import players.AiPlayerEasy;
import players.IPlayer;
import players.Player;
import statisticsservice.StatisticsService;

import java.util.List;

public class Match implements IMatch {

    private final Game g;
    private final IPlayer p1;
    private final IPlayer p2;

    public Match(Game g, int variant) {
        this.g = g;

        if (variant == g.PLAYER_VS_PLAYER) {
            this.p1 = g.getPlayer(1);
            this.p2 = g.getPlayer(2);
        }
        else if (variant == g.PLAYER_VS_COMPUTER) {
            this.p1 = g.getPlayer(1);
            IPlayer ai = g.playerSupplier.createPlayer(g.consoleMenu.userChooseAiDifficulty());
            ai.setGame(g);
            ai.setName("Computer");
            this.p2 = ai;
        }
        else { // COMPUTER_VS_COMPUTER
            IPlayer ai1 = g.playerSupplier.createPlayer(g.consoleMenu.userChooseAiDifficulty());
            ai1.setGame(g);
            ai1.setName("Computer 1");
            this.p1 = ai1;

            IPlayer ai2 = g.playerSupplier.createPlayer(g.consoleMenu.userChooseAiDifficulty());
            ai2.setGame(g);
            ai2.setName("Computer 2");
            this.p2 = ai2;
        }

        Board b1 = new Board(g);
        Board b2 = new Board(g);

        p1.setOwnBoard(b1);
        p1.setEnemyBoard(b2);

        p2.setOwnBoard(b2);
        p2.setEnemyBoard(b1);

        // te dwie linijki sa do zmiany, to tylko chwilowe rozwiązanie
        List<Battleship> ships1 = g.battleshipDeployer.getBattleshipsRandom(g.getBoardSize());
        List<Battleship> ships2 = g.battleshipDeployer.getBattleshipsRandom(g.getBoardSize());
        // -----------------------------------------------------------

        b1.importShips(ships1);
        b2.importShips(ships2);

        g.matchHistoryService.recordPlayers(p1, p2);
        g.matchHistoryService.recordShips(ships1, ships2);
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
        g.matchHistoryService.setWinner(winner.getName());
        g.matchHistoryService.saveMatchToFile();

        // Saving the player profiles to save changes -
        // we don't need to differentiate between temporary accounts and registered one -
        // function only updates existing accounts.
        g.registrationService.updatePlayer(winner.getPlayerProfile());
        g.registrationService.updatePlayer(loser.getPlayerProfile());
    }
}
