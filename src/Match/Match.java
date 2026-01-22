package Match;

import ServiceLocator.ServiceLocator;
import board.Board;
import observer.notifications.MatchConfiguredNotification;
import observer.notifications.MatchFinishedNotification;
import players.IPlayer;
import statisticsservice.StatisticsService;

public class Match implements IMatch { // Klasa odpowiedzialna za konteneryzację gry do jednej rozgrywki
    protected ServiceLocator sl;

    protected final IPlayer p1;
    protected final IPlayer p2;

    public static final int PLAYER_VS_PLAYER = 0;
    public static final int PLAYER_VS_COMPUTER = 1;

    public Match(int variant) { // Konstruktor
        this.sl = ServiceLocator.getInstance();

        if (variant == PLAYER_VS_PLAYER) {
            this.p1 = sl.getGlobalVariables().getPlayer(1);
            this.p2 = sl.getGlobalVariables().getPlayer(2);
        } else if (variant == PLAYER_VS_COMPUTER) {
            this.p1 = sl.getGlobalVariables().getPlayer(1);
            IPlayer ai = sl.getPlayerSupplier().createPlayer(sl.getConsoleMenu().userChooseAiDifficulty());
            ai.setName("Computer");
            this.p2 = ai;
        }
        else { // COMPUTER_VS_COMPUTER
            IPlayer ai1 = sl.getPlayerSupplier().createPlayer(sl.getConsoleMenu().userChooseAiDifficulty());
            ai1.setName("Computer 1");
            this.p1 = ai1;

            IPlayer ai2 = sl.getPlayerSupplier().createPlayer(sl.getConsoleMenu().userChooseAiDifficulty());
            ai2.setName("Computer 2");
            this.p2 = ai2;
        }

        // pobieramy Boardy z Deployera, z juz rozmieszczonymi statkami
        Board b1 = sl.getBattleshipDeployer().getBattleshipsRandom(sl.getGlobalVariables().getBoardSize());
        Board b2 = sl.getBattleshipDeployer().getBattleshipsRandom(sl.getGlobalVariables().getBoardSize());

        p1.setOwnBoard(b1);
        p1.setEnemyBoard(b2);

        p2.setOwnBoard(b2);
        p2.setEnemyBoard(b1);

        sl.getNotificationManager().publish(new MatchConfiguredNotification(p1, p2, b1.adaptShips(), b2.adaptShips()));
    }

    public void playMatch() { // Główna metoda obsługująca pętle gry

        System.out.println("Starting match between " + p1.getName() + " and " + p2.getName());

        IPlayer winner = null;
        IPlayer loser = null;

        while (true) {
            System.out.println("\n==========================");
            System.out.println("      " + p1.getName() + "'s turn");
            System.out.println("==========================");
            p1.takeTurn();

            if (p2.getOwnBoard().allSunk()) {
                winner = p1;
                loser = p2;
                break;
            }

            System.out.println("\n==========================");
            System.out.println("      " + p2.getName() + "'s turn");
            System.out.println("==========================");
            p2.takeTurn();

            if (p1.getOwnBoard().allSunk()) {
                winner = p2;
                loser = p1;
                break;
            }
        }
        finishMatch(winner, loser);
    }

    protected void finishMatch(IPlayer winner, IPlayer loser) { // Funkcja kończąca rozgrywkę
        assert winner != null && loser != null;
        System.out.println(winner.getName() + " wins!");
        sl.getNotificationManager().publish(new MatchFinishedNotification(winner, loser));
        // Saving the player profiles to save changes -
        // we don't need to differentiate between temporary accounts and registered one -
        // function only updates existing accounts.
        sl.getRegistrationServiceProxy().updatePlayer(winner.getPlayerProfile());
        sl.getRegistrationServiceProxy().updatePlayer(loser.getPlayerProfile());
    }
}
