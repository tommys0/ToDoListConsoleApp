import java.util.ArrayList;

public class AddTask {
    public static void add(ArrayList<Task> tasks, String description, int priority) {
        Task newTask = new Task(description, priority);
        tasks.add(newTask);
        System.out.println("Task added: " + description);
    }
}
