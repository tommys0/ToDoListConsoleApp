import java.time.LocalDate;
import java.util.ArrayList;

public class AddTask {
    public static void add(ArrayList<Task> tasks, String taskDescription, int taskPriority, LocalDate dueDate, String taskCategory) {
        Task task = new Task(taskDescription, taskPriority, dueDate, taskCategory);
        tasks.add(task);
        System.out.println("Task added: " + task.getDescription());
    }
}
