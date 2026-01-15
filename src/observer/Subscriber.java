package observer;

import observer.notifications.Notification;

public interface Subscriber {
    void update(Notification notification);
}
