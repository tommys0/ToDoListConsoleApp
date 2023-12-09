import java.util.ArrayList;

public class ShowTasks {
    public static void show(ArrayList<Task> tasks) {
        if (tasks.isEmpty()) {
            System.out.println("No tasks.");
            return;
        }

        System.out.println("Tasks:");
        for (int priority = 1; priority <= 5; priority++) {
            boolean foundTasks = false;
            System.out.println("\nPriority " + priority + " tasks:");
            for (Task task : tasks) {
                if (task.getPriority() == priority) {
                    String status = task.isDone() ? "[DONE]" : "[NOT DONE]";
                    System.out.println("- " + task.getDescription() + " " + status);
                    foundTasks = true;
                }
            }
            if (!foundTasks) {
                System.out.println("No tasks for this priority.");
            }
        }
    }
}
