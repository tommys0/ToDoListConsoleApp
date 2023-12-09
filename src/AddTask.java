import java.util.ArrayList;
import java.time.LocalDate;

public class AddTask {
    public static void add(ArrayList<Task> tasks, String description, int priority, LocalDate dueDate) {
        Task newTask = new Task(description, priority, dueDate);
        tasks.add(newTask);
        System.out.println("Task added: " + description);
    }
}
