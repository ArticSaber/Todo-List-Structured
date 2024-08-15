package dao;

import java.util.List;
import java.util.UUID;

import pojo.TodoPojo;
import service.TodoServiceImps.TodoFilter;

public interface TodoDao {
	List<TodoPojo> addTodo(String description, UUID userUuid);
	List<TodoPojo> updateTodo(UUID userUuid, int index, String newDescription, boolean completed);
	boolean deleteTodo(UUID userUuid, int index);
	List<TodoPojo> getTodosForUser(UUID userUuid, TodoFilter filter);
	List<TodoPojo> getDeletedTodosForUser(UUID userUuid);
	List<TodoPojo> getTodosForUserInRange(UUID userUuid, long startTimestamp, long endTimestamp);
	List<TodoPojo> sortTodosByDate(List<TodoPojo> todos);

}
