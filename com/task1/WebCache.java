package task1;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * WebCache — потокобезопасный кэш для URL → content и статистики обращений.
 */
public class WebCache {

    // Кэш: URL -> content
    private final ConcurrentHashMap<String, String> cache = new ConcurrentHashMap<>();

    // Статистика: URL -> количество обращений
    // По условию используем ConcurrentHashMap<String, Integer> и атомарные операции map.merge(...)
    private final ConcurrentHashMap<String, Integer> accessCounts = new ConcurrentHashMap<>();

    /**
     * Получить содержимое из кеша и атомарно увеличить счётчик обращений.
     * Если содержимого нет — возвращаем null (как в примере).
     *
     * @param url URL страницы
     * @return содержимое страницы или null
     */
    public String get(String url) {
        // Атомарно увеличиваем счётчик обращений
        accessCounts.merge(url, 1, Integer::sum);
        // Возвращаем содержимое (может быть null)
        return cache.get(url);
    }

    /**
     * Добавить содержимое в кэш.
     *
     * @param url URL
     * @param content содержимое
     */
    public void put(String url, String content) {
        // Простая put — ConcurrentHashMap обеспечивает потокобезопасность
        cache.put(url, content);
    }

    /**
     * Получить количество обращений к URL.
     *
     * @param url URL
     * @return количество обращений (0 если нет записей)
     */
    public int getAccessCount(String url) {
        return accessCounts.getOrDefault(url, 0);
    }

    /**
     * Вернуть топ-N самых популярных URL (по убыванию обращений).
     *
     * @param n количество URL в топе
     * @return упорядоченная Map (URL -> count) в порядке убывания
     */
    public Map<String, Integer> getTopAccessed(int n) {
        return accessCounts.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue(Comparator.reverseOrder()))
                .limit(n)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (a, b) -> a,
                        LinkedHashMap::new
                ));
    }

    /**
     * Возвращает snapshot всех записанных страниц (для тестов / вывода).
     */
    public Map<String, String> snapshotCache() {
        return new HashMap<>(cache);
    }
}