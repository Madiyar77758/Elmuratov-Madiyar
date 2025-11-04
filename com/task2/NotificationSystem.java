package task2;

import java.util.concurrent.CopyOnWriteArrayList;

public class NotificationSystem {
    private final CopyOnWriteArrayList<Subscriber> subscribers = new CopyOnWriteArrayList<>();

    public void subscribe(Subscriber subscriber) {
        subscribers.add(subscriber);
    }

    public void unsubscribe(Subscriber subscriber) {
        subscribers.remove(subscriber);
    }

    public void notifyAll(String message) {
        for (Subscriber subscriber : subscribers) {
            subscriber.onNotify(message);
        }
    }

    public int getSubscriberCount() {
        return subscribers.size();
    }
}
