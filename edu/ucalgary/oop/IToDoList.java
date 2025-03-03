package edu.ucalgary.oop;

interface IToDoList {
    public void add(Task action);
    public void complete(Task action);
    public void delete(Task action);
    public Task edit(Task oldTask, Task newTask);
    public String listing();
    public boolean undo();
}
