package statisticsservice;

import ServiceLocator.ServiceLocator;
import observer.Subscriber;
import observer.notifications.MatchFinishedNotification;
import observer.notifications.Notification;
import observer.notifications.TurnTakenNotification;
import players.IPlayer;
import players.Player;

public class StatisticsService implements IStatisticsService, Subscriber { // Klasa obsługująca statystyki gracza
    public StatisticsService(){
        ServiceLocator.getInstance().getNotificationManager().subscribe(this);
    }

    void RegisterShot(Player player, boolean hit) { // Metoda rejestrująca strzał
        if(hit){
            player.getPlayerProfile().SetHitCount(player.getPlayerProfile().GetHitCount() + 1);
        }
        else{
            player.getPlayerProfile().SetMissCount(player.getPlayerProfile().GetMissCount() + 1);
        }
    }

    void RegisterMove(Player player) { // Metoda rejestrująca ruch
        player.getPlayerProfile().SetMoveCount(player.getPlayerProfile().GetMoveCount() + 1);
    }

    void RegisterMatch(IPlayer winner, IPlayer loser) { // Metoda rejestrująca mecz
        winner.getPlayerProfile().SetMatchesWon(winner.getPlayerProfile().GetMatchesWon() + 1);
        winner.getPlayerProfile().SetMatchesPlayed(winner.getPlayerProfile().GetMatchesPlayed() + 1);

        loser.getPlayerProfile().SetMatchesPlayed(loser.getPlayerProfile().GetMatchesPlayed() + 1);
    }

    @Override
    public void update(Notification notification) { // Metoda wywołująca powiadomienia
        if(notification instanceof TurnTakenNotification n){
            RegisterShot(n.player(), n.hit());
            RegisterMove(n.player());
        }
        else if(notification instanceof MatchFinishedNotification n){
            RegisterMatch(n.winner(), n.loser());
        }
    }
}
