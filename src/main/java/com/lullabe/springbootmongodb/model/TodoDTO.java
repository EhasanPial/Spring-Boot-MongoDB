package com.lullabe.springbootmongodb.model;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "todos")
public class TodoDTO {
	private String id;

	@NotNull(message = "todo cannot be null")
	private String todo;

	@NotNull(message = "description cannot be")
	private String description;

	@NotNull(message = "complete cannot be null")
	private Boolean completed;
	private Date createdAt;
	private Date modifiedAt;
}
