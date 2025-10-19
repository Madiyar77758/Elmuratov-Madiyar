package library1;

// Класс для представления DVD
// Наследует от Item и добавляет поля: продолжительность и режиссёр
public class DVD extends Item {
    private int duration;
    private String director;

    public DVD(String title, String author, int year, int duration, String director) {
        super(title, author, year);
        setDuration(duration);
        setDirector(director);
    }

    // Геттеры и сеттеры
    public int getDuration() { return duration; }
    public String getDirector() { return director; }

    public void setDuration(int duration) {
        if (duration <= 0)
            throw new IllegalArgumentException("Продолжительность должна быть положительной");
        this.duration = duration;
    }

    public void setDirector(String director) {
        if (director == null || director.isEmpty())
            throw new IllegalArgumentException("Режиссёр не может быть пустым");
        this.director = director;
    }

    // Переопределение метода toString() для вывода информации о DVD
    @Override
    public String toString() {
        return String.format("DVD: %s — Продолжительность: %d мин., Режиссёр: %s", super.toString(), duration, director);
    }
}
