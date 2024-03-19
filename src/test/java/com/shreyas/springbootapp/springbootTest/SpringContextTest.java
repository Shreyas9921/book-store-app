package com.shreyas.springbootapp.springbootTest;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.apache.commons.lang3.RandomStringUtils;

import com.shreyas.springbootapp.SpringBootstrapLiveTest;
import com.shreyas.springbootstrap.datamodel.BookEntity;

import io.restassured.RestAssured;
import io.restassured.response.Response;

@RunWith(SpringRunner.class)
public class SpringContextTest extends SpringBootstrapLiveTest {
	
	@Test
	public void contextLoads() {
	}
	
	/*
	 * First, we can try to find books 
	 * using variant methods:
	 */
	@Test
	public void WhenGetAllBooks_thenOk() {
		Response response = RestAssured.get(API_ROOT);
		
		assertEquals(HttpStatus.OK.value(), 
				response.getStatusCode());
	}
	
	@Test
	public void whenGetBooksByTitle_thenOk() {
		BookEntity book = createRandomBook();
		createBookAsUri(book);
		
		Response response = RestAssured.get(
				API_ROOT + "/title/" + book.getTitle());
		
		assertEquals(HttpStatus.OK.value(), 
				response.getStatusCode());
	}
	
	@Test
	public void whenGetCreatedBookById_thenOk() {
		BookEntity book = createRandomBook();
		String location = createBookAsUri(book);
		Response response = RestAssured.get(location);
		
		assertEquals(HttpStatus.OK.value(), 
				response.getStatusCode());
		
		assertEquals(book.getTitle(), 
				response.jsonPath().get("Title"));
	}
	
	@Test
	public void whenGetNotExistBookById_thenNotFound() {
	    Response response = RestAssured
	    		.get(API_ROOT + "/" + RandomStringUtils.randomNumeric(4));
	    
	    assertEquals(HttpStatus.NOT_FOUND.value(), 
	    		response.getStatusCode());
	}

	/*Next, we’ll test 
	 * creating a new book:
	 */

	@Test
	public void whenCreateNewBook_thenCreated() {
	    BookEntity book = createRandomBook();
	    Response response = RestAssured.given()
	      .contentType(MediaType.APPLICATION_JSON_VALUE)
	      .body(book)
	      .post(API_ROOT);
	    
	    assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());
	}

	@Test
	public void whenInvalidBook_thenError() {
	    BookEntity book = createRandomBook();
	    book.setAuthor(null);
	    Response response = RestAssured.given()
	      .contentType(MediaType.APPLICATION_JSON_VALUE)
	      .body(book)
	      .post(API_ROOT);
	    
	    assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());
	}

	/*Then we’ll update 
	 * an existing book:
	 */

	@Test
	public void whenUpdateCreatedBook_thenUpdated() {
	    BookEntity book = createRandomBook();
	    String location = createBookAsUri(book);
	    book.setId(Long.parseLong(location.split("api/books/")[1]));
	    book.setAuthor("newAuthor");
	    Response response = RestAssured.given()
	      .contentType(MediaType.APPLICATION_JSON_VALUE)
	      .body(book)
	      .put(location);
	    
	    assertEquals(HttpStatus.OK.value(), response.getStatusCode());

	    response = RestAssured.get(location);
	    
	    assertEquals(HttpStatus.OK.value(), response.getStatusCode());
	    assertEquals("newAuthor", response.jsonPath()
	      .get("author"));
	}
	
	/* And we can 
	 * delete a book:
	 */

	@Test
	public void whenDeleteCreatedBook_thenOk() {
	    BookEntity book = createRandomBook();
	    String location = createBookAsUri(book);
	    Response response = RestAssured.delete(location);
	    
	    assertEquals(HttpStatus.OK.value(), response.getStatusCode());

	    response = RestAssured.get(location);
	    assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode());
	}
	
}
