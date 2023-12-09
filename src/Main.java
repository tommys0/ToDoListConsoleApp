import java.io.*;
        import java.util.ArrayList;
        import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final String TASK_FILE = "tasks.txt";
    private static final String[] MENU_OPTIONS = {
            "Add a task",
            "Delete a task",
            "Show tasks",
            "Reset tasks",
            "Delete done tasks",
            "Mark task as done",
            "Exit"
    };

    public static void main(String[] args) {
        ArrayList<Task> tasks = new ArrayList<>();
        loadTasksFromFile(tasks);

        System.out.println("Welcome to your To-Do List!");

        while (true) {
            displayMenuOptions();

            String userInput = scanner.nextLine();
            handleUserInput(userInput, tasks);
        }
    }

    private static void displayMenuOptions() {
        System.out.println("\nChoose an action:");
        for (int i = 0; i < MENU_OPTIONS.length; i++) {
            System.out.println((i + 1) + ". " + MENU_OPTIONS[i]);
        }
    }

    private static void handleUserInput(String userInput, ArrayList<Task> tasks) {
        switch (userInput) {
            case "1":
                System.out.print("Enter task description: ");
                String taskDescription = scanner.nextLine();
                AddTask.add(tasks, taskDescription);
                break;
            case "2":
                DeleteTask.delete(tasks, 0);
                break;
            case "3":
                ShowTasks.show(tasks);
                break;
            case "4":
                ResetTasks.reset(tasks);
                System.out.println("Tasks reset.");
                break;
            case "5":
                DeleteDoneTasks.deleteDone(tasks);
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
            case "7":
                exitProgram(tasks);
                break;

            default:
                System.out.println("Invalid option. Please choose a valid action.");
        }
    }

    // Other methods remain unchanged...

    private static void exitProgram(ArrayList<Task> tasks) {
        saveTasksToFile(tasks);
        System.out.println("Exiting the program. Goodbye!");
        scanner.close();
        System.exit(0);
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
