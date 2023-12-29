package com.hepsiemlak.todo.repository;

import java.util.List;

import org.springframework.data.couchbase.core.query.Query;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.stereotype.Repository;

import com.hepsiemlak.todo.entity.Todo;

@Repository
public interface TodoRepository extends CouchbaseRepository<Todo, String> {

	@Query("#{#n1ql.selectEntity}")
	List<Todo> findAllByTodos();

	@Query("#{#n1ql.selectEntity} WHERE isCompleted = true")
	List<Todo> findAllCompletedTodos();

    Todo findByTodoTitleAndIsCompleted(String todoTitle, boolean isCompleted);
}