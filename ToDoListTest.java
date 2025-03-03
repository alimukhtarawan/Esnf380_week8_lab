package edu.ucalgary.oop;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.List;

public class ToDoListTest {
    private ToDoList toDoList;
    private Task expectedTask;
    private List<Task> actualTasks;

    @Before
    public void setUp() {
        toDoList = new ToDoList();
    }

    @Test
    public void testAddTask() {
        expectedTask = new Task("1", "Learn Java");
        toDoList.addTask(expectedTask);
        actualTasks = toDoList.listTasks();

        assertFalse("Task list should not be empty after adding a task", actualTasks.isEmpty());
        assertEquals("Task list size should be 1 after adding a task", 1, actualTasks.size());
        assertEquals("The added task should match the one in the list", expectedTask, actualTasks.get(0));
    }

    @Test
    public void testCompleteTask() {
        expectedTask = new Task("1", "Learn Java");
        toDoList.addTask(expectedTask);
        toDoList.completeTask("1");
        boolean taskCompleted = toDoList.listTasks().get(0).isCompleted();

        assertTrue("Task should be marked as completed", taskCompleted);
    }

    @Test
    public void testDeleteTask() {
        expectedTask = new Task("1", "Learn Java");
        toDoList.addTask(expectedTask);
        toDoList.deleteTask("1");
        actualTasks = toDoList.listTasks();

        assertTrue("Task list should be empty after deleting the task", actualTasks.isEmpty());
    }

    @Test
    public void testEditTask() {
        expectedTask = new Task("1", "Learn Java");
        toDoList.addTask(expectedTask);
        toDoList.editTask("1", "Learn JUnit", true);
        Task actualEditedTask = toDoList.listTasks().get(0);

        assertEquals("Task title should be updated after edit", "Learn JUnit", actualEditedTask.getTitle());
        assertTrue("Task should be marked as completed after edit", actualEditedTask.isCompleted());
    }

    @Test
    public void testUndoAddTask() {
        toDoList.addTask(new Task("1", "Learn Java"));
        toDoList.undo();
        actualTasks = toDoList.listTasks();

        assertTrue("Task list should be empty after undoing add task", actualTasks.isEmpty());
    }

    @Test
    public void testUndoDeleteTask() {
        expectedTask = new Task("1", "Learn Java");
        toDoList.addTask(expectedTask);
        toDoList.deleteTask("1");
        toDoList.undo();
        actualTasks = toDoList.listTasks();

        assertFalse("Task list should not be empty after undoing delete task", actualTasks.isEmpty());
        assertEquals("Task list should contain the undone task after undoing delete", expectedTask, actualTasks.get(0));
    }

    @Test
    public void testMultipleUndos() {
        Task firstExpectedTask = new Task("1", "Task 1");
        Task secondExpectedTask = new Task("2", "Task 2");
        toDoList.addTask(firstExpectedTask);
        toDoList.addTask(secondExpectedTask);
        toDoList.deleteTask("1");
        toDoList.undo(); // Undo delete
        toDoList.undo(); // Undo second add
        actualTasks = toDoList.listTasks();

        assertEquals("After multiple undos, task list should have 1 task", 1, actualTasks.size());
        assertEquals("The remaining task should be the first one added", firstExpectedTask, actualTasks.get(0));
    }
}
