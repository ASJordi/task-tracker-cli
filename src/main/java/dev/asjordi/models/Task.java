package dev.asjordi.models;

import java.time.LocalDate;
import java.util.Objects;
import java.util.StringJoiner;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Task {

    public static int id = 0;
    private int taskId;
    private String description;
    private Status status;
    private final LocalDate createdAt;
    private LocalDate updatedAt;

    @JsonCreator
    public Task(@JsonProperty("taskId") int taskId,
                @JsonProperty("description") String description,
                @JsonProperty("status") Status status,
                @JsonProperty("createdAt") LocalDate createdAt,
                @JsonProperty("updatedAt") LocalDate updatedAt) {
        id = taskId;
        this.taskId = taskId;
        this.description = description;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Task(String description, Status status) {
        this.taskId = ++id;
        this.description = description;
        this.status = status;
        this.createdAt = LocalDate.now();
        this.updatedAt = null;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Task.class.getSimpleName() + "[", "]")
                .add("taskId=" + taskId)
                .add("description='" + description + "'")
                .add("status=" + status)
                .add("createdAt=" + createdAt)
                .add("updatedAt=" + updatedAt)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task task)) return false;
        return getTaskId() == task.getTaskId() && Objects.equals(getDescription(), task.getDescription()) && getStatus() == task.getStatus() && Objects.equals(getCreatedAt(), task.getCreatedAt()) && Objects.equals(getUpdatedAt(), task.getUpdatedAt());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTaskId(), getDescription(), getStatus(), getCreatedAt(), getUpdatedAt());
    }
}
