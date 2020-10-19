package dao;

import java.util.List;

import model.Task;

public interface TaskDAO {
	public void createTask(Task task);
	public void editTask(Task task);
	public void deleteTask(int id);
    public List<Task> listAllTasks();
    public List<Task> listAllAtrasada();
    public Task getTaskById(int id);
}
