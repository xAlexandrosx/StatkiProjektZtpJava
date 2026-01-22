package observer;

import observer.notifications.Notification;

import java.util.ArrayList;
import java.util.List;

public class NotificationManager { // Klasa odpowiedzialna za zarządzanie powiadomieniami
    private List<Subscriber> subscribers;

    public NotificationManager(){ // Lista osób zapisanych do otrzymywania powiadomień
        subscribers = new ArrayList<>();
    }

    public void subscribe(Subscriber subscriber){
        subscribers.add(subscriber);
    } // Metoda subskrybująca
    public void unsubscribe(Subscriber subscriber){
        subscribers.remove(subscriber);
    } // Metoda odsubskrybująca

    public void publish(Notification notification){ // Metoda rozsyłająca powiadomienia
        for (Subscriber subscriber : subscribers){
            subscriber.update(notification);
        }
    }
}
