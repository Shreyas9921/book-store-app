package com.shreyas.springbootstrap.customexception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.shreyas.springbootstrap.customexception.BookIdMismatchException;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
	
	public RestExceptionHandler() {
		super();
	}
	
	@ExceptionHandler({BookIdMismatchException.class})
	protected ResponseEntity<Object> handleNotFound(
			Exception ex, WebRequest request) {
		return handleExceptionInternal(ex, "Book not found", new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}
	
	@ExceptionHandler({ BookIdMismatchException.class, 
//					ContraintViolationException.class, 
					DataIntegrityViolationException.class })
	public ResponseEntity<Object> handleBadRequest(
		Exception ex, WebRequest request) {
		return handleExceptionInternal(ex, ex.getLocalizedMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}
}
