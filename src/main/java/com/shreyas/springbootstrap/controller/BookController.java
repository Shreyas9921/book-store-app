package com.shreyas.springbootstrap.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.shreyas.springbootstrap.customexception.BookIdMismatchException;
import com.shreyas.springbootstrap.customexception.BookNotFoundException;
import com.shreyas.springbootstrap.datamodel.BookEntity;
import com.shreyas.springbootstrap.repository.BookRepository;

@RestController
@RequestMapping("/api/books")
public class BookController {

	@Autowired
	private BookRepository bookRepository;

	@GetMapping
	public Iterable<BookEntity> findAll() {
		return bookRepository.findAll();
	}
	
	@GetMapping("/title/{bookTitle}")
	public List findByTitle(@PathVariable String bookTitle) {
		return bookRepository.findByTitle(bookTitle);
	}

	@GetMapping("/{id}")
	public BookEntity findOne(@PathVariable Long id) {
		return bookRepository.findById(id).orElseThrow();
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public BookEntity create(@RequestBody BookEntity book) {
		return bookRepository.save(book);
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		bookRepository.findById(id).orElseThrow();
		bookRepository.deleteById(id);
	}
	
	@PutMapping("/{id}")
	public BookEntity updateBook(@RequestBody BookEntity book, @PathVariable Long id) throws BookIdMismatchException {
		if(book.getId() != id) {
			throw new BookIdMismatchException();
		}
		bookRepository.findById(id).orElseThrow();
		return bookRepository.save(book);
	}
}
