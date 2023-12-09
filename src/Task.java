import java.time.LocalDate;

public class Task {
    private String description;
    private int priority;
    private LocalDate deadline;
    private boolean isDone;

    public Task(String description, int priority, LocalDate deadline) {
        this.description = description;
        this.priority = priority;
        this.deadline = deadline;
        this.isDone = false;
    }

    public String getDescription() {
        return description;
    }

    public int getPriority() {
        return priority;
    }

    public boolean isDone() {
        return isDone;
    }

    public void markAsDone() {
        this.isDone = true;
    }

    public void markAsNotDone() {
        this.isDone = false;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }
}
