package com.labs.textprocessor.datamanagement;

import java.util.Objects;

/**
 * The type Task.
 */
public class Task {
    // Unique task identifier
    private String taskId;

    // Task description
    private String description;

    // Task priority (higher number means higher priority)
    private int priority;

    /**
     * Instantiates a new Task.
     *
     * @param taskId      the task id
     * @param description the description
     * @param priority    the priority
     */
    public Task(String taskId, String description, int priority) {
        this.taskId = taskId;
        this.description = description;
        this.priority = priority;
    }

    /**
     * Gets task id.
     *
     * @return the task id
     */
// Getters and setters
    public String getTaskId() {
        return taskId;
    }

    /**
     * Sets task id.
     *
     * @param taskId the task id
     */
    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    /**
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets description.
     *
     * @param description the description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets priority.
     *
     * @return the priority
     */
    public int getPriority() {
        return priority;
    }

    /**
     * Sets priority.
     *
     * @param priority the priority
     */
    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Task task = (Task) obj;
        return taskId.equals(task.taskId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskId);
    }

    @Override
    public String toString() {
        return "Task [taskId=" + taskId + ", description=" + description + ", priority=" + priority + "]";
    }
}
