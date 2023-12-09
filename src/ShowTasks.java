import java.util.ArrayList;

public class ShowTasks {
    public static void show(ArrayList<Task> tasks) {
        if (tasks.isEmpty()) {
            System.out.println("Task list is empty!");
        } else {
            System.out.println("Tasks:");
            for (int i = 0; i < tasks.size(); i++) {
                Task currentTask = tasks.get(i);
                String status = currentTask.isDone() ? "[DONE]" : "[NOT DONE]";
                System.out.println((i + 1) + ". " + status + " " + currentTask.getDescription());
            }
        }
    }
}
