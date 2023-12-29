package com.hepsiemlak.todo.api;

import java.util.Collections;
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

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;

@Validated
@RestController
@RequestMapping(ApiPaths.ToDoCtrl.CTRL)
@Api(value = ApiPaths.ToDoCtrl.CTRL, description = "TODO APIs")
@RequiredArgsConstructor
public class ToDoControllerImpl implements ToDoController {

	private static final Logger logger = LoggerFactory.getLogger(ToDoControllerImpl.class);

	private final TodoServiceImpl todoServiceImpl;

	@Override
	public ResponseEntity<List<TodoDto>> findAllTodos() {
		try {
			List<TodoDto> todos = todoServiceImpl.findAllTodos();
			logger.info("[TodoController] (findAllTodos) Retrieved {} all todo's task successfully.", todos.size());
			return ResponseEntity.ok(todos);
		} catch (Exception ex) {
			logger.error("[TodoController] (findAllTodos) An unexpected error occurred.", ex);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
		}
	}

	@Override
	public ResponseEntity<TodoDto> createTodo(TodoDto todoDto) {
		try {
			TodoDto createdTodo = todoServiceImpl.createTodo(todoDto);
			return ResponseEntity.status(HttpStatus.CREATED).body(createdTodo);
		} catch (Exception ex) {
			logger.error("[ToDoControllerImpl] (createTodo) An unexpected error occurred.", ex.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@Override
	public ResponseEntity<TodoDto> getTodoById(String id) {
		try {
			TodoDto todo = todoServiceImpl.getTodoById(id);
			logger.info("[TodoController] (getTodoById) Retrieved Todo with ID: {} successfully.", id);
			return ResponseEntity.ok(todo);
		} catch (Exception ex) {
			logger.error("[TodoController] (getTodoById) An unexpected error occurred.", ex);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	@Override
	public ResponseEntity<String> deleteTodoById(String id) {
		boolean isDeleted = todoServiceImpl.deleteTodo(id);
		if (isDeleted) {
			return ResponseEntity.ok("Todo with ID " + id + " is marked as deleted.");
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Todo with ID " + id + " not found.");
		}
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