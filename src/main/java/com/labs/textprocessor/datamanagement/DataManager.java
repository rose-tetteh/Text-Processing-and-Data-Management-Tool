package com.labs.textprocessor.datamanagement;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class DataManager {

    private Set<Task> taskSet;
    private Map<String, Task> taskMap;

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
     * @param taskName      the task id
     * @param description the description
     * @param priority    the priority
     */
    public void createTask(String taskName, String description, int priority) {
        Task task = new Task(taskName, description, priority);
        taskSet.add(task);
        taskMap.put(taskName, task);
    }

    /**
     * Update task boolean.
     *
     * @param taskId         the task id
     * @param newDescription the new description
     * @param newPriority    the new priority
     * @return the boolean
     */
    public boolean updateTask(String taskId, String newDescription, int newPriority) {
        Task task = taskMap.get(taskId);
        if (task != null) {
            task.setDescription(newDescription);
            task.setPriority(newPriority);
            return true;
        }
        return false;
    }

    /**
     * Delete task boolean.
     *
     * @param taskId the task id
     * @return the boolean
     */
    public boolean deleteTask(String taskId) {
        Task task = taskMap.remove(taskId);
        if (task != null) {
            taskSet.remove(task);
            return true;
        }
        return false;
    }

    /**
     * Gets all tasks.
     *
     * @return the all tasks
     */
    public Map<String, Task> getAllTasks() {
        return taskMap;
    }

    /**
     * Gets task by id.
     *
     * @param taskId the task id
     * @return the task by id
     */
    public Task getTaskById(String taskId) {
        return taskMap.get(taskId);
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
