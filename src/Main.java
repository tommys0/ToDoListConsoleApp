import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.time.LocalDate;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final String TASK_FILE = "tasks.txt";
    private static final String[] MENU_OPTIONS = {
            "\u001B[36mAdd a task\u001B[0m",
            "\u001B[35mDelete a task\u001B[0m",
            "\u001B[33mShow tasks\u001B[0m",
            "\u001B[34mReset tasks\u001B[0m",
            "\u001B[31mDelete done tasks\u001B[0m",
            "\u001B[32mMark task as done\u001B[0m",
            "\u001B[37mExit\u001B[0m"
    };

    public static void main(String[] args) {
        ArrayList<Task> tasks = new ArrayList<>();
        loadTasksFromFile(tasks);

        System.out.println("Welcome to your To-Do List!");

        boolean exit = false;
        do {
            displayMenuOptions();

            String userInput = scanner.nextLine();
            exit = handleUserInput(userInput, tasks);
        } while (!exit);
    }

    private static void displayMenuOptions() {
        System.out.println("\n*********************************");
        System.out.println("******* Choose an action: *******");
        System.out.println("*********************************");
        for (int i = 0; i < MENU_OPTIONS.length; i++) {
            System.out.println((i + 1) + ". " + MENU_OPTIONS[i]);
        }
    }


    private static boolean handleUserInput(String userInput, ArrayList<Task> tasks) {
        switch (userInput) {
            case "1":
                addTask(tasks);
                break;
            case "2":
                removeTask(tasks);
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
                markTaskAsDone(tasks);
                break;
            case "7":
                exitProgram(tasks);
                return true;
            default:
                System.out.println("Invalid option. Please choose a valid action.");
        }
        return false;
    }

    private static void addTask(ArrayList<Task> tasks) {
        System.out.print("Enter task description: ");
        String taskDescription = scanner.nextLine();

        System.out.print("Enter task priority (1-5 with 1 being highest priority): ");
        int taskPriority = scanner.nextInt();
        if (taskPriority < 1 || taskPriority > 5) {
            System.out.println("Invalid priority level.");
            return;
        }
        scanner.nextLine();

        System.out.print("Enter due date (YYYY-MM-DD): ");
        String dateInput = scanner.nextLine();
        LocalDate dueDate = LocalDate.parse(dateInput);

        AddTask.add(tasks, taskDescription, taskPriority, dueDate);
    }






    private static void removeTask(ArrayList<Task> tasks) {
        int indexToRemove = getUserIndex(tasks);
        DeleteTask.delete(tasks, indexToRemove);
    }

    private static void markTaskAsDone(ArrayList<Task> tasks) {
        int indexToMarkDone = getUserIndex(tasks);
        if (indexToMarkDone > 0 && indexToMarkDone <= tasks.size()) {
            Task taskToMarkDone = tasks.get(indexToMarkDone - 1);
            taskToMarkDone.markAsDone();
            System.out.println("Task marked as done: " + taskToMarkDone.getDescription());
        } else {
            System.out.println("Invalid index!");
        }
    }

    private static int getUserIndex(ArrayList<Task> tasks) {
        int index = -1;
        boolean validInput = false;
        do {
            try {
                System.out.print("Enter index: ");
                index = scanner.nextInt();
                scanner.nextLine();
                validInput = index > 0 && index <= tasks.size();
                if (!validInput) {
                    System.out.println("Invalid index. Please enter a valid index.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid index.");
                scanner.nextLine();
            }
        } while (!validInput);
        return index;
    }

    private static void exitProgram(ArrayList<Task> tasks) {
        saveTasksToFile(tasks);
        System.out.println("Exiting the program. Goodbye!");
        scanner.close();
        System.exit(0);
    }

    private static void saveTasksToFile(ArrayList<Task> tasks) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("tasks.txt"))) {
            for (Task task : tasks) {
                String formattedDate = task.getDeadline().toString();
                writer.println(task.getDescription() + ";" + task.getPriority() + ";" + formattedDate + ";" + task.isDone());
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
                String[] parts = line.split(";");
                if (parts.length >= 3) {
                    String description = parts[0];
                    int priority = Integer.parseInt(parts[1]);
                    LocalDate deadline = LocalDate.parse(parts[2]);
                    boolean isDone = Boolean.parseBoolean(parts[3]);
                    Task task = new Task(description, priority, deadline);
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
