package com.hepsiemlak.todo.service;

import com.hepsiemlak.todo.dto.TodoDto;
import com.hepsiemlak.todo.entity.Todo;
import com.hepsiemlak.todo.exception.AllToDoNotFoundException;
import com.hepsiemlak.todo.exception.ToDoAlreadyExistException;
import com.hepsiemlak.todo.exception.TodoNotFoundException;
import com.hepsiemlak.todo.repository.TodoRepository;
import com.hepsiemlak.todo.service.impl.TodoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TodoServiceImplTest {

    @InjectMocks
    private TodoServiceImpl todoServiceImpl;

    @Mock
    private TodoRepository todoRepository;

    private final ModelMapper modelMapper = new ModelMapper();

    @BeforeEach
    void setUp() {

        todoServiceImpl = new TodoServiceImpl(todoRepository, modelMapper);
    }

    @Test
    void testCreateTodo() {

        TodoDto todoDto = new TodoDto();

        todoDto.setTodoTitle("title");
        todoDto.setCompleted(true);

        when(todoRepository.findByTodoTitleAndIsCompleted(todoDto.getTodoTitle(), true)).thenReturn(null);

        Todo savedTodo = new Todo();

        savedTodo.setId("1");
        savedTodo.setTodoTitle("title");
        savedTodo.setCompleted(true);
        savedTodo.setDeleted(true);

        when(todoRepository.save(any(Todo.class))).thenReturn(savedTodo);

        TodoDto result = todoServiceImpl.createTodo(todoDto);

        assertEquals(savedTodo.getTodoTitle(), result.getTodoTitle());
        assertTrue(result.isCompleted());

        when(todoRepository.findByTodoTitleAndIsCompleted(todoDto.getTodoTitle(), true)).thenReturn(savedTodo);

        assertThrows(ToDoAlreadyExistException.class, () -> {
            todoServiceImpl.createTodo(todoDto);
        });
    }

    @Test
    void testFindAllTodos() {

        List<Todo> todos = new ArrayList<>();

        Todo todo = new Todo();

        todo.setCompleted(true);
        todo.setId("1");
        todo.setTodoTitle("title");
        todo.setCompleted(true);
        todo.setDeleted(true);
        todos.add(todo);

        when(todoRepository.findAllByTodos()).thenReturn(todos);

        List<TodoDto> todoDtos = todoServiceImpl.findAllTodos();

        assertEquals(1, todoDtos.size());
        assertEquals("1", todoDtos.get(0).getId());
        assertEquals("title", todoDtos.get(0).getTodoTitle());
        assertTrue(todoDtos.get(0).isCompleted());

        when(todoRepository.findAllByTodos()).thenReturn(new ArrayList<>());

        assertThrows(AllToDoNotFoundException.class, () -> todoServiceImpl.findAllTodos());
    }

    @Test
    void testGetTodoById() {

        Todo todo = new Todo();
        todo.setId("1");
        todo.setTodoTitle("title");
        todo.setCompleted(false);

        when(todoRepository.findById(todo.getId())).thenReturn(Optional.of(todo));

        TodoDto result = todoServiceImpl.getTodoById(todo.getId());

        assertNotNull(result);
        assertEquals(todo.getTodoTitle(), result.getTodoTitle());
        assertEquals(todo.isCompleted(), result.isCompleted());

        when(todoRepository.findById("nonExistingTodoId")).thenReturn(Optional.empty());

        assertThrows(TodoNotFoundException.class, () -> {
            todoServiceImpl.getTodoById("nonExistingTodoId");
        });
    }
}