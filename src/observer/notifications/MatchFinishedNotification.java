package observer.notifications;

import players.IPlayer;

// Powiadomienie informujące o zakończeniu meczu
public record MatchFinishedNotification(
        IPlayer winner,
        IPlayer loser
) implements Notification {}
