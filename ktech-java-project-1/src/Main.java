import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
//    //private static final String FILE_NAME = ;
//    private static ArrayList<Todo> todos = new ArrayList<>();
//    private static final String FILENAME = "todos.csv";
//
//    public static void main(String[] args) throws IOException {
//        Scanner scanner = new Scanner(System.in);
//        while(true){
//            displayTodos();
//            System.out.println("Input: " );
//            int choice = scanner.nextInt();
//            scanner.nextLine();
//
//            switch(choice){
//                case 1:
//                    createTodo(scanner);
//                    break;
//                case 2:
//                    editToto(scanner);
//                    break;
//                case 3:
//                    finishTodo(scanner);
//                    break;
//                case 4:
//                    deleteTodo(scanner);
//                    break;
//                case 5:
//                    saveTodo();
//                    System.out.println("Exiting...");
//                    return;
//                default:
//                    System.out.println("Invalid choice");
//
//            }
//        }
//    }
//    private static void displayTodos(){
//        if(todos.isEmpty()){
//            System.out.println("Welcome!!! \n\nYou have no more TODOs left!!!");
//        }else{
//            System.out.println("Current TODOs: ");
//            for (int i = 0; i < todos.size(); i++) {
//                System.out.println((i + 1) + ". " + todos.get(i).toString());
//            }
//        }
//        System.out.println("\n1. Create TODO");
//        System.out.println("2. Edit TODO");
//        System.out.println("3. Finish TODO");
//        System.out.println("4. Delete TODO");
//        System.out.println("5. Exit\n");
//    }
//    private static void createTodo(Scanner scanner){
//        System.out.print("Title: ");
//        String task = scanner.nextLine();
//        System.out.print("Until: ");
//        String deadline = scanner.nextLine();
//        todos.add(new Todo(task, deadline));
//        System.out.println("Saved!!!");
//    }
//    private static void editToto(Scanner scanner){
//        if (todos.isEmpty()) {
//            System.out.println("No TODOs to edit.");
//            return;
//        }
//        System.out.print("Enter TODO number to edit: ");
//        int number = scanner.nextInt();
//        scanner.nextLine(); // Consume newline
//        if (number < 1 || number > todos.size()) {
//            System.out.println("Invalid TODO number.");
//            return;
//        }
//        Todo todo = todos.get(number - 1);
//        System.out.print("Enter new task: ");
//        todo.task = scanner.nextLine();
//        System.out.print("Enter new deadline (yyyy-mm-dd): ");
//        todo.deadline = scanner.nextLine();
//    }
//    private static void finishTodo(Scanner scanner){
//        if (todos.isEmpty()) {
//            System.out.println("No TODOs to finish.");
//            return;
//        }
//        System.out.print("Enter TODO number to finish: ");
//        int number = scanner.nextInt();
//        scanner.nextLine();
//        if (number < 1 || number > todos.size()) {
//            System.out.println("Invalid TODO number.");
//            return;
//        }
//        todos.get(number - 1).isCompleted = true;
//    }
//    private static void deleteTodo(Scanner scanner){
//        if (todos.isEmpty()) {
//            System.out.println("No TODOs to delete.");
//            return;
//        }
//        System.out.print("Enter TODO number to delete: ");
//        int number = scanner.nextInt();
//        scanner.nextLine();
//        if (number < 1 || number > todos.size()) {
//            System.out.println("Invalid TODO number.");
//            return;
//        }
//        Todo todo = todos.get(number - 1);
//    }
//    private static void saveTodo() throws IOException {
//        try(FileReader fileReader = new FileReader("movie.csv");
//            BufferedReader reader = new BufferedReader(fileReader)) {
//            String line;
//            while ((line = reader.readLine()) != null) {
//                String[] parts = line.split("\\|");
//                if (parts.length == 3) {
//                    Todo todo = new Todo(parts[0], parts[1]);
//                    todo.isCompleted = Boolean.parseBoolean(parts[2]);
//                    todos.add(todo);
//                }
//            }
//        } catch (FileNotFoundException e) {
//            System.out.println("No saved TODOs found.");
//        } catch (IOException e) {
//            System.out.println("An error occurred while loading TODOs.");
//        }
//
//    }
//}
private TodoService todoService;
    private Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        TodoRepository todoRepository = new TodoRepository();
        TodoService todoService = new TodoService(todoRepository);
        Main manager = new Main(todoService);
        manager.run();
    }

    public Main(TodoService todoService) {
        this.todoService = todoService;
    }

    public void run() {
        while (true) {
            printWelcomeMessage();
            System.out.print("Input: ");
            int choice = readIntInput();
            switch (choice) {
                case 1:
                    createTodo();
                    break;
                case 2:
                    editTodo();
                    break;
                case 3:
                    finishTodo();
                    break;
                case 4:
                    deleteTodo();
                    break;
                case 5:
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }

    private void printWelcomeMessage() {
        System.out.println("Welcome!");

        List<Todo> pendingTodos = todoService.getPendingTodos();
        if (pendingTodos.isEmpty()) {
            System.out.println("You have no more TODOs left!!!");
        } else {
            System.out.println("You have " + pendingTodos.size() + " TODO" + (pendingTodos.size() > 1 ? "s" : "") + " left.");
            int todoIndex = 1;
            for (Todo todo : pendingTodos) {
                System.out.println(todoIndex + ". " + todo);
                todoIndex++;
            }
        }

        System.out.println("1. Create TODO");
        System.out.println("2. Edit TODO");
        System.out.println("3. Finish TODO");
        System.out.println("4. Delete TODO");
        System.out.println("5. Exit");
    }
//todo 만들기
    private void createTodo() {
        System.out.print("Title: ");
        String description = scanner.nextLine();
        System.out.print("Until: ");
        String deadline = scanner.nextLine();
        todoService.addTodo(description, deadline);
        System.out.println("Saved!!!");
    }
//todo 수정
    private void editTodo() {
        if (todoService.getTodos().isEmpty()) {
            System.out.println("No TODOs to edit.");
            return;
        }

        printTodosForEditing();
        int todoIndex = readIntInput("Edit TODO number: ");
        if (todoIndex < 1 || todoIndex > todoService.getTodos().size()) {
            System.out.println("Invalid TODO number.");
            return;
        }

        Todo todoToEdit = todoService.getTodos().get(todoIndex - 1);
        System.out.print("Title (" + todoToEdit.description + "): ");
        String newDescription = scanner.nextLine();
        System.out.print("Until (" + new SimpleDateFormat("yyyy-MM-dd").format(todoToEdit.deadline) + "): ");
        String newDeadline = scanner.nextLine();

        try {
            todoService.editTodo(todoIndex - 1, newDescription, newDeadline);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println("Saved!!!");
    }
//왕료
    private void finishTodo() {
        if (todoService.getTodos().isEmpty()) {
            System.out.println("No TODOs to finish.");
            return;
        }

        printTodosForEditing();
        int todoIndex = readIntInput("Finish TODO number: ");
        if (todoIndex < 1 || todoIndex > todoService.getTodos().size()) {
            System.out.println("Invalid TODO number.");
            return;
        }

        todoService.markTodoAsDone(todoIndex - 1);
        System.out.println("TODO marked as Done!");
    }

    private void deleteTodo() {
        if (todoService.getTodos().isEmpty()) {
            System.out.println("No TODOs to delete.");
            return;
        }

        printTodosForEditing();
        int todoIndex = readIntInput("Delete TODO number: ");
        if (todoIndex < 1 || todoIndex > todoService.getTodos().size()) {
            System.out.println("Invalid TODO number.");
            return;
        }

        todoService.deleteTodo(todoIndex - 1);
        System.out.println("Deleted TODO.");
    }

    private void printTodosForEditing() {
        System.out.println("\nCurrent TODOs:");
        int todoIndex = 1;
        for (Todo todo : todoService.getTodos()) {
            System.out.println(todoIndex + ". " + todo);
            todoIndex++;
        }
    }

    private int readIntInput() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    private int readIntInput(String prompt) {
        System.out.print(prompt);
        return readIntInput();
    }
}
