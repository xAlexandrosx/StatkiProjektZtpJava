package observer;

import observer.notifications.Notification;

import java.util.ArrayList;
import java.util.List;

public class NotificationManager {
    private List<Subscriber> subscribers;

    public NotificationManager(){
        subscribers = new ArrayList<>();
    }

    public void subscribe(Subscriber subscriber){
        subscribers.add(subscriber);
    }
    public void unsubscribe(Subscriber subscriber){
        subscribers.remove(subscriber);
    }

    public void publish(Notification notification){
        for (Subscriber subscriber : subscribers){
            subscriber.update(notification);
        }
    }
}
