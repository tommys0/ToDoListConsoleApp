public class Task {
    private String description;
    private int priority;
    private boolean isDone;

    public Task(String description, int priority) {
        this.description = description;
        this.priority = priority;
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
}
