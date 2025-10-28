package synchronization;

public class CachePerformanceTest {
    public static void main(String[] args) throws InterruptedException {
        ThreadSafeCache<String, Integer> cache = new ThreadSafeCache<>();

        for (int i = 0; i < 100; i++) {
            cache.put("key-" + i, i);
        }

        System.out.println("Initial cache size: " + cache.size());

        Thread[] readers = new Thread[10];
        for (int i = 0; i < readers.length; i++) {
            final int threadId = i;
            readers[i] = new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    String key = "key-" + (j % 100);
                    Integer value = cache.get(key);
                    if (j % 200 == 0) {
                        System.out.println("Reader-" + threadId + " read: " + key + " = " + value);
                    }
                }
            }, "Reader-" + i);
        }

        Thread[] writers = new Thread[2];
        for (int i = 0; i < writers.length; i++) {
            final int threadId = i;
            writers[i] = new Thread(() -> {
                for (int j = 0; j < 50; j++) {
                    String key = "key-" + (j % 100);
                    cache.put(key, j * 1000);
                    System.out.println("Writer-" + threadId + " wrote: " + key + " = " + (j * 1000));
                    try { Thread.sleep(20); } catch (InterruptedException e) {}
                }
            }, "Writer-" + i);
        }

        long startTime = System.currentTimeMillis();

        for (Thread reader : readers) reader.start();
        for (Thread writer : writers) writer.start();

        for (Thread reader : readers) reader.join();
        for (Thread writer : writers) writer.join();

        long endTime = System.currentTimeMillis();
        System.out.println("\nFinal cache size: " + cache.size());
        System.out.println("Execution time: " + (endTime - startTime) + " ms");
    }
}
