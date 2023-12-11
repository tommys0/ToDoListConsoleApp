import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Settings {
    private static final String SETTINGS_FILE = "settings.txt";
    private ArrayList<String> categories;
    private boolean colorOption;
    private boolean priorityOrder;

    public Settings(ArrayList<String> categories, boolean colorOption, boolean priorityOrder) {
        this.categories = categories;
        this.colorOption = colorOption;
        this.priorityOrder = priorityOrder;
    }

    public Settings() {
        this.categories = new ArrayList<>();
        this.colorOption = false;
        this.priorityOrder = true;
    }

    public ArrayList<String> getCategories() {
        return categories;
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
            System.out.println("3. Toggle color blind mode");
            System.out.println("4. Exit settings");

            String userInput = scanner.nextLine();

            switch (userInput) {
                case "1":
                    addCategory(scanner);
                    break;
                case "2":
                    removeCategory(scanner);
                    break;
                case "3":
                    toggleColorOption();
                    System.out.println("Color blind mode toggled: " + colorOption);
                    break;
                case "4":
                    exitSettings = true;
                    break;
                default:
                    System.out.println("Invalid option. Please choose a valid action.");
            }
        } while (!exitSettings);
    }

    public void toggleColorOption() {
        colorOption = !colorOption;
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
        scanner.nextLine();
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

    public void saveSettingsToFile() {
        try (FileWriter fileWriter = new FileWriter(SETTINGS_FILE);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {

            bufferedWriter.write(String.valueOf(colorOption));
            bufferedWriter.newLine();
            bufferedWriter.write(String.valueOf(priorityOrder));
            bufferedWriter.newLine();

            for (String category : categories) {
                bufferedWriter.write("Category: " + category);
                bufferedWriter.newLine();
            }

            System.out.println("Settings saved to file.");
        } catch (IOException e) {
            System.out.println("Error while saving settings: " + e.getMessage());
        }
    }

    public void loadSettingsFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(SETTINGS_FILE))) {
            categories.clear(); // Clear previous categories
            String line;
            int lineCount = 0;
            while ((line = reader.readLine()) != null && lineCount < 2) {
                if (lineCount == 0) {
                    colorOption = Boolean.parseBoolean(line);
                } else if (lineCount == 1) {
                    priorityOrder = Boolean.parseBoolean(line);
                } else {
                    if (line.startsWith("Category: ")) {
                        categories.add(line.substring("Category: ".length()));
                    }
                }
                lineCount++;
            }
            System.out.println("Settings loaded from file.");
        } catch (IOException e) {
            System.out.println("Error while loading settings: " + e.getMessage());
        }
    }
}