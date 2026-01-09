package observer.notifications;

import battleship.IBattleship;

import java.util.List;

public record ShipsPlacedNotification(
        List<IBattleship> player1Ships,
        List<IBattleship> player2Ships
) implements Notification {}
