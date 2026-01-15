package observer.notifications;

import battleship.IBattleship;
import players.IPlayer;

import java.util.List;

public record MatchConfiguredNotification(
    IPlayer player1,
    IPlayer player2,
    List<IBattleship> player1Ships,
    List<IBattleship> player2Ships
) implements Notification {}
