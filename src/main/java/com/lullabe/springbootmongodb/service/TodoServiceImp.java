package com.lullabe.springbootmongodb.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lullabe.springbootmongodb.exception.TodoCollectionException;
import com.lullabe.springbootmongodb.model.TodoDTO;
import com.lullabe.springbootmongodb.repository.TodoRepository;

import jakarta.validation.ConstraintViolationException;

@Service
public class TodoServiceImp implements TodoService {

	@Autowired
	private TodoRepository repository;

	@Override
	public void createTodo(TodoDTO dto) throws ConstraintViolationException, TodoCollectionException {
		Optional<TodoDTO> todoOptional = repository.findByTodo(dto.getTodo());
		if (todoOptional.isPresent()) {
			throw new TodoCollectionException(TodoCollectionException.TodoAlreadyExists());
		} else {
			dto.setCreatedAt(new Date(System.currentTimeMillis()));
			repository.save(dto);
		}
	}

	@Override
	public List<TodoDTO> getAllTodos() {
		List<TodoDTO> todos = repository.findAll();
		if (todos.size() > 0) {
			return todos;
		} else {
			return new ArrayList<TodoDTO>();
		}
	}

	@Override
	public TodoDTO getTodoById(String id) throws TodoCollectionException {
		Optional<TodoDTO> optionalToDo = repository.findById(id);
		if (!optionalToDo.isPresent()) {
			throw new TodoCollectionException(TodoCollectionException.NotFoundException(id));
		} else {
			return optionalToDo.get();
		}
	}

	@Override
	public void deleteTodoById(String id) throws TodoCollectionException {
		Optional<TodoDTO> todoOptional = repository.findById(id);

		if (!todoOptional.isPresent()) {
			throw new TodoCollectionException(TodoCollectionException.NotFoundException(id));
		} else {
			repository.deleteById(id);
		}
	}

	@Override
	public void updateTodo(String id, TodoDTO todo) throws TodoCollectionException {
		Optional<TodoDTO> todoOptional = repository.findById(id);
		Optional<TodoDTO> todoWithSameName = repository.findByTodo(todo.getTodo());

		if (!todoOptional.isPresent()) {
			throw new TodoCollectionException(TodoCollectionException.NotFoundException(id));
		} else {

			if (todoWithSameName.isPresent() && !todoWithSameName.get().getId().equals(id)) {
				throw new TodoCollectionException(TodoCollectionException.TodoAlreadyExists());
			}

			TodoDTO todoToUpdate = todoOptional.get();

			todoToUpdate.setTodo(todo.getTodo());
			todoToUpdate.setDescription(todo.getDescription());
			todoToUpdate.setCompleted(todo.getCompleted());
			todoToUpdate.setModifiedAt(new Date(System.currentTimeMillis()));
			repository.save(todo);
		}
	}

}
