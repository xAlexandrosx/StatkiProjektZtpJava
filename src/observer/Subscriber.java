package observer;

import observer.notifications.Notification;

public interface Subscriber { // Interfejs wzorca Observer
    void update(Notification notification);
}
