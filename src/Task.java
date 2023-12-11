import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Task {
    private String description;
    private int priority;
    private LocalDate dueDate;
    private String category;
    private boolean isDone;

    public Task(String description, int priority, LocalDate dueDate, String category) {
        this.description = description;
        this.priority = priority;
        this.dueDate = dueDate;
        this.category = "";
        this.isDone = false;
    }

    public String getDescription() {
        return description;
    }

    public int getPriority() {
        return priority;
    }

    public LocalDate getDeadline() {
        return dueDate;
    }

    public String getCategory() {
        return category;
    }

    public boolean isDone() {
        return isDone;
    }

    public void markAsDone() {
        isDone = true;
    }

    public long getDaysUntilDueDate() {
        LocalDate currentDate = LocalDate.now();
        return ChronoUnit.DAYS.between(currentDate, this.dueDate);
    }
}
