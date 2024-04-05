package com.lullabe.springbootmongodb.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.lullabe.springbootmongodb.exception.TodoCollectionException;
import com.lullabe.springbootmongodb.model.TodoDTO;
import com.lullabe.springbootmongodb.service.TodoService;

import jakarta.validation.ConstraintViolationException;

@RestController
public class TodoController {

	@Autowired
	private TodoService todoService;

	@GetMapping("/todos")
	public ResponseEntity<?> getAllTodos() {
		List<TodoDTO> todoList = todoService.getAllTodos();
		return new ResponseEntity<>(todoList, todoList.size() > 0 ? HttpStatus.OK : HttpStatus.NOT_FOUND);
	}

	@PostMapping("/todos")
	public ResponseEntity<?> createTodo(@RequestBody TodoDTO dto) {
		try {
			todoService.createTodo(dto);
			return new ResponseEntity<>(dto, HttpStatus.OK);
		} catch (ConstraintViolationException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
		} catch (TodoCollectionException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
		}
	}

	@GetMapping("/todos/{id}")
	public ResponseEntity<?> getStringTodo(@PathVariable String id) {
		try {
			return new ResponseEntity<>(todoService.getTodoById(id), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping("/todos/{id}")
	public ResponseEntity<?> updateById(@PathVariable String id, @RequestBody TodoDTO todo) {

		try {
			todoService.updateTodo(id, todo);
			return new ResponseEntity<>("Successfully updated with id " + id, HttpStatus.OK);

		} catch (ConstraintViolationException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);

		} catch (TodoCollectionException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("todos/{id}")
	public ResponseEntity<?> deleteById(@PathVariable String id) {
		try {
			todoService.deleteTodoById(id);
			return new ResponseEntity<>("Successfully deleted with id " + id, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

}
