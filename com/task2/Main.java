package task2;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        NotificationSystem system = new NotificationSystem();

        // Поток для добавления подписчиков
        Thread subscriberThread = new Thread(() -> {
            try {
                for (int i = 0; i < 100; i++) {
                    system.subscribe(new EmailSubscriber("user" + i + "@example.com"));
                    Thread.sleep(5); // Задержка между добавлением подписчиков
                }
            } catch (InterruptedException e) {
                System.out.println("Ошибка в потоке подписчиков: " + e.getMessage());
                Thread.currentThread().interrupt();  // Завершаем поток при ошибке
            }
        });

        // Поток для рассылки уведомлений
        Thread notifierThread = new Thread(() -> {
            try {
                for (int i = 0; i < 50; i++) {
                    system.notifyAll("Уведомление #" + i);
                    Thread.sleep(100); // Задержка между уведомлениями
                }
            } catch (InterruptedException e) {
                System.out.println("Ошибка в потоке уведомлений: " + e.getMessage());
                Thread.currentThread().interrupt();  // Завершаем поток при ошибке
            }
        });

        // Поток для удаления подписчиков
        Thread unsubscriberThread = new Thread(() -> {
            try {
                Thread.sleep(500); // Задержка перед удалением подписчиков
                for (int i = 0; i < 100; i += 10) {
                    system.unsubscribe(new EmailSubscriber("user" + i + "@example.com"));
                }
            } catch (InterruptedException e) {
                System.out.println("Ошибка в потоке удаления подписчиков: " + e.getMessage());
                Thread.currentThread().interrupt();  // Завершаем поток при ошибке
            }
        });

        // Запуск потоков
        subscriberThread.start();
        notifierThread.start();
        unsubscriberThread.start();

        // Ожидание завершения всех потоков
        subscriberThread.join();
        notifierThread.join();
        unsubscriberThread.join();

        // Вывод количества подписчиков после работы системы
        System.out.println("Итого подписчиков: " + system.getSubscriberCount());
    }
}
