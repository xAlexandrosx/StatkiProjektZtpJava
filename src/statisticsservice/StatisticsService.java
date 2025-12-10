package statisticsservice;

import Game.Game;
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
            player.playerProfile.SetHitCount(player.playerProfile.GetHitCount() + 1);
        }
        else{
            player.playerProfile.SetMissCount(player.playerProfile.GetMissCount() + 1);
        }
    }

    public void RegisterMove(Player player) {
        player.playerProfile.SetMoveCount(player.playerProfile.GetMoveCount() + 1);
    }

    public void RegisterMatch(Player winner, Player loser) {
        winner.playerProfile.SetMatchesWon(winner.playerProfile.GetMatchesWon() + 1);
        winner.playerProfile.SetMatchesPlayed(winner.playerProfile.GetMatchesPlayed() + 1);

        loser.playerProfile.SetMatchesPlayed(loser.playerProfile.GetMatchesPlayed() + 1);
    }
}
