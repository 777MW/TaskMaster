import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Main {
    private static final String FILE_NAME = "todoitems.txt"; // Name of the file storing to-do items
    private static List<String> todoList = new ArrayList<>(); // List to hold the to-do items

    public static void main(String[] args) {
        // Load existing to-do items from the file at the start of the application
        loadTodoItems();

        Scanner scanner = new Scanner(System.in);
        while (true) {
            // Display current to-do list and prompt the user for a command
            System.out.println("____________");
            displayTodoItems();
            System.out.println("\nEnter a command (Add [task] or Complete [number], or type 'exit' to quit): ");
            System.out.println("Examples: \nAdd Get eggs from grocery store\nComplete 2");
            System.out.println("____________");
            String userInput = scanner.nextLine();

            if (userInput.equalsIgnoreCase("exit")) { // Exit condition
                break;
            } else if (userInput.startsWith("Add ")) { // If the user wants to add a task
                addTask(userInput.substring(4).trim()); // Extract and add the task
            } else if (userInput.startsWith("Complete ")) { // If the user wants to complete a task
                try {
                    int taskNumber = Integer.parseInt(userInput.substring(9).trim()); // Parse task number
                    completeTask(taskNumber); // Complete the specified task
                } catch (NumberFormatException e) { // Handle non-integer task numbers
                    System.out.println("Invalid number format. Please enter a valid task number.");
                }
            } else { // Invalid command handling
                System.out.println("Invalid command. Please use 'Add [task]' or 'Complete [number]'.");
            }
        }

        scanner.close(); // Close the scanner to prevent resource leaks
    }

    // Method to load existing to-do items from the "todoitems.txt" file
    private static void loadTodoItems() {
        try {
            todoList = Files.readAllLines(Paths.get(FILE_NAME)); // Read all lines from the file into the list
        } catch (IOException e) { // Handle file reading issues
            System.out.println("Could not read/find to-do list file. Starting with an empty to-do list.");
        }
    }

    // Method to display the current to-do items in the console
    private static void displayTodoItems() {
        if (todoList.isEmpty()) { // Check if the list is empty
            System.out.println("Your to-do list is empty.");
        } else {
            System.out.println("Your To-Do List:");
            // Iterate through the list and display each item with a number
            for (int i = 0; i < todoList.size(); i++) {
                System.out.println((i + 1) + ". " + todoList.get(i));
            }
        }
    }

    // Method to add a new task to the to-do list
    private static void addTask(String task) {
        todoList.add(task); // Add the new task to the list
        saveTodoItems(); // Save the updated list back to the file
        System.out.println("Added task: " + task);
    }

    // Method to complete a task by its number and remove it from the list
    private static void completeTask(int taskNumber) {
        // Check if the task number is valid (within the bounds of the list)
        if (taskNumber > 0 && taskNumber <= todoList.size()) {
            String removedTask = todoList.remove(taskNumber - 1); // Remove the task (adjusting for 0-based index)
            saveTodoItems(); // Save the updated list back to the file
            System.out.println("Completed task: " + removedTask);
        } else {
            System.out.println("Invalid task number. Please try again.");
        }
    }

    // Method to save the current to-do list back to the "todoitems.txt" file
    private static void saveTodoItems() {
        try {
            Files.write(Paths.get(FILE_NAME), todoList); // Write the updated list back to the file
        } catch (IOException e) { // Handle file writing issues
            System.out.println("Could not save to-do items to file.");
        }
    }
}
