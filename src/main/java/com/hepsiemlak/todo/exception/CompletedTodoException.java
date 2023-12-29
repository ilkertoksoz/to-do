package com.hepsiemlak.todo.exception;

public class CompletedTodoException extends RuntimeException {

	private static final long serialVersionUID = 7792088657197411723L;

	public CompletedTodoException() {

		super("Completed To Do Not Found");
	}

}
