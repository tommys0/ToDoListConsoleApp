import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ArrayList<Task> tasks = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        String userInput;

        System.out.println("Welcome to your To-Do List!");

        while (true) {
            System.out.println("\nChoose an action:");
            System.out.println("1. Add a task");
            System.out.println("2. Delete a task");
            System.out.println("3. Show tasks");
            System.out.println("4. Reset tasks");
            System.out.println("5. Exit");
            System.out.println("6. Mark taks as done");

            userInput = scanner.nextLine();

            switch (userInput) {
                case "1":
                    System.out.print("Enter task to add: ");
                    String taskToAdd = scanner.nextLine();
                    AddTask.add(tasks, taskToAdd);
                    break;
                case "2":
                    System.out.print("Enter index of task to delete: ");
                    int indexToDelete = scanner.nextInt();
                    scanner.nextLine(); // Consume the newline character
                    DeleteTask.delete(tasks, indexToDelete - 1); // Adjust index to match user input
                    break;
                case "3":
                    ShowTasks.show(tasks);
                    break;
                case "4":
                    ResetTasks.reset(tasks);
                    System.out.println("Tasks reset.");
                    break;
                case "5":
                    System.out.println("Exiting the program. Goodbye!");
                    scanner.close(); // Close the scanner before exiting
                    System.exit(0);
                    break;
                case "6":
                    System.out.print("Enter index of task to mark as done: ");
                    int indexToMarkDone = scanner.nextInt();
                    scanner.nextLine(); // Consume the newline character
                    if (indexToMarkDone > 0 && indexToMarkDone <= tasks.size()) {
                        Task taskToMarkDone = tasks.get(indexToMarkDone - 1);
                        taskToMarkDone.markAsDone();
                        System.out.println("Task marked as done: " + taskToMarkDone.getDescription());
                    } else {
                        System.out.println("Invalid index!");
                    }
                    break;

                default:
                    System.out.println("Invalid option. Please choose a valid action.");
            }
        }
    }
}
