package com.hepsiemlak.todo.exception;

public class ToDoAlreadyExistException extends RuntimeException {


	private static final long serialVersionUID = 8193468380855800816L;

	public ToDoAlreadyExistException() {

		super("To Do Already Exist");
	}

}
