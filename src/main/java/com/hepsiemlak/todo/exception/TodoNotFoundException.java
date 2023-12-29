package com.hepsiemlak.todo.exception;

public class TodoNotFoundException extends RuntimeException {

	
	private static final long serialVersionUID = -4806529089763005295L;
	
	public TodoNotFoundException(String id) {
		
		super("To Do not found with id: " + id);
	}

}
