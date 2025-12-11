package statisticsservice;

import players.IPlayer;
import players.Player;

public class StatisticsService {
    private static StatisticsService instance;

    private StatisticsService(){}

    public static StatisticsService getInstance(){
        if(instance == null){
            instance = new StatisticsService();
        }
        return instance;
    }

    public void RegisterShot(Player player, boolean hit) {
        if(hit){
            player.getPlayerProfile().SetHitCount(player.getPlayerProfile().GetHitCount() + 1);
        }
        else{
            player.getPlayerProfile().SetMissCount(player.getPlayerProfile().GetMissCount() + 1);
        }
    }

    public void RegisterMove(Player player) {
        player.getPlayerProfile().SetMoveCount(player.getPlayerProfile().GetMoveCount() + 1);
    }

    public void RegisterMatch(IPlayer winner, IPlayer loser) {
        winner.getPlayerProfile().SetMatchesWon(winner.getPlayerProfile().GetMatchesWon() + 1);
        winner.getPlayerProfile().SetMatchesPlayed(winner.getPlayerProfile().GetMatchesPlayed() + 1);

        loser.getPlayerProfile().SetMatchesPlayed(loser.getPlayerProfile().GetMatchesPlayed() + 1);
    }
}
