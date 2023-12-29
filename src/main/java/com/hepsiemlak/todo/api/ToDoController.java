package com.hepsiemlak.todo.api;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.hepsiemlak.todo.dto.TodoDto;

public interface ToDoController {

	@GetMapping
	ResponseEntity<List<TodoDto>> findAllTodos();

	@PostMapping
	ResponseEntity<TodoDto> createTodo(@RequestBody TodoDto todoDto);

	@GetMapping("/{id}")
	public ResponseEntity<TodoDto> getTodoById(@PathVariable String id);

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteTodoById(@PathVariable String id);
	
	@GetMapping("/completed")
    public ResponseEntity<List<TodoDto>> findAllCompletedTodo();

}