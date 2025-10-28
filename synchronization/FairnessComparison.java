package synchronization;

import java.util.concurrent.locks.ReentrantLock;

public class FairnessComparison {
    public static void testFairness(boolean fair) throws InterruptedException {
        ReentrantLock lock = new ReentrantLock(fair);
        Runnable task = () -> {
            try {
                for (int i = 0; i < 3; i++) {
                    lock.lock();
                    try {
                        System.out.println(Thread.currentThread().getName() + " acquired lock");
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    } finally {
                        lock.unlock();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        Thread[] threads = new Thread[5];
        for (int i = 0; i < 5; i++) {
            threads[i] = new Thread(task, "Thread-" + i);
            threads[i].start();
        }

        for (Thread t : threads) {
            t.join();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Non-Fair mode:");
        testFairness(false);

        Thread.sleep(1000);

        System.out.println("\nFair mode:");
        testFairness(true);
    }
}
