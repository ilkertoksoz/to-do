package com.hepsiemlak.todo.api;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.hepsiemlak.todo.dto.TodoDto;
import com.hepsiemlak.todo.util.ApiPaths;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = ApiPaths.ToDoCtrl.CTRL, description = "TODO APIs")
public interface ToDoController {

	 @ApiOperation(value = "Get all Todos", notes = "Returns a list of all Todos")
	 @ApiResponses({
         @ApiResponse(code = 200, message = "Successfully retrieved Todos"),
         @ApiResponse(code = 404, message = "No To do's has been found"),
         @ApiResponse(code = 500, message = "Internal Server Error")
 })
	@GetMapping
	ResponseEntity<List<TodoDto>> findAllTodos();

	 @ApiOperation(value = "Create a new Todo", notes = "Creates a new Todo item")
	 @ApiResponses({
	     @ApiResponse(code = 201, message = "To do created successfully", response = TodoDto.class),
	     @ApiResponse(code = 400, message = "Bad request"),
	     @ApiResponse(code = 500, message = "Internal Server Error")
	 }) 
	@ApiParam(value = "To do details to be created", required = true) 
	@PostMapping
	ResponseEntity<TodoDto> createTodo(@RequestBody TodoDto todoDto);

	@GetMapping("/{id}")
	public ResponseEntity<TodoDto> getTodoById(@PathVariable String id);

	@DeleteMapping("/{id}")
	public ResponseEntity<Boolean> deleteTodoById(@PathVariable String id);

	@GetMapping("/completed")
	public ResponseEntity<List<TodoDto>> findAllCompletedTodo();

}