package Match;

import ServiceLocator.ServiceLocator;
import board.Board;
import observer.notifications.MatchFinishedNotification;
import observer.notifications.ShipsPlacedNotification;
import players.IPlayer;
import statisticsservice.StatisticsService;

public class Match implements IMatch {

    private final ServiceLocator sl;
    private final IPlayer p1;
    private final IPlayer p2;

    private final int PLAYER_VS_PLAYER = 0;
    private final int PLAYER_VS_COMPUTER = 1;

    public Match(ServiceLocator sl, int variant) {
        this.sl = sl;

        if (variant == PLAYER_VS_PLAYER) {
            this.p1 = sl.globalVariables.getPlayer(1);
            this.p2 = sl.globalVariables.getPlayer(2);
        } else if (variant == PLAYER_VS_COMPUTER) {
            this.p1 = sl.globalVariables.getPlayer(1);
            IPlayer ai = sl.playerSupplier.createPlayer(sl.consoleMenu.userChooseAiDifficulty());
            ai.setGame(sl);
            ai.setName("Computer");
            this.p2 = ai;
        }
        else { // COMPUTER_VS_COMPUTER
            IPlayer ai1 = sl.playerSupplier.createPlayer(sl.consoleMenu.userChooseAiDifficulty());
            ai1.setGame(sl);
            ai1.setName("Computer 1");
            this.p1 = ai1;

            IPlayer ai2 = sl.playerSupplier.createPlayer(sl.consoleMenu.userChooseAiDifficulty());
            ai2.setGame(sl);
            ai2.setName("Computer 2");
            this.p2 = ai2;
        }

        // pobieramy Boardy z Deployera, z juz rozmieszczonymi statkami
        Board b1 = sl.battleshipDeployer.getBattleshipsRandom(sl.globalVariables.getBoardSize());
        Board b2 = sl.battleshipDeployer.getBattleshipsRandom(sl.globalVariables.getBoardSize());

        p1.setOwnBoard(b1);
        p1.setEnemyBoard(b2);

        p2.setOwnBoard(b2);
        p2.setEnemyBoard(b1);

        sl.matchHistoryService.recordPlayers(p1, p2);
        sl.notificationManager.publish(new ShipsPlacedNotification(b1.adaptShips(), b2.adaptShips()));
        //sl.matchHistoryService.recordShips(b1.adaptShips(), b2.adaptShips());
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
        sl.notificationManager.publish(new MatchFinishedNotification(winner, loser));
        //sl.matchHistoryService.setWinner(winner.getName());
        //sl.matchHistoryService.saveMatchToFile();

        // Saving the player profiles to save changes -
        // we don't need to differentiate between temporary accounts and registered one -
        // function only updates existing accounts.
        sl.registrationServiceProxy.updatePlayer(winner.getPlayerProfile());
        sl.registrationServiceProxy.updatePlayer(loser.getPlayerProfile());
    }
}
