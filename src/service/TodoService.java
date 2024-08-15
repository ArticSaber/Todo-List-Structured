package service;
import java.util.List;
import java.util.UUID;

import pojo.TodoPojo;
import service.TodoServiceImps.TodoFilter;

public interface TodoService {
	List<TodoPojo> addTodoAsync(String description, UUID userUuid);
	List<TodoPojo> updateTodoAsync(UUID userUuid, int index, String newDescription, boolean completed);
	boolean deleteTodoAsync(UUID userUuid, int index);
	List<TodoPojo> getTodosForUserAsync(UUID userUuid, TodoFilter filter);
	List<TodoPojo> getDeletedTodosForUserAsync(UUID userUuid);
	List<TodoPojo> getTodosForUserInRange(UUID userUuid, long startTimestamp, long endTimestamp);
}
