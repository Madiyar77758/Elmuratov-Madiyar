package task3;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

public class PerformanceComparison {

    static class SynchronizedMapCounter {
        private final Map<String, Integer> map = Collections.synchronizedMap(new HashMap<>());

        public void count(String word) {
            synchronized (map) {
                map.put(word, map.getOrDefault(word, 0) + 1);
            }
        }
    }

    static class ConcurrentMapCounter {
        private final ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();

        public void count(String word) {
            map.merge(word, 1, Integer::sum);
        }
    }

    static class ExplicitLockCounter {
        private final Map<String, Integer> map = new HashMap<>();

        public synchronized void count(String word) {
            map.put(word, map.getOrDefault(word, 0) + 1);
        }
    }

    /**
     * Тестирует переданный счётчик и возвращает время выполнения в миллисекундах.
     */
    private static long testCounterRunnables(RunnableCounting counterRunner, int numThreads, int operationsPerThread, List<String> words) throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch doneLatch = new CountDownLatch(numThreads);

        for (int t = 0; t < numThreads; t++) {
            executor.submit(() -> {
                try {
                    startLatch.await(); // начинаем одновременно
                    counterRunner.runCounting(operationsPerThread, words);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    doneLatch.countDown();
                }
            });
        }

        long start = System.currentTimeMillis();
        startLatch.countDown(); // стартуем все потоки
        boolean finished = doneLatch.await(10, TimeUnit.MINUTES); // ждем максимум 10 минут
        long end = System.currentTimeMillis();

        executor.shutdownNow();

        if (!finished) {
            System.err.println("Warning: test did not finish within timeout");
        }
        return end - start;
    }

    @FunctionalInterface
    private interface RunnableCounting {
        void runCounting(int operations, List<String> words);
    }

    public static void main(String[] args) throws InterruptedException {
        final int numThreads = 10;
        final int operationsPerThread = 100_000;
        final int vocabSize = 100;

        // Подготовка словаря
        List<String> words = new ArrayList<>(vocabSize);
        for (int i = 0; i < vocabSize; i++) {
            words.add("word" + i);
        }

        System.out.println("=== Сравнение производительности (каждому потоку " + operationsPerThread + " операций) ===");

        // SynchronizedMap
        SynchronizedMapCounter syncCounter = new SynchronizedMapCounter();
        long syncTime = testCounterRunnables((ops, w) -> {
            Random rnd = ThreadLocalRandom.current();
            for (int i = 0; i < ops; i++) {
                syncCounter.count(w.get(rnd.nextInt(w.size())));
            }
        }, numThreads, operationsPerThread, words);

        // ConcurrentHashMap
        ConcurrentMapCounter concCounter = new ConcurrentMapCounter();
        long concTime = testCounterRunnables((ops, w) -> {
            Random rnd = ThreadLocalRandom.current();
            for (int i = 0; i < ops; i++) {
                concCounter.count(w.get(rnd.nextInt(w.size())));
            }
        }, numThreads, operationsPerThread, words);

        // ExplicitLock
        ExplicitLockCounter lockCounter = new ExplicitLockCounter();
        long lockTime = testCounterRunnables((ops, w) -> {
            Random rnd = ThreadLocalRandom.current();
            for (int i = 0; i < ops; i++) {
                lockCounter.count(w.get(rnd.nextInt(w.size())));
            }
        }, numThreads, operationsPerThread, words);

        // Вывод результатов
        System.out.println();
        System.out.println("Результаты (ms):");
        System.out.printf("SynchronizedMap: %d ms%n", syncTime);
        System.out.printf("ConcurrentHashMap: %d ms%n", concTime);
        System.out.printf("ExplicitLock: %d ms%n", lockTime);

        // Определяем победителя и коэффициенты
        long minTime = Math.min(syncTime, Math.min(concTime, lockTime));
        String winner;
        if (minTime == concTime) winner = "ConcurrentHashMap";
        else if (minTime == syncTime) winner = "SynchronizedMap";
        else winner = "ExplicitLock";

        // Коэффициент: во сколько раз ConcurrentHashMap быстрее SynchronizedMap (если concTime==0 — избегаем деления на ноль)
        String speedupInfo;
        if (concTime > 0) {
            double ratio = (double) syncTime / (double) concTime;
            speedupInfo = String.format("Winner: %s (в %.2f раза быстрее SynchronizedMap)", winner, ratio);
        } else {
            speedupInfo = "Winner: " + winner;
        }

        System.out.println();
        System.out.println(speedupInfo);
        System.out.println("====================================");
    }
}