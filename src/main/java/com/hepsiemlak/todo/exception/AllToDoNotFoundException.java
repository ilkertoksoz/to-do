package com.hepsiemlak.todo.exception;

public class AllToDoNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -1466829166902509912L;
	
	public AllToDoNotFoundException() {
		
		super("No To Do found");
	}

}
