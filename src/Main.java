import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final String TASK_FILE = "tasks.txt";
    private static final String SETTINGS_FILE = "settings.txt";
    public static String getSettingsFile() {
        return SETTINGS_FILE;
    }
    private static final String[] MENU_OPTIONS = {
            "\u001B[36mAdd a task\u001B[0m",
            "\u001B[35mDelete a task\u001B[0m",
            "\u001B[33mShow tasks\u001B[0m",
            "\u001B[34mReset tasks\u001B[0m",
            "\u001B[31mDelete done tasks\u001B[0m",
            "\u001B[32mMark task as done\u001B[0m",
            "\u001B[33mSettings\u001B[0m",
            "\u001B[37mExit\u001B[0m"
    };

    public static void main(String[] args) {
        ArrayList<Task> tasks = new ArrayList<>();
        loadTasksFromFile(tasks);

        Settings settings = new Settings();
        settings.loadSettingsFromFile();

        System.out.println("Welcome to your To-Do List!");

        boolean exit = false;
        do {
            displayMenuOptions(settings.isColorOption());

            String userInput = scanner.nextLine();
            exit = handleUserInput(userInput, tasks, settings);
        } while (!exit);

        settings.saveSettingsToFile();
    }

    private static void displayMenuOptions(boolean colorOption) {
        System.out.println("\n*********************************");
        System.out.println("******* Choose an action: *******");
        System.out.println("*********************************");

        if (colorOption) {
            for (int i = 0; i < MENU_OPTIONS.length; i++) {
                System.out.println((i + 1) + ". " + MENU_OPTIONS[i]);
            }
        } else {
            for (int i = 0; i < MENU_OPTIONS.length; i++) {
                System.out.println((i + 1) + ". " + stripColor(MENU_OPTIONS[i]));
            }
        }
    }

    private static String stripColor(String input) {
        return input.replaceAll("\u001B\\[[;\\d]*m", "");
    }

    private static boolean handleUserInput(String userInput, ArrayList<Task> tasks, Settings settings) {
        switch (userInput) {
            case "1":
                addTask(tasks, settings);
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
                settings.modifyCategories();
                return false;
            case "8":
                exitProgram(tasks, settings);
                return true;
            default:
                System.out.println("Invalid option. Please choose a valid action.");
        }
        return false;
    }

    private static void exitProgram(ArrayList<Task> tasks, Settings settings) {
        saveTasksToFile(tasks);
        settings.saveSettingsToFile();
        System.out.println("Exiting the program. Goodbye!");
        scanner.close();
        System.exit(0);
    }

    private static void addTask(ArrayList<Task> tasks, Settings settings) {
        System.out.print("Enter task description: ");
        String taskDescription = scanner.nextLine();

        System.out.print("Enter task priority (1-5 with 1 being highest priority): ");
        int taskPriority = scanner.nextInt();
        if (taskPriority < 1 || taskPriority > 5) {
            System.out.println("Invalid priority level.");
            return;
        }
        scanner.nextLine();

        System.out.println("Available categories:");
        ArrayList<String> categories = settings.getCategories();
        for (int i = 0; i < categories.size(); i++) {
            System.out.println((i + 1) + ". " + categories.get(i));
        }

        System.out.print("Choose a category number (or press Enter to skip): ");
        String categoryChoice = scanner.nextLine().trim();
        String taskCategory = null;

        if (!categoryChoice.isEmpty()) {
            try {
                int categoryNumber = Integer.parseInt(categoryChoice);
                if (categoryNumber >= 1 && categoryNumber <= categories.size()) {
                    taskCategory = categories.get(categoryNumber - 1);
                } else {
                    System.out.println("Invalid category number. Skipping category.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Skipping category.");
            }
        } else {
            System.out.println("No category chosen. Skipping category.");
        }

        LocalDate dueDate = getDueDate();

        AddTask.add(tasks, taskDescription, taskPriority, dueDate, taskCategory);
    }


    private static LocalDate getDueDate() {
        LocalDate dueDate;
        do {
            try {
                System.out.print("Enter due date (YYYY-MM-DD): ");
                String dateInput = scanner.nextLine();
                dueDate = LocalDate.parse(dateInput);
                break;
            } catch (Exception e) {
                System.out.println("Invalid date format. Please enter in YYYY-MM-DD.");
            }
        } while (true);
        return dueDate;
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
        try (PrintWriter writer = new PrintWriter(new FileWriter(TASK_FILE))) {
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
        try (BufferedReader reader = new BufferedReader(new FileReader(TASK_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length >= 4) {
                    String description = parts[0];
                    int priority = Integer.parseInt(parts[1]);
                    LocalDate deadline = LocalDate.parse(parts[2]);
                    boolean isDone = Boolean.parseBoolean(parts[3]);

                    Task task = new Task(description, priority, deadline, "DefaultCategoryOrNull");

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