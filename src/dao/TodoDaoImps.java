package dao;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import service.UserServiceImps;
import pojo.TodoPojo;
import service.TodoServiceImps.TodoFilter;
public class TodoDaoImps implements TodoDao{
	private Map<UUID, List<TodoPojo>> todosMap = new HashMap<>();
    private Map<UUID, List<TodoPojo>> deletedTodosMap = new HashMap<>();


	@Override
	public List<TodoPojo> addTodo(String description, UUID userUuid) {
		List<TodoPojo> todos = todosMap.getOrDefault(userUuid, new ArrayList<>());
        todos.add(new TodoPojo(description, userUuid));
 
        return todosMap.put(userUuid, todos);
	}

	@Override
	public List<TodoPojo> updateTodo(UUID userUuid, int index, String newDescription, boolean completed) {
		List<TodoPojo> todos = todosMap.get(userUuid);
		if (todos != null && index >= 0 && index < todos.size()) {
			TodoPojo todo = todos.get(index);
            todo.setDescription(newDescription);
            todo.setCompleted(completed);
			return todos;
			
		}
		return null;
	}

	@Override
	public boolean deleteTodo(UUID userUuid, int index) {
		List<TodoPojo> todos = todosMap.get(userUuid);
		if (todos != null) {
			TodoPojo todo = todos.remove(index);
            List<TodoPojo> deletedTodos = deletedTodosMap.getOrDefault(userUuid, new ArrayList<>());
            deletedTodos.add(todo);
            deletedTodosMap.put(userUuid, deletedTodos);
            return true;
			
		}
		return false;
	}

	@Override
	public List<TodoPojo> getTodosForUser(UUID userUuid, TodoFilter filter) {
		List<TodoPojo> todos = todosMap.getOrDefault(userUuid, new ArrayList<>());
		if (filter == TodoFilter.ALL) {
            return sortTodosByDate(todos);
        }
        List<TodoPojo> filteredTodos = new ArrayList<>();
        for (TodoPojo todo : todos) {
            if ((filter == TodoFilter.COMPLETED && todo.isCompleted()) ||
                (filter == TodoFilter.NOT_COMPLETED && !todo.isCompleted())) {
                filteredTodos.add(todo);
            }
        }
        return sortTodosByDate(filteredTodos);
	}
	
	@Override
    public List<TodoPojo> sortTodosByDate(List<TodoPojo> todos) {
        todos.sort(Comparator.comparingLong(TodoPojo::getTimestamp));
        return todos;
    }

	@Override
	public List<TodoPojo> getDeletedTodosForUser(UUID userUuid) {
		List<TodoPojo> deletedTodos = deletedTodosMap.getOrDefault(userUuid, new ArrayList<>());
        return sortTodosByDate(deletedTodos);
	}

	@Override
	public List<TodoPojo> getTodosForUserInRange(UUID userUuid, long startTimestamp, long endTimestamp) {
		List<TodoPojo> todos = Optional.ofNullable(todosMap.get(userUuid)).orElse(new ArrayList<>());
        return todos.stream()
                .filter(todo -> todo.getTimestamp() >= startTimestamp && todo.getTimestamp() <= endTimestamp)
                .sorted(Comparator.comparingLong(TodoPojo::getTimestamp))
                .collect(Collectors.toList());
	}

}
