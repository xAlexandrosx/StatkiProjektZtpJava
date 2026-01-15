package observer.notifications;

import players.IPlayer;

public record MatchFinishedNotification(
        IPlayer winner,
        IPlayer loser
) implements Notification {}
