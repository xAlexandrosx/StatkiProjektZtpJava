package observer.notifications;

import players.Player;

// Powiadomienie informujące o zakończeniu tury
public record TurnTakenNotification(
    Player player,
    int x,
    int y,
    boolean hit
) implements Notification {}
