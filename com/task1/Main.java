package task1;

import java.util.Map;
import java.util.concurrent.*;
import java.util.Random;
public class Main {
    public static void main(String[] args) throws InterruptedException {
        WebCache cache = new WebCache();
        ExecutorService executor = Executors.newFixedThreadPool(10);

        // Симулируем добавление данных (100 задач, но URL только 20 уникальных)
        for (int i = 0; i < 100; i++) {
            int urlId = i;
            executor.submit(() -> {
                String url = "https://example.com/page" + (urlId % 20);
                cache.put(url, "Content of page " + urlId);
            });
        }

        // Симулируем чтение данных (1000 обращений)
        Random random = new Random();
        for (int i = 0; i < 1000; i++) {
            executor.submit(() -> {
                String url = "https://example.com/page" + random.nextInt(20);
                String content = cache.get(url);
            });
        }

        executor.shutdown();
        boolean finished = executor.awaitTermination(1, TimeUnit.MINUTES);
        if (!finished) {
            System.err.println("Не все задачи завершились за 1 минуту.");
        }

        System.out.println("Топ-5 самых популярных страниц:");
        for (Map.Entry<String, Integer> e : cache.getTopAccessed(5).entrySet()) {
            System.out.printf("%s: %d обращений%n", e.getKey(), e.getValue());
        }

        // Доп: вывести конкретный счётчик
        System.out.println("Пример: access count для https://example.com/page5 = " +
                cache.getAccessCount("https://example.com/page5"));
    }
}