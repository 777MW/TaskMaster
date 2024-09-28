import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Main {
    private static final String FILE_NAME = "todoitems.txt";
    private static List<String> todoList = new ArrayList<>();

    public static void main(String[] args) {
        loadTodoItems();

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("____________");
            displayTodoItems();
            System.out.println("\nEnter a command (Add [task] or Complete [number], or type 'exit' to quit): ");
            System.out.println("Examples: \nAdd Get eggs from grocery store\nComplete 2");
            System.out.println("____________");
            String userInput = scanner.nextLine();

            if (userInput.equalsIgnoreCase("exit")) {
                break;
            } else if (userInput.startsWith("Add ")) {
                addTask(userInput.substring(4).trim());
            } else if (userInput.startsWith("Complete ")) {
                try {
                    int taskNumber = Integer.parseInt(userInput.substring(9).trim());
                    completeTask(taskNumber);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid number format. Please enter a valid task number.");
                }
            } else {
                System.out.println("Invalid command. Please use 'Add [task]' or 'Complete [number]'.");
            }
        }

        scanner.close();
    }

    // Load items from the file
    private static void loadTodoItems() {
        try {
            todoList = Files.readAllLines(Paths.get(FILE_NAME));
        } catch (IOException e) {
            System.out.println("Could not read/find to-do list file. Starting with an empty to-do list.");
        }
    }

    // Display items in the console
    private static void displayTodoItems() {
        if (todoList.isEmpty()) {
            System.out.println("Your to-do list is empty.");
        } else {
            System.out.println("Your To-Do List:");
            for (int i = 0; i < todoList.size(); i++) {
                System.out.println((i + 1) + ". " + todoList.get(i));
            }
        }
    }

    // Add a task to the list
    private static void addTask(String task) {
        todoList.add(task);
        saveTodoItems();
        System.out.println("Added task: " + task);
    }

    // Complete a task by removing it
    private static void completeTask(int taskNumber) {
        if (taskNumber > 0 && taskNumber <= todoList.size()) {
            String removedTask = todoList.remove(taskNumber - 1);
            saveTodoItems();
            System.out.println("Completed task: " + removedTask);
        } else {
            System.out.println("Invalid task number. Please try again.");
        }
    }

    // Save items back to the file
    private static void saveTodoItems() {
        try {
            Files.write(Paths.get(FILE_NAME), todoList);
        } catch (IOException e) {
            System.out.println("Could not save to-do items to file.");
        }
    }
}
