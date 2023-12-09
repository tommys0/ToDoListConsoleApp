import java.util.ArrayList;

public class DeleteDoneTasks {
    public static void deleteDone(ArrayList<Task> tasks) {
        ArrayList<Task> tasksToRemove = new ArrayList<>();
        for (Task task : tasks) {
            if (task.isDone()) {
                tasksToRemove.add(task);
            }
        }
        tasks.removeAll(tasksToRemove);
        System.out.println("Deleted done tasks.");
    }
}
