package com.hepsiemlak.todo.util;

public final class ApiPaths {

	private ApiPaths() {}

	private static final String BASE_PATH = "/api";
	public static final class ToDoController {

		private ToDoController() {}
		private static final String CHILD_PATH = "/todo";
		public static final String CTRL = BASE_PATH + CHILD_PATH;
	}
}