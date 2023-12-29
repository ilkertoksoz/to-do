package com.hepsiemlak.todo.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.powermock.core.classloader.annotations.PowerMockIgnore;

import com.hepsiemlak.todo.dto.TodoDto;
import com.hepsiemlak.todo.entity.Todo;
import com.hepsiemlak.todo.repository.TodoRepository;
import com.hepsiemlak.todo.service.impl.TodoServiceImpl;

@RunWith(MockitoJUnitRunner.class)
@PowerMockIgnore({ "javax.management.*", "javax.script.*" })
public class ToDoServiceImplTest {

    @InjectMocks
    private TodoServiceImpl todoServiceImpl;

    @Mock
    private TodoRepository todoRepository;

    @Mock
    private ModelMapper modelMapper;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        when(modelMapper.map(any(), eq(TodoDto.class))).thenReturn(new TodoDto());
    }

    @Test
    public void testFindAllTodos() {
    	
        List<Todo> todos = new ArrayList<>();
        Todo todo = new Todo();
        todo.setCompleted(true);
        todo.setId("1");
        todo.setTodoTitle("title");
        todos.add(todo);

        when(todoRepository.findAllByTodos()).thenReturn(todos);

        List<TodoDto> todoDtos = todoServiceImpl.findAllTodos();

        assertEquals(1, todoDtos.size());
        assertEquals("1", todoDtos.get(0).getId());
        assertEquals("title", todoDtos.get(0).getTodoTitle());
        assertEquals(true, todoDtos.get(0).isCompleted());
    }
}