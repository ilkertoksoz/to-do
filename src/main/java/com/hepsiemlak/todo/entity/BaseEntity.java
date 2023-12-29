package com.hepsiemlak.todo.entity;

import java.io.Serializable;
import java.time.Instant;

import org.springframework.data.couchbase.core.mapping.Document;

import com.couchbase.client.java.repository.annotation.Field;

import lombok.Data;

@Data
@Document
public abstract class BaseEntity implements Serializable {

	private static final long serialVersionUID = -215221893977203887L;

	@Field("created_at")
	private Instant createdAt;

	@Field("created_by")
	private String createdBy;

	@Field("updated_at")
	private Instant updatedAt;

	@Field("updated_by")
	private String updatedBy;

	@Field("status")
	private Boolean status;
}
