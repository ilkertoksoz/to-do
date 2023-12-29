package com.hepsiemlak.todo.api;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hepsiemlak.todo.dto.TodoDto;
import com.hepsiemlak.todo.service.impl.TodoServiceImpl;
import com.hepsiemlak.todo.util.ApiPaths;

import lombok.RequiredArgsConstructor;

@Validated
@RestController
@RequestMapping(ApiPaths.ToDoCtrl.CTRL)
@RequiredArgsConstructor
public class ToDoControllerImpl implements ToDoController {

	private static final Logger logger = LoggerFactory.getLogger(ToDoControllerImpl.class);

	private final TodoServiceImpl todoServiceImpl;

	@Override
	public ResponseEntity<List<TodoDto>> findAllTodos() {

		List<TodoDto> todos = todoServiceImpl.findAllTodos();

		logger.info("[TodoController] (findAllTodos) Retrieved {} all todo's task successfully.", todos.size());

		return ResponseEntity.ok(todos);
	}

	@Override
	public ResponseEntity<TodoDto> createTodo(TodoDto todoDto) {

		TodoDto createdTodo = todoServiceImpl.createTodo(todoDto);

		return ResponseEntity.status(HttpStatus.CREATED).body(createdTodo);
	}

	@Override
	public ResponseEntity<TodoDto> getTodoById(String id) {

		TodoDto todo = todoServiceImpl.getTodoById(id);

		logger.info("[TodoController] (getTodoById) Retrieved Todo with ID: {} successfully.", id);

		return ResponseEntity.ok(todo);

	}

	@Override
	public ResponseEntity<Boolean> deleteTodoById(String id) {

		logger.info("Todo with ID {} deleted successfully.", id);

		return ResponseEntity.ok(todoServiceImpl.deleteTodo(id));

	}

	@Override
	public ResponseEntity<List<TodoDto>> findAllCompletedTodo() {
		try {
			List<TodoDto> completedTodos = todoServiceImpl.findAllCompletedTodo();
			logger.info("[TodoController] (findAllCompletedTodo) Retrieved all completed Todos successfully.");
			return ResponseEntity.ok(completedTodos);
		} catch (Exception ex) {
			logger.error("[TodoController] (findAllCompletedTodo) An unexpected error occurred.", ex);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
}