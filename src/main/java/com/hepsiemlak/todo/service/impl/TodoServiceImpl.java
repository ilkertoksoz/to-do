package com.hepsiemlak.todo.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.hepsiemlak.todo.dto.TodoDto;
import com.hepsiemlak.todo.entity.Todo;
import com.hepsiemlak.todo.exception.AllToDoNotFoundException;
import com.hepsiemlak.todo.exception.CompletedTodoException;
import com.hepsiemlak.todo.exception.ToDoAlreadyExistException;
import com.hepsiemlak.todo.exception.TodoNotFoundException;
import com.hepsiemlak.todo.repository.TodoRepository;
import com.hepsiemlak.todo.service.TodoService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService {

	private static final Logger logger = LoggerFactory.getLogger(TodoServiceImpl.class);

	private final TodoRepository todoRepository;
	private final ModelMapper modelMapper;

	@Override
	public List<TodoDto> findAllTodos() {

		List<Todo> todos = todoRepository.findAllByTodos();

		if (todos.isEmpty()) {

			logger.error("[TodoServiceImpl] (findAllTodos) : There are no to-do's.");

			throw new AllToDoNotFoundException();
		}

		List<TodoDto> taskDtos = todos.stream().map(task -> modelMapper.map(task, TodoDto.class))
				.collect(Collectors.toList());

		logger.info("[TodoServiceImpl] (findAllTodos) Retrieved {} all todo's task successfully.", todos.size());

		return taskDtos;
	}

	// To-DO: @Transactional annotation needed to add however; have no idea for text
	// based DB's
	@Override
	public TodoDto createTodo(TodoDto todoDto) {

		Todo existingTodo = todoRepository.findByTodoTitleAndIsCompleted(todoDto.getTodoTitle(), todoDto.isCompleted());

		if (existingTodo != null) {

			logger.warn(
					"[TodoServiceImpl] (createTodo) To Do already exists with the same content. ID: {}, taskTitle: {}, isCompleted: {}",
					existingTodo.getId(), existingTodo.getTodoTitle(), existingTodo.isCompleted());

			throw new ToDoAlreadyExistException();

		}

		Todo todo = modelMapper.map(todoDto, Todo.class);

		Todo savedTodo = todoRepository.save(todo);

		logger.info("[TodoServiceImpl] (createTodo) New To Do created - id: {}, taskTitle: {}, isCompleted: {}",
				savedTodo.getId(), savedTodo.getTodoTitle(), savedTodo.isCompleted());

		return modelMapper.map(savedTodo, TodoDto.class);
	}

	@Override
	public TodoDto getTodoById(String id) {

		logger.error("[TodoServiceImpl] (getTodoById) To Do not found with id: {}", id);

		Todo task = todoRepository.findById(id).orElseThrow(() -> new TodoNotFoundException(id));

		logger.info("[TodoServiceImpl] (getTodoById) To Do found with ID: {}", id);

		return modelMapper.map(task, TodoDto.class);
	}

	@Override
	public Boolean deleteTodo(String id) {
		return todoRepository.findById(id).map(task -> {

			if (!task.isDeleted()) {

				task.setDeleted(true);

				todoRepository.save(task);

				logger.info("[TodoServiceImpl] (deleteTodo) Todo with ID {} has deleted", id);

				return true;

			} else {

				logger.info("[TodoServiceImpl] (deleteTodo) Todo with ID {} is already deleted", id);

				return false;
			}

		}).orElseThrow(() -> new TodoNotFoundException(id));
	}

	@Override
	public List<TodoDto> findAllCompletedTodo() {

		List<Todo> completedTodoDtos = todoRepository.findAllCompletedTodos();

		if (null == completedTodoDtos) {

			throw new CompletedTodoException();
		}

		List<TodoDto> completedTodoDtosList = completedTodoDtos.stream()
				.map(task -> modelMapper.map(task, TodoDto.class)).collect(Collectors.toList());

		return completedTodoDtosList;
	}
}
