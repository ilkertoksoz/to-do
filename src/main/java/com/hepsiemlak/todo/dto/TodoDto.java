package com.hepsiemlak.todo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "ToDo Data Transfer Object")
public class TodoDto {

	@ApiModelProperty(required = true, value = "ID")
	private String id;

	@ApiModelProperty(required = true, value = "todoTitle")
	private String todoTitle;

	@ApiModelProperty(required = true, value = "isCompleted")
	private boolean isCompleted;

}
