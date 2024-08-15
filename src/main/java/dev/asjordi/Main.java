package dev.asjordi;

import dev.asjordi.models.Status;
import dev.asjordi.models.Task;
import dev.asjordi.service.TaskService;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    private static final TaskService taskService = new TaskService();
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        String line = "";
        System.out.println("Task Tracker CLI");

        while (!line.equals("exit")) {
            System.out.print("Enter a command: ");
            line = sc.nextLine();

            if (line.replace(" ", "").trim().equalsIgnoreCase("exit")) break;

            String[] arguments = line.split(" ");
            switch (arguments.length) {
                case 0 -> processEmptyArguments(arguments);
                case 1 -> processSingleArgument(arguments);
                default -> processMultipleArguments(arguments);
            }
        }
    }

    public static void processMultipleArguments(String[] args) {
        switch (args[0]) {
            case "add" -> processAddArgument(args);
            case "update" -> processUpdateArgument(args);
            case "delete" -> processDeleteArgument(args);
            case "list" -> processListArgument(args);
            case "mark-in-progress" -> processMarkInProgressArgument(args);
            case "mark-done" -> processMarkDoneArgument(args);
            default -> System.out.println("Invalid command. Use 'help' to display the available commands.");
        }
    }

    private static void processAddArgument(String[] args) {
        Task task = new Task(String.join(" ", Arrays.copyOfRange(args, 1, args.length)), Status.TODO);
        taskService.add(task);
        System.out.println("Task added successfully (ID: " + task.getTaskId() + ")");
    }

    private static void processMarkDoneArgument(String[] args) {
        int id = -1;
        try {
            id = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            System.out.println("Invalid command. Use 'help' to display the available commands.");
            return;
        }
        var task = taskService.getById(id);
        if (task != null) {
            task.setStatus(Status.DONE);
            task.setUpdatedAt(LocalDate.now());
            taskService.update(task);
            System.out.println("Task with id " + id + " is now done.");
        } else System.out.println("Task not found.");
    }

    private static void processMarkInProgressArgument(String[] args) {
        int id = -1;
        try {
            id = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            System.out.println("Invalid command. Use 'help' to display the available commands.");
            return;
        }
        var task = taskService.getById(id);
        if (task != null) {
            task.setStatus(Status.IN_PROGRESS);
            task.setUpdatedAt(LocalDate.now());
            taskService.update(task);
            System.out.println("Task with id " + id + " is now in progress.");
        } else System.out.println("Task not found.");
    }

    private static void processDeleteArgument(String[] args) {
        int id = -1;
        try {
            id = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            System.out.println("Invalid command. Use 'help' to display the available commands.");
            return;
        }
        if (taskService.delete(id)) System.out.println("Task deleted.");
        else System.out.println("Task not found.");
    }

    private static void processUpdateArgument(String[] args) {
        int id = -1;
        try {
            id = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            System.out.println("Invalid command. Use 'help' to display the available commands.");
            return;
        }
        var task = taskService.getById(id);
        if (task != null) {
            task.setDescription(String.join(" ", Arrays.copyOfRange(args, 2, args.length)));
            task.setUpdatedAt(LocalDate.now());
            taskService.update(task);
            System.out.println("Task updated.");
        } else System.out.println("Task not found.");
    }

    private static void processListArgument(String[] args) {
        switch (args[1]) {
            case "done" -> taskService.getByStatus(Status.DONE).forEach(System.out::println);
            case "todo" -> taskService.getByStatus(Status.TODO).forEach(System.out::println);
            case "in-progress" -> taskService.getByStatus(Status.IN_PROGRESS).forEach(System.out::println);
            default -> System.out.println("Invalid command. Use 'help' to display the available commands.");
        }
    }

    public static void processSingleArgument(String[] args) {
        String argument = args[0];
        switch (argument) {
            case "list" -> {
                var tasks = taskService.getAll();
                if (tasks == null || tasks.isEmpty()) System.out.println("No tasks found.");
                else tasks.forEach(System.out::println);
            }
            case "help" -> showHelp();
            default -> System.out.println("Invalid command. Use 'help' to display the available commands.");
        }
    }

    public static void processEmptyArguments(String[] args) {
        System.out.println("No arguments provided. Use 'help' to display the available commands.");
    }

    private static void showHelp() {
        System.out.println("Usage: java -jar task-tracker.jar [command]");
        System.out.println("The options below may be used to perform the desired operations:");
        System.out.println("    add <description>               - Add a new task");
        System.out.println("    update <taskId> <description>   - Update a task");
        System.out.println("    delete <taskId>                 - Delete a task");
        System.out.println("    mark-in-progress <taskId>       - Mark a task as in progress");
        System.out.println("    mark-done <taskId>              - Mark a task as done");
        System.out.println("    list                            - List all tasks");
        System.out.println("    list done                       - List all done tasks");
        System.out.println("    list todo                       - List all todo tasks");
        System.out.println("    list in-progress                - List all in progress tasks");
        System.out.println("    help                            - Display help information");
    }
}