import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ArrayList<Task> tasks = new ArrayList<>();
        loadTasksFromFile(tasks);
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
                    scanner.nextLine();
                    DeleteTask.delete(tasks, indexToDelete - 1);
                    break;
                case "3":
                    ShowTasks.show(tasks);
                    break;
                case "4":
                    ResetTasks.reset(tasks);
                    System.out.println("Tasks reset.");
                    break;
                case "5":
                    saveTasksToFile(tasks);
                    System.out.println("Exiting the program. Goodbye!");
                    scanner.close();
                    System.exit(0);
                    break;
                case "6":
                    System.out.print("Enter index of task to mark as done: ");
                    int indexToMarkDone = scanner.nextInt();
                    scanner.nextLine();
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

    private static void saveTasksToFile(ArrayList<Task> tasks) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("tasks.txt"))) {
            for (Task task : tasks) {
                writer.println(task.getDescription() + "," + task.isDone());
            }
            System.out.println("Tasks saved to file.");
        } catch (IOException e) {
            System.out.println("Error while saving tasks: " + e.getMessage());
        }
    }

    private static void loadTasksFromFile(ArrayList<Task> tasks) {
        try (BufferedReader reader = new BufferedReader(new FileReader("tasks.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String description = parts[0];
                    boolean isDone = Boolean.parseBoolean(parts[1]);
                    Task task = new Task(description);
                    if (isDone) {
                        task.markAsDone();
                    }
                    tasks.add(task);
                }
            }
            System.out.println("Tasks loaded from file.");
        } catch (IOException e) {
            System.out.println("Error while loading tasks: " + e.getMessage());
        }
    }
}
