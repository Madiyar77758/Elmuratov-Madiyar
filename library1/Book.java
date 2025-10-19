package library1;

// Класс для представления книги
// Наследует от Item и добавляет поля: количество страниц и жанр
public class Book extends Item {
    private int pageCount;
    private String genre;

    public Book(String title, String author, int year, int pageCount, String genre) {
        super(title, author, year); // Вызов конструктора базового класса
        setPageCount(pageCount);
        setGenre(genre);
    }

    // Геттеры и сеттеры
    public int getPageCount() { return pageCount; }
    public String getGenre() { return genre; }

    public void setPageCount(int pageCount) {
        if (pageCount <= 0)
            throw new IllegalArgumentException("Количество страниц должно быть положительным");
        this.pageCount = pageCount;
    }

    public void setGenre(String genre) {
        if (genre == null || genre.isEmpty())
            throw new IllegalArgumentException("Жанр не может быть пустым");
        this.genre = genre;
    }

    // Переопределение метода toString() для вывода информации о книге
    @Override
    public String toString() {
        return String.format("Книга: %s — %d стр., жанр: %s", super.toString(), pageCount, genre);
    }
}
