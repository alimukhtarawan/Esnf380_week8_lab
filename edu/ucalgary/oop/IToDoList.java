package edu.ucalgary.oop;

import java.util.List;

interface IToDoList {
    public List<Task> add(Task action);
    public boolean complete(Task action);
    public List<Task> delete(Task action);
    public Task edit(Task oldTask, Task newTask);
    public String listing();
    public boolean undo();
}
