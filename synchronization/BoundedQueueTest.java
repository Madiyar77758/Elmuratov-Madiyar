package synchronization;

public class BoundedQueueTest {
    public static void main(String[] args) throws InterruptedException {
        BoundedQueue<String> queue = new BoundedQueue<>(5);

        Thread producer = new Thread(() -> {
            try {
                for (int i = 1; i <= 10; i++) {
                    String item = "Item-" + i;
                    System.out.println("Producer: adding " + item);
                    queue.put(item);
                    System.out.println("Producer: added " + item + ", queue size: " + queue.size());
                    Thread.sleep(100);
                }
                System.out.println("Producer: finished");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        Thread consumer1 = new Thread(() -> {
            try {
                for (int i = 0; i < 5; i++) {
                    String item = queue.take();
                    System.out.println("Consumer-1: took " + item);
                    Thread.sleep(200);
                }
                System.out.println("Consumer-1: finished");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        Thread consumer2 = new Thread(() -> {
            try {
                Thread.sleep(500);
                for (int i = 0; i < 5; i++) {
                    String item = queue.take();
                    System.out.println("Consumer-2: took " + item);
                    Thread.sleep(150);
                }
                System.out.println("Consumer-2: finished");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        producer.start();
        consumer1.start();
        consumer2.start();

        producer.join();
        consumer1.join();
        consumer2.join();

        System.out.println("\nQueue is empty: " + queue.isEmpty());
    }
}
