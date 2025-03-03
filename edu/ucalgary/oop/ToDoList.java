package edu.ucalgary.oop;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class ToDoList implements IToDoList {
    private List<Task> tasks = new ArrayList<>();
    private Stack<Operation> history = new Stack<>();
    
    private class Operation {
        private OperationType type;
        private Task task;
        private Task oldTask;
        
        public Operation(OperationType type, Task task) {
            this.type = type;
            this.task = task.copy();
        }
        
        public Operation(OperationType type, Task oldTask, Task newTask) {
            this.type = type;
            this.task = newTask.copy();
            this.oldTask = oldTask.copy();
        }
    }
    
    private enum OperationType {
        ADD, DELETE, COMPLETE, EDIT
    }
    
    // Methods from IToDoList interface
    @Override
    public void add(Task task) {
        tasks.add(task);
        history.push(new Operation(OperationType.ADD, task));
    }

    @Override
    public void delete(Task task) {
        tasks.remove(task);
        history.push(new Operation(OperationType.DELETE, task));
    }

    @Override
    public void complete(Task task) {
        task.setCompleted(true);
        history.push(new Operation(OperationType.COMPLETE, task));
    }

    @Override
    public Task edit(Task oldTask, Task newTask) {
        int index = tasks.indexOf(oldTask);
        if (index != -1) {
            Task taskToEdit = tasks.get(index);
            Task originalTask = taskToEdit.copy();
            
            taskToEdit.setTitle(newTask.getTitle());
            taskToEdit.setCompleted(newTask.isCompleted());
            
            history.push(new Operation(OperationType.EDIT, originalTask, taskToEdit));
            return taskToEdit;
        }
        return null;
    }

    @Override
    public String listing() {
        StringBuilder sb = new StringBuilder();
        for (Task task : tasks) {
            sb.append(task.getId()).append(": ")
              .append(task.getTitle())
              .append(" [").append(task.isCompleted() ? "Completed" : "Pending").append("]")
              .append("\n");
        }
        return sb.toString();
    }

    @Override
    public boolean undo() {
        if (history.isEmpty()) {
            return false;
        }
        
        Operation lastOperation = history.pop();
        
        switch (lastOperation.type) {
            case ADD:
                tasks.remove(findTaskById(lastOperation.task.getId()));
                break;
                
            case DELETE:
                tasks.add(lastOperation.task);
                break;
                
            case COMPLETE:
                Task taskToUncomplete = findTaskById(lastOperation.task.getId());
                if (taskToUncomplete != null) {
                    taskToUncomplete.setCompleted(false);
                }
                break;
                
            case EDIT:
                Task taskToRevert = findTaskById(lastOperation.oldTask.getId());
                if (taskToRevert != null) {
                    taskToRevert.setTitle(lastOperation.oldTask.getTitle());
                    taskToRevert.setCompleted(lastOperation.oldTask.isCompleted());
                }
                break;
        }
        
        return true;
    }
    
    public void addTask(Task task) {
        add(task);
    }
    
    public List<Task> listTasks() {
        return new ArrayList<>(tasks);
    }
    
    public void completeTask(String id) {
        Task task = findTaskById(id);
        if (task != null) {
            complete(task);
        }
    }
    
    public void deleteTask(String id) {
        Task task = findTaskById(id);
        if (task != null) {
            delete(task);
        }
    }
    
    public void editTask(String id, String newTitle, boolean completed) {
        Task task = findTaskById(id);
        if (task != null) {
            Task newTask = new Task(id, newTitle, completed);
            edit(task, newTask);
        }
    }
    
    private Task findTaskById(String id) {
        for (Task task : tasks) {
            if (task.getId().equals(id)) {
                return task;
            }
        }
        return null;
    }
}
