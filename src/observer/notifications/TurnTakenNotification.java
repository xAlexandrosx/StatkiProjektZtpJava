package observer.notifications;

import players.Player;

public record TurnTakenNotification(
    Player player,
    int x,
    int y,
    boolean hit
) implements Notification {}
