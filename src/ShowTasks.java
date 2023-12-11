import java.util.ArrayList;

public class ShowTasks {
    public static void show(ArrayList<Task> tasks) {
        System.out.println("Tasks:");

        for (Task task : tasks) {
            System.out.println("Description: " + task.getDescription());
            System.out.println("Priority: " + task.getPriority());
            System.out.println("Due Date: " + task.getDeadline());

            if (task.getCategory() != null) {
                System.out.println("Category: " + task.getCategory());
            } else {
                System.out.println("Category: No category specified");
            }

            long daysUntilDue = task.getDaysUntilDueDate();
            if (daysUntilDue > 0) {
                System.out.println("Days Until Due: " + daysUntilDue);
            } else if (daysUntilDue == 0) {
                System.out.println("Due Today");
            } else {
                System.out.println("Overdue by " + Math.abs(daysUntilDue) + " days");
            }

            System.out.println("---------------------");
        }
    }
}
