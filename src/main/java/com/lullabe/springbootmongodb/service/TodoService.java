package com.lullabe.springbootmongodb.service;

import java.util.List;

import com.lullabe.springbootmongodb.exception.TodoCollectionException;
import com.lullabe.springbootmongodb.model.TodoDTO;

import jakarta.validation.ConstraintViolationException;

public interface TodoService {
	public void createTodo(TodoDTO dto) throws ConstraintViolationException, TodoCollectionException;

	public List<TodoDTO> getAllTodos();

	public TodoDTO getTodoById(String id) throws TodoCollectionException;

	public void deleteTodoById(String id) throws TodoCollectionException;

	public void updateTodo(String id, TodoDTO dto) throws TodoCollectionException;

}
