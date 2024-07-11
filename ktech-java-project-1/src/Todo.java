import java.text.SimpleDateFormat;
import java.util.Date;

public class Todo {
//    public static String csv;
//    public String task;
//    public boolean isCompleted;
//    private int title;
//    String deadline;
//    private boolean iscompleted;
//
//    public Todo(String task, String deadline) {
//        this.task = task;
//        this.deadline = deadline;
//        this.iscompleted = false;
//
//    }
//
//    public int getTitle() {
//        return title;
//    }
//
//    public void setTitle(int title) {
//        this.title = title;
//    }
//
//    public String getDeadline() {
//        return deadline;
//    }
//
//    public void setDeadline(String deadline) {
//        this.deadline = deadline;
//    }

    String description;
    boolean isDone;
    Date deadline;

    Todo(String description, String deadline) {
        this.description = description;
        this.isDone = false;
        try {
            this.deadline = new SimpleDateFormat("yyyy-MM-dd").parse(deadline);
        } catch (Exception e) {
            this.deadline = new Date();
        }
    }

    void markAsDone() {
        this.isDone = true;
    }

    boolean isPastDeadline() {
        return new Date().after(this.deadline);
    }

    @Override
    public String toString() {
        return description + (isDone ? " (Done)" : "");
    }
}




