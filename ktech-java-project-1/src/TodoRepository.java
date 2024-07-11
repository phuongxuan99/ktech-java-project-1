import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class TodoRepository {
    private final String filePath = "todos.csv";
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    List<Todo> loadTodos() {
        List<Todo> todos = new ArrayList<>();
        File file = new File(filePath);

        // 파일이 존재하지 않으면 새 파일 생성
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String description = parts[0];
                boolean isDone = Boolean.parseBoolean(parts[1]);
                LocalDate deadline = LocalDate.parse(parts[2], formatter);
                Todo todo = new Todo(description, deadline.toString());
                if (isDone) {
                    todo.markAsDone();
                }
                todos.add(todo);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return todos;
    }

    void saveTodos(List<Todo> todos) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (Todo todo : todos) {
                bw.write(todo.description + "," + todo.isDone + "," + todo.deadline.toGMTString());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
