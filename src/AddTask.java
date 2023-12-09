import java.util.ArrayList;

public class AddTask {
    public static void add(ArrayList<Task> tasks, String description) {
        Task newTask = new Task(description);
        tasks.add(newTask);
        System.out.println("Task added: " + description);
    }
}
