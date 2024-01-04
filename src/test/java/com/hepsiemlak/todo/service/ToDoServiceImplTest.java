package com.hepsiemlak.todo.service;

import com.hepsiemlak.todo.dto.TodoDto;
import com.hepsiemlak.todo.entity.Todo;
import com.hepsiemlak.todo.exception.AllToDoNotFoundException;
import com.hepsiemlak.todo.exception.CompletedTodoException;
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
import static org.mockito.ArgumentMatchers.anyString;
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

        when(todoRepository.findById("nonExistingTodoId")).thenReturn(Optional.empty());

        assertThrows(TodoNotFoundException.class, () -> {
            todoServiceImpl.getTodoById("nonExistingTodoId");
        });
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

        List<Todo> todoList = new ArrayList<>();

        Todo todo = new Todo();

        todo.setCompleted(true);
        todo.setId("1");
        todo.setTodoTitle("title");
        todo.setCompleted(true);
        todo.setDeleted(true);
        todoList.add(todo);

        when(todoRepository.findAllByTodos()).thenReturn(todoList);

        List<TodoDto> todoDtoList = todoServiceImpl.findAllTodos();

        assertEquals(1, todoDtoList.size());
        assertEquals("1", todoDtoList.get(0).getId());
        assertEquals("title", todoDtoList.get(0).getTodoTitle());
        assertTrue(todoDtoList.get(0).isCompleted());

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


    }

    @Test
    void testDeleteTodo() {

        Todo todoNotDeleted = new Todo();

        todoNotDeleted.setId("1");
        todoNotDeleted.setTodoTitle("title");
        todoNotDeleted.setCompleted(false);
        todoNotDeleted.setDeleted(false);

        Todo todoAlreadyDeleted = new Todo();

        todoAlreadyDeleted.setId("2");
        todoAlreadyDeleted.setTodoTitle("title");
        todoAlreadyDeleted.setCompleted(false);
        todoAlreadyDeleted.setDeleted(true);

        when(todoRepository.findById(anyString()))
                .thenReturn(Optional.of(todoNotDeleted))
                .thenReturn(Optional.of(todoAlreadyDeleted));

        when(todoRepository.save(any(Todo.class))).thenReturn(new Todo());

        TodoDto resultNotDeleted = todoServiceImpl.deleteTodo("1");
        TodoDto resultAlreadyDeleted = todoServiceImpl.deleteTodo("2");

        assertTrue(resultNotDeleted.isDeleted());
        assertFalse(resultAlreadyDeleted.isDeleted());
    }

    @Test
    void testFindAllCompletedTodo() {

        List<Todo> todoCompletedList = new ArrayList<>();

        Todo completedTodo = new Todo();

        completedTodo.setCompleted(true);
        completedTodo.setId("1");
        completedTodo.setTodoTitle("title");
        completedTodo.setCompleted(true);
        completedTodo.setDeleted(true);
        todoCompletedList.add(completedTodo);

        when(todoRepository.findAllCompletedTodos())
                .thenReturn(todoCompletedList);

        List<TodoDto> todoDtoList = todoServiceImpl.findAllCompletedTodo();

        assertEquals(1, todoDtoList.size());
        assertEquals("1", todoDtoList.get(0).getId());
        assertEquals("title", todoDtoList.get(0).getTodoTitle());
        assertTrue(todoDtoList.get(0).isCompleted());

        when(todoRepository.findAllCompletedTodos()).thenReturn(null);

        assertThrows(CompletedTodoException.class, () -> todoServiceImpl.findAllCompletedTodo());

    }
}