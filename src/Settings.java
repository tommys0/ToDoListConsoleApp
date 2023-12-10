import java.util.ArrayList;
import java.util.Scanner;

public class Settings {
    private ArrayList<String> categories;
    private boolean colorOption;
    private boolean priorityOrder;

    public Settings(ArrayList<String> categories, boolean colorOption, boolean priorityOrder) {
        this.categories = categories;
        this.colorOption = colorOption;
        this.priorityOrder = priorityOrder;
    }

    public Settings() {
        // Default constructor
        this.categories = new ArrayList<>();
        this.colorOption = false;
        this.priorityOrder = true;  // Default is true for ascending order
    }

    public void modifyCategories() {
        Scanner scanner = new Scanner(System.in);
        boolean exitSettings = false;

        do {
            System.out.println("\nCategories:");
            if (categories.isEmpty()) {
                System.out.println("No categories available.");
            } else {
                for (int i = 0; i < categories.size(); i++) {
                    System.out.println((i + 1) + ". " + categories.get(i));
                }
            }
            System.out.println("Options:");
            System.out.println("1. Add a category");
            System.out.println("2. Remove a category");
            System.out.println("3. Exit settings");

            String userInput = scanner.nextLine();

            switch (userInput) {
                case "1":
                    addCategory(scanner);
                    break;
                case "2":
                    removeCategory(scanner);
                    break;
                case "3":
                    exitSettings = true;
                    break;
                default:
                    System.out.println("Invalid option. Please choose a valid action.");
            }
        } while (!exitSettings);
    }

    private void addCategory(Scanner scanner) {
        System.out.print("Enter category to add: ");
        String category = scanner.nextLine();
        categories.add(category);
        System.out.println("Category added: " + category);
    }

    private void removeCategory(Scanner scanner) {
        if (categories.isEmpty()) {
            System.out.println("No categories to remove.");
            return;
        }
        System.out.println("Select a category to remove:");
        for (int i = 0; i < categories.size(); i++) {
            System.out.println((i + 1) + ". " + categories.get(i));
        }
        int index = scanner.nextInt();
        scanner.nextLine();  // Consume newline character
        if (index > 0 && index <= categories.size()) {
            String removedCategory = categories.remove(index - 1);
            System.out.println("Category removed: " + removedCategory);
        } else {
            System.out.println("Invalid index!");
        }
    }

    public boolean isColorOption() {
        return colorOption;
    }

    public void setColorOption(boolean colorOption) {
        this.colorOption = colorOption;
    }
}
