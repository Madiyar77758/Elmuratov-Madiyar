package task2;

public class EmailSubscriber implements Subscriber {
    private final String email;

    public EmailSubscriber(String email) {
        this.email = email;
    }

    @Override
    public void onNotify(String message) {
        try {
            Thread.sleep(10); // Симуляция задержки отправки email
            System.out.println("Email sent to " + email + ": " + message);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
