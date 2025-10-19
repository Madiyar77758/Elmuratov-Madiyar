package library1;

// Главный класс программы "Библиотека"
// Демонстрирует создание, выдачу и возврат различных элементов
public class Main {
    static void main() {
        System.out.println("Библиотека:\n");


        Item[] items = {
                new Book("1984", "Джордж Оруэлл", 1949, 328, "Антиутопия"),
                new Book("Гарри Поттер и философский камень", "Джоан Роулинг", 1997, 223, "Фэнтези"),
                new Magazine("National Geographic", "Редакция National Geographic", 2025, 112, "Ежемесячно"),
                new Magazine("Science Today", "Редакция Science Today", 2024, 15, "Ежемесячно"),
                new DVD("Темный рыцарь", "Кристофер Нолан", 2008, 152, "Кристофер Нолан"),
                new DVD("Матрица", "Лана и Лилли Вачовски", 1999, 136, "Лана и Лилли Вачовски")
        };


        System.out.println("Список всех элементов в библиотеке:");
        for (Item i : items) {
            System.out.println(i);
        }

        System.out.println("\nОперации с элементами:");

        System.out.println("\nВыдача элементов:");
        items[0].borrowItem();
        items[3].borrowItem();
        items[4].borrowItem();

        System.out.println("\nВозврат элементов:");
        items[0].returnItem();
        items[1].borrowItem();
        items[4].returnItem();

        System.out.println("\nПосле изменений:");
        for (Item i : items) {
            System.out.println(i);
        }
    }
}
