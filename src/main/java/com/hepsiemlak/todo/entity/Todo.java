package com.hepsiemlak.todo.entity;

import javax.validation.constraints.NotBlank;

import org.springframework.data.couchbase.core.mapping.Document;

import com.couchbase.client.java.repository.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Todo extends BaseEntity {

	private static final long serialVersionUID = 2699060654290617271L;

	@Id
	private String id;

	@NotBlank
	private String todoTitle;

	private boolean isCompleted;

	private boolean isDeleted;

}