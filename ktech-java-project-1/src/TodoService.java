import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TodoService {
    private List<Todo> todos;
    private TodoRepository todoRepository;

    TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
        this.todos = todoRepository.loadTodos();
    }

    List<Todo> getTodos() {
        return new ArrayList<>(todos);
    }

    List<Todo> getPendingTodos() {
        List<Todo> pendingTodos = new ArrayList<>();
        for (Todo todo : todos) {
            if (!todo.isDone || !todo.isPastDeadline()) {
                pendingTodos.add(todo);
            }
        }
        pendingTodos.sort(Comparator.comparing(todo -> todo.deadline));
        return pendingTodos;
    }

    void addTodo(String description, String deadline) {
        todos.add(new Todo(description, deadline));
        todoRepository.saveTodos(todos);
    }

    void editTodo(int index, String newDescription, String newDeadline) throws ParseException {
        Todo todo = todos.get(index);
        if (!newDescription.isEmpty()) {
            todo.description = newDescription;
        }
        if (!newDeadline.isEmpty()) {
            todo.deadline = new SimpleDateFormat("yyyy-MM-dd").parse(newDeadline);
        }
        todoRepository.saveTodos(todos);
    }

    void markTodoAsDone(int index) {
        todos.get(index).markAsDone();
        todoRepository.saveTodos(todos);
    }

    void deleteTodo(int index) {
        todos.remove(index);
        todoRepository.saveTodos(todos);
    }
}
