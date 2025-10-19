package library1;

// Класс Item — базовый класс для всех элементов библиотеки
public class Item {
    private String title;
    private String author;
    private int year;

    public Item(String title, String author, int year) {
        setTitle(title);
        setAuthor(author);
        setYear(year);
    }

    public void setTitle(String title) {
        if (title == null || title.isEmpty())
            throw new IllegalArgumentException("Название не может быть пустым");
        this.title = title;
    }

    public void setAuthor(String author) {
        if (author == null || author.isEmpty())
            throw new IllegalArgumentException("Автор не может быть пустым");
        this.author = author;
    }

    public void setYear(int year) {
        if (year < 0)
            throw new IllegalArgumentException("Год выпуска не может быть отрицательным");
        this.year = year;
    }

    // Переопределение метода toString()
    @Override
    public String toString() {
        return String.format("\"%s\" (%d), Автор: %s", title, year, author);
    }

    public void borrowItem() {
    }

    public void returnItem() {
    }
}
