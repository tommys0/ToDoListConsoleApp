import java.util.ArrayList;

public class AddTask {
    public static void add(ArrayList<Task> tasks, String taskDescription) {
        Task newTask = new Task(taskDescription);
        tasks.add(newTask);
    }
}
