package presentation;

import service.UserService;
import service.TodoService;
import service.UserServiceImps;
import service.TodoServiceImps;
import pojo.TodoPojo;
import pojo.UserPojo;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {
    private static UserService userService = new UserServiceImps();
    private static TodoService todoService = new TodoServiceImps();
//    private static TodoService todoService = new TodoService();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n*** Welcome to the Todo App ***");
            System.out.println("1. Login");
            System.out.println("2. Sign Up");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    handleLogin();
                    break;
                case 2:
                    handleSignUp();
                    break;
                case 3:
                    System.out.println("Exiting the application. Goodbye!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");
                    break;
            }
        }
    }

    private static void handleLogin() {
        System.out.println("\n*** Login ***");
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        if (userService.loginAsync(email, password)) {
            if (email.equals("admin@example.com")) {
                handleAdminOperations();
            } else {
                UUID userUuid = userService.getCurrentUserUuidAsync(); // Get current user UUID
                handleTodoOperations(userUuid);
//                System.out.println(userUuid);
            }
        } else {
            System.out.println("Login failed. Please check your credentials.");
        }
    }

    private static void handleSignUp() {
        System.out.println("\n*** Sign Up ***");
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        if (userService.registerAsync(email, password) == null) {
        	System.out.println("Sign Up successful! You can now log in.");
        } else {
            System.out.println("User already exists. Please log in.");
        }
    }

    private static void handleTodoOperations(UUID userUuid) {
        while (true) {
            System.out.println("\n*** Todo Operations ***");
            System.out.println("1. Add Todo");
            System.out.println("2. Update Todo");
            System.out.println("3. Delete Todo");
            System.out.println("4. View Todos");
            System.out.println("5. View Deleted Todos");
            System.out.println("6. Logout");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    addTodo();
                    break;
                case 2:
                    updateTodo();
                    break;
                case 3:
                    deleteTodo();
                    break;
                case 4:
                    viewTodos();
                    break;
                case 5:
                    viewDeletedTodos();
                    break;
                case 6:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice, please try again.");
                    break;
            }
        }
    }

    private static void handleAdminOperations() {
        while (true) {
            System.out.println("\n*** Admin Operations ***");
            System.out.println("1. View All Users");
            System.out.println("2. Update User");
            System.out.println("3. Delete User");
            System.out.println("4. Logout");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    viewAllUsers();
                    break;
                case 2:
                    updateUser();
                    break;
                case 3:
                    deleteUser();
                    break;
                case 4:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice, please try again.");
                    break;
            }
        }
    }

    private static void viewAllUsers() {
        System.out.println("\n*** View All Users ***");
        Map<String, UserPojo> users = userService.getAllUsersAsync();
        
        if (users.isEmpty()) {
            System.out.println("No users found.");
        } else {
            for (String email : users.keySet()) {
                UserPojo user = users.get(email);
                System.out.println("Email: " + email + ", UUID: " + user.getUuid());
            }
        }
    }

    private static void updateUser() {
        System.out.println("\n*** Update User ***");
        System.out.print("Email of user to update: ");
        String email = scanner.nextLine();
        System.out.print("New password: ");
        String newPassword = scanner.nextLine();

        if (userService.updateUserAsync(email, newPassword) != null) {
            System.out.println("User updated successfully!");
        } else {
            System.out.println("User not found or update failed.");
        }
    }

    private static void deleteUser() {
        System.out.println("\n*** Delete User ***");
        System.out.print("Email of user to delete: ");
        String email = scanner.nextLine();

        if (userService.deleteUserAsync(email)) {
            System.out.println("User deleted successfully!");
        } else {
            System.out.println("User not found or deletion failed.");
        }
    }

    private static void addTodo() {
        System.out.println("\n*** Add Todo ***");
        System.out.print("Todo description: ");
        String description = scanner.nextLine();
        UUID userUuid = userService.getCurrentUserUuidAsync(); // Get current user UUID
        todoService.addTodoAsync(description, userUuid);
        System.out.println("Todo added successfully!");
    }

    private static void updateTodo() {
        UUID userUuid = userService.getCurrentUserUuidAsync(); // Get current user UUID
        List<TodoPojo> todos = todoService.getTodosForUserAsync(userUuid, TodoServiceImps.TodoFilter.ALL);
        
        if (todos.isEmpty()) {
            System.out.println("No todos available to update.");
            return;
        }

        System.out.println("\n*** Update Todo ***");
        for (int i = 0; i < todos.size(); i++) {
            TodoPojo todo = todos.get(i);
            System.out.println(i + ". " + todo.getDescription() + " (Completed: " + todo.isCompleted() + ")");
        }

        System.out.print("Enter the number of the todo to update: ");
        int todoId = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        if (todoId >= 0 && todoId < todos.size()) {
            System.out.print("New Todo description: ");
            String newDescription = scanner.nextLine();
            System.out.print("Completed (true/false): ");
            boolean completed = scanner.nextBoolean();
            scanner.nextLine();  // Consume newline
            todoService.updateTodoAsync(userUuid, todoId, newDescription, completed);
            System.out.println("Todo updated successfully!");
        } else {
            System.out.println("Invalid todo number.");
        }
    }

    private static void deleteTodo() {
        UUID userUuid = userService.getCurrentUserUuidAsync(); // Get current user UUID
        List<TodoPojo> todos = todoService.getTodosForUserAsync(userUuid, TodoServiceImps.TodoFilter.ALL);
        
        if (todos.isEmpty()) {
            System.out.println("No todos available to delete.");
            return;
        }

        System.out.println("\n*** Delete Todo ***");
        for (int i = 0; i < todos.size(); i++) {
            TodoPojo todo = todos.get(i);
            System.out.println(i + ". " + todo.getDescription() + " (Completed: " + todo.isCompleted() + ")");
        }

        System.out.print("Enter the number of the todo to delete: ");
        int todoId = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        if (todoId >= 0 && todoId < todos.size()) {
            todoService.deleteTodoAsync(userUuid, todoId);
            System.out.println("Todo deleted successfully!");
        } else {
            System.out.println("Invalid todo number.");
        }
    }

    private static void viewTodos() {
        System.out.println("\n*** View Todos ***");
        System.out.println("1. View all todos");
        System.out.println("2. View completed todos");
        System.out.println("3. View not completed todos");
        System.out.println("4. View todos sorted by date");
        System.out.print("Enter your choice: ");
        int viewChoice = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        TodoServiceImps.TodoFilter filter;
        switch (viewChoice) {
            case 1:
                filter = TodoServiceImps.TodoFilter.ALL;
                break;
            case 2:
                filter = TodoServiceImps.TodoFilter.COMPLETED;
                break;
            case 3:
                filter = TodoServiceImps.TodoFilter.NOT_COMPLETED;
                break;
            case 4:
            	filter = TodoServiceImps.TodoFilter.FILTER;
                break;
            default:
                System.out.println("Invalid choice. Viewing all todos by default.");
                filter = TodoServiceImps.TodoFilter.ALL;
                break;
        }

        UUID userUuid = userService.getCurrentUserUuidAsync(); // Get current user UUID
        List<TodoPojo> todos = todoService.getTodosForUserAsync(userUuid, filter);
    	if (viewChoice == 4) {
    		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            System.out.print("Enter start date (dd/MM/yyyy): ");
            String startDateStr = scanner.nextLine();
            System.out.print("Enter end date (dd/MM/yyyy): ");
            String endDateStr = scanner.nextLine();
            try {
                Date startDate = sdf.parse(startDateStr);
                Date endDate = sdf.parse(endDateStr);
                long startTimestamp = startDate.getTime();
                long endTimestamp = endDate.getTime();

                todos = todoService.getTodosForUserInRange(userUuid, startTimestamp, endTimestamp);
            } catch (ParseException e) {
                System.out.println("Invalid date format.");
                return;
            }
        }
        if (todos.isEmpty()) {
            System.out.println("No todos to display.");
        } else {
            for (TodoPojo todo : todos) {
                System.out.println("Description: " + todo.getDescription());
                System.out.println("Timestamp: " + todo.getFormattedTimestamp());
                System.out.println("Completed: " + todo.isCompleted());
                System.out.println();
            }
        }
    }
    

    private static void viewDeletedTodos() {
        System.out.println("\n*** View Deleted Todos ***");
        UUID userUuid = userService.getCurrentUserUuidAsync(); // Get current user UUID
        List<TodoPojo> deletedTodos = todoService.getDeletedTodosForUserAsync(userUuid);
        
        if (deletedTodos.isEmpty()) {
            System.out.println("No deleted todos.");
        } else {
            for (TodoPojo todo : deletedTodos) {
                System.out.println("Description: " + todo.getDescription());
                System.out.println("Completed: " + todo.isCompleted());
                System.out.println("Timestamp: " + todo.getFormattedTimestamp());
                System.out.println();
            }
        }
    }
}
