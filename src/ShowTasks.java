import java.time.LocalDate;
import java.util.ArrayList;

public class ShowTasks {
    public static void show(ArrayList<Task> tasks) {
        if (tasks.isEmpty()) {
            System.out.println("Task list is empty!");
        } else {
            System.out.println("Tasks:");
            for (Task task : tasks) {
                String status = task.isDone() ? "[DONE]" : "[NOT DONE]";
                System.out.println(
                        "Description: " + task.getDescription() +
                                " | Priority: " + task.getPriority() +
                                " | Deadline: " + task.getDeadline() +
                                " | Status: " + status
                );
            }
        }
    }
}
