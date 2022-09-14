package global.goit.romashko.concurrency.web.task;

import java.util.Objects;

public class Task {
    private final String userId;
    private final int id;
    private String title;
    private boolean completed;

    public Task(String userId, int id) {
        this.userId = userId;
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Task task = (Task) o;

        if (id != task.id) return false;
        return Objects.equals(userId, task.userId);
    }

    @Override
    public int hashCode() {
        int result = userId != null ? userId.hashCode() : 0;
        result = 31 * result + id;
        return result;
    }

    @Override
    public String toString() {
        return "Task{" +
                "userId='" + userId + '\'' +
                ", id=" + id +
                ", title='" + title + '\'' +
                ", completed=" + completed +
                '}';
    }
}
