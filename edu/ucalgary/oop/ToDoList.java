package edu.ucalgary.oop;

import java.util.ArrayList;
import java.util.List;


public class ToDoList implements IToDoList {
    private Task task; //Probably remove.
    private List<Task> tasks = new ArrayList<>();
    
    @Override
    public void add(Task task) {
        tasks.add(task);
    }

    @Override
    public void delete(Task task) {
        tasks.remove(task);
    }

    @Override
    public void complete(Task task) {
        task.setCompleted(true);
    }

   


}
