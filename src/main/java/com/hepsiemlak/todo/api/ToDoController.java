package com.hepsiemlak.todo.api;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.hepsiemlak.todo.config.SwaggerConfig;
import com.hepsiemlak.todo.config.SwaggerErrorResponseCode;
import com.hepsiemlak.todo.dto.TodoDto;
import com.hepsiemlak.todo.util.ApiPaths;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = ApiPaths.ToDoCtrl.CTRL, tags = {SwaggerConfig.HEPSI_EMLAK_TO_DO_SERVICE_TAG})
public interface ToDoController {

	 @ApiOperation(
			 value = "Get all Todos", 
			 nickname = "getAllTodos",
			 notes = "Returns a list of all To do's")
	 
	 @ApiResponses({
         @ApiResponse(code = 200, message = "Successfully retrieved To do's", response = TodoDto.class),
         @ApiResponse(code = 400, message = "Bad Request", response = SwaggerErrorResponseCode.class),
         @ApiResponse(code = 401, message = "Unauthorized", response = SwaggerErrorResponseCode.class),
         @ApiResponse(code = 403, message = "Forbidden", response = SwaggerErrorResponseCode.class),
         @ApiResponse(code = 404, message = "No To do's has been found", response = SwaggerErrorResponseCode.class),
         @ApiResponse(code = 405, message = "Method Not allowed", response = SwaggerErrorResponseCode.class),
         @ApiResponse(code = 409, message = "Conflict", response = SwaggerErrorResponseCode.class),
         @ApiResponse(code = 500, message = "Internal Server Error", response = SwaggerErrorResponseCode.class),
         @ApiResponse(code = 503, message = "Can not reached remote URL", response = SwaggerErrorResponseCode.class)
 })
	@GetMapping
	ResponseEntity<List<TodoDto>> findAllTodos();

	 @ApiOperation(
			 value = "Create a new Todo", 
			 nickname = "createNewTodo",
			 notes = "Creates a new Todo item",
			 response = TodoDto.class)
	 
	 @ApiResponses({
	     @ApiResponse(code = 201, message = "To do created successfully", response = TodoDto.class),
	     @ApiResponse(code = 400, message = "Bad Request", response = SwaggerErrorResponseCode.class),
         @ApiResponse(code = 401, message = "Unauthorized", response = SwaggerErrorResponseCode.class),
         @ApiResponse(code = 403, message = "Forbidden", response = SwaggerErrorResponseCode.class),
         @ApiResponse(code = 404, message = "No To do's has been found", response = SwaggerErrorResponseCode.class),
         @ApiResponse(code = 405, message = "Method Not allowed", response = SwaggerErrorResponseCode.class),
         @ApiResponse(code = 409, message = "Conflict", response = SwaggerErrorResponseCode.class),
         @ApiResponse(code = 500, message = "Internal Server Error", response = SwaggerErrorResponseCode.class),
         @ApiResponse(code = 503, message = "Can not reached remote URL", response = SwaggerErrorResponseCode.class)
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