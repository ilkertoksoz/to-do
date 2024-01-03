package com.hepsiemlak.todo.service;

import java.util.List;

import com.hepsiemlak.todo.dto.TodoDto;

public interface TodoService {

    List<TodoDto> findAllTodos();

    TodoDto createTodo(TodoDto taskDto);

    TodoDto getTodoById(String id);

    TodoDto deleteTodo(String id);

    List<TodoDto> findAllCompletedTodo();
}
