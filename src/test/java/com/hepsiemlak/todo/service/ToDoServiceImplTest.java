package com.hepsiemlak.todo.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.hepsiemlak.todo.dto.TodoDto;
import com.hepsiemlak.todo.entity.Todo;
import com.hepsiemlak.todo.exception.AllToDoNotFoundException;
import com.hepsiemlak.todo.repository.TodoRepository;
import com.hepsiemlak.todo.service.impl.TodoServiceImpl;

@ExtendWith(MockitoExtension.class)
public class TodoServiceImplTest {

    @InjectMocks
    private TodoServiceImpl todoServiceImpl;

    @Mock
    private TodoRepository todoRepository;

    private final ModelMapper modelMapper = new ModelMapper();

    @BeforeEach
    void setUp() {

        todoServiceImpl = new TodoServiceImpl(todoRepository, modelMapper);

        List<Todo> todos = new ArrayList<>();

        Todo todo = new Todo();

        todo.setCompleted(true);
        todo.setId("1");
        todo.setTodoTitle("title");
        todos.add(todo);

        when(todoRepository.findAllByTodos()).thenReturn(todos);
    }

    @Test
    void testFindAllTodos() {

        List<TodoDto> todoDtos = todoServiceImpl.findAllTodos();

        assertEquals(1, todoDtos.size());
        assertEquals("1", todoDtos.get(0).getId());
        assertEquals("title", todoDtos.get(0).getTodoTitle());
        assertTrue(todoDtos.get(0).isCompleted());
    }

    @Test
    void testFindAllTodosWhenEmpty() {

        when(todoRepository.findAllByTodos()).thenReturn(new ArrayList<>());

        assertThrows(AllToDoNotFoundException.class, () -> todoServiceImpl.findAllTodos());
    }
}