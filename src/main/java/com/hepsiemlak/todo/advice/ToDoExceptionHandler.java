package com.hepsiemlak.todo.advice;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.hepsiemlak.todo.exception.AllToDoNotFoundException;
import com.hepsiemlak.todo.exception.ToDoAlreadyExistException;
import com.hepsiemlak.todo.exception.TodoNotFoundException;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@RestController
@Slf4j
public class ToDoExceptionHandler {

	@ExceptionHandler(TodoNotFoundException.class)
	public final ResponseEntity<Object> handleToDoNotFoundException(TodoNotFoundException ex, WebRequest request) {
		log.debug("To Do Not Found By Id --> {}", ex.getMessage(), request);
		ExceptionResponse exceptionResponse = new ExceptionResponse(LocalDateTime.now(), ex.getMessage());
		return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(ToDoAlreadyExistException.class)
	public final ResponseEntity<Object> handleToDoAlreadyExistException(ToDoAlreadyExistException ex,
			WebRequest request) {
		log.debug("To Do Already Exist --> {}", ex.getMessage(), request);
		ExceptionResponse exceptionResponse = new ExceptionResponse(LocalDateTime.now(), ex.getMessage());
		return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(AllToDoNotFoundException.class)
	public final ResponseEntity<Object> handleAllToDoNotFoundException(AllToDoNotFoundException ex,
			WebRequest request) {
		log.debug("To Do Not Found --> {}", ex.getMessage(), request);
		ExceptionResponse exceptionResponse = new ExceptionResponse(LocalDateTime.now(), ex.getMessage());
		return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
	}
}