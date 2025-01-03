package com.labs.textprocessor.datamanagement;

import com.labs.textprocessor.utils.InvalidTaskParameterException;
import com.labs.textprocessor.utils.TaskAlreadyExistsException;
import com.labs.textprocessor.utils.TaskNotFoundException;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DataManager {
    private Set<Task> taskSet;
    private Map<String, Task> taskMap;
    private String errorMessage = "Task with name '%s' not found";

    /**
     * Instantiates a new Data manager.
     */
    public DataManager() {
        taskSet = new HashSet<>();
        taskMap = new HashMap<>();
    }

    /**
     * Create task.
     *
     * @param taskName    the task name
     * @param description the description
     * @param priority    the priority
     * @throws TaskAlreadyExistsException if task with same name exists
     * @throws InvalidTaskParameterException if parameters are invalid
     */
    public void createTask(String taskName, String description, int priority)
            throws TaskAlreadyExistsException, InvalidTaskParameterException {
        if (taskName == null || taskName.trim().isEmpty()) {
            throw new InvalidTaskParameterException("Task name cannot be null or empty");
        }
        if (description == null) {
            throw new InvalidTaskParameterException("Description cannot be null");
        }
        if (priority < 0) {
            throw new InvalidTaskParameterException("Priority cannot be negative");
        }

        // Check for existing task
        if (taskMap.containsKey(taskName)) {
            throw new TaskAlreadyExistsException("Task with name '" + taskName + "' already exists");
        }

        Task task = new Task(taskName, description, priority);
        taskSet.add(task);
        taskMap.put(taskName, task);
    }

    /**
     * Update task.
     *
     * @param taskId         the task id
     * @param newDescription the new description
     * @param newPriority    the new priority
     * @throws TaskNotFoundException if task doesn't exist
     * @throws InvalidTaskParameterException if parameters are invalid
     */
    public void updateTask(String taskId, String newDescription, int newPriority)
            throws TaskNotFoundException, InvalidTaskParameterException {
        if (newDescription == null) {
            throw new InvalidTaskParameterException("Description cannot be null");
        }
        if (newPriority < 0) {
            throw new InvalidTaskParameterException("Priority cannot be negative");
        }

        Task task = taskMap.get(taskId);
        if (task == null) {
            throw new TaskNotFoundException(String.format(errorMessage, taskId));
        }

        task.setDescription(newDescription);
        task.setPriority(newPriority);
    }

    /**
     * Delete task.
     *
     * @param taskId the task id
     * @throws TaskNotFoundException if task doesn't exist
     */
    public void deleteTask(String taskId) throws TaskNotFoundException {
        Task task = taskMap.remove(taskId);
        if (task == null) {
            throw new TaskNotFoundException(String.format(errorMessage, taskId));
        }
        taskSet.remove(task);
    }

    /**
     * Gets all tasks.
     *
     * @return the all tasks
     */
    public Map<String, Task> getAllTasks() {
        return new HashMap<>(taskMap); // Return a copy to prevent external modification
    }

    /**
     * Gets task by id.
     *
     * @param taskId the task id
     * @return the task by id
     * @throws TaskNotFoundException if task doesn't exist
     */
    public Task getTaskById(String taskId) throws TaskNotFoundException {
        Task task = taskMap.get(taskId);
        if (task == null) {
            throw new TaskNotFoundException(String.format(errorMessage, taskId));
        }
        return task;
    }

    /**
     * Gets task count.
     *
     * @return the task count
     */
    public int getTaskCount() {
        return taskSet.size();
    }
}