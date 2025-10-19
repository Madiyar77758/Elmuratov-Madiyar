package library1;

// Класс для представления журнала
// Наследует от Item и добавляет поля: номер выпуска и частоту
public class Magazine extends Item {
    private int issueNumber;
    private String frequency;

    public Magazine(String title, String author, int year, int issueNumber, String frequency) {
        super(title, author, year);
        setIssueNumber(issueNumber);
        setFrequency(frequency);
    }


    public int getIssueNumber() { return issueNumber; }
    public String getFrequency() { return frequency; }

    public void setIssueNumber(int issueNumber) {
        if (issueNumber <= 0)
            throw new IllegalArgumentException("Номер выпуска должен быть положительным");
        this.issueNumber = issueNumber;
    }

    public void setFrequency(String frequency) {
        if (frequency == null || frequency.isEmpty())
            throw new IllegalArgumentException("Частота не может быть пустой");
        this.frequency = frequency;
    }


    @Override
    public String toString() {
        return String.format("Журнал: %s — Выпуск: %d, Частота: %s", super.toString(), issueNumber, frequency);
    }
}
