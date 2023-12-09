import java.util.ArrayList;

public class DeleteTask {
    public static void delete(ArrayList<Task> tasks, int index) {
        if (index >= 0 && index < tasks.size()) {
            tasks.remove(index);
        } else {
            System.out.println("Invalid index!");
        }
    }
}
