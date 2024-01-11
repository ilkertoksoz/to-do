package com.hepsiemlak.todo.controller;

import com.hepsiemlak.todo.api.ToDoControllerImpl;
import com.hepsiemlak.todo.dto.TodoDto;
import com.hepsiemlak.todo.service.impl.TodoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ToDoControllerImplTest {

    @InjectMocks
    private ToDoControllerImpl toDoControllerImpl;

    @Mock
    private TodoServiceImpl todoService;

    @BeforeEach
    void setUp() {

        toDoControllerImpl = new ToDoControllerImpl(todoService);
    }

    @Test
    void testFindAllTodos() {

        TodoDto todoDtoList1 = new TodoDto();

        todoDtoList1.setId("1");
        todoDtoList1.setTodoTitle("title");
        todoDtoList1.setCompleted(true);
        todoDtoList1.setDeleted(true);


        TodoDto todoDtoList2 = new TodoDto();

        todoDtoList2.setId("2");
        todoDtoList2.setTodoTitle("title2");
        todoDtoList2.setCompleted(false);
        todoDtoList2.setDeleted(false);


        List<TodoDto> todoDtoList = Arrays.asList(todoDtoList1, todoDtoList2);

        when(todoService.findAllTodos()).thenReturn(todoDtoList);

        ResponseEntity<List<TodoDto>> responseEntity = toDoControllerImpl.findAllTodos();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(todoDtoList, responseEntity.getBody());

    }

    @Test
    void testCreateTodo() {

        TodoDto todoDto = new TodoDto();

        todoDto.setId("1");
        todoDto.setTodoTitle("todoTitle");

        when(todoService.createTodo(any())).thenReturn(todoDto);

        ResponseEntity<TodoDto> responseEntity = toDoControllerImpl.createTodo(todoDto);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(todoDto, responseEntity.getBody());
    }

    @Test
    void testGetTodoById() {

        TodoDto todoDto = new TodoDto();

        todoDto.setId("1");
        todoDto.setTodoTitle("todoTitle");
        todoDto.setCompleted(true);

        when(todoService.getTodoById(todoDto.getId())).thenReturn(todoDto);

        ResponseEntity<TodoDto> responseEntity = toDoControllerImpl.getTodoById(todoDto.getId());

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(todoDto, responseEntity.getBody());
    }

    @Test
    void testDeleteTodoById() {

        TodoDto todoDeleted = new TodoDto();

        todoDeleted.setId("2");
        todoDeleted.setTodoTitle("title");
        todoDeleted.setCompleted(false);
        todoDeleted.setDeleted(true);

        when(todoService.deleteTodo(todoDeleted.getId())).thenReturn(todoDeleted);

        ResponseEntity<TodoDto> responseEntity = toDoControllerImpl.deleteTodoById(todoDeleted.getId());

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(todoDeleted, responseEntity.getBody());
    }

    @Test
    void testFindAllCompletedTodo() {

        TodoDto todoDtoList1 = new TodoDto();

        todoDtoList1.setId("1");
        todoDtoList1.setTodoTitle("title");
        todoDtoList1.setCompleted(true);
        todoDtoList1.setDeleted(true);


        TodoDto todoDtoList2 = new TodoDto();

        todoDtoList2.setId("2");
        todoDtoList2.setTodoTitle("title2");
        todoDtoList2.setCompleted(false);
        todoDtoList2.setDeleted(false);


        List<TodoDto> todoDtoList = Arrays.asList(todoDtoList1, todoDtoList2);

        when(todoService.findAllCompletedTodo()).thenReturn(todoDtoList);

        ResponseEntity<List<TodoDto>> responseEntity = toDoControllerImpl.findAllCompletedTodo();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(todoDtoList, responseEntity.getBody());
    }
}