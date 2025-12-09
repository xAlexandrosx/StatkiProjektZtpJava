package statisticsservice;

import players.Player;

public interface IStatisticsService {
    public void RegisterShot(Player player, boolean hit);
    public void RegisterMove(Player player);
    public void RegisterMatch(Player victoriousPlayer, Player loserPlayer);
}
