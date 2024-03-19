package com.shreyas.springbootapp;

import org.springframework.http.MediaType;
import org.apache.commons.lang3.RandomStringUtils;


import com.shreyas.springbootstrap.datamodel.BookEntity;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class SpringBootstrapLiveTest {
	protected static final String API_ROOT = "http://Localhost:8081/api/books";
	
	public BookEntity createRandomBook() {
		BookEntity book = new BookEntity();
		book.setTitle(RandomStringUtils.randomAlphabetic(10));
        book.setAuthor(RandomStringUtils.randomAlphabetic(15));

        return book;
	}
	
	public String createBookAsUri(BookEntity book) {
		Response response = RestAssured.given()
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.body(book)
				.post(API_ROOT);
		return  API_ROOT + "/" + response.jsonPath().getByte("id");
	}
}