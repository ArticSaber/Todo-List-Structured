package service;

import java.util.List;
import java.util.UUID;

import pojo.TodoPojo;
import dao.TodoDao;
import dao.TodoDaoImps;

public class TodoServiceImps implements TodoService{
	
	TodoDao todoDao;
	
	public TodoServiceImps() {
		todoDao = new TodoDaoImps();
	}
	
    public enum TodoFilter {
        ALL, COMPLETED, NOT_COMPLETED, FILTER
    }

	@Override
	public List<TodoPojo> addTodoAsync(String description, UUID userUuid) {
		return todoDao.addTodo(description, userUuid);
	}

	@Override
	public List<TodoPojo> updateTodoAsync(UUID userUuid, int index, String newDescription, boolean completed) {
		return todoDao.updateTodo(userUuid, index, newDescription, completed);
	}

	@Override
	public boolean deleteTodoAsync(UUID userUuid, int index) {
		return todoDao.deleteTodo(userUuid, index);
	}

	@Override
	public List<TodoPojo> getTodosForUserAsync(UUID userUuid, TodoFilter filter) {
		return todoDao.getTodosForUser(userUuid, filter);
	}

	@Override
	public List<TodoPojo> getDeletedTodosForUserAsync(UUID userUuid) {
		return todoDao.getDeletedTodosForUser(userUuid);
	}

	@Override
	public List<TodoPojo> getTodosForUserInRange(UUID userUuid, long startTimestamp, long endTimestamp) {
		return todoDao.getTodosForUserInRange(userUuid, startTimestamp, endTimestamp);
	}

}
